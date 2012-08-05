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

import java.util.Arrays;

import org.junit.Test;

import com.trenako.values.Visibility;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionTests {

	private Account owner = new Account.Builder("mail@mail.com")
		.displayName("bob")
		.build();
	private Account alice = new Account.Builder("mail@mail.com")
		.displayName("alice")
		.build();
	
	@Test
	public void shouldCreatePublicCollections() {
		Collection coll = new Collection(owner);
		assertEquals(true, coll.isVisible());
	}
	
	@Test
	public void shouldFillTheNameFromOwner() {
		Collection coll = new Collection(owner);
		assertEquals("bob", coll.getOwner().getSlug());
	}
	
	@Test
	public void shouldCheckWhetherTwoCollectionsAreEquals() {
		Collection x = new Collection(owner, Visibility.PRIVATE);
		Collection y = new Collection(owner, Visibility.PRIVATE);
		assertTrue(x.equals(x));
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoCollectionsAreDifferents() {
		Collection x = new Collection(owner, Visibility.PRIVATE);
		Collection y = new Collection(alice, Visibility.PRIVATE);
		assertFalse(x.equals(y));
	}
	
	@Test
	public void shouldProduceStringRepresentations() {
		Collection x = new Collection(owner, Visibility.PRIVATE);
		assertEquals("collection{owner: bob, visibility: private, item(s): 0}", x.toString());
		
		Collection y = new Collection(owner, Visibility.PRIVATE);
		y.setItems(Arrays.asList(new CollectionItem(), new CollectionItem()));
		assertEquals("collection{owner: bob, visibility: private, item(s): 2}", y.toString());
	}
}