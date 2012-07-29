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

import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import com.trenako.mapping.DbReferenceable;
import com.trenako.mapping.LocalizedField;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RailwayTests {

	@Test
	public void shouldProduceRailwayLabels() {
		Railway x = new Railway.Builder("DB").companyName("Die bahn").build();
		assertEquals("DB (Die bahn)", x.getLabel());
		
		Railway y = new Railway.Builder("Scnf").build();
		assertEquals("Scnf", y.getLabel());
	}
	
	@Test
	public void shouldProduceStringRepresentations() {
		Date now = new Date();
		Railway r = new Railway.Builder("Scnf")
			.companyName("Société nationale des chemins de fer français")
			.country("fr")
			.operatingSince(now)
			.operatingUntil(now)
			.build();
		assertEquals("Scnf", r.toString());
	}
	
	@Test
	public void shouldCreateNewRailwaysUsingTheBuilder() {
		Date now = new Date();
		Railway r = new Railway.Builder("Scnf")
			.companyName("Société nationale des chemins de fer français")
			.country("fr")
			.description("Scnf description")
			.operatingSince(now)
			.operatingUntil(now)
			.build();
		assertNotNull(r);
		assertEquals("Scnf", r.getName());
		assertEquals("fr", r.getCountry());
		assertEquals("Société nationale des chemins de fer français", r.getCompanyName());
		assertEquals(now, r.getOperatingSince());
		assertEquals(now, r.getOperatingUntil());
		assertEquals("Scnf description", r.getDescription().getDefault());
	}
	
	@Test
	public void shouldChecksWhetherTwoRailwaysAreDifferent() {
		Railway x = new Railway.Builder("DB")
			.country("de")
			.companyName("Die bahn")
			.build();
		Railway y = new Railway.Builder("Sncf")
			.country("fr")
			.companyName("Société nationale des chemins de fer français")
			.build();
		
		assertFalse(x.equals(y));
		assertFalse(x.equals(new String()));
	}
	
	@Test
	public void shouldChecksWhetherTwoRailwaysAreEquals() {
		Railway x = new Railway.Builder("DB")
			.country("de")
			.companyName("Die bahn")
			.build();
		Railway y = new Railway.Builder("DB")
			.country("de")
			.companyName("Die bahn")
			.build();
		
		assertTrue(x.equals(x));
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldProduceTheSameHashCodeForTwoEqualRailways() {
		Railway x = new Railway("DB");
		Railway y = new Railway("DB");
		assertEquals(x.hashCode(), y.hashCode());
	}

	@Test
	public void shouldFillTheSlugValue() {
		Railway x = new Railway("s.n.c.f.");
		assertEquals("sncf", x.getSlug());
	}
	
	@Test
	public void shouldImplementDbReferenceable() {
		DbReferenceable ref = new Railway.Builder("DB").companyName("Die bahn").build();
		assertEquals("db", ref.getSlug());
		assertEquals("DB (Die bahn)", ref.getLabel());
		
		DbReferenceable ref2 = new Railway.Builder("FS").build();
		assertEquals("fs", ref2.getSlug());
		assertEquals("FS", ref2.getLabel());
	}
	
	@Test
	public void shouldProduceLocalizedRailwaysDescriptions() {
		Railway x = new Railway("DB");
		LocalizedField<String> desc = new LocalizedField<String>("German railways");
		desc.put(Locale.ITALIAN, "Ferrovie tedesche");
		x.setDescription(desc);

		assertEquals("German railways", x.getDescription().getDefault());
		assertEquals("German railways", x.getDescription().getValue(Locale.CHINESE));
		assertEquals("Ferrovie tedesche", x.getDescription().getValue(Locale.ITALIAN));
	}
}
