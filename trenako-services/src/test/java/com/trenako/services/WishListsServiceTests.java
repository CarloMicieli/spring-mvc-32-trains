/*
 * Copyright 2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.services;

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Account;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;
import com.trenako.repositories.WishListsRepository;
import com.trenako.values.Visibility;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class WishListsServiceTests {

	private Account owner = new Account.Builder("mail@mail.com")
		.displayName("Bob")
		.build();
	private RollingStock rs = new RollingStock.Builder(acme(), "123456")
		.railway(fs())
		.scale(scaleH0())
		.build();

	private @Mock WishListsRepository repo;
	private WishListsService service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new WishListsServiceImpl(repo);
	}

	@Test
	public void shouldFindWishListsByOwner() {
		when(repo.findByOwner(eq(owner), eq(false)))
			.thenReturn(Arrays.asList(new WishList(), new WishList()));
		
		Iterable<WishList> results = service.findByOwner(owner);

		assertNotNull("Wish lists were not found", results);
		verify(repo, times(1)).findByOwner(eq(owner), eq(false));
	}
	
	@Test
	public void shouldReturnNullWhenTheProvidedOwnerHasNoWishLists() {
		when(repo.findByOwner(eq(owner), eq(false))).thenReturn(null);
		
		Iterable<WishList> results = service.findByOwner(owner);

		assertNull("Wish lists were found", results);
		verify(repo, times(1)).findByOwner(eq(owner), eq(false));
	}
	
	@Test
	public void shouldFindWishListsBySlug() {
		String slug = "list-slug";
		
		when(repo.findBySlug(eq(slug)))
			.thenReturn(new WishList());
		
		WishList result = service.findBySlug(slug);
	
		assertNotNull("A wish list was not found", result);
		verify(repo, times(1)).findBySlug(eq(slug));
	}

	@Test
	public void shouldReturnNullIfNoWishListIsFoundWithTheProvidedSlug() {
		String slug = "list-slug";
		when(repo.findBySlug(eq(slug))).thenReturn(null);
		
		WishList result = service.findBySlug(slug);
	
		assertNull("A wish list was found", result);
		verify(repo, times(1)).findBySlug(eq(slug));
	}
	
	@Test
	public void shouldFindDefaultWishListByOwner() {
		when(repo.findDefaultListByOwner(eq(owner)))
			.thenReturn(new WishList());
		
		WishList result = service.findDefaultListByOwner(owner);
		
		assertNotNull("A default wish list was not found", result);
		verify(repo, times(1)).findDefaultListByOwner(eq(owner));
	}
	
	@Test
	public void shouldReturnNullIfDefaultWishListIsFoundForTheProvidedOwner() {
		when(repo.findDefaultListByOwner(eq(owner)))
			.thenReturn(null);
		
		WishList result = service.findDefaultListByOwner(owner);
		
		assertNull("A default wish list was found", result);
		verify(repo, times(1)).findDefaultListByOwner(eq(owner));
	}

	@Test
	public void shouldCheckWhetherWishListContainsTheProvidedRollingStock() {
		WishList wishList = newWishList();

		when(repo.containsRollingStock(eq(wishList), eq(rs))).thenReturn(true);

		boolean rv = service.containsRollingStock(wishList, rs);

		assertTrue(rv);
		verify(repo, times(1)).containsRollingStock(eq(wishList), eq(rs));
	}
	
	@Test
	public void shouldCheckWhetherWishListDontContainsTheProvidedRollingStock() {
		WishList wishList = newWishList();

		when(repo.containsRollingStock(eq(wishList), eq(rs))).thenReturn(false);
		
		boolean rv = service.containsRollingStock(wishList, rs);
		
		assertFalse(rv);
		verify(repo, times(1)).containsRollingStock(eq(wishList), eq(rs));
	}
	
	@Test
	public void shouldAddNewItemsToWishLists() {
		WishList wishList = newWishList();
		WishListItem newItem = newWishListItem();
		
		service.addItem(wishList, newItem);
		
		verify(repo, times(1)).addItem(eq(wishList), eq(newItem));
	}
	
	@Test
	public void shouldUpdateItemsOfWishLists() {
		WishList wishList = newWishList();
		WishListItem item = newWishListItem();
		
		service.updateItem(wishList, item);
		
		verify(repo, times(1)).updateItem(eq(wishList), eq(item));
	}
	
	@Test
	public void shouldRemoveItemsFromWishLists() {
		WishList wishList = newWishList();
		WishListItem item = newWishListItem();
		
		service.removeItem(wishList, item);
		
		verify(repo, times(1)).removeItem(eq(wishList), eq(item));
	}
	
	@Test
	public void shouldMoveItemsBetweenTwoWishLists() {
		WishList source = newWishList();
		WishList target = newWishList(owner, "My other list");
		WishListItem item = newWishListItem();
		
		service.moveItem(source, target, item);
		
		verify(repo, times(1)).addItem(eq(target), eq(item));
		verify(repo, times(1)).removeItem(eq(source), eq(item));
	}
	
	@Test
	public void shouldAbortMovingItemsWhenSourceAndTargetListsAreEquals() {
		WishList source = newWishList();
		WishList target = newWishList();
		WishListItem item = newWishListItem();
		
		service.moveItem(source, target, item);
		
		// no further repository interactions
		verify(repo, times(0)).addItem(eq(target), eq(item));
		verify(repo, times(0)).removeItem(eq(source), eq(item));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowAnExceptionMovingATheItemBetweenListsWithDifferentOwners() {
		WishListItem item = newWishListItem();
		WishList source = newWishList();

		Account differentOwner = new Account.Builder("mail@mail.com")
			.displayName("Alice")
			.build();
		WishList target = newWishList(differentOwner, "My other list");
		
		service.moveItem(source, target, item);
	}

	@Test
	public void shouldChangeWishListsVisibility() {
		WishList wishList = newWishList();
		
		service.changeVisibility(wishList, Visibility.PRIVATE);
		
		verify(repo, times(1)).changeVisibility(eq(wishList), eq(Visibility.PRIVATE));
	}
	
	@Test
	public void shouldSetAWishListAsTheDefaultOne() {
		WishList wishList = newWishList();
		
		service.setAsDefault(owner, wishList);
		
		verify(repo, times(1)).resetDefault(eq(owner));
		verify(repo, times(1)).changeDefault(eq(wishList), eq(true));
	}

	@Test
	public void shouldCreateNewWishLists() {
		service.createNew(owner, "My list");
		
		ArgumentCaptor<WishList> arg = ArgumentCaptor.forClass(WishList.class);
		verify(repo, times(1)).save(arg.capture());
		assertEquals("My list", arg.getValue().getName());
		assertEquals("bob-my-list", arg.getValue().getSlug());
		assertEquals("public", arg.getValue().getVisibility());
		assertEquals("{label=Bob, slug=bob}", arg.getValue().getOwner().toString());
	}
	
	@Test
	public void shouldRemoveWishLists() {
		WishList wishList = newWishList();
	
		service.remove(wishList);
		
		verify(repo, times(1)).remove(eq(wishList));
	}
	
	private WishList newWishList() {
		return new WishList(owner, "My list", Visibility.PUBLIC);
	}
	
	private WishList newWishList(Account owner, String name) {
		return new WishList(owner, name, Visibility.PUBLIC);
	}
	
	private WishListItem newWishListItem() {
		return new WishListItem(rs);
	}
}