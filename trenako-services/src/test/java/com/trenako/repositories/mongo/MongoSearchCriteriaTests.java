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
package com.trenako.repositories.mongo;

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;

import com.trenako.criteria.SearchCriteria;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class MongoSearchCriteriaTests {
	
	@Test
	public void shouldBuildBrandCriteriaCommand() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(acme())
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"brand.slug\" : \"acme\"}", criteria.getCriteriaObject().toString());
	}

	@Test
	public void shouldBuildCategoryCriteriaCommand() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.category(electricLocomotives())
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"category\" : \"electric-locomotives\"}", 
				criteria.getCriteriaObject().toString());
	}
	
	@Test
	public void shouldBuildCatCriteriaCommand() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.cat(dcElectricLocomotives())
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"category\" : \"electric-locomotives\" , \"powerMethod\" : \"dc\"}", 
				criteria.getCriteriaObject().toString());
	}
	
	@Test
	public void shouldBuildEraCriteriaCommand() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.era(eraIII())
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"era\" : \"iii\"}", 
				criteria.getCriteriaObject().toString());
	}
	
	@Test
	public void shouldBuildPowerMethodCriteriaCommand() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.powerMethod(ac())
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"powerMethod\" : \"ac\"}", 
				criteria.getCriteriaObject().toString());
	}
	
	@Test
	public void shouldBuildRailwayCriteriaCommand() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.railway(db())
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"railway.slug\" : \"db\"}", 
				criteria.getCriteriaObject().toString());
	}
	
	@Test
	public void shouldBuildCriteriaWithOnlyOneCategoryAndPowerMethod() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.cat(dcElectricLocomotives())
			.category(steamLocomotives())
			.powerMethod(ac())
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"category\" : \"electric-locomotives\" , \"powerMethod\" : \"dc\"}", 
				criteria.getCriteriaObject().toString());
	}
	
	@Test
	public void shouldBuildScaleCriteriaCommand() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.scale(scaleH0())
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"scale.slug\" : \"h0\"}", 
				criteria.getCriteriaObject().toString());
	}
}
