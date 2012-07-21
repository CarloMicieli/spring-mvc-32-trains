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

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.RollingStocksSearchRepository;
import com.trenako.results.PaginatedResults;
import com.trenako.results.RangeRequest;
import com.trenako.results.mongo.RollingStockResults;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Repository("rollingStocksSearchRepository")
public class RollingStocksSearchRepositoryImpl implements RollingStocksSearchRepository {

	private final MongoTemplate mongo;

	/**
	 * Creates a new {@code RollingStocksSearchRepositoryImpl}.
	 * @param mongo the mongo template
	 */
	@Autowired
	public RollingStocksSearchRepositoryImpl(MongoTemplate mongo) {
		this.mongo = mongo;
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
