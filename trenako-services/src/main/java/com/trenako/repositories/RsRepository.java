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
package com.trenako.repositories;

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.RollingStock;
import com.trenako.results.PaginatedResults;
import com.trenako.results.RangeRequest;

public interface RsRepository {
	/**
	 * Returns the {@code RollingStock} list by {@code Brand} name.
	 * @param brand the brand name
	 * @param range the {@code RangeRequest} information
	 * @return the {@code RollingStock} list
	 */
	PaginatedResults<RollingStock> findByBrand(String brand, RangeRequest range);
	
	/**
	 * Returns the {@code RollingStock} list by {@code Era}.
	 * @param era the era
	 * @param range the {@code RangeRequest} information
	 * @return the {@code RollingStock} list
	 */
	PaginatedResults<RollingStock> findByEra(String era, RangeRequest range);

	/**
	 * Returns the {@code RollingStock} list by {@code Scale} name.
	 * @param scale the {@code Scale} name
	 * @param range the {@code RangeRequest} information
	 * @return the {@code RollingStock} list
	 */
	PaginatedResults<RollingStock> findByScale(String scale, RangeRequest range);
	
	/**
	 * Returns the {@code RollingStock} list by {@code Category} name.
	 * @param category the {@code Category} name
	 * @param range the {@code RangeRequest} information
	 * @return the {@code RollingStock} list
	 */
	PaginatedResults<RollingStock> findByCategory(String category, RangeRequest range);
	
	/**
	 * Returns the {@code RollingStock} list by power method.
	 * @param powerMethod the power method
	 * @param range the {@code RangeRequest} information
	 * @return the {@code RollingStock} list
	 */
	PaginatedResults<RollingStock> findByPowerMethod(String powerMethod, RangeRequest range);

	/**
	 * Returns the {@code RollingStock} list by {@code Railway} name.
	 * @param railway the {@code Railway} name
	 * @return the {@code RollingStock} list
	 */
	PaginatedResults<RollingStock> findByRailway(String railway, RangeRequest range);
	
	/**
	 * Returns the {@code RollingStock} list by {@code Brand} name and era.
	 * @param brand the {@code Brand} name
	 * @param era the era
	 * @param range the {@code RangeRequest} information
	 * @return the {@code RollingStock} list
	 */
	PaginatedResults<RollingStock> findByBrandAndEra(String brand, String era, RangeRequest range);
	
	/**
	 * Returns the {@code RollingStock} list by {@code Brand} name and {@code Scale} name.
	 * @param brand the {@code Brand} name
	 * @param scale the {@code Scale} name
	 * @param range the {@code RangeRequest} information
	 * @return the {@code RollingStock} list
	 */
	PaginatedResults<RollingStock> findByBrandAndScale(String brand, String scale, RangeRequest range);
	
	/**
	 * Returns the {@code RollingStock} list by {@code Brand} name and category.
	 * @param brand the {@code Brand} name
	 * @param category the category name
	 * @param range the {@code RangeRequest} information
	 * @return the {@code RollingStock} list
	 */
	PaginatedResults<RollingStock> findByBrandAndCategory(String brand, String category, RangeRequest range);
	
	/**
	 * Returns the {@code RollingStock} list by {@code Brand} name and {@code Railway} name.
	 * @param brand the {@code Brand} name
	 * @param railway the {@code Railway} name
	 * @param range the {@code RangeRequest} information
	 * @return the {@code RollingStock} list
	 */
	PaginatedResults<RollingStock> findByBrandAndRailway(String brand, String railway, RangeRequest range);
	
	/**
	 * Returns the {@code RollingStock} list according the provided search criteria.
	 * @param sc the search criteria
	 * @param range the {@code RangeRequest} information
	 * @return a {@code RollingStock} list
	 */
	PaginatedResults<RollingStock> findByCriteria(SearchCriteria sc, RangeRequest range);
	
	/**
	 * Returns the {@code RollingStock} list with the provided tag.
	 * @param tag the tag value
	 * @param range the {@code RangeRequest} information
	 * @return a {@code RollingStock} list
	 */
	PaginatedResults<RollingStock> findByTag(String tag, RangeRequest range);
}
