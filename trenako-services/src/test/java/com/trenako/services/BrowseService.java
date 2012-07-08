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

import com.trenako.SearchCriteria;
import com.trenako.entities.RollingStock;

/**
 * It represents a service for the rolling stocks browsing.
 * @author Carlo Micieli
 *
 */
public interface BrowseService {
	/**
	 * Returns the {@code RollingStock} list by {@code Brand} name.
	 * @param brand the brand name
	 * @return the {@code RollingStock} list
	 */
	Iterable<RollingStock> findByBrand(String brand);
	
	/**
	 * Returns the {@code RollingStock} list by {@code Era}.
	 * @param era the era
	 * @return the {@code RollingStock} list
	 */
	Iterable<RollingStock> findByEra(String era);

	/**
	 * Returns the {@code RollingStock} list by {@code Scale} name.
	 * @param scale the {@code Scale} name
	 * @return the {@code RollingStock} list
	 */
	Iterable<RollingStock> findByScale(String scale);
	
	/**
	 * Returns the {@code RollingStock} list by {@code Category} name.
	 * @param category the {@code Category} name
	 * @return the {@code RollingStock} list
	 */
	Iterable<RollingStock> findByCategory(String category);
	
	/**
	 * Returns the {@code RollingStock} list by power method.
	 * @param powerMethod the power method
	 * @return the {@code RollingStock} list
	 */
	Iterable<RollingStock> findByPowerMethod(String powerMethod);

	/**
	 * Returns the {@code RollingStock} list by {@code Railway} name.
	 * @param railway the {@code Railway} name
	 * @return the {@code RollingStock} list
	 */
	Iterable<RollingStock> findByRailway(String railway);
	
	/**
	 * Returns the {@code RollingStock} list by {@code Brand} name and era.
	 * @param brand the {@code Brand} name
	 * @param era the era
	 * @return the {@code RollingStock} list
	 */
	Iterable<RollingStock> findByBrandAndEra(String brand, String era);
	
	/**
	 * Returns the {@code RollingStock} list by {@code Brand} name and {@code Scale} name.
	 * @param brand the {@code Brand} name
	 * @param scale the {@code Scale} name
	 * @return the {@code RollingStock} list
	 */
	Iterable<RollingStock> findByBrandAndScale(String brand, String scale);
	
	/**
	 * Returns the {@code RollingStock} list by {@code Brand} name and category.
	 * @param brand the {@code Brand} name
	 * @param category the category name
	 * @return the {@code RollingStock} list
	 */
	Iterable<RollingStock> findByBrandAndCategory(String brand, String category);
	
	/**
	 * Returns the {@code RollingStock} list by {@code Brand} name and {@code Railway} name.
	 * @param brand the {@code Brand} name
	 * @param railway the {@code Railway} name
	 * @return the {@code RollingStock} list
	 */
	Iterable<RollingStock> findByBrandAndRailway(String brand, String railway);
	
	/**
	 * Returns the {@code RollingStock} list according the provided search criteria.
	 * @param sc the search criteria
	 * @return a {@code RollingStock} list
	 */
	Iterable<RollingStock> findByCriteria(SearchCriteria sc);
	
	/**
	 * 
	 * @param tag
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByTag(String tag);
}
