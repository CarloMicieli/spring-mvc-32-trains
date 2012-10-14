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

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.data.domain.Sort;

import com.trenako.entities.RollingStock;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockResultsTests {

	private final ObjectId MAX_ID = new ObjectId();
	private final ObjectId SINCE_ID = new ObjectId();
		
	@Test
	public void shouldFillEmptyResults() {
		RangeRequest range = buildRangeById(10);
		
		PaginatedResults<RollingStock> results =
				new RollingStockResults(new ArrayList<RollingStock>(), null, range);
		
		assertTrue("Result set is not empty", results.isEmpty());
		assertEquals(0, ((List<RollingStock>) results.getItems()).size());
		assertNull(results.getRange());
		assertEquals(false, results.hasNextPage());
		assertEquals(false, results.hasPreviousPage());
	}
	
	@Test
	public void shouldFillTheResultsByObjectId() {
		List<RollingStock> results = resultsByObjectId(10, false);
		RangeRequest range = buildRangeById(10);
		
		PaginatedResults<RollingStock> pagResults =
				new RollingStockResults(results, null, range);
		
		assertEquals(10, ((List<RollingStock>)pagResults.getItems()).size());
		assertEquals(SINCE_ID, pagResults.getRange().getSince());
		assertEquals(MAX_ID, pagResults.getRange().getMax());
	}
	
	@Test
	public void shouldFillTheResultsByLastModifiedDates() {
		Date since = fulldate("2010/06/10 09:30:00");
		Date max = fulldate("2010/06/10 09:30:00");
		
		List<RollingStock> results = resultsByLastModified(10, false, since, max);
		RangeRequest range = buildRange(10);
		
		PaginatedResults<RollingStock> pagResults =
				new RollingStockResults(results, null, range);
		
		assertEquals(10, ((List<RollingStock>)pagResults.getItems()).size());
		assertTrue("Since date is different", since.equals(pagResults.getRange().getSince()));
		assertEquals(max, pagResults.getRange().getMax());
	}
	
	@Test
	public void shouldFillTheResultsWhenItemIsLessThanCount() {
		List<RollingStock> results = resultsByObjectId(6, false);
		RangeRequest range = buildRangeById(10);
		
		PaginatedResults<RollingStock> pagResults =
				new RollingStockResults(results, null, range);
		
		assertEquals(6, ((List<RollingStock>)pagResults.getItems()).size());
		assertEquals(SINCE_ID, pagResults.getRange().getSince());
		assertEquals(MAX_ID, pagResults.getRange().getMax());
	}
	
	@Test
	public void shouldFillFirstPageResults() {
		List<RollingStock> results = resultsByObjectId(10, true);
		RangeRequest range = buildRange(10, null, null);
		
		PaginatedResults<RollingStock> pagResults =
				new RollingStockResults(results, null, range);
		
		assertEquals(false, pagResults.hasPreviousPage());
		assertEquals(true, pagResults.hasNextPage());
	}
	
	@Test
	public void shouldFillMiddlePageResults() {
		List<RollingStock> results = resultsByObjectId(10, true);
		RangeRequest range = buildRange(10, new ObjectId(), new ObjectId());
		
		PaginatedResults<RollingStock> pagResults =
				new RollingStockResults(results, null, range);
		
		assertEquals(true, pagResults.hasPreviousPage());
		assertEquals(true, pagResults.hasNextPage());
	}
	
	@Test
	public void shouldFillLastPageResults() {
		List<RollingStock> results = resultsByObjectId(10, false);
		RangeRequest range = buildRange(10, new ObjectId(), new ObjectId());
		
		PaginatedResults<RollingStock> pagResults =
				new RollingStockResults(results, null, range);
		
		assertEquals(true, pagResults.hasPreviousPage());
		assertEquals(false, pagResults.hasNextPage());
	}
	
	@Test
	public void shouldFillLastPageResultsWithLessItemsThanCount() {
		List<RollingStock> results = resultsByObjectId(5, false);
		RangeRequest range = buildRange(10, new ObjectId(), new ObjectId());
		
		PaginatedResults<RollingStock> pagResults =
				new RollingStockResults(results, null, range);
		
		assertEquals(true, pagResults.hasPreviousPage());
		assertEquals(false, pagResults.hasNextPage());
	}
	
	private List<RollingStock> resultsByLastModified(int numberOfResults, boolean includeAdditionItem, Date since, Date max) {
		List<RollingStock> items = new ArrayList<RollingStock>(numberOfResults);
		
		RollingStock rsSince = new RollingStock.Builder(acme(), "123456").lastModified(since).build();
		items.add(rsSince);
		for (int i=1; i<numberOfResults-1; i++) {
			items.add(new RollingStock(new ObjectId()));
		}
		RollingStock rsMax = new RollingStock.Builder(acme(), "456789").lastModified(max).build();
		items.add(rsMax);
		
		if (includeAdditionItem) {
			RollingStock rs = new RollingStock.Builder(acme(), "456789")
				.lastModified(fulldate("2010/06/10 09:30:00"))
				.build();
			items.add(rs);
		}
		
		return items;
	}
	
	private List<RollingStock> resultsByObjectId(int numberOfResults, boolean includeAdditionItem) {
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
	
	private RangeRequest buildRangeById(int count) {
		RangeRequest r = new RangeRequest();
		r.setSize(count);
		r.setSort(new Sort("id"));
		return r;
	}
	
	private RangeRequest buildRange(int count) {
		RangeRequest r = new RangeRequest();
		r.setSize(count);
		return r;
	}
	
	private RangeRequest buildRange(int count, Object since, Object max) {
		RangeRequest r = new RangeRequest();
		r.setSince(since);
		r.setMax(max);
		r.setSize(count);
		r.setSort(new Sort("id"));
		return r;
	}
}
