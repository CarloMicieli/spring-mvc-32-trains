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

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.Map;

import org.junit.Test;

import com.trenako.values.Condition;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionTests {

	private Account owner = new Account.Builder("mail@mail.com")
		.displayName("bob")
		.build();
	private RollingStock rs = new RollingStock.Builder(acme(), "123456")
		.scale(scaleH0())
		.railway(db())
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
	public void shouldAddNewRollingStocks() {
		Collection coll = new Collection(owner);
	
		CollectionItem item = new CollectionItem.Builder(rs)
			.addedAt(new Date())
			.condition(Condition.NEW)
			.build();
	
		coll.addItem("electric-locomotives", item);
	
		assertEquals(1, coll.getItems().size());
	}
	
	@Test
	public void shouldReturnReadOnlyMapWithAllCategories() {
		Collection coll = new Collection(owner);
		
		Map<String, Integer> categoriesCount = coll.getCategories();

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
		Collection coll = new Collection(owner);

		coll.addItem("electric-locomotives", new CollectionItem.Builder(new RollingStock.Builder(acme(), "123456")
				.category("electric-locomotives")
				.scale(scaleH0())
				.railway(db())
				.build())
			.addedAt(new Date())
			.condition(Condition.NEW)
			.build());
		coll.addItem("diesel-locomotives", new CollectionItem.Builder(new RollingStock.Builder(acme(), "123457")
				.category("diesel-locomotives")
				.scale(scaleH0())
				.railway(db())
				.build())
			.addedAt(new Date())
			.condition(Condition.NEW)
			.build());
		coll.addItem("electric-locomotives", new CollectionItem.Builder(new RollingStock.Builder(acme(), "123458")
				.category("electric-locomotives")
				.scale(scaleH0())
				.railway(db())
				.build())
			.addedAt(new Date())
			.condition(Condition.NEW)
			.build());
			
		assertEquals(3, coll.getItems().size());
		assertEquals(2, coll.count("electric-locomotives"));
		assertEquals(1, coll.count("diesel-locomotives"));
	}
}