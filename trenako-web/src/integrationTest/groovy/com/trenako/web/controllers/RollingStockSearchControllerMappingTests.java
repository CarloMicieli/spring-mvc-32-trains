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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.criteria.SearchCriteria;
import com.trenako.criteria.SearchRequest;
import com.trenako.entities.RollingStock;
import com.trenako.results.RangeRequest;
import com.trenako.results.RollingStockResults;
import com.trenako.services.BrowseService;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockSearchControllerMappingTests extends AbstractSpringControllerTests {
	
	private @Autowired BrowseService mockService;
	
	@Override
	protected void init() {
		RollingStockResults value = new RollingStockResults(
				Arrays.asList(new RollingStock(), new RollingStock()),
				new SearchCriteria(),
				new RangeRequest());
		
		when(mockService.findByCriteria(isA(SearchRequest.class), isA(RangeRequest.class)))
			.thenReturn(value);
	}
		
	@After
	public void cleanUp() {
		reset(mockService);
	}
	
	@Test
	public void shouldPerformSearchByBrands() throws Exception {
		
		mockMvc().perform(get("/rs/brand/{brand}", "acme"))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("results"))
			.andExpect(model().attributeExists("options"));
		
		ArgumentCaptor<SearchRequest> arg = ArgumentCaptor.forClass(SearchRequest.class);
		verify(mockService, times(1)).findByCriteria(arg.capture(), isA(RangeRequest.class));
		
		SearchRequest expected = new SearchRequest();
		expected.setBrand("acme");
		assertEquals(expected, arg.getValue());
	}
	
	@Test
	public void shouldPerformSearchWithMoreCriteria() throws Exception {
		mockMvc().perform(get("/rs/brand/{brand}/scale/{scale}/era/{era}", "acme", "h0", "iii"))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("results"))
			.andExpect(model().attributeExists("options"));
		
		ArgumentCaptor<SearchRequest> arg = ArgumentCaptor.forClass(SearchRequest.class);
		verify(mockService, times(1)).findByCriteria(arg.capture(), isA(RangeRequest.class));
		
		SearchRequest expected = new SearchRequest();
		expected.setBrand("acme");
		expected.setScale("h0");
		expected.setEra("iii");
		assertEquals(expected, arg.getValue());
	}
}
