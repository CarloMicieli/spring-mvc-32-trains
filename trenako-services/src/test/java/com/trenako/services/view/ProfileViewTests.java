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
package com.trenako.services.view;

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.trenako.entities.Account;
import com.trenako.entities.CategoriesCount;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;
import com.trenako.values.Priority;
import com.trenako.values.Visibility;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ProfileViewTests {
	
	@Test
	public void shouldCreateNewProfileViewsFromCollectionAndWishLists() {
		ProfileView pv = new ProfileView(collection(), wishLists(), options());
		assertEquals(collection(), pv.getCollection());
		assertEquals(wishLists(), pv.getWishLists());
	}
	
	@Test
	public void shouldFillCategoriesCountWhenTheCollectionIsNull() {
		ProfileView pv = new ProfileView(null, wishLists(), options());
		assertNull(pv.getCollection());
		assertEquals(new CategoriesCount(), pv.getCategoriesCount());
	}
	
	@Test
	public void shouldExtractWishListNames() {
		ProfileView pv = new ProfileView(collection(), wishLists(), options());
		Map<String, String> names = pv.getWishListNames();
		assertEquals("{bob-my-first-list=My first list, bob-my-second-list=My second list}", names.toString());
	}
	
	@Test
	public void shouldExtractRollingStocksFromCollection() {
		ProfileView pv = new ProfileView(collection(), wishLists(), options());
		
		List<ItemView> items = (List<ItemView>) pv.getCollectionItems();
		
		assertEquals(2, items.size());
		assertEquals("itemView{listSlug: bob, listName: collection, itemSlug: acme-1004, itemName: ACME 1004}", 
				items.get(0).toString());
		assertEquals("itemView{listSlug: bob, listName: collection, itemSlug: acme-1003, itemName: ACME 1003}", 
				items.get(1).toString());
	}
	
	@Test
	public void shouldExtractRollingStocksFromWishlists() {
		ProfileView pv = new ProfileView(collection(), wishLists(), options());
		
		List<ItemView> items = (List<ItemView>) pv.getWishListItems();
		
		assertEquals(2, items.size());
		assertEquals("itemView{listSlug: bob-my-first-list, listName: My first list, itemSlug: acme-1002, itemName: ACME 1002}", 
				items.get(0).toString());
		assertEquals("itemView{listSlug: bob-my-second-list, listName: My second list, itemSlug: acme-1004, itemName: ACME 1004}", 
				items.get(1).toString());
	}
	
	private ProfileOptions options() {
		return new ProfileOptions(2, 2);
	}
	
	private RollingStock rollingStock(String itemNumber) {
		return new RollingStock.Builder(acme(), itemNumber)
			.railway(fs())
			.scale(scaleH0())
			.build();
	}
	
	private Collection collection() {
		Collection c = new Collection(bob, Visibility.PUBLIC);
		c.getItems().add(new CollectionItem.Builder(rollingStock("1000")).addedAt(date("2012/01/01")).build());
		c.getItems().add(new CollectionItem.Builder(rollingStock("1001")).addedAt(date("2012/02/01")).build());
		c.getItems().add(new CollectionItem.Builder(rollingStock("1002")).addedAt(date("2012/03/01")).build());
		c.getItems().add(new CollectionItem.Builder(rollingStock("1003")).addedAt(date("2012/04/01")).build());
		c.getItems().add(new CollectionItem.Builder(rollingStock("1004")).addedAt(date("2012/05/01")).build());
		return c;
	}
	
	private List<WishList> wishLists() {
		List<WishList> lists = new ArrayList<WishList>();
		
		WishList list1 = new WishList(bob, "My first list", Visibility.PUBLIC);
		list1.getItems().add(new WishListItem(rollingStock("1000"), "notes", Priority.LOW, date("2012/01/01")));
		list1.getItems().add(new WishListItem(rollingStock("1001"), "notes", Priority.LOW, date("2012/02/01")));
		list1.getItems().add(new WishListItem(rollingStock("1002"), "notes", Priority.LOW, date("2012/03/02")));
		lists.add(list1);
		
		WishList list2 = new WishList(bob, "My second list", Visibility.PUBLIC);
		list2.getItems().add(new WishListItem(rollingStock("1003"), "notes", Priority.LOW, date("2012/02/01")));
		list2.getItems().add(new WishListItem(rollingStock("1004"), "notes", Priority.LOW, date("2012/03/01")));
		lists.add(list2);
		
		return lists;
	}
	
	static final Account bob = new Account.Builder("bob@mail.com").displayName("Bob").build();
}
