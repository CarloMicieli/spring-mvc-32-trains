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
package com.trenako.repositories.mongo;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.trenako.SearchCriteria;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.RollingStocksRepository;
import com.trenako.repositories.mongo.core.MongoRepository;

/**
 * The concrete implementation for rolling stocks repository for mongodb.
 * @author Carlo Micieli
 *
 */
@Repository("rollingStocksRepository")
public class RollingStocksRepositoryImpl implements RollingStocksRepository {

	private final MongoRepository<RollingStock> mongo;
	
	@Autowired
	public RollingStocksRepositoryImpl(MongoTemplate mongoOps) {
		this.mongo = new MongoRepository<RollingStock>(mongoOps, RollingStock.class);
	}
	
	// constructor: for testing
	RollingStocksRepositoryImpl(MongoRepository<RollingStock> mongo) {
		this.mongo = mongo;
	}

	@Override
	public RollingStock findBySlug(String slug) {
		return mongo.findOne("slug", slug);
	}

	@Override
	public Iterable<RollingStock> findByBrand(String brandName) {
		return mongo.find("brandName", brandName);
	}

	@Override
	public Iterable<RollingStock> findByEra(String era) {
		return mongo.find("era", era);
	}

	@Override
	public Iterable<RollingStock> findByScale(String scaleName) {
		return mongo.find("scaleName", scaleName);
	}

	@Override
	public Iterable<RollingStock> findByCategory(String category) {
		return mongo.find("category", category);
	}

	@Override
	public Iterable<RollingStock> findByPowerMethod(String powerMethod) {
		return mongo.find("powerMethod", powerMethod);
	}

	@Override
	public Iterable<RollingStock> findByRailwayName(String railwayName) {
		return mongo.find("railwayName", railwayName);
	}

	@Override
	public Iterable<RollingStock> findByBrandAndEra(String brandName, String era) {
		return mongo.find("brandName", brandName, "era", era);
	}

	@Override
	public Iterable<RollingStock> findByBrandAndScale(String brandName,
			String scale) {
		return mongo.find("brandName", brandName, "scaleName", scale);
	}

	@Override
	public Iterable<RollingStock> findByBrandAndCategory(String brandName,
			String categoryName) {
		return mongo.find("brandName", brandName, "category", categoryName);
	}

	@Override
	public Iterable<RollingStock> findByBrandAndRailway(String brandName,
			String railwayName) {
		return mongo.find("brandName", brandName, "railwayName", railwayName);
	}

	@Override
	public Iterable<RollingStock> findByTag(String tag) {
		return mongo.find("tags", tag);
	}

	@Override
	public RollingStock findById(ObjectId id) {
		return mongo.findById(id);
	}

	@Override
	public Iterable<RollingStock> findAll() {
		return mongo.findAll();
	}

	@Override
	public void save(RollingStock rs) {
		mongo.save(rs);
	}

	@Override
	public void remove(RollingStock rs) {
		mongo.remove(rs);
	}

	@Override
	public Iterable<RollingStock> find(SearchCriteria sc) {
		// TODO Auto-generated method stub
		return null;
	}
}
