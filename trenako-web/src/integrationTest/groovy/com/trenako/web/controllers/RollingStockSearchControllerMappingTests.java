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
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import org.junit.After;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.criteria.SearchCriteria;
import com.trenako.results.RangeRequest;
import com.trenako.services.RollingStockSearchService;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockSearchControllerMappingTests extends AbstractSpringControllerTests {
	
	private @Autowired RollingStockSearchService mockService;
	
	@After
	public void cleanUp() {
		reset(mockService);
	}
	
	@Test
	public void shouldPerformSearchByBrands() throws Exception {
		
		mockMvc().perform(get("/rs/brand/{brand}", "ACME"))
			.andExpect(status().isOk());
		
		ArgumentCaptor<SearchCriteria> arg = ArgumentCaptor.forClass(SearchCriteria.class);
		verify(mockService, times(1)).findByCriteria(arg.capture(), isA(RangeRequest.class));
		
		SearchCriteria expected = new SearchCriteria.Builder()
			.brand("ACME").build();
		assertEquals(expected, arg.getValue());
	}
	
	@Test
	public void shouldPerformSearchWithMoreCriteria() throws Exception {
		
		mockMvc().perform(get("/rs/brand/{brand}/scale/{scale}/era/{era}", "ACME", "H0", "III"))
			.andExpect(status().isOk());
		
		ArgumentCaptor<SearchCriteria> arg = ArgumentCaptor.forClass(SearchCriteria.class);
		verify(mockService, times(1)).findByCriteria(arg.capture(), isA(RangeRequest.class));
		
		SearchCriteria expected = new SearchCriteria.Builder()
			.brand("ACME")
			.scale("H0")
			.era("III")
			.build();
		assertEquals(expected, arg.getValue());
	}
}
