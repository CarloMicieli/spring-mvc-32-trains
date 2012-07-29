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

import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.trenako.test.AbstractValidationTests;

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
		Railway railway = new Railway.Builder("AAA")
			.description("Railway description")
			.country("de")
			.build();
		
		Map<String, String> errors = validate(railway);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidRailways() {
		Railway railway = new Railway();
		
		Map<String, String> errors = validate(railway);
		assertEquals(3, errors.size());
		assertEquals("railway.name.required", errors.get("name"));
		assertEquals("railway.country.required", errors.get("country"));
		assertEquals("railway.description.required", errors.get("description"));
	}
	
	@Test
	public void shouldValidateRailwayNameSize() {
		Railway railway = new Railway.Builder("12345678901") //max = 10
			.description("Railway description")
			.country("de")
			.build();
		
		Map<String, String> errors = validate(railway);
		assertEquals(1, errors.size());
		assertEquals("railway.name.size.notmet", errors.get("name"));
	}
	
	@Test
	public void shouldValidateRailwayCountryCodes() {
		Map<String, String> errors = null;
		Railway railway = new Railway.Builder("AAA")
			.description("Railway description")
			.country("rr") 
			.build();
		
		errors = validate(railway);
		assertEquals(1, errors.size());
		assertEquals("railway.country.code.invalid", errors.get("country"));
	}
	
	@Test
	public void shouldValidateOperatingSinceYear() {
		Railway railway = new Railway.Builder("AAA")
			.description("Railway description")
			.country("de")
			.operatingSince(tomorrow())
			.build();

		Map<String, String> errors = validate(railway);
		assertEquals(1, errors.size());
		assertEquals("railway.operatingSince.past.notmet", errors.get("operatingSince"));
	}
	
	@Test
	public void shouldValidateOperatingUntilYear() {
		Railway railway = new Railway.Builder("AAA")
			.description("Railway description")
			.country("de")	
			.operatingUntil(tomorrow())
			.build();

		Map<String, String> errors = validate(railway);
		assertEquals(1, errors.size());
		assertEquals("railway.operatingUntil.past.notmet", errors.get("operatingUntil"));
	}
	
	@Test
	public void shouldValidateDescriptionForDefaultLanguage() {
		Map<String, String> errors = null;
		Railway r1 = new Railway.Builder("AAA")
			.description("Railway description")
			.country("de")
			.build();
		errors = validate(r1);
		assertEquals(0, errors.size());
		
		Railway r2 = new Railway.Builder("AAA")
			.description(Locale.FRENCH, "Railway description")
			.country("de")
			.build();
		errors = validate(r2);
		assertEquals(1, errors.size());
		assertEquals("railway.description.default.required", errors.get("description"));
	}
}
