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
import com.trenako.values.Category;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
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
		when(mockService.eras()).thenReturn(LocalizedEnum.list(Era.class));
		when(mockService.categories()).thenReturn(LocalizedEnum.list(Category.class));
		
		when(mockService.findBrand("acme")).thenReturn(new Brand("ACME"));
		when(mockService.findRailway("fs")).thenReturn(new Railway("FS"));
		when(mockService.findScale("h0")).thenReturn(new Scale("H0"));
		when(mockService.findCategory("electric-locomotives")).thenReturn(new LocalizedEnum<Category>(Category.ELECTRIC_LOCOMOTIVES));
		when(mockService.findEra("iii")).thenReturn(new LocalizedEnum<Era>(Era.III));
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
	public void shouldRenderBrowseBrandHomepage() throws Exception {
		mockMvc().perform(get("/browse/brands/{slug}", "acme"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("brand"))
			.andExpect(forwardedUrl(view("browse", "brand")));
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
	public void shouldRenderBrowseRailwayHomepage() throws Exception {
		mockMvc().perform(get("/browse/railways/{slug}", "fs"))
			.andExpect(status().isOk())
			.andExpect(model().size(3))
			.andExpect(model().attributeExists("railway"))
			.andExpect(model().attributeExists("eras"))
			.andExpect(model().attributeExists("categories"))
			.andExpect(forwardedUrl(view("browse", "railway")));
	}
	
	@Test
	public void shouldRenderBrowseScales() throws Exception {
		mockMvc().perform(get("/browse/scales"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("scales"))
			.andExpect(forwardedUrl(view("browse", "scales")));
	}
	
	@Test
	public void shouldRenderBrowseScaleHomepage() throws Exception {
		mockMvc().perform(get("/browse/scales/{slug}", "h0"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("scale"))
			.andExpect(forwardedUrl(view("browse", "scale")));
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
	public void shouldRenderBrowseEraHomepage() throws Exception {
		mockMvc().perform(get("/browse/eras/{slug}", "iii"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("era"))
			.andExpect(forwardedUrl(view("browse", "era")));
	}
	
	@Test
	public void shouldRenderBrowseCategories() throws Exception {
		mockMvc().perform(get("/browse/categories"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("categories"))
			.andExpect(forwardedUrl(view("browse", "categories")));
	}
	
	@Test
	public void shouldRenderBrowseCategoryHomepage() throws Exception {
		mockMvc().perform(get("/browse/categories/{slug}", "electric-locomotives"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("category"))
			.andExpect(forwardedUrl(view("browse", "category")));
	}
}
