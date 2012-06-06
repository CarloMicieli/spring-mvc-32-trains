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

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ItemsCountTests {
	@Test
	public void shouldProduceAStringRepresentation() {
		ItemsCount counter = new ItemsCount("element-name", "Element name", 100);
		assertEquals("Element name (element-name, items=100)", counter.toString());
	}
	
	@Test
	public void shouldReturnsTrueIfTwoCountersAreEquals() {
		ItemsCount x = new ItemsCount("element-name", "Element name", 100);
		ItemsCount y = new ItemsCount("element-name", "Element name", 100);
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldReturnsFalseIfTwoCountersAreDifferents() {
		ItemsCount x = new ItemsCount("element-1", "Element 1", 140);
		ItemsCount y = new ItemsCount("element-2", "Element 2", 160);
		assertFalse(x.equals(y));
	}
	
	@Test
	public void shouldCompareTwoCountersInstance() {
		ItemsCount x = new ItemsCount("element-1", "aaa", 160);
		ItemsCount y = new ItemsCount("element-2", "bbb", 140);
		assertTrue(x.compareTo(y) > 0); // sort must be x, y
		
		ItemsCount z = new ItemsCount("element-3", "ccc", 160);
		assertTrue(x.compareTo(z) > 0); // sort must be x, z
	}
}
