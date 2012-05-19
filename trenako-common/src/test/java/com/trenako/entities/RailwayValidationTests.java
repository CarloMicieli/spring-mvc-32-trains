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

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.trenako.AbstractValidationTests;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RailwayValidationTests extends AbstractValidationTests<Railway> {
	
	@Before
	public void initValidator() {
		super.init(Railway.class);
	}
	
	@Test
	public void shouldValidateRailways() {
		Railway railway = new Railway();
		railway.setName("AAA");
		railway.setCountry("ITA");
		
		Map<String, String> errors = validate(railway);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidRailways() {
		Railway railway = new Railway();
		
		Map<String, String> errors = validate(railway);
		assertEquals(2, errors.size());
		assertEquals("railway.name.required", errors.get("name"));
		assertEquals("railway.country.required", errors.get("country"));
	}
	
	@Test
	public void shouldValidateRailwayNameSize() {
		Railway railway = new Railway();
		railway.setName("12345678901"); //max = 10
		railway.setCountry("ITA");
		
		Map<String, String> errors = validate(railway);
		assertEquals(1, errors.size());
		assertEquals("railway.name.size.notmet", errors.get("name"));
	}
	
	@Test
	public void shouldValidateRailwayCountrySize() {
		Railway railway = new Railway();
		railway.setName("AAA");
		railway.setCountry("1234"); //max = 3
		
		Map<String, String> errors = validate(railway);
		assertEquals(1, errors.size());
		assertEquals("railway.country.size.notmet", errors.get("country"));
	}	
}
