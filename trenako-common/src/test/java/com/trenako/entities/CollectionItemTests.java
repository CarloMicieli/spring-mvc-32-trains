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

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.trenako.values.Condition;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionItemTests {
	
	private RollingStock rollingStock() {
		return new RollingStock.Builder(acme(), "123456")
			.railway(db())
			.scale(scaleN())
			.category("electric-locomotives")
			.build();
	}
	
	private Money USD100() {
		return new Money(BigDecimal.valueOf(100), "USD");
	}
	
	@Test
	public void shouldCreateNewCollectionItems() {
		Date now = new Date();
		CollectionItem item = new CollectionItem(rollingStock(), now, "Notes text", USD100(), Condition.NEW);
		
		assertEquals("{slug: acme-123456, label: ACME 123456}", item.getRollingStock().toCompleteString());
		assertEquals("new", item.getCondition());
		assertEquals(now, item.getAddedAt());
		assertEquals("Notes text", item.getNotes());
		assertEquals("$100.00", item.getPrice().toString());
	}
	
	@Test
	public void shouldFillTheItemId() {
		CollectionItem item = new CollectionItem(rollingStock(), date("2012/01/01"));		
		assertEquals("acme-123456-2012-01-01", item.getItemId());
	}

	@Test
	public void shouldFillTheItemCategoryUsingTheRollingStockValue() {
		CollectionItem item = new CollectionItem(rollingStock(), date("2012/01/01"));		
		assertEquals("electric-locomotives", item.getCategory());
	}
	
	@Test
	public void shouldCheckWhetherTwoItemsAreEquals() {
		Date now = new Date();
		CollectionItem x = new CollectionItem(rollingStock(), now);
		CollectionItem y = new CollectionItem(rollingStock(), now);
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoItemsAreDifferents() throws ParseException {
		CollectionItem x = new CollectionItem(rollingStock(), date("2012/01/31"));
		CollectionItem y = new CollectionItem(rollingStock(), date("2012/02/11"));
		assertFalse(x.equals(y));
	}
}
