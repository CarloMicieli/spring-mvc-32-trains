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

import org.junit.Before;
import org.junit.Test;

import com.trenako.test.AbstractValidationTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionItemValidationTests extends AbstractValidationTests<CollectionItem> {
	
	private RollingStock rollingStock = new RollingStock.Builder(acme(), "123456")
		.scale(scaleH0())
		.railway(db())
		.build();
	
	@Before
	public void initValidator() {
		super.init(CollectionItem.class);
	}

	@Test
	public void shouldValidateValidCollectionsItem() {
		CollectionItem item = new CollectionItem(rollingStock, new Date());
		
		Map<String, String> errors = validate(item);
		
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidCollectionsItem() {
		CollectionItem item = new CollectionItem();
		
		Map<String, String> errors = validate(item);
		
		assertEquals(3, errors.size());
		assertEquals("item.itemId.required", errors.get("itemId"));
		assertEquals("item.rollingStock.required", errors.get("rollingStock"));
		assertEquals("item.addedAt.required", errors.get("addedAt"));
	}
	
	@Test
	public void shouldValidateAddedAtDate() {
		CollectionItem item = new CollectionItem(rollingStock, tomorrow());
		
		Map<String, String> errors = validate(item);
		assertEquals(1, errors.size());
		assertEquals("item.addedAt.past.notmet", errors.get("addedAt"));
	}
	
	@Test
	public void shouldValidateItemPrices() {
		CollectionItem item = new CollectionItem(rollingStock, new Date());
		item.setPrice(new Money(-1, "USD"));
		
		Map<String, String> errors = validate(item);
		assertEquals(1, errors.size());
		assertEquals("item.price.money.notmet", errors.get("price"));
	}
}
