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

import java.math.BigDecimal;
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
	public void shouldCreateMoneysFromDecimalAndUserProfile() {
		Account user = new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.profile(new Profile("USD"))
			.build();
		
		Money m = Money.newMoney(BigDecimal.valueOf(100), user);
		assertEquals("$100.00", m.toString());
	}
	
	@Test
	public void shouldCreateNullMoneysWhenDecimalValueIsNull() {
		Account user = new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.profile(new Profile("USD"))
			.build();
		
		Money m = Money.newMoney(null, user);
		assertNull("Money is not null", m);
	}
	
	@Test
	public void shouldCreateMoneyUsingALocale() {
		Locale loc = Locale.ITALY;
		
		Money m = new Money(10050, loc);
		
		assertEquals(10050, m.getValue());
		assertEquals("EUR", m.getCurrency());
	}

	@Test
	public void shouldCreateMoneyValueFromBigDecimalValues() {
		Money m1 = new Money(BigDecimal.valueOf(100), "EUR");
		assertEquals(10000, m1.getValue());
		
		Money m2 = new Money(BigDecimal.valueOf(100.50), "EUR");
		assertEquals(10050, m2.getValue());

		Money m3 = new Money(BigDecimal.valueOf(100.5), "EUR");
		assertEquals(10050, m3.getValue());
	}
	
	@Test
	public void shouldProduceStringRepresentations() {
		Money m = new Money(10050, "USD");
		assertEquals("$100.50", m.toString());
	}
	
	@Test
	public void shouldExtractValueFromMoneys() {
		Money m = new Money(10050, "USD");
		assertEquals(BigDecimal.valueOf(100.5), Money.moneyValue(m));
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