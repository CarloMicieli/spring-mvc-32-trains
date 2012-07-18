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

import static com.trenako.web.infrastructure.RangeRequestQueryParamsBuilder.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.trenako.results.RangeRequest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RangeRequestQueryParamsBuilderTests {

	@Test
	public void shouldBuildQueryParamsForDefaultRanges() {
		RangeRequest rangeRequest = new RangeRequest();
		String queryParams = buildQueryParams(rangeRequest);
		assertEquals("?count=10", queryParams);
	}

	@Test
	public void shouldBuildQueryParamsForRangesWithCountOnly() {
		RangeRequest rangeRequest = new RangeRequest();
		rangeRequest.setCount(50);
		
		String queryParams = buildQueryParams(rangeRequest);
		assertEquals("?count=50", queryParams);
	}

	@Test
	public void shouldBuildQueryParamsForRangesWithCountAndSort() {
		RangeRequest rangeRequest = new RangeRequest();
		rangeRequest.setCount(25);
		rangeRequest.setSort(new Sort(Direction.DESC, "name"));
		
		String queryParams = buildQueryParams(rangeRequest);
		assertEquals("?count=25&sort=name&order=DESC", queryParams);
	}
	
}
