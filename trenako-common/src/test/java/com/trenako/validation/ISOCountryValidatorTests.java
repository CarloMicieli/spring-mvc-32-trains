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

import org.junit.Test;
import org.mockito.Mockito;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ISOCountryValidatorTests {

	private ISOCountryValidator validator = new ISOCountryValidator();
	private ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);

	@Test
	public void shouldValidatedEmptyValues() {
		assertTrue(validator.isValid(null, context));
		assertTrue(validator.isValid("", context));
	}

	@Test
	public void shouldValidateCorrectISOCountryCodes() {
		assertTrue(validator.isValid("de", context));
		assertTrue(validator.isValid("gb", context));
	}
	
	@Test
	public void shouldValidateIncorrectISOCountryCodes() {
		assertFalse(validator.isValid("rr", context));
		assertFalse(validator.isValid("ub", context));
	}
}
