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
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;
import com.trenako.repositories.CollectionsRepository;
import com.trenako.repositories.WishListsRepository;
import com.trenako.services.view.ProfileView;
import com.trenako.values.Priority;
import com.trenako.values.Visibility;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProfilesServiceTests {

	@Mock CollectionsRepository collectionsRepo;
	@Mock WishListsRepository wishListsRepo;
	private ProfilesService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new ProfilesServiceImpl(collectionsRepo, wishListsRepo);
	}
	
	@Test
	public void shouldReturnEmptyProfileViews() {
		ProfileView pv = service.findProfileView(owner);
		
		assertNotNull("Profile view is null", pv);
		assertEquals(defaultCollection(), pv.getCollection());
		assertEquals(defaultWishList(), pv.getWishLists());
	}
	
	@Test
	public void shouldFillProfileViewForAccounts() {
		when(collectionsRepo.findByOwner(eq(owner))).thenReturn(collection());
		when(wishListsRepo.findByOwner(eq(owner), eq(true))).thenReturn(wishLists());
		
		ProfileView pv = service.findProfileView(owner);
		
		assertNotNull("Profile view is null", pv);
		assertEquals(collection(), pv.getCollection());
		assertEquals(wishLists(), pv.getWishLists());
	}
	
	Collection defaultCollection() {
		return new Collection(owner);
	}
	
	List<WishList> defaultWishList() {
		return new ArrayList<WishList>();
	}
	
	static final Account owner = new Account.Builder("bob@mail.com").displayName("Bob").build();
	
	private Collection collection() {
		Collection c = new Collection(owner, Visibility.PUBLIC);
		c.getItems().add(new CollectionItem.Builder(rollingStock("1000")).addedAt(date("2012/01/01")).build());
		c.getItems().add(new CollectionItem.Builder(rollingStock("1001")).addedAt(date("2012/02/01")).build());
		c.getItems().add(new CollectionItem.Builder(rollingStock("1002")).addedAt(date("2012/03/01")).build());
		c.getItems().add(new CollectionItem.Builder(rollingStock("1003")).addedAt(date("2012/04/01")).build());
		c.getItems().add(new CollectionItem.Builder(rollingStock("1004")).addedAt(date("2012/05/01")).build());
		return c;
	}
	
	private List<WishList> wishLists() {
		List<WishList> lists = new ArrayList<WishList>();
		
		WishList list1 = new WishList(owner, "My first list", Visibility.PUBLIC);
		list1.getItems().add(new WishListItem(rollingStock("1000"), "notes", Priority.LOW, date("2012/01/01")));
		list1.getItems().add(new WishListItem(rollingStock("1001"), "notes", Priority.LOW, date("2012/02/01")));
		list1.getItems().add(new WishListItem(rollingStock("1002"), "notes", Priority.LOW, date("2012/03/02")));
		lists.add(list1);
		
		WishList list2 = new WishList(owner, "My second list", Visibility.PUBLIC);
		list2.getItems().add(new WishListItem(rollingStock("1003"), "notes", Priority.LOW, date("2012/02/01")));
		list2.getItems().add(new WishListItem(rollingStock("1004"), "notes", Priority.LOW, date("2012/03/01")));
		lists.add(list2);
		
		return lists;
	}
	
	private RollingStock rollingStock(String itemNumber) {
		return new RollingStock.Builder(acme(), itemNumber)
			.railway(fs())
			.scale(scaleH0())
			.build();
	}
}
