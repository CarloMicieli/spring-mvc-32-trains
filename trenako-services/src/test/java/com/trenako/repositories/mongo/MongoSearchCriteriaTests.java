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

import com.trenako.SearchCriteria;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class MongoSearchCriteriaTests {

	@Test
	public void shouldFillTheListOfCriteria() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand("ACME")
			.category("electric-locomotives")
			.era("IV")
			.powerMethod("AC")
			.railway("DB")
			.scale("H0")
			.build();
		
		MongoSearchCriteria mongoCrit = 
				new MongoSearchCriteria(sc);
		
		assertEquals(6, mongoCrit.criterias().length);	
	}

}
