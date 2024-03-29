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

import com.trenako.criteria.SearchRequest;
import com.trenako.results.RangeRequest;
import com.trenako.results.RollingStockResults;
import com.trenako.services.BrowseService;
import com.trenako.web.controllers.form.ResultsOptionsForm;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RollingStocksSearchControllerTests {

	@Mock BrowseService mockService;
	RollingStocksSearchController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new RollingStocksSearchController(mockService);
	}

	@Test
	public void shouldRenderTheRollingStockResultsView() {
		SearchRequest search = mock(SearchRequest.class);
		RangeRequest range = new RangeRequest(RangeRequest.DEFAULT_SORT, 10, null, null);
		
		RollingStockResults value = mock(RollingStockResults.class);
		when(mockService.findByCriteria(eq(search), eq(range))).thenReturn(value);
		
		ModelAndView mav = controller.search(search, range);
		
		verify(mockService, times(1)).findByCriteria(eq(search), eq(range));
		assertViewName(mav, "browse/results");
		assertModelAttributeValue(mav, "results", value);
		assertModelAttributeValue(mav, "options", ResultsOptionsForm.buildFor(range));
	}
}
