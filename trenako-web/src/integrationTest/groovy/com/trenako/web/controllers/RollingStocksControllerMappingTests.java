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

import static com.trenako.test.TestDataBuilder.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.entities.Account;
import com.trenako.entities.RollingStock;
import com.trenako.security.AccountDetails;
import com.trenako.services.FormValuesService;
import com.trenako.services.RollingStocksService;
import com.trenako.services.view.RollingStockView;
import com.trenako.web.security.UserContext;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStocksControllerMappingTests extends AbstractSpringControllerTests {

	private final static String ID = "47cc67093475061e3d95369d";
	private @Autowired RollingStocksService mockService;
	private @Autowired FormValuesService mockValuesService;
	private @Autowired UserContext mockUserContext;

	@Override
	protected void init() {
		super.init();
		
		when(mockValuesService.getBrand(eq("acme"))).thenReturn(acme());
		when(mockValuesService.getRailway(eq("fs"))).thenReturn(fs());
		when(mockValuesService.getScale(eq("h0"))).thenReturn(scaleH0());
		
		Account user = new Account.Builder("mail@mail.com")
			.id(new ObjectId())
			.displayName("bob")
			.build();
		when(mockUserContext.getCurrentUser()).thenReturn(new AccountDetails(user));
		
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.scale(scaleH0())
			.railway(fs())
			.description("desc")
			.build();
		when(mockService.findRollingStockView(eq("acme-123456"), (Account) isNull()))
			.thenReturn(new RollingStockView(rs, null, null, null));
		when(mockService.findRollingStockView(eq("acme-123456"), eq(user)))
			.thenReturn(new RollingStockView(rs, null, null, null));
	}
	
	@After
	public void cleanUp() {
		reset(mockService);
		reset(mockValuesService);
		reset(mockUserContext);
	}
	
	@Test
	public void shouldShowRollingStocks() throws Exception {
		String slug = "acme-123456";
		when(mockUserContext.getCurrentUser()).thenReturn(null);
		
		mockMvc().perform(get("/rollingstocks/{slug}", slug))
			.andExpect(status().isOk())	
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("result"))
			.andExpect(forwardedUrl(view("rollingstock", "show")));
	}
	
	@Test
	public void shouldShowRollingStocksWithCommentsBoxForAuthenticatedUsers() throws Exception {
		String slug = "acme-123456";
		
		mockMvc().perform(get("/rollingstocks/{slug}", slug))
			.andExpect(status().isOk())	
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("result"))
			.andExpect(model().attributeExists("commentForm"))
			.andExpect(forwardedUrl(view("rollingstock", "show")));
	}
		
	@Test
	public void shouldReturn404IfRollingStockNotFound() throws Exception {
		mockMvc().perform(get("/rollingstocks/{slug}", "not-found"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldRenderCreationForm() throws Exception {
		mockMvc().perform(get("/rollingstocks/new"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl(view("rollingstock", "new")));
	}
	
	@Test
	public void shouldCreateNewRollingStocks() throws Exception {
		mockMvc().perform(fileUpload("/rollingstocks")
				.file("file", new byte[]{})
				.param("rs.brand", "acme")
				.param("rs.railway", "fs")
				.param("rs.scale", "h0")
				.param("rs.era", "iv")
				.param("rs.category", "electric-locomotives")
				.param("rs.description['en']", "Electric locomotive")
				.param("rs.itemNumber", "123456"))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(RollingStocksController.ROLLING_STOCK_CREATED_MSG)))
			.andExpect(redirectedUrl("/rollingstocks/acme-123456"));
	}
	
	@Test
	public void shouldRenderRollingStockEditingForms() throws Exception {
		String slug = "rs-slug";
		when(mockService.findBySlug(eq(slug))).thenReturn(new RollingStock());
		
		mockMvc().perform(get("/rollingstocks/{slug}/edit", slug))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl(view("rollingstock", "edit")));
	}
	
	@Test
	public void shouldSaveRollingStockChanges() throws Exception {
		mockMvc().perform(put("/rollingstocks")
				.param("rs.id", ID)				
				.param("rs.brand", "acme")
				.param("rs.railway", "fs")
				.param("rs.slug", "acme-123456")
				.param("rs.scale", "h0")
				.param("rs.era", "iv")
				.param("rs.category", "electric-locomotives")
				.param("rs.description['en']", "Electric locomotive")
				.param("rs.itemNumber", "123456"))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(RollingStocksController.ROLLING_STOCK_SAVED_MSG)))
			.andExpect(redirectedUrl("/rollingstocks/acme-123456"));
	}
	
	@Test
	public void shouldReturns404IfRollingStockToEditIsNotFound() throws Exception {
		String slug = "rs-slug";
		mockMvc().perform(get("/rollingstocks/{slug}/edit", slug))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldDeleteRollingStocks() throws Exception {
		mockMvc().perform(delete("/rollingstocks")
				.param("id", ID))
		.andExpect(status().isOk())
		.andExpect(flash().attributeCount(1))
		.andExpect(flash().attribute("message", equalTo(RollingStocksController.ROLLING_STOCK_DELETED_MSG)))
		.andExpect(redirectedUrl("/rs"));
	}
}
