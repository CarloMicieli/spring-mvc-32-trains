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

import java.util.Date;
import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.trenako.AppGlobals;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class SearchRangeTests {
	
	static final Object SINCE = new ObjectId("501171ab575ef9abd1a0c71e");
	static final Object MAX = new ObjectId("501171ab575ef9abd1a0c71f");
	
	@Test
	public void shouldReturnSearchRangeParametersAsMap() {
		SearchRange range = new SearchRange(25, new Sort(Direction.DESC, "name"), SINCE, MAX);
		
		Map<String, Object> params = range.asMap();
		
		String expected = "{dir=DESC, max=501171ab575ef9abd1a0c71f, " +
				"since=501171ab575ef9abd1a0c71e, size=25, sort=name}";
		
		assertNotNull(params);
		assertEquals(5, params.size());
		assertEquals(expected, params.toString());
	}
	
	@Test
	public void shouldReturnParamsAsMapsOnlyForNotDefaultValues() {
		SearchRange range = new SearchRange(10, new Sort(Direction.DESC, "lastModified"), SINCE, MAX);
		
		Map<String, Object> params = range.asMap();
		
		String expected = "{max=501171ab575ef9abd1a0c71f, since=501171ab575ef9abd1a0c71e}";
		
		assertNotNull(params);
		assertEquals(2, params.size());
		assertEquals(expected, params.toString());
	}
	
	@Test
	public void shouldReturnParamsAsMapsOnlyForNotNullValues() {
		SearchRange range = new SearchRange(20, new Sort(Direction.DESC, "name"), null, null);
		
		Map<String, Object> params = range.asMap();
		
		String expected = "{dir=DESC, size=20, sort=name}";
		
		assertNotNull(params);
		assertEquals(3, params.size());
		assertEquals(expected, params.toString());
	}
	
	@Test
	public void shouldCreateSearchRangeForDates() {
		Date since = fulldate("2012/06/01 10:30:00.500");
		Date max = fulldate("2012/06/30 10:30:00.500");
		
		SearchRange range = new SearchRange(20, new Sort(Direction.DESC, "name"), since, max);
		
		Map<String, Object> params = range.asMap();
		assertEquals(since, params.get("since"));
		assertEquals(max, params.get("max"));
	}
	
	@Test
	public void shouldCheckIfTheProvidedSortIsTheDefaultValue() {
		SearchRange r1 = new SearchRange(10, null, null, null);
		assertTrue(r1.isDefaultSort());
		
		SearchRange r2 = new SearchRange(10, new Sort(Direction.DESC, "lastModified"), SINCE, MAX);
		assertTrue(r2.isDefaultSort());
		
		SearchRange r3 = new SearchRange(10, new Sort(Direction.DESC, "name"), SINCE, MAX);
		assertFalse(r3.isDefaultSort());
	}
	
	@Test
	public void shouldCheckIfTheProvidedPageSizeIsTheDefaultValue() {
		SearchRange r1 = new SearchRange(AppGlobals.PAGE_SIZE, null, null, null);
		assertTrue(r1.isDefaultPageSize());
		
		SearchRange r2 = new SearchRange(50, null, null, null);
		assertFalse(r2.isDefaultPageSize());
	}
	
}
