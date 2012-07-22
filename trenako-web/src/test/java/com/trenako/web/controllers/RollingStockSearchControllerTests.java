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
package com.trenako.web.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.trenako.criteria.SearchCriteria;
import com.trenako.results.RangeRequest;
import com.trenako.results.RangeRequestImpl;
import com.trenako.results.mongo.RollingStockResults;
import com.trenako.services.BrowseService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RollingStockSearchControllerTests {

	@Mock BrowseService mockService;
	RollingStocksSearchController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new RollingStocksSearchController(mockService);
	}

	@Test
	public void shouldSearchRollingStocks() {
		SearchCriteria sc = new SearchCriteria();
		RangeRequest range = new RangeRequestImpl();
		
		RollingStockResults value = mock(RollingStockResults.class);
		when(mockService.findByCriteria(eq(sc), eq(range))).thenReturn(value);
		
		ModelAndView mav = controller.search(sc, range);
		
		verify(mockService, times(1)).findByCriteria(eq(sc), eq(range));
		assertViewName(mav, "browse/results");
		assertModelAttributeValue(mav, "criteria", sc);
		assertModelAttributeValue(mav, "results", value);
	}
}
