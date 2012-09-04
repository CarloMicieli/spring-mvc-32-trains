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
package com.trenako.criteria;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class SearchRequestTests {

	@Test
	public void shouldClearSearchRequests() {
		SearchRequest c = buildSearchRequest("ACME", "FS");
		assertFalse(c.isEmpty());
		
		c.clear();
		
		assertTrue(c.isEmpty());		
	}
	
	@Test
	public void shouldCheckWhetherSearchRequestsAreEmpty() {
		SearchRequest sr = new SearchRequest();
		assertTrue("Result is not empty", sr.isEmpty());
		
		SearchRequest sr2 = new SearchRequest();
		sr2.setBrand("ACME");
		assertFalse("Result is empty", sr2.isEmpty());
	}
	
	@Test
	public void shouldCreateNewSearchRequests() {
		SearchRequest sr = new SearchRequest("ACME", 
				"H0", 
				"Scnf", 
				"III", 
				"ac-electric-locomotives", 
				"AC", 
				"electric-locomotives");
		
		String expected = "{BRAND=ACME, SCALE=H0, " +
				"CAT=ac-electric-locomotives, RAILWAY=Scnf, " +
				"ERA=III, POWER_METHOD=AC, CATEGORY=electric-locomotives}";
		assertEquals(expected, sr.toString());
	}

	@Test
	public void shouldBuildNewSearchRequestsThroughSetters() {
		SearchRequest sr = new SearchRequest();
		sr.setBrand("ACME");
		sr.setCat("ac-electric-locomotives");
		sr.setEra("III");
		sr.setCategory("electric-locomotives");
		sr.setPowerMethod("AC");
		sr.setScale("H0");
		sr.setRailway("Scnf");
		
		String expected = "{BRAND=ACME, SCALE=H0, " +
				"CAT=ac-electric-locomotives, RAILWAY=Scnf, " +
				"ERA=III, POWER_METHOD=AC, CATEGORY=electric-locomotives}";
		assertEquals(expected, sr.toString());
	}
	
	@Test 
	public void shouldCheckWhetherTwoRequestsAreEquals() {
		SearchRequest a = new SearchRequest();
		assertTrue("Results are different", a.equals(a));
		
		SearchRequest c = buildSearchRequest("ACME", "FS");
		SearchRequest d = buildSearchRequest("ACME", "FS");
		assertTrue("Results are different", c.equals(c));
		assertTrue("Results are different", c.equals(d));
	}
	
	@Test 
	public void shouldCheckWhetherTwoRequestsAreDifferent() {
		SearchRequest e = buildSearchRequest("ACME", "FS");
		SearchRequest f = buildSearchRequest("Marklin", "DB");
		assertFalse("Results are equals", e.equals(f));
	}
	
	private SearchRequest buildSearchRequest(String brand, String railway) {
		SearchRequest c = new SearchRequest();
		c.setBrand(brand);
		c.setRailway(railway);
		return c;
	}
}
