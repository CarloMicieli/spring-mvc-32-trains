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

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.entities.RollingStock;
import com.trenako.services.RollingStocksService;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStocksControllerMappingTests extends AbstractSpringControllerTests {
	
	private ObjectId OID = new ObjectId();
	private String ID = OID.toString();
	
	private @Autowired RollingStocksService mockService;
	
	@After
	public void cleanUp() {
		// TODO code smell
		// waiting 3.2 <https://jira.springsource.org/browse/SPR-9493>
		reset(mockService);
	}
	
	@Test
	public void shouldRenderCreationForm() throws Exception {
		mockMvc().perform(get("/rollingstocks/new"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl(view("rollingstock", "new")));
	}
	
	@Test
	public void shouldRenderEditingForm() throws Exception {
		String slug = "rs-slug";
		when(mockService.findBySlug(eq(slug))).thenReturn(new RollingStock());
		
		mockMvc().perform(get("/rollingstocks/{slug}/edit", slug))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl(view("rollingstock", "edit")));
	}
	
	@Test
	public void shouldReturns404IfRollingStockToEditIsNotFound() throws Exception {
		String slug = "rs-slug";
		mockMvc().perform(get("/rollingstocks/{slug}/edit", slug))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldRenderRollingStocks() throws Exception {
		String slug = "rs-slug";
		when(mockService.findBySlug(eq(slug))).thenReturn(new RollingStock());
		
		mockMvc().perform(get("/rollingstocks/{slug}", slug))
			.andExpect(status().isOk())	
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("rollingStock"))
			.andExpect(forwardedUrl(view("rollingstock", "show")));
	}
	
	@Test
	public void shouldCreateNewRollingStocks() throws Exception {
		mockMvc().perform(fileUpload("/rollingstocks")
				.param("brand", ID)
				.param("railway", ID)
				.param("scale", ID)
				.param("era", "IV")
				.param("category", "locomotive")
				.param("description", "Electric locomotive")
				.param("itemNumber", "123456"))
			.andExpect(status().isOk())
			.andExpect(redirectedUrl("/rollingstocks/acme-123456"));
	}
	
	@Test
	public void shouldReturn404IfRollingStockNotFound() throws Exception {
		mockMvc().perform(get("/rollingstocks/{slug}", "not-found"))
			.andExpect(status().isNotFound());
	}
	
}
