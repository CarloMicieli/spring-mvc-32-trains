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
package com.trenako.results;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.trenako.entities.RollingStock;
import com.trenako.results.mongo.RollingStockResults;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockResultsTests {

	private final ObjectId MAX_ID = new ObjectId();
	private final ObjectId SINCE_ID = new ObjectId();
	
	private List<RollingStock> buildResults(int numberOfResults, boolean includeAdditionItem) {
		List<RollingStock> items = new ArrayList<RollingStock>(numberOfResults);
		
		items.add(new RollingStock(SINCE_ID));
		for (int i=1; i<numberOfResults-1; i++) {
			items.add(new RollingStock(new ObjectId()));
		}
		items.add(new RollingStock(MAX_ID));
		
		if (includeAdditionItem) {
			items.add(new RollingStock(new ObjectId()));
		}
		
		return items;
	}

	private RangeRequest buildRange(int count) {
		RangeRequestImpl r = new RangeRequestImpl();
		r.setCount(count);
		return r;
	}
	
	private RangeRequest buildRange(int count, ObjectId since, ObjectId max) {
		RangeRequestImpl r = new RangeRequestImpl();
		r.setSinceId(since);
		r.setMaxId(max);
		r.setCount(count);
		return r;
	}
	
	@Test
	public void shouldFillEmptyResults() {
		RangeRequest range = buildRange(10);
		
		PaginatedResults<RollingStock> pagResults =
				new RollingStockResults(new ArrayList<RollingStock>(), range);
		
		assertEquals(0, ((List<RollingStock>)pagResults.getItems()).size());
		assertEquals(null, pagResults.getSinceId());
		assertEquals(null, pagResults.getMaxId());
		assertEquals(false, pagResults.hasNextPage());
		assertEquals(false, pagResults.hasPreviousPage());
	}
	
	@Test
	public void shouldFillTheResults() {
		List<RollingStock> results = buildResults(10, false);
		RangeRequest range = buildRange(10);
		
		PaginatedResults<RollingStock> pagResults =
				new RollingStockResults(results, range);
		
		assertEquals(10, ((List<RollingStock>)pagResults.getItems()).size());
		assertEquals(SINCE_ID, pagResults.getSinceId());
		assertEquals(MAX_ID, pagResults.getMaxId());
	}
	
	@Test
	public void shouldFillTheResultsWhenItemIsLessThanCount() {
		List<RollingStock> results = buildResults(6, false);
		RangeRequest range = buildRange(10);
		
		PaginatedResults<RollingStock> pagResults =
				new RollingStockResults(results, range);
		
		assertEquals(6, ((List<RollingStock>)pagResults.getItems()).size());
		assertEquals(SINCE_ID, pagResults.getSinceId());
		assertEquals(MAX_ID, pagResults.getMaxId());
	}
	
	@Test
	public void shouldFillFirstPageResults() {
		List<RollingStock> results = buildResults(10, true);
		RangeRequest range = buildRange(10, null, null);
		
		PaginatedResults<RollingStock> pagResults =
				new RollingStockResults(results, range);
		
		assertEquals(false, pagResults.hasPreviousPage());
		assertEquals(true, pagResults.hasNextPage());
	}
	
	@Test
	public void shouldFillMiddlePageResults() {
		List<RollingStock> results = buildResults(10, true);
		RangeRequest range = buildRange(10, new ObjectId(), new ObjectId());
		
		PaginatedResults<RollingStock> pagResults =
				new RollingStockResults(results, range);
		
		assertEquals(true, pagResults.hasPreviousPage());
		assertEquals(true, pagResults.hasNextPage());
	}
	
	@Test
	public void shouldFillLastPageResults() {
		List<RollingStock> results = buildResults(10, false);
		RangeRequest range = buildRange(10, new ObjectId(), new ObjectId());
		
		PaginatedResults<RollingStock> pagResults =
				new RollingStockResults(results, range);
		
		assertEquals(true, pagResults.hasPreviousPage());
		assertEquals(false, pagResults.hasNextPage());
	}
	
	@Test
	public void shouldFillLastPageResultsWithLessItemsThanCount() {
		List<RollingStock> results = buildResults(5, false);
		RangeRequest range = buildRange(10, new ObjectId(), new ObjectId());
		
		PaginatedResults<RollingStock> pagResults =
				new RollingStockResults(results, range);
		
		assertEquals(true, pagResults.hasPreviousPage());
		assertEquals(false, pagResults.hasNextPage());
	}
}
