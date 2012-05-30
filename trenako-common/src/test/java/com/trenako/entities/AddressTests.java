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
}
