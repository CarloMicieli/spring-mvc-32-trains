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

import com.trenako.SearchCriteria;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;

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
	 * Returns a {@link RollingStock} list.
	 * <p>
	 * The clients must use this method when two or more search criteria are needed.
	 * </p>
	 *
	 * <p>
	 * This method returns at most a number of items from {@link com.trenako.AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending {@link RollingStock#getLastModified()}.
	 * </p>
	 *
	 * @param criteria the search criteria
	 * @return a {@code RollingStock} list
	 * @see com.trenako.SearchCriteria
	 */
	Iterable<RollingStock> findAll(SearchCriteria criteria);
	
	/**
	 * Returns a list of {@link RollingStock} with the same brand.
	 * <p>
	 * This method is using the {@link Brand#getSlug()} value as a search key.
	 * </p>
	 * <p>
	 * This method returns at most a number of items from {@link com.trenako.AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param brand the brand
	 * @return a {@code RollingStock} list
	 * @see com.trenako.entities.Brand
	 */
	Iterable<RollingStock> findByBrand(String brand);

	/**
	 * Returns a list of {@link RollingStock} with the same era.
	 * <p>
	 * This method is performing a case sensitive search (ie {@code iv} and {@code IV} produce different results).
	 * </p>
	 * <p>
	 * This method returns at most a number of items from {@link com.trenako.AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param era the era
	 * @return a {@code RollingStock} list
	 * @see com.trenako.entities.Era
	 */	 
	Iterable<RollingStock> findByEra(String era);

	/**
	 * Returns a list of {@link RollingStock} with the same scale.
	 * <p>
	 * This method is performing a case sensitive search (ie {@code h0} and {@code H0} produce different results).
	 * </p>
	 * <p>
	 * This method returns at most a number of items from {@link com.trenako.AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param scale the scale
	 * @return a {@code RollingStock} list
	 * @see com.trenako.entities.Scale
	 */
	Iterable<RollingStock> findByScale(String scale);

	/**
	 * Returns a list of {@link RollingStock} with the same category.
	 * <p>
	 * This method returns at most a number of items from {@link com.trenako.AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param category the category
	 * @return a {@code RollingStock} list
	 * @see com.trenako.entities.Category
	 */
	Iterable<RollingStock> findByCategory(String category);

	/**
	 * Returns a list of {@link RollingStock} with the same power method.
	 * <p>
	 * This method returns at most a number of items from {@link com.trenako.AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param powerMethod the power method
	 * @return a {@code RollingStock} list
	 * @see com.trenako.entities.PowerMethod
	 */	 
	Iterable<RollingStock> findByPowerMethod(String powerMethod);

	/**
	 * Returns a list of {@link RollingStock} with the same railway.
	 * <p>
	 * This method is using the {@link Railway#getSlug()} value as a search key.
	 * </p>	 
	 * <p>
	 * This method returns at most a number of items from {@link com.trenako.AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param railway the railway
	 * @return a {@code RollingStock} list
	 * @see com.trenako.entities.Railway
	 */	
	Iterable<RollingStock> findByRailwayName(String railway);

	/**
	 * Returns the rolling stock documents by brand and era.
	 * @param brandName the brand name.
	 * @param era the era.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByBrandAndEra(String brandName, String era);

	/**
	 * Returns the rolling stock documents by brand and scale.
	 * @param brandName the brand name.
	 * @param scale the scale.
	 * @return the rolling stocks list.
	 */	
	Iterable<RollingStock> findByBrandAndScale(String brandName, String scale);

	/**
	 * Returns the rolling stock documents by brand and category.
	 * @param brandName the brand name.
	 * @param category the category.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByBrandAndCategory(String brandName,
			String category);

	/**
	 * Returns the rolling stock documents by brand and railway.
	 * @param brandName the brand name.
	 * @param railwayName the railway.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByBrandAndRailway(String brandName, String railwayName);

	/**
	 * Returns a list of {@link RollingStock} with the same tag.
	 * <p>
	 * This method returns at most a number of items from {@link com.trenako.AppGlobals#MAX_RESULT_SET_SIZE}; 
	 * the results are sort by descending posted date. 
	 * </p>
	 *
	 * @param tag the tag
	 * @return a {@code RollingStock} list
	 */
	Iterable<RollingStock> findByTag(String tag);

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
}
