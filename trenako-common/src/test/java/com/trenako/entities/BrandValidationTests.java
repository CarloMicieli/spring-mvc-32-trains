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

import com.trenako.test.AbstractValidationTests;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class BrandValidationTests extends AbstractValidationTests<Brand> {
	
	@Before
	public void initValidator() {
		super.init(Brand.class);
	}

	@Test
	public void shouldValidateValidBrands() {
		Brand b = new Brand.Builder("ACME").build();
		
		Map<String, String> errors = validate(b);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidBrands() {
		Brand b = new Brand();
		
		Map<String, String> errors = validate(b);
		assertEquals(1, errors.size());
		assertEquals("brand.name.required", errors.get("name"));
	}
	
	@Test
	public void shouldValidateBrandNameSize() {
		Brand b = new Brand.Builder("12345678901234567890123456")
			.build();
		
		Map<String, String> errors = validate(b);
		assertEquals(1, errors.size());
		assertEquals("brand.name.size.notmet", errors.get("name"));
	}
	
	@Test
	public void shouldValidateBrandWebsite() {
		Brand b = new Brand.Builder("AAA")
			.website("http://localhost")
			.build();
		
		Map<String, String> errors = validate(b);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidBrandWebsite() {
		Brand b = new Brand.Builder("AAA")
			.website("wr0ng$website")
			.build();
		
		Map<String, String> errors = validate(b);
		assertEquals(1, errors.size());
		assertEquals("brand.website.url.invalid", errors.get("website"));
	}

	@Test
	public void shouldValidateBrandEmail() {
		Brand b = new Brand.Builder("AAA")
			.emailAddress("mail@mail.com")
			.build();
		
		Map<String, String> errors = validate(b);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidBrandEmail() {
		Brand b = new Brand.Builder("AAA")
			.emailAddress("wr0ng$mail")
			.build();

		Map<String, String> errors = validate(b);
		assertEquals(1, errors.size());
		assertEquals("brand.emailAddress.email.invalid", errors.get("emailAddress"));
	}
}
