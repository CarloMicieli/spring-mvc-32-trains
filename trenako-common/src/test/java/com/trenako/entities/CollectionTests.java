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
import java.util.Date;
import java.util.SortedMap;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionTests {

	@Test
	public void shouldCreateNewCollectionItems() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		
		Date now = new Date();
		CollectionItem item = new CollectionItem.Builder(rs)
			.addedAt(now)
			.condition(Condition.NEW)
			.notes("Notes text")
			.price(100)
			.build();
		
		assertEquals(rs, item.getRollingStock());
		assertEquals("new", item.getCondition());
		assertEquals(now, item.getAddedAt());
		assertEquals("Notes text", item.getNotes());
		assertEquals(100, item.getPrice());
	}
	
	@Test
	public void shouldCreatePublicCollections() {
		Account owner = new Account.Builder("mail@mail.com").build();
		Collection coll = new Collection(owner);
		assertEquals(true, coll.isVisible());
	}
	
	@Test
	public void shouldFillTheNameFromOwner() {
		Account owner = new Account.Builder("mail@mail.com")
			.displayName("bob")
			.build();
		Collection coll = new Collection(owner);
		assertEquals("bob", coll.getOwnerName());
	}
	
	@Test
	public void shouldAddNewRollingStocks() {
		Account owner = new Account.Builder("mail@mail.com").build();
		Collection coll = new Collection(owner);
	
		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.build();
		CollectionItem item = new CollectionItem.Builder(rs)
			.addedAt(new Date())
			.condition(Condition.NEW)
			.build();
	
		coll.addItem(item);
	
		assertEquals(1, coll.getItems().size());
	}
	
	@Test
	public void shouldReturnReadOnlyMapWithAllCategories() {
		Account owner = new Account.Builder("mail@mail.com").build();
		Collection coll = new Collection(owner);
		
		SortedMap<String, Integer> categoriesCount = coll.getCategories();

		assertNotNull(categoriesCount);
		assertEquals(0, (int) categoriesCount.get("electric-locomotives"));
		assertEquals(0, (int) categoriesCount.get("diesel-locomotives"));
		assertEquals(0, (int) categoriesCount.get("steam-locomotives"));
		assertEquals(0, (int) categoriesCount.get("train-sets"));
		assertEquals(0, (int) categoriesCount.get("railcars"));
		assertEquals(0, (int) categoriesCount.get("passenger-cars"));
		assertEquals(0, (int) categoriesCount.get("freight-cars"));
	}
	
	@Test
	public void shouldUpdateTheCategoryCounters() {
		Account owner = new Account.Builder("mail@mail.com").build();
		Collection coll = new Collection(owner);

		coll.addItem(new CollectionItem.Builder(new RollingStock.Builder("ACME", "123456")
				.category("electric-locomotives")
				.build())
			.addedAt(new Date())
			.condition(Condition.NEW)
			.build());
		coll.addItem(new CollectionItem.Builder(new RollingStock.Builder("ACME", "123457")
				.category("diesel-locomotives")
				.build())
			.addedAt(new Date())
			.condition(Condition.NEW)
			.build());
		coll.addItem(new CollectionItem.Builder(new RollingStock.Builder("ACME", "123458")
				.category("electric-locomotives")
				.build())
			.addedAt(new Date())
			.condition(Condition.NEW)
			.build());
			
		assertEquals(3, coll.getItems().size());
		assertEquals(2, coll.count("electric-locomotives"));
		assertEquals(1, coll.count("diesel-locomotives"));
	}
}
