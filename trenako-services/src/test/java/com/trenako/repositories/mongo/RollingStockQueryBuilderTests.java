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

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.trenako.results.RangeRequest;


import static org.springframework.data.mongodb.core.query.Criteria.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockQueryBuilderTests {

	private final ObjectId maxId = new ObjectId("50013ff884aef01777a8b316");
	private final ObjectId sinceId = new ObjectId("50013ff884aef01777a8b317");
	private final Criteria where = where("brandName").is("ACME");
	
	@Test
	public void test() {
		
		RangeRequest range = new RangeRequest();
		range.setMaxId(maxId);
		range.setSinceId(sinceId);
		range.setCount(10);
		
		Query query = RollingStockQueryBuilder.buildQuery(where, range);
		assertQuery(query, "{ \"brandName\" : \"ACME\" ," +
				" \"id\" : { \"$gt\" : { \"$oid\" : \"50013ff884aef01777a8b317\"} ," +
				" \"$lt\" : { \"$oid\" : \"50013ff884aef01777a8b316\"}}}");
		assertSort(query, "{ \"lastModified\" : -1}");
		assertEquals(11, query.getLimit());
	}

	
	private void assertQuery(Query query, String queryText) {
		assertEquals(queryText, query.getQueryObject().toString());
	}
	
	private void assertSort(Query query, String sortText) {
		assertEquals(sortText, query.getSortObject().toString());
	}
}
