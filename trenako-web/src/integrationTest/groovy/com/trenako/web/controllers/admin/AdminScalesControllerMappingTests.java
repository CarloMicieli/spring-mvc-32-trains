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

import com.trenako.entities.Scale;
import com.trenako.services.ScalesService;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class AdminScalesControllerMappingTests extends AbstractSpringControllerTests {
	private @Autowired ScalesService mockService;
	private final static String ID = "47cc67093475061e3d95369d";
	private final static ObjectId OID = new ObjectId(ID);
	
	@After
	public void cleanUp() {
		reset(mockService);
	}
	
	@Test
	public void shouldGETScales() throws Exception {
		when(mockService.findById(eq(OID))).thenReturn(new Scale());
		mockMvc().perform(get("/admin/scales/{id}", ID))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("scale"))
			.andExpect(forwardedUrl(view("scale", "show")));
	}
	
	@Test
	public void shouldReturn404IfScaleNotFound() throws Exception {
		mockMvc().perform(get("/admin/scales/{id}", ID))
			.andExpect(status().isNotFound());		
	}
	
	@Test
	public void shouldRenderNewScalesForm() throws Exception {
		mockMvc().perform(get("/admin/scales/new"))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("scale"))
			.andExpect(forwardedUrl(view("scale", "new")));
	}
	
	@Test
	public void shouldPOSTNewScales() throws Exception {
		mockMvc().perform(post("/admin/scales").param("name", "H0").param("ratio", "87").param("gauge", "16.5"))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo("Scale created")))
			.andExpect(redirectedUrl("/admin/scales"));
	}
	
	@Test
	public void shouldRenderEditingScalesForm() throws Exception {
		when(mockService.findById(eq(OID))).thenReturn(new Scale());
		mockMvc().perform(get("/admin/scales/{id}/edit", ID))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("scale"))
			.andExpect(forwardedUrl(view("scale", "edit")));
	}
	
	@Test
	public void shouldPUTScaleChanges() throws Exception {
		mockMvc().perform(put("/admin/scales").param("id", ID).param("name", "H0").param("ratio", "87").param("gauge", "16.5"))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo("Scale saved")))
			.andExpect(redirectedUrl("/admin/scales"));
	}
	
	
	@Test
	public void shouldDELETEScales() throws Exception {
		when(mockService.findById(eq(OID))).thenReturn(new Scale(OID));
		mockMvc().perform(delete("/admin/scales/{id}", ID))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo("Scale deleted")))
			.andExpect(redirectedUrl("/admin/scales"));
	}
		
	@Test
	public void shouldRenderTheScalesListView() throws Exception {
		when(mockService.findAll(Mockito.isA(Pageable.class)))
			.thenReturn(new PageImpl<Scale>(Arrays.asList(new Scale(), new Scale())));

		mockMvc().perform(get("/admin/scales"))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("scales"))
			.andExpect(forwardedUrl(view("scale", "list")));
	}

	@Test
	public void shouldShowTheFirstPageOfScalesByDefault() throws Exception {
		ArgumentCaptor<Pageable> arg = ArgumentCaptor.forClass(Pageable.class);

		mockMvc().perform(get("/admin/scales"))
			.andExpect(status().isOk());

		verify(mockService, times(1)).findAll(arg.capture());

		Pageable p = arg.getValue();
		assertEquals(0, p.getPageNumber());
		assertEquals(10, p.getPageSize());
	}

	@Test
	public void shouldProcessPagingParametersForScales() throws Exception {
		ArgumentCaptor<Pageable> arg = ArgumentCaptor.forClass(Pageable.class);

		mockMvc().perform(get("/admin/scales")
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
