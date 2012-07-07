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

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.trenako.test.AbstractValidationTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionItemValidationTests extends AbstractValidationTests<CollectionItem> {
	
	@Before
	public void initValidator() {
		super.init(CollectionItem.class);
	}

	@Test
	public void shouldValidateValidCollectionsItem() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		CollectionItem item = new CollectionItem.Builder(rs).build();
		
		Map<String, String> errors = validate(item);
		
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidCollectionsItem() {
		CollectionItem item = new CollectionItem();
		
		Map<String, String> errors = validate(item);
		
		assertEquals(1, errors.size());
		assertEquals("item.rollingStock.required", errors.get("rollingStock"));
	}
	
	@Test
	public void shouldValidateItemQuantity() {
		Map<String, String> errors = null;
		
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		CollectionItem item1 = new CollectionItem.Builder(rs)
			.quantity(0)
			.build();
		
		errors = validate(item1);
		assertEquals(1, errors.size());
		assertEquals("item.quantity.range.notmet", errors.get("quantity"));
		
		CollectionItem item2 = new CollectionItem.Builder(rs)
			.quantity(100)
			.build();
	
		errors = validate(item2);
		assertEquals(1, errors.size());
		assertEquals("item.quantity.range.notmet", errors.get("quantity"));
	}
	
	@Test
	public void shouldValidateItemPrice() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		CollectionItem item = new CollectionItem.Builder(rs)
			.price(-1)
			.build();
		
		Map<String, String> errors = validate(item);
		assertEquals(1, errors.size());
		assertEquals("item.price.range.notmet", errors.get("price"));
	}
	
	@Test
	public void shouldValidateAddedAtDate() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		CollectionItem item = new CollectionItem.Builder(rs)
			.addedAt(tomorrow())
			.build();
		
		Map<String, String> errors = validate(item);
		assertEquals(1, errors.size());
		assertEquals("item.addedAt.past.notmet", errors.get("addedAt"));
	}
}
