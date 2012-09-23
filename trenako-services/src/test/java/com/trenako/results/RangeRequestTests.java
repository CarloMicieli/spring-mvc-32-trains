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

import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.trenako.AppGlobals;
import com.trenako.results.RangeRequest.RangeTypes;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RangeRequestTests {
	RangeRequest range = new RangeRequest();
	
	@Test
	public void shouldCreateDifferentRangeTypesAccordingTheSortCriteria() {
		RangeRequest x = new RangeRequest();
		x.setSort(RangeRequest.DEFAULT_SORT);
		assertEquals(RangeTypes.DATES, x.getRangeType());
		
		x.setSort(new Sort("name"));
		assertEquals(RangeTypes.STRINGS, x.getRangeType());
	}
	
	@Test
	public void shouldSetOnlyValidCountValues() {
		range.setSize(1000);
		assertEquals(AppGlobals.MAX_RESULT_SET_SIZE, range.getSize());		
	
		range.setSize(0);
		assertEquals(10, range.getSize());		
		
		range.setSize(-10);
		assertEquals(10, range.getSize());	
	}

	@Test
	public void shouldReturnTheDefaultSort() {
		assertEquals(RangeRequest.DEFAULT_SORT, range.getSort());
	}
	
	@Test
	public void shouldReturnTheFirstOrder() {
		range.setSort(new Sort(Direction.ASC, "name"));
		assertEquals("name", range.getFirstOrder().getProperty());
		assertEquals(Direction.ASC, range.getFirstOrder().getDirection());
	}
	
	@Test
	public void shouldReturnThePropertyNameUsedForSorting() {
		RangeRequest r1 = new RangeRequest();
		assertEquals("lastModified", r1.getSortProperty());

		RangeRequest r2 = new RangeRequest();
		r2.setSort(new Sort("name"));
		assertEquals("name", r2.getSortProperty());
	}
}
