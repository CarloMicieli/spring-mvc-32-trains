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

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.trenako.Condition;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionItemTests {
	
	@Test
	public void shouldCreateNewCollectionItems() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		
		Date now = new Date();
		CollectionItem item = new CollectionItem.Builder(rs)
			.addedAt(now)
			.condition(Condition.NEW)
			.quantity(2)
			.notes("Notes text")
			.price(100)
			.build();
		
		assertEquals(rs, item.getRollingStock());
		assertEquals("new", item.getCondition());
		assertEquals(now, item.getAddedAt());
		assertEquals("Notes text", item.getNotes());
		assertEquals(100, item.getPrice());
		assertEquals(2, item.getQuantity());
	}
	
	@Test
	public void shouldCreateItemWithDefaultQuantity() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		CollectionItem item = new CollectionItem.Builder(rs).build();
		assertEquals(1, item.getQuantity());
	}
	
	@Test
	public void shouldReturnThePriceAsMoney() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		CollectionItem item = new CollectionItem.Builder(rs)
			.price(9912)
			.build();
		BigDecimal money = item.price();
		assertEquals(new BigDecimal("99.12"), money);
	}
	
	@Test
	public void shouldCheckWhetherTwoItemsAreEquals() {
		Date now = new Date();
		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.build();
		CollectionItem x = new CollectionItem.Builder(rs)
			.addedAt(now)
			.build();
		CollectionItem y = new CollectionItem.Builder(rs)
			.addedAt(now)
			.build();
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoItemsAreDifferents() throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = format.parse("2012-01-31");
		Date date2 = format.parse("2012-02-11");
		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.build();
		CollectionItem x = new CollectionItem.Builder(rs)
			.addedAt(date1)
			.build();
		CollectionItem y = new CollectionItem.Builder(rs)
			.addedAt(date2)
			.build();
		assertFalse(x.equals(y));
	}
}
