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

import static org.springframework.data.mongodb.core.query.Criteria.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;

import com.trenako.SearchCriteria;

/**
 * It converts a {@link SearchCriteria} object to mongodb criteria.
 * @author Carlo Micieli
 *
 */
public class MongoSearchCriteria {

	private final SearchCriteria searchCriteria;
	
	/**
	 * Creates a new {@code MongoSearchCriteria}.
	 * @param searchCriteria the search criteria
	 */
	public MongoSearchCriteria(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	
	/**
	 * Returns a list of {@link Criteria}.
	 * @return the list of {@code Criteria}
	 */
	public Criteria[] criterias() {
		List<Criteria> crit = new ArrayList<Criteria>();
		
		if( searchCriteria.hasBrand() ) {
			crit.add(where("brandName").is(searchCriteria.getBrand()));
		}
		
		if( searchCriteria.hasCategory() ) {
			crit.add(where("category").is(searchCriteria.getCategory()));
		}
		
		if( searchCriteria.hasEra() ) {
			crit.add(where("era").is(searchCriteria.getEra()));
		}
		
		if( searchCriteria.hasPowerMethod() ) {
			crit.add(where("powerMethod").is(searchCriteria.getPowerMethod()));
		}
		
		if( searchCriteria.hasRailway() ) {
			crit.add(where("railwayName").is(searchCriteria.getRailway()));
		}
		
		if( searchCriteria.hasScale() ) {
			crit.add(where("scaleName").is(searchCriteria.getScale()));
		}
		
		return crit.toArray(new Criteria[crit.size()]);
	}
}
