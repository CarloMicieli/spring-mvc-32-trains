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

import com.trenako.values.Category;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CategoriesCountTests {

	@Test
	public void shouldConvertCategoryToFieldName() {
		String fieldName = CategoriesCount.getKey(Category.RAILCARS);
		assertEquals("railcars", fieldName);
		
		String fieldName2 = CategoriesCount.getKey(Category.ELECTRIC_LOCOMOTIVES);
		assertEquals("electricLocomotives", fieldName2);
	}
	
	@Test
	public void shouldConvertCategoryLabelToFieldName() {
		String fieldName = CategoriesCount.getKey("railcars");
		assertEquals("railcars", fieldName);
		
		String fieldName2 = CategoriesCount.getKey("electric-locomotives");
		assertEquals("electricLocomotives", fieldName2);
	}
}
