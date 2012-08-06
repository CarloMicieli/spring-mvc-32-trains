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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Carlo Micieli
 *
 */
public class MoneyTests {
	
	@Test
	public void shouldCreateMoneyUsingALocale() {
		Locale loc = Locale.ITALY;
		
		Money m = new Money(10050, loc);
		
		assertEquals(10050, m.getValue());
		assertEquals("EUR", m.getCurrency());
	}
		
	@Test
	public void shouldProduceStringRepresentations() {
		Money m = new Money(10050, "USD");
		assertEquals("$100.50", m.toString());
	}
	
	@Test
	public void shouldCheckWheterTwoMoneysAreEquals() {
		Money x = new Money(10050, "USD");
		Money y = new Money(10050, "USD");
		assertTrue(x.equals(x));
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldCheckWheterTwoMoneysAreDifferent() {
		Money x = new Money(10050, "USD");
		Money y = new Money(10050, "EUR");
		assertFalse(x.equals(y));
		
		Money z = new Money(20050, "USD");
		assertFalse(x.equals(z));
	}
}