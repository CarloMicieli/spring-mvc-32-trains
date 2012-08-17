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

import com.trenako.entities.RollingStock;
import com.trenako.services.view.RollingStockView;

/**
 * The interface for the rolling stocks service.
 * <p>
 * The concrete classes that implements the {@code RollingStocksService} interface will provide
 * the following functionalities:
 * <ul>
 * <li>returns the {@code RollingStock} by id;</li>
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
	 * Finds the {@code RollingStock} with the provided id.
	 * @param id the unique id
	 * @return a {@code RollingStock} if found; {@code null} otherwise
	 */
	RollingStock findById(ObjectId id);

	/**
	 * Finds the {@code RollingStock} with the provided slug.
	 * @param slug the slug
	 * @return a {@code RollingStock} if found; {@code null} otherwise
	 * @see com.trenako.entities.RollingStock#getSlug()
	 */
	RollingStock findBySlug(String slug);

	/**
	 * Finds the {@code RollingStock} with the provided slug and fills its view.
	 * <p>
	 * A {@code RollingStock} view contains also other information for the rolling 
	 * stock like its comments, its reviews and so on.
	 * </p>
	 * @param slug the {@code RollingStock} slug
	 * @return a view if {@code RollingStock} if found; {@code null} otherwise
	 */
	RollingStockView findViewBySlug(String slug);
	
	/**
	 * Persists a {@code RollingStock} changes in the data store.
	 * <p>
	 * This method performs a "upsert": if the {@code RollingStock} is not present in the data store
	 * a new {@code RollingStock} is created; otherwise the method will update the existing {@code RollingStock}. 
	 * </p>	 
	 * @param rs the {@code RollingStock} to be saved
	 */
	void save(RollingStock rs);

	/**
	 * Removes a {@code RollingStock} from the data store.
	 * @param rs the {@code RollingStock} to be removed
	 */
	void remove(RollingStock rs);
}
