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

import org.bson.types.ObjectId;

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.values.Category;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;

/**
 * The interface for the rolling stocks service.
 * <p>
 * The concrete classes that implements the {@code RollingStocksService} interface will provide
 * the following functionalities:
 * <ul>
 * <li>returns the {@code RollingStock} by id;</li>
 * <li>returns the {@code RollingStock} list by a {@link SearchCriteria};</li>
 * <li>returns the {@code RollingStock} list;</li>
 * <li>saves/removes a {@code RollingStock}.</li>
 * </ul>
 * </p>
 * <p>
 * The service provide search methods with only one search criteria, if more search criteria are needed
 * the clients should use the {@link RollingStocksService#findAll(SearchCriteria)} method.
 * </p>
 *
 * @author Carlo Micieli
 * @see com.trenako.entities.RollingStock
 */ 
public interface RollingStocksService {

	/**
	 * Finds the {@link RollingStock} with the provided id.
	 * @param id the unique id
	 * @return a {@code RollingStock} if found; {@code null} otherwise
	 */
	RollingStock findById(ObjectId id);

	/**
	 * Finds the {@link RollingStock} with the provided slug.
	 * @param slug the slug
	 * @return a {@code RollingStock} if found; {@code null} otherwise
	 * @see com.trenako.entities.RollingStock#getSlug()
	 */
	RollingStock findBySlug(String slug);

	/**
	 * Persists a {@link RollingStock} changes in the data store.
	 * <p>
	 * This method performs a "upsert": if the {@code RollingStock} is not present in the data store
	 * a new {@code RollingStock} is created; otherwise the method will update the existing {@code RollingStock}. 
	 * </p>	 
	 * @param rs the {@code RollingStock} to be saved
	 */
	void save(RollingStock rs);

	/**
	 * Removes a {@link RollingStock} from the data store.
	 * @param rs the {@code RollingStock} to be removed
	 */
	void remove(RollingStock rs);
	
	/**
	 * Returns the list of {@code Brand}s.
	 * @return the {@code Brand} list
	 */
	Iterable<Brand> brands();

	/**
	 * Returns the list of {@code Railway}s.
	 * @return the {@code Railway} list
	 */
	Iterable<Railway> railways();

	/**
	 * Returns the list of {@code Scale}s.
	 * @return the {@code Scale} list
	 */
	Iterable<Scale> scales();

	/**
	 * Returns the list of categories.
	 * @return the {@code Category} list
	 */
	Iterable<LocalizedEnum<Category>> categories();
	
	/**
	 * Returns the list of eras.
	 * @return the {@code Era} list
	 */
	Iterable<LocalizedEnum<Era>> eras();
	
	/**
	 * Returns the list of power methods.
	 * @return the {@code power method} list
	 */
	Iterable<LocalizedEnum<PowerMethod>> powerMethods();

	/**
	 * Returns the {@code Brand} with the provided id.
	 * @param brandId the {@code Brand} id
	 * @return a {@code Brand} if found; {@code null} otherwise
	 */
	Brand findBrand(ObjectId brandId);
	
	/**
	 * Returns the {@code Scale} with the provided id.
	 * @param scaleId the {@code Scale} id
	 * @return a {@code Scale} if found; {@code null} otherwise
	 */
	Scale findScale(ObjectId scaleId);
	
	/**
	 * Returns the {@code Railway} with the provided id.
	 * @param railwayId the {@code Railway} id
	 * @return a {@code Railway} if found; {@code null} otherwise
	 */
	Railway findRailway(ObjectId railwayId);
}
