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

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;

import com.trenako.SearchCriteria;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class MongoSearchCriteriaTests {
	
	@Test
	public void shouldBuildBrandCriteriaCommand() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand("ACME")
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"brandName\" : \"ACME\"}", criteria.getCriteriaObject().toString());
	}

	@Test
	public void shouldBuildCategoryCriteriaCommand() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.category("electric-locomotives")
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"category\" : \"electric-locomotives\"}", 
				criteria.getCriteriaObject().toString());
	}
	
	@Test
	public void shouldBuildCatCriteriaCommand() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.cat("ac-electric-locomotives")
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"category\" : \"electric-locomotives\" , \"powerMethod\" : \"ac\"}", 
				criteria.getCriteriaObject().toString());
	}
	
	@Test
	public void shouldBuildEraCriteriaCommand() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.era("IV")
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"era\" : \"IV\"}", 
				criteria.getCriteriaObject().toString());
	}
	
	@Test
	public void shouldBuildPowerMethodCriteriaCommand() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.powerMethod("ac-dcc")
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"powerMethod\" : \"ac-dcc\"}", 
				criteria.getCriteriaObject().toString());
	}
	
	@Test
	public void shouldBuildRailwayCriteriaCommand() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.railway("DB")
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"railwayName\" : \"DB\"}", 
				criteria.getCriteriaObject().toString());
	}
	
	@Test
	public void shouldBuildCriteriaWithOnlyOneCategoryAndPowerMethod() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.cat("ac-electric-locomotives")
			.category("diesel-locomotives")
			.powerMethod("dc")
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"category\" : \"electric-locomotives\" , \"powerMethod\" : \"ac\"}", 
				criteria.getCriteriaObject().toString());
	}
	
	@Test
	public void shouldBuildScaleCriteriaCommand() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.scale("H0")
			.build();
		
		Criteria criteria = MongoSearchCriteria.buildCriteria(sc);
		
		assertEquals("{ \"scaleName\" : \"H0\"}", 
				criteria.getCriteriaObject().toString());
	}
}
