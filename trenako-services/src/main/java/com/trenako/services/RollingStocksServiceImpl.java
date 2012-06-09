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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenako.SearchCriteria;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.RollingStocksRepository;

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
	
	@Override
	public RollingStock findById(ObjectId id) {
		return repo.findById(id);
	}
	
	@Override
	public RollingStock findBySlug(String slug) {
		return repo.findBySlug(slug);
	}

	@Override
	public Iterable<RollingStock> findAll(SearchCriteria sc) {
		return repo.findAll();
	}
	
	@Override
	public Iterable<RollingStock> findByBrand(String brandName) {
		return repo.findByBrand(brandName);
	}

	@Override
	public Iterable<RollingStock> findByEra(String era) {
		return repo.findByEra(era);
	}

	@Override
	public Iterable<RollingStock> findByScale(String scale) {
		return repo.findByScale(scale);
	}

	@Override
	public Iterable<RollingStock> findByCategory(String category) {
		return repo.findByCategory(category);
	}

	@Override
	public Iterable<RollingStock> findByPowerMethod(String powerMethod) {
		return repo.findByPowerMethod(powerMethod);
	}

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
	
	@Override
	public void save(RollingStock rs) {
		repo.save(rs);
	}

	@Override
	public void remove(RollingStock rs) {
		repo.remove(rs);
	}
}
