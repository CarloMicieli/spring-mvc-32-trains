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

import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class BrandTests {

	@Test
	public void shouldFillTheSlugValue() {
		Brand x = new Brand("Ls Models");
		assertEquals("ls-models", x.getSlug());
	}
	
	@Test
	public void equalsShouldFalseForDifferentBrands() {
		Brand x = new Brand("AAAA");
		Brand y = new Brand("BBBB");
		
		assertFalse(x.equals(y));
	}
	
	@Test
	public void equalsShouldTrueForEqualBrands() {
		Brand x = new Brand("AAAA");
		Brand y = new Brand("AAAA");
		
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldProduceTheSameHashCodeForTwoEgualsBrands() {
		Brand x = new Brand.Builder("AAAA")
			.description("Desc")
			.emailAddress("mail@mail.com")
			.website("http://localhost")
			.industrial(true)
			.build();
		
		Brand y = new Brand.Builder("AAAA")
			.description("Desc")
			.emailAddress("mail@mail.com")
			.website("http://localhost")
			.industrial(true)
			.build();	
		
		assertTrue(x.equals(y));
		assertEquals(x.hashCode(), y.hashCode());		
	}

	@Test
	public void shouldBuildNewBrands() {
		Brand b = new Brand.Builder("ACME")
			.website("http://localhost")
			.emailAddress("mail@mail.com")
			.description("Description")
			.industrial(true)
			.build();
			
		assertEquals("ACME", b.getName());
		assertEquals("http://localhost", b.getWebsite());
		assertEquals("mail@mail.com", b.getEmailAddress());
		assertEquals("Description", b.getDescription());
		assertEquals(true, b.isIndustrial());
	}
	
	@Test
	public void shouldManageTheListOfScales() {
		Brand b = new Brand.Builder("ACME").build();
		b.addScale("H0");
		b.addScale("N");
		
		List<String> scales = (List<String>) b.getScales();
		assertEquals("[H0, N]", scales.toString());
	}

	@Test
	public void shouldManageBrandAddress() {
		Address a = new Address.Builder()
			.streetAddress("30 Commercial Rd.")
			.city("Bristol")
			.postalCode("PO1 1AA")
			.country("England")
			.build();
		
		Brand b = new Brand.Builder("ACME")
			.address(a)
			.build();
		
		assertEquals("30 Commercial Rd., PO1 1AA Bristol, (England)", 
				b.getAddress().toString());
	}
	
	@Test
	public void shouldManageLocalBranches() {
		Address en = new Address.Builder()
			.streetAddress("30 Commercial Rd.")
			.city("Bristol")
			.postalCode("PO1 1AA")
			.country("England")
			.build();
		Address de = new Address.Builder()
			.streetAddress("Schulstrasse 4")
			.city("Bad Oyenhausen")
			.postalCode("32547")
			.country("Germany")
			.build();
			
		Brand b = new Brand.Builder("ACME")
			.address("en", en)
			.address("de", de)
			.build();
	
		assertEquals("30 Commercial Rd., PO1 1AA Bristol, (England)", 
				b.getAddress("en").toString());

		assertEquals("Schulstrasse 4, 32547 Bad Oyenhausen, (Germany)", 
				b.getAddress("de").toString());
		
		assertEquals(null, b.getAddress("fr"));
	}
}
