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

import org.junit.Test;

import com.trenako.values.Priority;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class WishListItemTests {

	@Test
	public void shouldCheckWheterTwoWishListItemsAreEquals() {
		WishListItem x = new WishListItem(rollingStock("123456"), "my notes", Priority.LOW, now());
		WishListItem y = new WishListItem(rollingStock("123456"), "my notes", Priority.LOW, now());
		assertTrue("The wish list items are different", x.equals(x));
		assertTrue("The wish list items are different", x.equals(y));
	}
	
	@Test
	public void shouldCheckWheterTwoWishListItemsAreDifferent() {
		WishListItem x = new WishListItem(rollingStock("123456"), "my notes", Priority.LOW, now());
		WishListItem y = new WishListItem(rollingStock("123457"), "my notes", Priority.LOW, now());
		assertFalse("The wish list items are equals", x.equals(y));
	}
	
	@Test
	public void shouldReturnNormalAsDefaultPriorityIfNoneIsSelected() {
		WishListItem x = new WishListItem(rollingStock("123456"));
		assertEquals(Priority.NORMAL, x.priority());
	}
	
	@Test
	public void shouldImplementsComparableInterface() {
		WishListItem a = new WishListItem(rollingStock("123456"), "my notes", Priority.HIGH, now());
		WishListItem b = new WishListItem(rollingStock("123457"), "my notes", Priority.LOW, now());
		assertTrue("HIGH < LOW", a.compareTo(b) < 0);
		
		WishListItem c = new WishListItem(rollingStock("123456"), "my notes", Priority.NORMAL, date("2012/1/1"));
		WishListItem d = new WishListItem(rollingStock("123457"), "my notes", Priority.NORMAL, date("2012/1/1"));
		assertTrue("NORMAL != NORMAL", c.compareTo(d) == 0);
		
		WishListItem e = new WishListItem(rollingStock("123456"), "my notes", Priority.LOW, now());
		WishListItem f = new WishListItem(rollingStock("123457"), "my notes", Priority.NORMAL, now());
		assertTrue("LOW > NORMAL", e.compareTo(f) > 0);
		
		WishListItem g = new WishListItem(rollingStock("123456"), "my notes", Priority.NORMAL, date("2012/1/1"));
		WishListItem h = new WishListItem(rollingStock("123457"), "my notes", Priority.NORMAL, date("2010/1/1"));
		assertTrue("2012/1/1 < 2010/1/1", g.compareTo(h) < 0);
		
	}
		
	RollingStock rollingStock(String itemNumber) {
		return new RollingStock.Builder(acme(), itemNumber)
			.scale(scaleH0())
			.railway(fs())
			.description("Desc")
			.build();
	}
}
