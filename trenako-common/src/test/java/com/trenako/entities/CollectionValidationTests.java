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

import com.trenako.AbstractValidationTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionValidationTests extends AbstractValidationTests<Collection> {
	
	@Before
	public void initValidator() {
		super.init(Collection.class);
	}

	@Test
	public void shouldValidateValidCollections() {
		Account owner = new Account.Builder("mail@mail.com").build();
		Collection c = new Collection(owner);
		
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
}
