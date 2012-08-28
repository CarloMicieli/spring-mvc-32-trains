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

	private Account georgeStephenson() {
		return new Account.Builder("george@mail.com")		
			.displayName("George Stephenson")
			.build();
	}
	
	private Account theodoreJudah() {
		return new Account.Builder("theodore@mail.com")		
			.displayName("Theodore Judah")
			.build();
	}
	
	@Test
	public void shouldCreateCollectionsPublicByDefault() {
		Collection coll = new Collection(georgeStephenson());
		assertEquals(Visibility.PUBLIC.label(), coll.getVisibility());
	}
	
	@Test
	public void shouldFillCollectionNames() {
		Collection coll = new Collection(georgeStephenson());
		assertEquals("george-stephenson", coll.getOwner());
	}
	
	@Test
	public void shouldCheckWhetherTwoCollectionsAreEquals() {
		Collection x = new Collection(georgeStephenson(), Visibility.PRIVATE);
		Collection y = new Collection(georgeStephenson(), Visibility.PRIVATE);
		assertTrue(x.equals(x));
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoCollectionsAreDifferents() {
		Collection x = new Collection(georgeStephenson(), Visibility.PRIVATE);
		Collection y = new Collection(theodoreJudah(), Visibility.PRIVATE);
		assertFalse(x.equals(y));
	}
	
	@Test
	public void shouldProduceStringRepresentations() {
		Collection x = new Collection(georgeStephenson(), Visibility.PRIVATE);
		assertEquals("collection{owner: george-stephenson, visibility: private, item(s): 0}", x.toString());
		
		Collection y = new Collection(georgeStephenson(), Visibility.PRIVATE);
		y.setItems(Arrays.asList(new CollectionItem(), new CollectionItem()));
		assertEquals("collection{owner: george-stephenson, visibility: private, item(s): 2}", y.toString());
	}
}