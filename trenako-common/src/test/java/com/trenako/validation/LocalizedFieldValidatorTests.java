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

import java.util.Locale;

import javax.validation.ConstraintValidatorContext;

import org.junit.Test;
import org.mockito.Mockito;

import com.trenako.mapping.LocalizedField;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class LocalizedFieldValidatorTests {

	private LocalizedFieldValidator validator = new LocalizedFieldValidator();
	private ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
	
	@Test
	public void shoudlValidateEmptyValue() {
		boolean rv = validator.isValid(null, context);
		assertTrue(rv);
	}

	@Test
	public void shouldValidateValuesWithDefault() {
		boolean rv = validator.isValid(new LocalizedField<String>("default value"), context);
		assertTrue(rv);
	}
	
	@Test
	public void shouldValidateValuesWithoutDefault() {
		LocalizedField<String> field = new LocalizedField<String>();
		field.put(Locale.FRENCH, "Value");
		boolean rv = validator.isValid(field, context);
		assertFalse(rv);
	}
}
