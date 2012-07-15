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

import java.util.Arrays;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.services.BrowseService;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class BrowseControllerMappingTests extends AbstractSpringControllerTests {

	private @Autowired BrowseService mockService;
	
	@Override
	protected void init() {
		when(mockService.brands()).thenReturn(Arrays.asList(new Brand()));
		when(mockService.railways()).thenReturn(Arrays.asList(new Railway()));
		when(mockService.scales()).thenReturn(Arrays.asList(new Scale()));
		when(mockService.eras()).thenReturn(Arrays.asList("I", "II"));
		when(mockService.categories()).thenReturn(Arrays.asList("cat1", "cat2"));
	}
	
	@After
	public void cleanUp() {
		reset(mockService);
	}
	
	@Test
	public void shouldRenderBrowseIndexView() throws Exception {
		mockMvc().perform(get("/browse"))
			.andExpect(status().isOk())
			.andExpect(model().size(5))
			.andExpect(model().attributeExists("brands"))
			.andExpect(model().attributeExists("scales"))
			.andExpect(model().attributeExists("railways"))
			.andExpect(model().attributeExists("eras"))
			.andExpect(model().attributeExists("categories"))
			.andExpect(forwardedUrl(view("browse", "index")));
	}
	
	@Test
	public void shouldRenderBrowseBrands() throws Exception {
		mockMvc().perform(get("/browse/brands"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("brands"))
			.andExpect(forwardedUrl(view("browse", "brands")));
	}
	
	@Test
	public void shouldRenderBrowseRailways() throws Exception {
		mockMvc().perform(get("/browse/railways"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("railways"))
			.andExpect(forwardedUrl(view("browse", "railways")));
	}
	
	@Test
	public void shouldRenderBrowseEras() throws Exception {
		mockMvc().perform(get("/browse/eras"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("eras"))
			.andExpect(forwardedUrl(view("browse", "eras")));
	}
	
	@Test
	public void shouldRenderBrowseCategories() throws Exception {
		mockMvc().perform(get("/browse/categories"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("categories"))
			.andExpect(forwardedUrl(view("browse", "categories")));
	}
}
