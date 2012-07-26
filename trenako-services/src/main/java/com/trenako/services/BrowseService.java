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

import com.trenako.criteria.SearchRequest;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.results.PaginatedResults;
import com.trenako.results.RangeRequest;
import com.trenako.values.Category;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;

/**
 * It represents a service for the rolling stocks browsing.
 * @author Carlo Micieli
 *
 */
public interface BrowseService {

	/**
	 * Returns the list of model railway {@code Era}.
	 * @return the eras list
	 */
	Iterable<LocalizedEnum<Era>> eras();
	
	/**
	 * Returns the list of model railway {@code Category}.
	 * @return the categories list
	 */
	Iterable<LocalizedEnum<Category>> categories();
	
	/**
	 * Returns the list of model railway {@code PowerMethod}.
	 * @return the power methods list
	 */
	Iterable<LocalizedEnum<PowerMethod>> powerMethods();
	
	/**
	 * Returns the list of model railway {@code Scale}.
	 * @return the scales list
	 */
	Iterable<Scale> scales();
	
	/**
	 * Returns the list of {@code Railway}.
	 * @return the scales list
	 */
	Iterable<Railway> railways();

	/**
	 * Returns the list of model railway {@code Brand}.
	 * @return the brands list
	 */
	Iterable<Brand> brands();
	
	/**
	 * Returns the {@code RollingStock} list according the provided search criteria.
	 * @param sc the search criteria
	 * @param range the {@code RangeRequest} information
	 * @return a {@code RollingStock} list
	 */
	PaginatedResults<RollingStock> findByCriteria(SearchRequest sc, RangeRequest range);

	/**
	 * Returns the {@code Brand} with the provided slug.
	 * @param slug the slug
	 * @return a {@code Brand} if found; {@code null} otherwise
	 */
	Brand findBrand(String slug);
	
	/**
	 * Returns the {@code Railway} with the provided slug.
	 * @param slug the slug
	 * @return a {@code Railway} if found; {@code null} otherwise
	 */
	Railway findRailway(String slug);
	
	/**
	 * Returns the {@code Scale} with the provided slug.
	 * @param slug the slug
	 * @return a {@code Scale} if found; {@code null} otherwise
	 */
	Scale findScale(String slug);
	
	/**
	 * Returns the {@code Category} with the provided slug.
	 * @param slug the slug
	 * @return a {@code Category} if found; {@code null} otherwise
	 */
	LocalizedEnum<Category> findCategory(String slug);
	
	/**
	 * Returns the {@code Era} with the provided slug.
	 * @param slug the slug
	 * @return a {@code Era} if found; {@code null} otherwise
	 */
	LocalizedEnum<Era> findEra(String slug);
}
