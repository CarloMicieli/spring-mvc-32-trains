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

import org.springframework.data.mongodb.core.query.Criteria;

import com.trenako.criteria.SearchCriteria;

/**
 * It converts a {@link SearchCriteria} object to mongodb criteria.
 * @author Carlo Micieli
 *
 */
public final class MongoSearchCriteria {

	private MongoSearchCriteria() {
	}

	/**
	 * Builds a mongo criteria for the provided {@code SearchCriteria}.
	 * @param sc the search criteria
	 * @return a mongo criteria
	 */
	public static Criteria buildCriteria(SearchCriteria sc) {
		Criteria c = new Criteria();
		
		if (sc.hasBrand()) {
			c.and("brandName").is(sc.getBrand());
		}
		
		if (sc.hasEra()) {
			c.and("era").is(sc.getEra());
		}
		
		if (sc.hasRailway()) {
			c.and("railwayName").is(sc.getRailway());
		}
		
		if (sc.hasScale()) {
			c.and("scaleName").is(sc.getScale());
		}
		
		if (sc.hasCat()) {
			c.and("category").is(sc.getCat().category())
				.and("powerMethod").is(sc.getCat().powerMethod());
		}
		
		if (!sc.hasCat() && sc.hasPowerMethod()) {
			c.and("powerMethod").is(sc.getPowerMethod());
		}

		if (!sc.hasCat() && sc.hasCategory()) {
			c.and("category").is(sc.getCategory());
		}
		
		return c;
	}
}
