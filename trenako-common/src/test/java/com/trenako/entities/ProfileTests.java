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
import org.junit.Test;

import java.util.Currency;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ProfileTests {

	private final static Currency EURO = Currency.getInstance("EUR");

	@Test
	public void shouldReturnTheDefaultCurrencyForDefaultProfiles() {
		Profile p = Profile.defaultProfile();
		assertEquals(EURO, p.getCurrency());
	}

	@Test
	public void shouldReturnTheDefaultCurrencyForEmptyProfiles() {
		Profile p = new Profile();
		assertEquals(EURO, p.getCurrency());
	}

	@Test
	public void shouldReturnStringRepresentationForProfiles() {
		Profile p = new Profile("USD");
		assertEquals("profile{currency: USD}", p.toString());
	}

	@Test
	public void shouldCheckWhetherTwoProfilesAreEquals() {
		Profile x = new Profile("USD");
		Profile y = new Profile("USD");
		assertTrue("Profiles are different", x.equals(x));
		assertTrue("Profiles are different", x.equals(y));
	}

	@Test
	public void shouldCheckWhetherTwoProfilesAreDifferent() {
		Profile x = new Profile("USD");
		Profile y = new Profile("EUR");
		assertFalse("Profiles are equals", x.equals(y));
	}
}
