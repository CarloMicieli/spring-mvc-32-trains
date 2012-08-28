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
import static com.trenako.test.TestDataBuilder.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.trenako.test.AbstractValidationTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionValidationTests extends AbstractValidationTests<Collection> {
	
	private RollingStock rollingStock() {
		return new RollingStock.Builder(acme(), "123456")
			.railway(fs())
			.scale(scaleH0())
			.description("Description")
			.build();
	}
	
	private Account georgeStephenson() {
		return new Account.Builder("mail@mail.com")
			.displayName("George Stephenson")
			.build();
	}
	
	private List<CollectionItem> items() {
		return Arrays.asList(new CollectionItem(rollingStock(), date("2012/01/01")));
	}
	
	@Before
	public void initValidator() {
		super.init(Collection.class);
	}

	@Test
	public void shouldValidateValidCollections() {
		Collection c = new Collection(georgeStephenson());
		
		Map<String, String> errors = validate(c);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidCollections() {
		Collection c = new Collection();
		
		Map<String, String> errors = validate(c);
		assertEquals(1, errors.size());
		assertEquals("collection.owner.required", errors.get("owner"));
	}
	
	@Test
	public void shouldValidateCollectionItems() {
		Collection c = new Collection(georgeStephenson());
		c.setItems(items());
		
		Map<String, String> errors = validate(c);
		assertEquals(0, errors.size());
	}
}
