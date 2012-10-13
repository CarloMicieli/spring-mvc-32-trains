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

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.trenako.results.RangeRequest;


import static org.springframework.data.mongodb.core.query.Criteria.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockQueryBuilderTests {

	private final Object max = "maxValue";
	private final Object since = "sinceValue";
	private final Criteria where = where("brandName").is("ACME");
	
	@Test
	public void shouldBuildQueryWithADateRange() {
		Query query = RollingStockQueryBuilder.buildQuery(where, range(fulldate("2012/06/01 10:30:00.500"), fulldate("2012/06/30 10:30:00.500")));
		assertQuery(query, "{ \"brandName\" : \"ACME\" , "+
				"\"lastModified\" : { \"$lt\" : { \"$date\" : \"2012-06-01T08:30:00.500Z\"} , \"$gt\" : { \"$date\" : \"2012-06-30T08:30:00.500Z\"}}}");
		assertSort(query, "{ \"lastModified\" : -1}");
		assertLimit(query, 11);
	}
	
	@Test
	public void shouldBuildQueryWithAObjectIdRange() {
		Query query = RollingStockQueryBuilder.buildQuery(where, range(new ObjectId("47cc67093475061e3d95369d"), new ObjectId("47cc67093475061e3d95389e")));
		assertQuery(query, "{ \"brandName\" : \"ACME\" , "+
				"\"lastModified\" : { \"$lt\" : { \"$oid\" : \"47cc67093475061e3d95369d\"} , \"$gt\" : { \"$oid\" : \"47cc67093475061e3d95389e\"}}}");
		assertSort(query, "{ \"lastModified\" : -1}");
		assertLimit(query, 11);
	}
	
	@Test
	public void shouldBuildAQueryWithDefaultSortingAndCompleteRange() {
		Query query = RollingStockQueryBuilder.buildQuery(where, range(since, max));
		assertQuery(query, "{ \"brandName\" : \"ACME\" ," +
				" \"lastModified\" : { \"$lt\" : \"sinceValue\" ," +
				" \"$gt\" : \"maxValue\"}}");
		assertSort(query, "{ \"lastModified\" : -1}");
		assertLimit(query, 11);
	}
	
	@Test
	public void shouldBuildQueryWithSinceValueOnly() {
		Query query = RollingStockQueryBuilder.buildQuery(where, range(since, null));
		assertQuery(query, "{ \"brandName\" : \"ACME\" ," +
				" \"lastModified\" : { \"$lt\" : \"sinceValue\"}}");
		assertSort(query, "{ \"lastModified\" : -1}");
		assertLimit(query, 11);
	}
	
	@Test
	public void shouldBuildQueryWithMaxValueOnly() {
		Query query = RollingStockQueryBuilder.buildQuery(where, range(null, max));
		assertQuery(query, "{ \"brandName\" : \"ACME\" ," +
				" \"lastModified\" : { \"$gt\" : \"maxValue\"}}");
		assertSort(query, "{ \"lastModified\" : -1}");
		assertLimit(query, 11);
	}

	@Test
	public void shouldBuildQueryWithSorting() {
		Query query = RollingStockQueryBuilder.buildQuery(where, range(null, max, "powerMethod", Direction.ASC));
		assertQuery(query, "{ \"brandName\" : \"ACME\" ," +
				" \"powerMethod\" : { \"$gt\" : \"maxValue\"}}");
		assertSort(query, "{ \"powerMethod\" : 1}");
		assertLimit(query, 11);
	}
	
	private RangeRequest range(Object since, Object max) {
		RangeRequest range = new RangeRequest();
		range.setMax(max);
		range.setSince(since);
		range.setSize(10);
		return range;
	}
	
	private RangeRequest range(Object since, Object max, String sort, Direction dir) {
		RangeRequest range = new RangeRequest();
		range.setMax(max);
		range.setSince(since);
		range.setSize(10);
		range.setSort(new Sort(dir, sort));
		return range;
	}
	
	private void assertLimit(Query query, int count) {
		assertEquals(count, query.getLimit());
	}
	
	private void assertQuery(Query query, String queryText) {
		assertEquals(queryText, query.getQueryObject().toString());
	}
	
	private void assertSort(Query query, String sortText) {
		assertEquals(sortText, query.getSortObject().toString());
	}
}
