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
package com.trenako;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class SearchCriteriaTests {

	@Test
	public void shouldCheckIfBrandCriteriaIsSet() {
		SearchCriteria x = new SearchCriteria.Builder()
			.brand("ACME")
			.build();
		assertEquals(true, x.hasBrand());
		
		SearchCriteria y = new SearchCriteria.Builder()
			.brand("")
			.build();
		assertEquals(false, y.hasBrand());
		
		SearchCriteria z = new SearchCriteria.Builder()
			.brand(null)
			.build();
		assertEquals(false, z.hasBrand());
	}
	
	@Test
	public void shouldCheckIfCategoryCriteriaIsSet() {
		SearchCriteria x = new SearchCriteria.Builder()
			.category("electric-locomotives")
			.build();
		assertEquals(true, x.hasCategory());
		
		SearchCriteria y = new SearchCriteria.Builder()
			.category("")
			.build();
		assertEquals(false, y.hasCategory());
		
		SearchCriteria z = new SearchCriteria.Builder()
			.category(null)
			.build();
		assertEquals(false, z.hasCategory());
	}
	
	@Test
	public void shouldCheckIfEraCriteriaIsSet() {
		SearchCriteria x = new SearchCriteria.Builder()
			.era("IV")
			.build();
		assertEquals(true, x.hasEra());
		
		SearchCriteria y = new SearchCriteria.Builder()
			.era("")
			.build();
		assertEquals(false, y.hasEra());
		
		SearchCriteria z = new SearchCriteria.Builder()
			.era(null)
			.build();
		assertEquals(false, z.hasEra());
	}
	
	@Test
	public void shouldCheckIfRailwayCriteriaIsSet() {
		SearchCriteria x = new SearchCriteria.Builder()
			.railway("DB")
			.build();
		assertEquals(true, x.hasRailway());
		
		SearchCriteria y = new SearchCriteria.Builder()
			.railway("")
			.build();
		assertEquals(false, y.hasRailway());
		
		SearchCriteria z = new SearchCriteria.Builder()
			.railway(null)
			.build();
		assertEquals(false, z.hasRailway());
	}
	
	@Test
	public void shouldCheckIfScaleCriteriaIsSet() {
		SearchCriteria x = new SearchCriteria.Builder()
			.scale("H0")
			.build();
		assertEquals(true, x.hasScale());
		
		SearchCriteria y = new SearchCriteria.Builder()
			.scale("")
			.build();
		assertEquals(false, y.hasScale());
		
		SearchCriteria z = new SearchCriteria.Builder()
			.scale(null)
			.build();
		assertEquals(false, z.hasScale());
	}
	
	@Test
	public void shouldCheckIfPowerMethodCriteriaIsSet() {
		SearchCriteria x = new SearchCriteria.Builder()
			.powerMethod("DC")
			.build();
		assertEquals(true, x.hasPowerMethod());
		
		SearchCriteria y = new SearchCriteria.Builder()
			.powerMethod("")
			.build();
		assertEquals(false, y.hasPowerMethod());
		
		SearchCriteria z = new SearchCriteria.Builder()
			.powerMethod(null)
			.build();
		assertEquals(false, z.hasPowerMethod());
	}
	
	@Test
	public void shouldReturnStringRepresentation() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand("ACME")
			.category("electric-locomotives")
			.era("IV")
			.powerMethod("AC")
			.railway("DB")
			.scale("H0")
			.build();
		
		String expected = 
				"{brand=ACME, " +
				"category=electric-locomotives, "+
				"era=IV, "+
				"powerMethod=AC, "+
				"railway=DB, "+
				"scale=H0}";
		assertEquals(expected, sc.toString());
	}
	
	@Test
	public void shouldFillTheSearchCriteria() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand("brand")
			.category("category")
			.era("era")
			.powerMethod("powerMethod")
			.railway("railway")
			.scale("scale")
			.build();
		assertEquals("brand", sc.getBrand());
		assertEquals("category", sc.getCategory());
		assertEquals("era", sc.getEra());
		assertEquals("powerMethod", sc.getPowerMethod());
		assertEquals("scale", sc.getScale());
		assertEquals("railway", sc.getRailway());
	}

	@Test
	public void shouldTrueIfTwoCriteriaAreEquals() {
		SearchCriteria x = new SearchCriteria.Builder()
			.brand("brand")
			.category("category")
			.era("era")
			.powerMethod("powerMethod")
			.railway("railway")
			.scale("scale")
			.build();
		SearchCriteria y = new SearchCriteria.Builder()
			.brand("brand")
			.category("category")
			.era("era")
			.powerMethod("powerMethod")
			.railway("railway")
			.scale("scale")
			.build();
		assertEquals(true, x.equals(y));
	}
	
	@Test
	public void shouldFalseIfTwoCriteriaAreEquals() {
		SearchCriteria x = new SearchCriteria.Builder()
			.brand("brand")
			.category("category")
			.era("era")
			.powerMethod("DC")
			.railway("railway")
			.scale("scale")
			.build();
		SearchCriteria y = new SearchCriteria.Builder()
			.brand("brand")
			.category("category")
			.era("era")
			.powerMethod("AC")
			.railway("railway")
			.scale("scale")
			.build();
		assertEquals(false, x.equals(y));
	}
}
