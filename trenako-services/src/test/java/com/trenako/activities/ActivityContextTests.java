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
package com.trenako.activities;

import static org.junit.Assert.*;

import org.junit.Test;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.WishList;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ActivityContextTests {

	@Test
	public void shouldProduceStringRepresentationsForActivityContexts() {
		ActivityContext x = new ActivityContext("type", "name", "description");
		assertEquals("activityContext{type: type, name: name, description: description}", x.toString());
	}
	
	@Test
	public void shouldCheckWhetherTwoActivityContextsAreEquals() {
		ActivityContext x = new ActivityContext("type", "name", "description");
		ActivityContext y = new ActivityContext("type", "name", "description");
		assertTrue("Activity contexts are different", x.equals(x));
		assertTrue("Activity contexts are different", x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoActivityContextsAreDifferent() {
		ActivityContext x = new ActivityContext("type", "name", "description1");
		ActivityContext y = new ActivityContext("type", "name", "description2");
		assertFalse("Activity contexts are equals", x.equals(y));
		
		ActivityContext z = new ActivityContext("type2", "name", "description1");
		assertFalse("Activity contexts are equals", x.equals(z));
		
		ActivityContext t = new ActivityContext("type", "name2", "description1");
		assertFalse("Activity contexts are equals", x.equals(t));
	}
	
	@Test
	public void shouldCreateActivityContextsForCollections() {
		ActivityContext ctx = ActivityContext.collectionContext(collection());
		assertEquals(ActivityContext.COLLECTION, ctx.getContextType());
		assertEquals(collection().getSlug(), ctx.getName());
		assertEquals("", ctx.getDescription());
	}

	@Test
	public void shouldCreateActivityContextsForWishLists() {
		ActivityContext ctx = ActivityContext.wishListContext(wishList());
		assertEquals(ActivityContext.WISH_LIST, ctx.getContextType());
		assertEquals(wishList().getSlug(), ctx.getName());
		assertEquals(wishList().getName(), ctx.getDescription());
	}
	
	private WishList wishList() {
		return new WishList(georgeStephenson(), "My list");
	}
	
	private Collection collection() {
		return new Collection(georgeStephenson());
	}

	private Account georgeStephenson() {
		return new Account.Builder("george@mail.com")		
			.displayName("George Stephenson")
			.build();
	}
}
