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
package com.trenako.services.mongo;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenako.entities.RollingStock;
import com.trenako.repositories.RollingStocksRepository;
import com.trenako.services.RollingStocksService;

/**
 * A concrete implementation for the rolling stocks service for mongodb.
 * @author Carlo Micieli
 *
 */
@Service("rollingStocksService")
public class RollingStocksServiceImpl implements RollingStocksService {
	
	private RollingStocksRepository repo;
	
	@Autowired
	public RollingStocksServiceImpl(RollingStocksRepository repo) {
		this.repo = repo;
	}
	
	/**
	 * Finds the rolling stock document in the collection by id.
	 * @param id the rolling stock id.
	 * @return the rolling stock document if found; <em>null</em> otherwise.
	 */
	@Override
	public RollingStock findById(ObjectId id) {
		return repo.findById(id);
	}
	
	/**
	 Finds the rolling stock document in the collection by slug.
	 * @param slug the rolling stock slug.
	 * @return the rolling stock document if found; <em>null</em> otherwise.
	 */
	@Override
	public RollingStock findBySlug(String slug) {
		return repo.findBySlug(slug);
	}
	
	@Override
	public Iterable<RollingStock> findAll() {
		return repo.findAll();
	}
	
	/**
	 * Returns the rolling stock documents by brand name.
	 * @param brandName the brand name.
	 * @return the rolling stocks list.
	 */
	@Override
	public Iterable<RollingStock> findByBrand(String brandName) {
		return repo.findByBrand(brandName);
	}
	
	/**
	 * Returns the rolling stock documents by era.
	 * @param era the era.
	 * @return the rolling stocks list.
	 */
	@Override
	public Iterable<RollingStock> findByEra(String era) {
		return repo.findByEra(era);
	}

	/**
	 * Returns the rolling stock documents by scale.
	 * @param scale the scale.
	 * @return the rolling stocks list.
	 */
	@Override
	public Iterable<RollingStock> findByScale(String scale) {
		return repo.findByScale(scale);
	}
	
	/**
	 * Returns the rolling stock documents by category.
	 * @param category the category.
	 * @return the rolling stocks list.
	 */
	@Override
	public Iterable<RollingStock> findByCategory(String category) {
		return repo.findByCategory(category);
	}
	
	/**
	 * Returns the rolling stock documents by power method.
	 * @param powerMethod the power method.
	 * @return the rolling stocks list.
	 */
	@Override
	public Iterable<RollingStock> findByPowerMethod(String powerMethod) {
		return repo.findByPowerMethod(powerMethod);
	}

	/**
	 * Returns the rolling stock documents by railway.
	 * @param railwayName the railway name.
	 * @return the rolling stocks list.
	 */
	@Override
	public Iterable<RollingStock> findByRailwayName(String railwayName) {
		return repo.findByRailwayName(railwayName);
	}
	
	@Override
	public Iterable<RollingStock> findByBrandAndEra(String brandName, String era) {
		return repo.findByBrandAndEra(brandName, era);
	}
	
	@Override
	public Iterable<RollingStock> findByBrandAndScale(String brandName, String scale) {
		return repo.findByBrandAndScale(brandName, scale);
	}
	
	@Override
	public Iterable<RollingStock> findByBrandAndCategory(String brandName, String categoryName) {
		return repo.findByBrandAndCategory(brandName, categoryName);
	}
	
	@Override
	public Iterable<RollingStock> findByBrandAndRailway(String brandName, String railwayName) {
		return repo.findByBrandAndRailway(brandName, railwayName);
	}
	
	@Override
	public Iterable<RollingStock> findByTag(String tag) {
		return repo.findByTag(tag);
	}
	
	/**
	 * Saves the rolling stock document in the collection.
	 * @param rs a brand.
	 */
	@Override
	public void save(RollingStock rs) {
		repo.save(rs);
	}
	
	/**
	 * Remove the rolling stock document from the collection.
	 * @param rs a brand.
	 */
	@Override
	public void remove(RollingStock rs) {
		repo.remove(rs);
	}

}
