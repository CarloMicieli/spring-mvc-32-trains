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

import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import com.trenako.SearchCriteria;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.BrowseRepository;
import com.trenako.results.PaginatedResults;
import com.trenako.results.RangeRequest;
import com.trenako.results.mongo.RollingStockResults;

/**
 * The concrete implementation for the browse repository.
 * @author Carlo Micieli
 *
 */
public class BrowseRepositoryImpl implements BrowseRepository {
	
	private final MongoTemplate mongo;
	
	/**
	 * Creates a new {@code BrowseRepositoryImpl}.
	 * @param mongo the mongo template
	 */
	@Autowired
	public BrowseRepositoryImpl(MongoTemplate mongo) {
		this.mongo = mongo;
	}

	private Iterable<RollingStock> runQuery(Query query) {
		query.sort().on("lastModified", Order.DESCENDING);
		return mongo.find(query, RollingStock.class);
	}
	
	private List<RollingStock> runQuery2(Query query) {
		query.sort().on("lastModified", Order.DESCENDING);
		return mongo.find(query, RollingStock.class);
	}
	
	@Override
	public PaginatedResults<RollingStock> findByBrand(String brand, RangeRequest range) {
		return new RollingStockResults(
				runQuery2(query(where("brandName").is(brand))), range);
	}

	@Override
	public Iterable<RollingStock> findByEra(String era) {
		return runQuery(query(where("era").is(era)));
	}

	@Override
	public Iterable<RollingStock> findByScale(String scale) {
		return runQuery(query(where("scaleName").is(scale)));
	}

	@Override
	public Iterable<RollingStock> findByCategory(String category) {
		return runQuery(query(where("category").is(category)));
	}

	@Override
	public Iterable<RollingStock> findByPowerMethod(String powerMethod) {
		return runQuery(query(where("powerMethod").is(powerMethod)));
	}

	@Override
	public Iterable<RollingStock> findByRailway(String railway) {
		return runQuery(query(where("railwayName").is(railway)));
	}

	@Override
	public Iterable<RollingStock> findByBrandAndEra(String brand, String era) {
		return runQuery(query(where("brandName").is(brand).and("era").is(era)));
	}

	@Override
	public Iterable<RollingStock> findByBrandAndScale(String brand, String scale) {
		return runQuery(query(where("brandName").is(brand).and("scaleName").is(scale)));
	}

	@Override
	public Iterable<RollingStock> findByBrandAndCategory(String brand, String category) {
		return runQuery(query(where("brandName").is(brand).and("category").is(category)));
	}

	@Override
	public Iterable<RollingStock> findByBrandAndRailway(String brand, String railway) {
		return runQuery(query(where("brandName").is(brand).and("railwayName").is(railway)));
	}

	@Override
	public Iterable<RollingStock> findByCriteria(SearchCriteria sc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<RollingStock> findByTag(String tag) {
		return runQuery(query(where("tag").is(tag)));
	}

}
