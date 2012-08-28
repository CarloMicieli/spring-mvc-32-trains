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

import com.trenako.entities.Money;


/**
 * 
 * @author Carlo Micieli
 *
 */
public class MoneyValidatorTests {
	private MoneyValidator validator;
	private ConstraintValidatorContext context = Mockito.mock(ConstraintValidatorContext.class);
	
	@Before
	public void setup() {
		validator = new MoneyValidator();
	}
	
	@Test
	public void shouldValidateEmptyMoney() {
		boolean isValid = validator.isValid(new Money(), context);
		assertTrue(isValid);
	}
	
	@Test
	public void shouldValidateMoneyValues() {
		boolean isValid1 = validator.isValid(new Money(100, "USD"), context);
		assertTrue("Money is not valid", isValid1);
		
		boolean isValid2 = validator.isValid(new Money(-100, "USD"), context);
		assertFalse("Money is valid", isValid2);
	}
	
	@Test
	public void shouldValidateMoneyCurrencies() {
		boolean isValid1 = validator.isValid(new Money(100, "USD"), context);
		assertTrue("Money is not valid", isValid1);
		
		boolean isValid2 = validator.isValid(new Money(100, ""), context);
		assertFalse("Money is valid", isValid2);
		
		boolean isValid3 = validator.isValid(new Money(100, "ZZZ"), context);
		assertFalse("Money is valid", isValid3);
	}
}
