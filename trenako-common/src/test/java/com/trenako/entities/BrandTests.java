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

import com.trenako.mapping.DbReferenceable;
import com.trenako.mapping.LocalizedField;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class BrandTests {

	@Test
	public void shouldProduceBrandLabels() {
		Brand b = new Brand("Ls Models");
		assertEquals("Ls Models", b.getLabel());
	}
	
	@Test
	public void shouldFillTheSlugValueForBrands() {
		Brand x = new Brand("Ls Models");
		assertEquals("ls-models", x.getSlug());
	}
	
	@Test
	public void shouldProduceStringRepresentations() {
		Brand brand = new Brand.Builder("ACME")
			.description("Italian brand")
			.emailAddress("mail@acme.com")
			.website("http://www.acmetreni.com")
			.industrial(true)
			.build();
		assertEquals("ACME", brand.toString());
	}
	
	@Test
	public void shouldChecksWhetherTwoBrandsAreDifferent() {
		Brand x = new Brand("AAAA");
		Brand y = new Brand("BBBB");
		
		assertFalse(x.equals(y));
		assertFalse(x.equals(new String()));
	}
	
	@Test
	public void shouldChecksWhetherTwoBrandsAreEquals() {
		Brand x = new Brand("AAAA");
		Brand y = new Brand("AAAA");
		
		assertTrue(x.equals(x));
		assertTrue(x.equals(y));
	}
	
//	@Test
//	public void shouldProduceTheSameHashCodeForTwoEqualsBrands() {
//		Brand x = new Brand.Builder("AAAA")
//			.description("Desc")
//			.emailAddress("mail@mail.com")
//			.website("http://localhost")
//			.industrial(true)
//			.build();
//		
//		Brand y = new Brand.Builder("AAAA")
//			.description("Desc")
//			.emailAddress("mail@mail.com")
//			.website("http://localhost")
//			.industrial(true)
//			.build();	
//		
//		assertTrue(x.equals(y));
//		assertEquals(x.hashCode(), y.hashCode());		
//	}

	@Test
	public void shouldCreateNewBrandsUsingTheBuilder() {
		Brand b = new Brand.Builder("ACME")
			.companyName("Anonima Costruttori Modelli Esatti")
			.website("http://localhost")
			.emailAddress("mail@mail.com")
			.description("Description")
			.industrial(true)
			.scales("H0", "N")
			.build();
			
		assertEquals("ACME", b.getName());
		assertEquals("Anonima Costruttori Modelli Esatti", b.getCompanyName());
		assertEquals("http://localhost", b.getWebsite());
		assertEquals("mail@mail.com", b.getEmailAddress());
		assertEquals("Description", b.getDescription().getDefault());
		assertEquals(true, b.isIndustrial());
		assertEquals("[H0, N]", b.getScales().toString());
	}
	
	@Test
	public void shouldAssignAddressToBrands() {
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
	public void shouldManageLocalBranchesForBrands() {
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
	
	@Test
	public void shouldImplementDbReferenceable() {
		DbReferenceable ref = new Brand.Builder("ACME").build();
		assertEquals("acme", ref.getSlug());
		assertEquals("ACME", ref.getLabel());
	}
	
	@Test
	public void shouldProduceLocalizedBrandsDescriptions() {
		Brand x = new Brand("ACME");
		LocalizedField<String> desc = new LocalizedField<String>("English description");
		desc.put(Locale.ITALIAN, "Descrizione");
		x.setDescription(desc);

		assertEquals("English description", x.getDescription().getDefault());
		assertEquals("English description", x.getDescription().getValue(Locale.CHINESE));
		assertEquals("Descrizione", x.getDescription().getValue(Locale.ITALIAN));
	}
}
