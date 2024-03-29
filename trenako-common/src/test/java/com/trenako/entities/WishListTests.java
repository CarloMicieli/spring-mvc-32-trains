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
package com.trenako.entities;

import static org.junit.Assert.*;

import org.junit.Test;

import com.trenako.values.Visibility;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class WishListTests {
	
	private Account bob = new Account.Builder("bob@mail.com")
		.displayName("Bob")
		.build();
	private Account alice = new Account.Builder("bob@mail.com")
		.displayName("Alice")
		.build();
		
	@Test
	public void shouldProduceSlugFromWishLists() {
		WishList x = new WishList(bob, "My first list", Visibility.PUBLIC);
		assertEquals("bob-my-first-list", x.getSlug());
	}
	
	@Test
	public void shouldProduceStringRepresentationFromWishLists() {
		WishList x = new WishList(bob, "My first list", Visibility.PUBLIC);
		assertEquals("wishList{slug: bob-my-first-list, owner: bob, name: My first list, visibility: public}", x.toString());
	}
	
	@Test
	public void shouldCheckWhetherTwoWishListsAreEquals() {
		WishList x = new WishList(bob, "My first list", Visibility.PUBLIC);
		WishList y = new WishList(bob, "My first list", Visibility.PUBLIC);
		assertTrue(x.equals(x));
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoWishListsAreDifferent() {
		WishList x = new WishList(bob, "My first list", Visibility.PUBLIC);
		WishList y = new WishList(bob, "My second list", Visibility.PRIVATE);
		assertFalse(x.equals(y));
	}
	
	@Test
	public void shouldCreatePublicWishListsByDefault() {
		WishList wl = new WishList(bob, "My first list");
		assertEquals(Visibility.PUBLIC, wl.getVisibilityValue());
	}
	
	@Test
	public void shouldCreateTheDefaultWishListForTheUser() {
		WishList wl = WishList.defaultList(bob);
		assertEquals(Visibility.PUBLIC.label(), wl.getVisibility());
		assertEquals(WishList.DEFAULT_LIST_NAME, wl.getName());
		assertEquals("bob-new-list", wl.getSlug());
	}
	
	@Test
	public void shouldGetTheDefaultWishListSlug() {
		String slug = WishList.defaultListSlug(bob);
		assertEquals("bob-new-list", slug);
	}
	
	@Test
	public void shouldCheckWishListOwner() {
		WishList wl = new WishList(bob, "My first list");
		assertTrue("User is not the wish list owner", wl.isOwnedBy(bob));
		assertFalse("User is the wish list owner", wl.isOwnedBy(alice));
	}
	
	@Test
	public void shouldImplementComparableInterface() {
		WishList a = new WishList(bob, "My first list", Visibility.PUBLIC);
		WishList b = new WishList(bob, "My first list", Visibility.PRIVATE);
		assertEquals(0, a.compareTo(b));
		
		WishList c = new WishList(bob, "My second list", Visibility.PRIVATE);
		assertTrue(a.compareTo(c) < 0);
		assertTrue(c.compareTo(a) > 0);
		
		WishList d = new WishList(bob, "My awesome list", Visibility.PRIVATE);
		assertTrue(a.compareTo(d) > 0);
		assertTrue(d.compareTo(a) < 0);
	}
}
