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
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.repositories.BrowseRepository;
import com.trenako.results.PaginatedResults;
import com.trenako.results.RangeRequest;
import com.trenako.results.RollingStockResults;

/**
 * The concrete implementation for the browse repository.
 * @author Carlo Micieli
 *
 */
@Repository("browseRepository")
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

	@Override
	public Iterable<Brand> getBrands() {
		return findAll(Brand.class);
	}

	@Override
	public Iterable<Scale> getScales() {
		return findAll(Scale.class);
	}

	@Override
	public Iterable<Railway> getRailways() {
		return findAll(Railway.class);
	}

	@Override
	public PaginatedResults<RollingStock> findByCriteria(SearchCriteria sc, RangeRequest range) {
		return runRangeQuery(MongoSearchCriteria.buildCriteria(sc), sc, range);
	}

	@Override
	public PaginatedResults<RollingStock> findByTag(String tag, RangeRequest range) {
		return runRangeQuery(where("tag").is(tag), new SearchCriteria(), range);
	}

	@Override
	public <T> T findBySlug(String slug, Class<T> entityClass) {
		return mongo.findOne(query(where("slug").is(slug)), entityClass);
	}
	
	private RollingStockResults runRangeQuery(Criteria criteria, SearchCriteria sc, RangeRequest range) {
		final Query query = buildQuery(criteria, range);
		final List<RollingStock> results = mongo.find(query, RollingStock.class);
		return new RollingStockResults(results, sc, range);
	}
	
	private static final Sort NAME_SORT = new Sort(Direction.ASC, "name");
	private <T> Iterable<T> findAll(Class<T> clazz) {
		Query query = new Query();
		query.with(NAME_SORT);
		return mongo.find(query, clazz);
	}
}
