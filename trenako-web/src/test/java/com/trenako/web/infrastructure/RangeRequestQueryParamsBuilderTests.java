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
package com.trenako.web.infrastructure;

import static com.trenako.test.TestDataBuilder.*;
import static com.trenako.web.infrastructure.RangeRequestQueryParamsBuilder.*;
import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.TreeMap;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.trenako.results.RangeRequest;
import com.trenako.results.SearchRange;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RangeRequestQueryParamsBuilderTests {
	
	final static ObjectId SINCE = new ObjectId("501190f0575eef87e33687d7");
	final static ObjectId MAX = new ObjectId("501190f0575eef87e33687d8");
	SearchRange searchRange;

	@Test
	public void shouldBuildQueryParamsForDefaultRanges() {
		searchRange = new SearchRange(10, new Sort(Direction.DESC, "lastModified"), null, null);
		
		String queryParams = buildQueryParamsNext(searchRange);
		assertEquals("", queryParams);
	}

	@Test
	public void shouldBuildQueryParamsForRangesWithCountOnly() {
		searchRange = new SearchRange(50, null, null, null);
		
		String queryParams = buildQueryParamsNext(searchRange);
		assertEquals("?size=50", queryParams);
	}

	@Test
	public void shouldBuildQueryParamsForRangesWithCountAndSort() {
		searchRange = new SearchRange(25, new Sort(Direction.DESC, "name"), null, null);
		
		String queryParams = buildQueryParamsNext(searchRange);
		assertEquals("?dir=DESC&size=25&sort=name", queryParams);
	}
	
	@Test
	public void shouldBuildQueryParamsForNextPage() throws UnsupportedEncodingException {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("size", 25);
		params.put("sort", "name");
		params.put("dir", "DESC");
		params.put("since", SINCE);
		params.put("max", MAX);
		
		String queryParams = buildQueryParams(params, RangeRequest.SINCE_NAME);
		assertEquals("?dir=DESC&since=501190f0575eef87e33687d7&size=25&sort=name", queryParams);
	}
	
	@Test
	public void shouldBuildQueryParamsForPreviousPage() throws UnsupportedEncodingException {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("size", 25);
		params.put("sort", "name");
		params.put("dir", "DESC");
		params.put("max", MAX);
		
		String queryParams = buildQueryParams(params, RangeRequest.MAX_NAME);
		assertEquals("?dir=DESC&max=501190f0575eef87e33687d8&size=25&sort=name", queryParams);
	}
	
	@Test
	public void shouldEncodeBuildQueryParams() throws UnsupportedEncodingException {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("size", 25);
		params.put("sort", "complete name");
		params.put("dir", "<DESC>");
		params.put("since", SINCE);
		params.put("max", MAX);
		
		String queryParams = buildQueryParams(params, RangeRequest.MAX_NAME);
		assertEquals("?dir=%3CDESC%3E&max=501190f0575eef87e33687d8&size=25&sort=complete+name", queryParams);
	}
	
	@Test
	public void shouldBuildQueryParamsForDates() throws UnsupportedEncodingException {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("size", 25);
		params.put("sort", "name");
		params.put("dir", "DESC");
		params.put("max", fulldate("2012/06/01 11:00:00.000"));
		
		String queryParams = buildQueryParams(params, RangeRequest.MAX_NAME);
		assertEquals("?dir=DESC&max=2012-06-01T11%3A00%3A00&size=25&sort=name", queryParams);
	}
}
