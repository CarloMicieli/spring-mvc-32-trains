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

import static com.trenako.test.TestDataBuilder.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.entities.Account;
import com.trenako.entities.RollingStock;
import com.trenako.security.AccountDetails;
import com.trenako.services.RollingStocksService;
import com.trenako.web.security.UserContext;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionsControllerMappingTests extends AbstractSpringControllerTests {

	private @Autowired RollingStocksService rsService;
	private @Autowired UserContext mockUserContext;
	
	@Override
	protected void init() {
		super.init();
		
		AccountDetails ownerDetails = new AccountDetails(
				new Account.Builder("mail@mail.com").displayName("Bob").build());
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails);
		RollingStock value = new RollingStock.Builder(acme(), "123456")
			.scale(scaleH0())
			.railway(fs())
			.description("desc")
			.build();
		when(rsService.findBySlug(eq("acme-123456"))).thenReturn(value);
	}
	
	@After
	public void cleanup() {
		reset(rsService);
		reset(mockUserContext);
	}
	
	@Test
	public void shouldRenderTheFormToAddItemsToCollections() throws Exception {
		mockMvc().perform(get("/collections/add/{slug}", "acme-123456"))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("itemForm"))
			.andExpect(model().attributeExists("rs"))
			.andExpect(forwardedUrl(view("collection", "add")));
	}
	
	@Test
	public void shouldAddItemsToCollections() throws Exception {
		mockMvc().perform(post("/collections")
				.param("item.itemId", "2012-01-02_acme-123456")
				.param("item.rollingStock.slug", "acme-123456")
				.param("item.rollingStock.label", "ACME 123456")
				.param("item.category", "electric-locomotives")
				.param("item.addedAt", "2012-01-01")
				.param("price", "100.50"))
			.andExpect(status().isOk())
			.andExpect(redirectedUrl("/rollingstocks/acme-123456"));
	}
}
