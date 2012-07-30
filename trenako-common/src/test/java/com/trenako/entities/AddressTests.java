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

/**
 * 
 * @author Carlo Micieli
 *
 */
public class AddressTests {

	@Test
	public void shouldCheckWhetherTwoAddressesAreEquals() {
		Address x = new Address.Builder()
			.streetAddress("30 Commercial Rd.")
			.city("Bristol")
			.postalCode("PO1 1AA")
			.country("England")
			.locality("2A")
			.build();
		Address y = new Address.Builder()
			.streetAddress("30 Commercial Rd.")
			.city("Bristol")
			.postalCode("PO1 1AA")
			.country("England")
			.locality("2A")
			.build();
		assertTrue(x.equals(x));
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoAddressesAreDifferent() {
		Address x = new Address.Builder()
			.streetAddress("30 Commercial Rd.")
			.city("London")
			.postalCode("PO1 1AA")
			.country("England")
			.locality("2A")
			.build();
		Address y = new Address.Builder()
			.streetAddress("30 Commercial Rd.")
			.city("Bristol")
			.postalCode("PO1 1AA")
			.country("USA")
			.locality("2A")
			.build();
		assertFalse(x.equals(y));
	}
	
	@Test
	public void shouldBuildAddresses() {
		Address a = new Address.Builder()
			.streetAddress("30 Commercial Rd.")
			.city("Bristol")
			.postalCode("PO1 1AA")
			.country("England")
			.locality("2A")
			.build();

		assertEquals("30 Commercial Rd.", a.getStreetAddress());
		assertEquals("Bristol", a.getCity());
		assertEquals("PO1 1AA", a.getPostalCode());
		assertEquals("England", a.getCountry());
		assertEquals("2A", a.getLocality());
	}
	
	@Test
	public void shouldConvertAddressToString() {
		Address a = new Address.Builder()
			.streetAddress("30 Commercial Rd.")
			.city("Bristol")
			.postalCode("PO1 1AA")
			.country("England")
			.build();
		assertEquals("30 Commercial Rd., PO1 1AA Bristol, (England)", a.toString());
	}
	
	@Test
	public void shouldReturnBlankForEmptyAddresses() {
		Address a = new Address();
		assertEquals("", a.toString());
	}
	
	@Test
	public void isEmptyShouldReturnTrueForEmptyAddresses() {
		Address a = new Address();
		assertTrue(a.isEmpty());
	}
	
	@Test
	public void isEmptyShouldReturnFalseIfAddressIsNotEmpty() {
		Address a = new Address.Builder()
			.streetAddress("AAAA").build();
		assertFalse(a.isEmpty());
		
		Address b = new Address.Builder()
			.city("AAAA").build();
		assertFalse(b.isEmpty());
		
		Address c = new Address.Builder()
			.postalCode("AAAA").build();
		assertFalse(c.isEmpty());
		
		Address d = new Address.Builder()
			.country("AAAA").build();
		assertFalse(d.isEmpty());
		
		Address e = new Address.Builder()
			.locality("AAAA").build();
		assertFalse(e.isEmpty());
	}
}
