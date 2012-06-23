/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.web.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.entities.Brand;
import com.trenako.services.BrandsService;
import com.trenako.web.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class BrandControllerMappingTests extends AbstractSpringControllerTests {
	private @Autowired BrandsService mockService;
	
	@Test
	public void shouldListAllBrands() throws Exception {
		when(mockService.findAll()).thenReturn(Arrays.asList(new Brand(), new Brand()));
		
		mockMvc().perform(get("/brands"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("brands"))
			.andExpect(forwardedUrl(view("brand", "list")));
		
		// TODO code smell
		// waiting 3.2 <https://jira.springsource.org/browse/SPR-9493>
		reset(mockService);
	}
	
	@Test
	public void shouldShowABrand() throws Exception {
		when(mockService.findBySlug(eq("acme"))).thenReturn(new Brand());
		
		mockMvc().perform(get("/brands/{slug}", "acme"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("brand"))
			.andExpect(forwardedUrl(view("brand", "show")));
		
		reset(mockService);
	}
	
	@Test
	public void shouldReturn404WhenTheBrandNotFound() throws Exception {
		when(mockService.findBySlug(eq("acme"))).thenReturn(null);
		mockMvc().perform(get("/brands/{slug}", "acme"))
			.andExpect(status().isNotFound());
		
		reset(mockService);
	}
}
