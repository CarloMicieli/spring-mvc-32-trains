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
package com.trenako.validation;

import static org.junit.Assert.*;

import javax.validation.ConstraintValidatorContext;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.trenako.entities.Address;


/**
 * 
 * @author Carlo Micieli
 *
 */
public class AddressValidatorTests {
	private AddressValidator validator;
	private ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
	
	@Before
	public void setup() {
		validator = new AddressValidator();
	}
	
	@Test
	public void shouldValidateEmptyAddress() {
		boolean isValid = validator.isValid(new Address(), context);
		assertTrue(isValid);
	}
	
	@Test
	public void shouldValidateAddressValues() {
		Address validAddress = new Address.Builder()
			.streetAddress("streetAddress")
			.city("city")
			.postalCode("postalCode")
			.country("country")
			.build();
		boolean valid = validator.isValid(validAddress, context);
		assertTrue("Address is not valid", valid);
	}

	@Test
	public void shouldValidateWrongAddressValues() {
		Address wrongAddress1 = new Address.Builder()
			.streetAddress("streetAddress")
			.postalCode("postalCode")
			.country("country")
			.build();
		boolean notValid1 = validator.isValid(wrongAddress1, context);
		assertFalse("Address is valid", notValid1);

		Address wrongAddress2 = new Address.Builder()
			.streetAddress("streetAddress")
			.postalCode("postalCode")
			.city("city")
			.build();
		boolean notValid2 = validator.isValid(wrongAddress2, context);
		assertFalse("Address is valid", notValid2);
	}
}