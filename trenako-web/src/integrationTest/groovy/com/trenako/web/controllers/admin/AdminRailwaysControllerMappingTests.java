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
package com.trenako.web.controllers.admin;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.trenako.entities.Railway;
import com.trenako.services.RailwaysService;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class AdminRailwaysControllerMappingTests extends AbstractSpringControllerTests {
	private @Autowired RailwaysService mockService;
	private final static String ID = "47cc67093475061e3d95369d";
	private final static ObjectId OID = new ObjectId(ID);
	
	@After
	public void cleanUp() {
		reset(mockService);
	}
	
	@Test
	public void shouldGETRailways() throws Exception {
		when(mockService.findById(eq(OID))).thenReturn(new Railway());
		mockMvc().perform(get("/admin/railways/{id}", ID))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("railway"))
			.andExpect(forwardedUrl(view("railway", "show")));
	}
	
	@Test
	public void shouldReturn404IfRailwayNotFound() throws Exception {
		mockMvc().perform(get("/admin/railways/{id}", ID))
			.andExpect(status().isNotFound());		
	}
	
	@Test
	public void shouldRenderNewRailwaysForm() throws Exception {
		mockMvc().perform(get("/admin/railways/new"))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("railway"))
			.andExpect(forwardedUrl(view("railway", "new")));
	}
	
	@Test
	public void shouldPOSTNewRailways() throws Exception {
		mockMvc().perform(fileUpload("/admin/railways").file("file", new byte[]{}).param("name", "DB").param("country", "DE"))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo("Railway created")))
			.andExpect(redirectedUrl("/admin/railways"));
	}
	
	@Test
	public void shouldRenderEditingRailwaysForm() throws Exception {
		when(mockService.findById(eq(OID))).thenReturn(new Railway());
		mockMvc().perform(get("/admin/railways/{id}/edit", ID))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("railway"))
			.andExpect(forwardedUrl(view("railway", "edit")));
	}
	
	@Test
	public void shouldPUTRailwayChanges() throws Exception {
		mockMvc().perform(put("/admin/railways").param("id", ID).param("name", "DB").param("country", "DE"))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo("Railway saved")))
			.andExpect(redirectedUrl("/admin/railways"));
	}
	
	
	@Test
	public void shouldDELETERailways() throws Exception {
		when(mockService.findById(eq(OID))).thenReturn(new Railway(OID));
		mockMvc().perform(delete("/admin/railways/{id}", ID))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo("Railway deleted")))
			.andExpect(redirectedUrl("/admin/railways"));
	}
		
	@Test
	public void shouldRenderTheRailwaysListView() throws Exception {
		when(mockService.findAll(Mockito.isA(Pageable.class)))
			.thenReturn(new PageImpl<Railway>(Arrays.asList(new Railway(), new Railway())));

		mockMvc().perform(get("/admin/railways"))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("railways"))
			.andExpect(forwardedUrl(view("railway", "list")));
	}

	@Test
	public void shouldShowTheFirstPageOfRailwaysByDefault() throws Exception {
		ArgumentCaptor<Pageable> arg = ArgumentCaptor.forClass(Pageable.class);

		mockMvc().perform(get("/admin/railways"))
			.andExpect(status().isOk());

		verify(mockService, times(1)).findAll(arg.capture());

		Pageable p = arg.getValue();
		assertEquals(0, p.getPageNumber());
		assertEquals(10, p.getPageSize());
	}

	@Test
	public void shouldProcessPagingParametersForRailways() throws Exception {
		ArgumentCaptor<Pageable> arg = ArgumentCaptor.forClass(Pageable.class);

		mockMvc().perform(get("/admin/railways")
				.param("page.page", "2")
				.param("page.size", "25")
				.param("page.sort", "name")
				.param("page.sort.dir", "desc"))
			.andExpect(status().isOk());

		verify(mockService, times(1)).findAll(arg.capture());

		Pageable p = arg.getValue();
		assertEquals(1, p.getPageNumber());
		assertEquals(25, p.getPageSize());
		assertNotNull("Sort is null", p.getSort().getOrderFor("name"));
		assertEquals(Sort.Direction.DESC, p.getSort().getOrderFor("name").getDirection());
	}
}
