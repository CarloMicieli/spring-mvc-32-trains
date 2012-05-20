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

import org.bson.types.ObjectId;

import com.trenako.entities.RollingStock;

public interface RollingStocksRepository {
	/**
	 * Finds the rolling stock document in the collection by id.
	 * @param id the rolling stock id.
	 * @return the rolling stock document if found; <em>null</em> otherwise.
	 */
	RollingStock findById(ObjectId id);
	
	/**
	 Finds the rolling stock document in the collection by slug.
	 * @param slug the rolling stock slug.
	 * @return the rolling stock document if found; <em>null</em> otherwise.
	 */
	RollingStock findBySlug(String slug);
	
	/**
	 * Returns the rolling stock documents by brand name.
	 * @param brandName the brand name.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByBrand(String brandName);
	
	/**
	 * Returns the rolling stock documents in the collection.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findAll();
	
	/**
	 * Returns the rolling stock documents by era.
	 * @param era the era.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByEra(String era);

	/**
	 * Returns the rolling stock documents by scale.
	 * @param scale the scale.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByScale(String scale);
	
	/**
	 * Returns the rolling stock documents by category.
	 * @param category the category.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByCategory(String category);
	
	/**
	 * Returns the rolling stock documents by power method.
	 * @param powerMethod the power method.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByPowerMethod(String powerMethod);

	/**
	 * Returns the rolling stock documents by railway.
	 * @param railwayName the railway name.
	 * @return the rolling stocks list.
	 */
	Iterable<RollingStock> findByRailwayName(String railwayName);
	
	Iterable<RollingStock> findByBrandAndEra(String brandName, String era);
	Iterable<RollingStock> findByBrandAndScale(String brandName, String scale);
	Iterable<RollingStock> findByBrandAndCategory(String brandName, String categoryName);
	Iterable<RollingStock> findByBrandAndRailway(String brandName, String railwayName);
	
	Iterable<RollingStock> findByTag(String tag);
	
	/**
	 * Saves the rolling stock document in the collection.
	 * @param rs a brand.
	 */
	void save(RollingStock rs);
	
	/**
	 * Remove the rolling stock document from the collection.
	 * @param rs a brand.
	 */
	void remove(RollingStock rs);

}
