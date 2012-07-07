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
package com.trenako;

import java.util.List;

import org.junit.Test;

import com.trenako.Category;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CategoryTests {

	@Test
	public void shouldProduceDescription() {
		Category el = Category.ELECTRIC_LOCOMOTIVES;
		assertEquals("electric-locomotives", el.label());
		
		Category emu = Category.ELECTRIC_MULTIPLE_UNIT;
		assertEquals("electric-multiple-unit", emu.label());
	}
	
	@Test
	public void shouldParseAStringValue() {
		Category el = Category.parse("electric-locomotives");
		assertEquals(Category.ELECTRIC_LOCOMOTIVES, el);
	}
	
	@Test
	public void shouldListAllTheCategoryLabels() {
		List<String> l = Category.list();
		assertEquals("steam-locomotives", l.get(0));
		assertEquals("diesel-locomotives", l.get(1));
		assertEquals("electric-locomotives", l.get(2));
		assertEquals("railcars", l.get(3));
		assertEquals("electric-multiple-unit", l.get(4));
		assertEquals("freight-cars", l.get(5));
		assertEquals("passenger-cars", l.get(6));
	}
	
	@Test
	public void shouldListAllTheCategoryLabelsByCategory() {
		Iterable<String> categories = Category.list(false);
		List<String> l = (List<String>) categories;
		
		assertEquals("category-ac-steam-locomotives", l.get(0));
		assertEquals("category-ac-diesel-locomotives", l.get(1));
		assertEquals("category-ac-electric-locomotives", l.get(2));
		assertEquals("category-ac-railcars", l.get(3));
		assertEquals("category-ac-electric-multiple-unit", l.get(4));
		assertEquals("category-ac-freight-cars", l.get(5));
		assertEquals("category-ac-passenger-cars", l.get(6));
	}
	
	@Test
	public void shouldListAllTheCategoriesByPowerMethod() {
		Iterable<String> acCategories = Category.list(false);
		List<String> lAc = (List<String>) acCategories;
		assertEquals("category-ac-steam-locomotives", lAc.get(0));

		Iterable<String> dcCategories = Category.list(true);
		List<String> lDc = (List<String>) dcCategories;
		assertEquals("category-dc-steam-locomotives", lDc.get(0));
	}
}
