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

import static com.trenako.repositories.mongo.RollingStockQueryBuilder.buildQuery;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.trenako.SearchCriteria;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.RsRepository;
import com.trenako.results.PaginatedResults;
import com.trenako.results.RangeRequest;
import com.trenako.results.mongo.RollingStockResults;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Repository("rsRepository")
public class RsRepositoryImpl implements RsRepository {
	
	private final MongoTemplate mongo;
	
	/**
	 * Creates a new {@code RsRepositoryImpl}.
	 * @param mongo the mongo template
	 */
	@Autowired
	public RsRepositoryImpl(MongoTemplate mongo) {
		this.mongo = mongo;
	}
	
	@Override
	public PaginatedResults<RollingStock> findByBrand(String brand, RangeRequest range) {
		return runRangeQuery(where("brandName").is(brand), range);
	}

	@Override
	public PaginatedResults<RollingStock> findByEra(String era, RangeRequest range) {
		return runRangeQuery(where("era").is(era), range);
	}

	@Override
	public PaginatedResults<RollingStock> findByScale(String scale, RangeRequest range) {
		return runRangeQuery(where("scaleName").is(scale), range);
	}

	@Override
	public PaginatedResults<RollingStock> findByCategory(String category, RangeRequest range) {
		return runRangeQuery(where("category").is(category), range);
	}

	@Override
	public PaginatedResults<RollingStock> findByPowerMethod(String powerMethod, RangeRequest range) {
		return runRangeQuery(where("powerMethod").is(powerMethod), range);
	}

	@Override
	public PaginatedResults<RollingStock> findByRailway(String railway, RangeRequest range) {
		return runRangeQuery(where("railwayName").is(railway), range);
	}

	@Override
	public PaginatedResults<RollingStock> findByBrandAndEra(String brand, String era, RangeRequest range) {
		return runRangeQuery(where("brandName").is(brand).and("era").is(era), range);
	}

	@Override
	public PaginatedResults<RollingStock> findByBrandAndScale(String brand, String scale, RangeRequest range) {
		return runRangeQuery(where("brandName").is(brand).and("scaleName").is(scale), range);
	}

	@Override
	public PaginatedResults<RollingStock> findByBrandAndCategory(String brand, String category, RangeRequest range) {
		return runRangeQuery(where("brandName").is(brand).and("category").is(category), range);
	}

	@Override
	public PaginatedResults<RollingStock> findByBrandAndRailway(String brand, String railway, RangeRequest range) {
		return runRangeQuery(where("brandName").is(brand).and("railwayName").is(railway), range);
	}

	@Override
	public PaginatedResults<RollingStock> findByCriteria(SearchCriteria sc, RangeRequest range) {
		return runRangeQuery(MongoSearchCriteria.buildCriteria(sc), range);
	}

	@Override
	public PaginatedResults<RollingStock> findByTag(String tag, RangeRequest range) {
		return runRangeQuery(where("tag").is(tag), range);
	}

	private RollingStockResults runRangeQuery(Criteria criteria, RangeRequest range) {
		final Query query = buildQuery(criteria, range);
		final List<RollingStock> results = mongo.find(query, RollingStock.class);
		return new RollingStockResults(results, range);
	}
}
