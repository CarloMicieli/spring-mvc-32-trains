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
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Account;
import com.trenako.entities.Money;
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
	public void shouldReturnOnlyTheDefaultWishListWhenUserHasNoListYet() {
		WishList defaultList = WishList.defaultList(owner);
		when(repo.findByOwner(eq(owner))).thenReturn(null);
		
		Iterable<WishList> results = service.findByOwner(owner);
		
		assertNotNull("Default Wish list was not found", results);
		List<WishList> list = (List<WishList>) results;
		assertEquals(defaultList.getName(), list.get(0).getName());
	}
	
	@Test
	public void shouldReturnTheWishListNamesByOwner() {
		WishList defaultList = WishList.defaultList(owner);
		when(repo.findByOwner(eq(owner)))
			.thenReturn(Arrays.asList(newWishList("First list"), newWishList("Second list")));
		
		Iterable<WishList> results = service.findByOwner(owner);
	
		assertNotNull("Wish lists were not found", results);
		verify(repo, times(1)).findByOwner(eq(owner));
		
		List<WishList> list = (List<WishList>) results;
		assertEquals(3, list.size());
		assertEquals(defaultList.getName(), list.get(2).getName());
	}
	
	@Test
	public void shouldReturnTheWishListNamesByOwnerWhenDefaultListIsUsed() {
		when(repo.findByOwner(eq(owner)))
			.thenReturn(Arrays.asList(WishList.defaultList(owner), newWishList("Second list")));
		
		Iterable<WishList> results = service.findByOwner(owner);
	
		assertNotNull("Wish lists were not found", results);
		verify(repo, times(1)).findByOwner(eq(owner));
		
		List<WishList> list = (List<WishList>) results;
		assertEquals(2, list.size());
	}
	
	@Test
	public void shouldFindWishListsByOwnerLoadingTheLatestItems() {
		int maxNumberOfItems = 10;
		when(repo.findAllByOwner(eq(owner), eq(maxNumberOfItems)))
			.thenReturn(Arrays.asList(new WishList(), new WishList()));
		
		Iterable<WishList> results = service.findAllByOwner(owner, maxNumberOfItems);

		assertNotNull("Wish lists were not found", results);
		verify(repo, times(1)).findAllByOwner(eq(owner), eq(maxNumberOfItems));
	}
	
	@Test
	public void shouldReturnNullWhenTheProvidedOwnerHasNoWishLists() {
		int maxNumberOfItems = 10;
		when(repo.findAllByOwner(eq(owner), eq(maxNumberOfItems))).thenReturn(null);
		
		Iterable<WishList> results = service.findAllByOwner(owner, maxNumberOfItems);
		
		assertNull("Wish lists were found", results);
		verify(repo, times(1)).findAllByOwner(eq(owner), eq(maxNumberOfItems));
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
	public void shouldReturnTheDefaultWishListForTheUserAlsoWhenItWasNotFoundInTheDB() {
		String slug = "list-slug";
		WishList result = service.findBySlugOrDefault(owner, slug);
		assertNull("A wish list was found", result);
		verify(repo, times(1)).findBySlug(eq(slug));
		
		WishList wl2 = service.findBySlugOrDefault(owner, "bob-new-list");
		assertNotNull("A wish list was not found", wl2);
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
		
		service.updateItem(wishList, item, oldPrice());
		
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
	public void shouldChangeWishListsBudget() {
		Money newBudget = new Money(100, "USD");
		WishList wishList = newWishList();
		
		service.changeBudget(wishList, newBudget);
		
		verify(repo, times(1)).changeBudget(eq(wishList), eq(newBudget));
	}
	
	@Test
	public void shouldChangeWishListsName() {
		WishList wishList = newWishList();
		String newName = "New name";
		
		service.changeName(wishList, newName);
		
		verify(repo, times(1)).changeName(eq(wishList), eq(newName));
	}

	@Test
	public void shouldCreateNewWishListsFromOwnerAndName() {
		service.createNew(owner, "My list");
		
		ArgumentCaptor<WishList> arg = ArgumentCaptor.forClass(WishList.class);
		verify(repo, times(1)).save(arg.capture());
		assertEquals("My list", arg.getValue().getName());
		assertEquals("bob-my-list", arg.getValue().getSlug());
		assertEquals("public", arg.getValue().getVisibility());
		assertEquals("bob", arg.getValue().getOwner());
	}
	
	@Test
	public void shouldCreateNewWishLists() {
		WishList wishList = newWishList();
		
		service.createNew(wishList);
		
		verify(repo, times(1)).save(eq(wishList));
	}

	@Test
	public void shouldSaveWishListChanges() {
		WishList wishList = newWishList();
		
		service.saveChanges(wishList);
		
		verify(repo, times(1)).saveChanges(eq(wishList));
	}
	
	@Test
	public void shouldRemoveWishLists() {
		WishList wishList = newWishList();
	
		service.remove(wishList);
		
		verify(repo, times(1)).remove(eq(wishList));
	}
	
	private Money oldPrice() {
		return new Money(100, "USD");
	}
	
	private WishList newWishList() {
		return new WishList(owner, "My list", Visibility.PUBLIC);
	}

	private WishList newWishList(String name) {
		return new WishList(owner, name, Visibility.PUBLIC);
	}
	
	private WishList newWishList(Account owner, String name) {
		return new WishList(owner, name, Visibility.PUBLIC);
	}
	
	private WishListItem newWishListItem() {
		return new WishListItem(rs);
	}
}