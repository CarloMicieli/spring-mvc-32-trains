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

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class SearchCriteriaTests {

	@Test
	public void shouldProduceSingletonValueForEmptySearchCriteria() {
		SearchCriteria x = SearchCriteria.EMPTY;
		assertNotNull(x);
		assertTrue(x.isEmpty());
		
		SearchCriteria y = SearchCriteria.EMPTY;
		assertTrue(x == y);
	}
	
	@Test
	public void shouldAcceptNullForTheObjectVersionOfBuilderMethods() {
		SearchCriteria x = new SearchCriteria.Builder()
			.brand((Brand) null)
			.build();
		assertTrue(x.isEmpty());
	}
	
	@Test
	public void shouldReturnNullIfCriterionNotSet() {
		SearchCriteria y = new SearchCriteria.Builder()
			.brand(acme())
			.build();
		assertNull(y.getPowerMethod());
	}
	
	@Test
	public void shouldCheckIfSearchCriteriaIsEmpty() {
		SearchCriteria x = new SearchCriteria();
		assertTrue(x.isEmpty());
		
		SearchCriteria y = new SearchCriteria.Builder()
			.brand(acme())
			.build();
		assertFalse(y.isEmpty());
	}
	
	@Test
	public void shouldGetPairsFromSearchCriteria() {
		SearchCriteria x = new SearchCriteria.Builder()
			.brand(acme())
			.railway(fs())
			.scale(scaleH0())
			.build();
		
		assertEquals("(acme,ACME)", x.get(Brand.class).toString());
		assertEquals("(fs,FS (Ferrovie dello stato))", x.get(Railway.class).toString());
		assertEquals("(h0,H0 (1:87))", x.get(Scale.class).toString());
	}
	
	@Test
	public void shouldReturnNullWhenCriterionNotFound() {
		SearchCriteria x = new SearchCriteria();
		Pair<String, String> notFound = x.get(Railway.class);
		assertNull(notFound);	
	}
	
	@Test
	public void shouldCheckIfBrandCriteriaIsSet() {
		SearchCriteria x = new SearchCriteria.Builder()
			.brand(acme())
			.build();
		assertEquals(true, x.hasBrand());
		
		SearchCriteria z = new SearchCriteria.Builder()
			.brand(null)
			.build();
		assertEquals(false, z.hasBrand());
	}
	
	@Test
	public void shouldCheckIfCategoryCriteriaIsSet() {
		SearchCriteria x = new SearchCriteria.Builder()
			.category(steamLocomotives())
			.build();
		assertEquals(true, x.hasCategory());
		
		
		SearchCriteria z = new SearchCriteria.Builder()
			.category(null)
			.build();
		assertEquals(false, z.hasCategory());
	}
	
	@Test
	public void shouldCheckIfCatCriteriaIsSet() {
		SearchCriteria x = new SearchCriteria.Builder()
			.cat(dcElectricLocomotives())
			.build();
		assertEquals(true, x.hasCat());
		
		SearchCriteria z = new SearchCriteria.Builder()
			.cat(null)
			.build();
		assertEquals(false, z.hasCat());
	}
	
	@Test
	public void shouldCheckIfEraCriteriaIsSet() {
		SearchCriteria x = new SearchCriteria.Builder()
			.era(eraIII())
			.build();
		assertEquals(true, x.hasEra());
		
		SearchCriteria z = new SearchCriteria.Builder()
			.era(null)
			.build();
		assertEquals(false, z.hasEra());
	}
	
	@Test
	public void shouldCheckIfRailwayCriteriaIsSet() {
		SearchCriteria x = new SearchCriteria.Builder()
			.railway(fs())
			.build();
		assertEquals(true, x.hasRailway());
		
		SearchCriteria z = new SearchCriteria.Builder()
			.railway(null)
			.build();
		assertEquals(false, z.hasRailway());
	}
	
	@Test
	public void shouldCheckIfScaleCriteriaIsSet() {
		SearchCriteria x = new SearchCriteria.Builder()
			.scale(scaleH0())
			.build();
		assertEquals(true, x.hasScale());
		
		SearchCriteria z = new SearchCriteria.Builder()
			.scale(null)
			.build();
		assertEquals(false, z.hasScale());
	}
	
	@Test
	public void shouldCheckIfPowerMethodCriteriaIsSet() {
		SearchCriteria x = new SearchCriteria.Builder()
			.powerMethod(dc())
			.build();
		assertEquals(true, x.hasPowerMethod());
		
		SearchCriteria z = new SearchCriteria.Builder()
			.powerMethod(null)
			.build();
		assertEquals(false, z.hasPowerMethod());
	}
	
	@Test
	public void shouldReturnStringRepresentation() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(acme())
			.category(steamLocomotives())
			.cat(dcElectricLocomotives())
			.era(eraIII())
			.powerMethod(dc())
			.railway(fs())
			.scale(scaleH0())
			.build();
		
		String expected = 
				"{BRAND=(acme,ACME), SCALE=(h0,H0 (1:87)), " +
				"CAT=(dc-electric-locomotives,dc-electric-locomotives), " +
				"RAILWAY=(fs,FS (Ferrovie dello stato)), ERA=(iii,iii), " +
				"POWER_METHOD=(dc,dc), CATEGORY=(steam-locomotives,steam-locomotives)}";
		assertEquals(expected, sc.toString());
	}
	
	@Test
	public void shouldFillTheSearchCriteria() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(acme())
			.category(steamLocomotives())
			.cat(acElectricLocomotives())
			.era(eraIII())
			.powerMethod(dc())
			.railway(fs())
			.scale(scaleH0())
			.build();
		assertEquals("acme", sc.getBrand());
		assertEquals("steam-locomotives", sc.getCategory());
		assertEquals("ac-electric-locomotives", sc.getCat());
		assertEquals("iii", sc.getEra());
		assertEquals("dc", sc.getPowerMethod());
		assertEquals("h0", sc.getScale());
		assertEquals("fs", sc.getRailway());
	}

	@Test
	public void shouldReturnTrueIfTwoCriteriaAreEquals() {
		SearchCriteria x = new SearchCriteria.Builder()
			.brand(acme())
			.category(steamLocomotives())
			.cat(dcElectricLocomotives())
			.era(eraIII())
			.powerMethod(dc())
			.railway(fs())
			.scale(scaleH0())
			.build();
		SearchCriteria y = new SearchCriteria.Builder()
			.brand(acme())
			.category(steamLocomotives())
			.cat(dcElectricLocomotives())
			.era(eraIII())
			.powerMethod(dc())
			.railway(fs())
			.scale(scaleH0())
			.build();
		assertEquals(true, x.equals(y));
	}
	
	@Test
	public void shouldReturnsFalseIfTwoCriteriaAreDifferent() {
		SearchCriteria x = new SearchCriteria.Builder()
			.brand(acme())
			.category(steamLocomotives())
			.cat(dcElectricLocomotives())
			.era(eraIII())
			.powerMethod(dc())
			.railway(db())
			.scale(scaleH0())
			.build();
		SearchCriteria y = new SearchCriteria.Builder()
			.brand(acme())
			.category(steamLocomotives())
			.cat(dcElectricLocomotives())
			.era(eraIII())
			.powerMethod(dc())
			.railway(fs())
			.scale(scaleN())
			.build();
		assertEquals(false, x.equals(y));
	}	
}
