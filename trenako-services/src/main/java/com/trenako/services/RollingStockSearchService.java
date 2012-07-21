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
package com.trenako.services;

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.RollingStock;
import com.trenako.results.PaginatedResults;
import com.trenako.results.RangeRequest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public interface RollingStockSearchService {

	/**
	 * Returns the {@code RollingStock} list according the provided search criteria.
	 * @param sc the search criteria
	 * @param range the {@code RangeRequest} information
	 * @return a {@code RollingStock} list
	 */
	PaginatedResults<RollingStock> findByCriteria(SearchCriteria sc, RangeRequest range);

}
