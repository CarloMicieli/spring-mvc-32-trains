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
		.displayName("bob")
		.build();
	
	@Test
	public void shouldProduceSlugFromWishLists() {
		WishList x = new WishList(bob, "My first list", Visibility.PUBLIC);
		assertEquals("bob-my-first-list", x.getSlug());
	}
	
	@Test
	public void shouldProduceStringRepresentationFromWishLists() {
		WishList x = new WishList(bob, "My first list", Visibility.PUBLIC);
		assertEquals("wishList{owner: {label=bob, slug=bob}, name: My first list, visibility: public}", x.toString());
	}
	
	@Test
	public void shouldCheckWheterTwoWishListsAreEquals() {
		WishList x = new WishList(bob, "My first list", Visibility.PUBLIC);
		WishList y = new WishList(bob, "My first list", Visibility.PUBLIC);
		assertTrue(x.equals(x));
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldCheckWheterTwoWishListsAreDifferent() {
		WishList x = new WishList(bob, "My first list", Visibility.PUBLIC);
		WishList y = new WishList(bob, "My second list", Visibility.PRIVATE);
		assertFalse(x.equals(y));
	}
}
