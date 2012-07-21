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

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RailwayTests {

	@Test
	public void shouldReturnRailwayLabels() {
		Railway x = new Railway.Builder("DB").companyName("Die bahn").build();
		assertEquals("DB (Die bahn)", x.label());
		
		Railway y = new Railway.Builder("Scnf").build();
		assertEquals("Scnf", y.label());
	}
	
	@Test
	public void shouldBuildNewRailways() {
		Date now = new Date();
		Railway r = new Railway.Builder("Scnf")
			.companyName("Société nationale des chemins de fer français")
			.country("fr")
			.operatingSince(now)
			.operatingUntil(now)
			.build();
		assertNotNull(r);
		assertEquals("scnf", r.getSlug());
		assertEquals("Scnf (Société nationale des chemins de fer français)", r.label());
	}
	
	@Test
	public void equalsShouldFalseForDifferentRailways() {
		Railway x = new Railway("DB");
		Railway y = new Railway("Sncf");
		assertFalse(x.equals(y));
	}
	
	@Test
	public void equalsShouldTrueForEqualRailways() {
		Railway x = new Railway("DB");
		Railway y = new Railway("DB");
		assertTrue(x.equals(y));
	}
	
	@Test
	public void hashCodeShouldProduceTheSameHashForEqualRailways() {
		Railway x = new Railway("DB");
		Railway y = new Railway("DB");
		assertEquals(x.hashCode(), y.hashCode());
	}

	@Test
	public void shouldFillTheSlugValue() {
		Railway x = new Railway("s.n.c.f.");
		assertEquals("sncf", x.getSlug());
	}
}
