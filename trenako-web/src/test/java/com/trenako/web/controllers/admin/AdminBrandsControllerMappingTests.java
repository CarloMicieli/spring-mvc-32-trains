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
package com.trenako.web.controllers.admin;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

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

import com.trenako.entities.Brand;
import com.trenako.services.BrandsService;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class AdminBrandsControllerMappingTests extends AbstractSpringControllerTests {
	private @Autowired BrandsService mockService;
	private final static String ID = "47cc67093475061e3d95369d";
	private final static ObjectId OID = new ObjectId(ID);
	
	@After
	public void cleanUp() {
		// TODO code smell
		// waiting 3.2 <https://jira.springsource.org/browse/SPR-9493>
		reset(mockService);
	}
	
	@Test
	public void shouldHaveGetBrandMapping() throws Exception {
		when(mockService.findById(eq(OID))).thenReturn(new Brand());
		mockMvc().perform(get("/admin/brands/{id}", ID))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("brand"))
			.andExpect(forwardedUrl(view("brand", "show")));
	}
	
	@Test
	public void shouldRenderTheBrandsListView() throws Exception {
		when(mockService.findAll(Mockito.isA(Pageable.class)))
			.thenReturn(new PageImpl<Brand>(Arrays.asList(new Brand(), new Brand())));
		
		mockMvc().perform(get("/admin/brands"))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("brands"))
			.andExpect(forwardedUrl(view("brand", "list")));
	}
	
	@Test
	public void shouldShowTheFirstPageOfBrandsByDefault() throws Exception {
		ArgumentCaptor<Pageable> arg = ArgumentCaptor.forClass(Pageable.class);
		
		mockMvc().perform(get("/admin/brands"))
			.andExpect(status().isOk());
		
		verify(mockService, times(1)).findAll(arg.capture());
		
		Pageable p = arg.getValue();
		assertEquals(0, p.getPageNumber());
		assertEquals(10, p.getPageSize());
	}
	
	@Test
	public void shouldProcessPagingParameters() throws Exception {
		ArgumentCaptor<Pageable> arg = ArgumentCaptor.forClass(Pageable.class);
		
		mockMvc().perform(get("/admin/brands")
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
	
	@Test
	public void shouldRenderTheNewBrandForm() throws Exception {
		mockMvc().perform(get("/admin/brands/new"))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("brand"))
			.andExpect(model().attributeExists("countries"))
			.andExpect(forwardedUrl(view("brand", "new")));
	}
	
	@Test
	public void shouldRedirectAfterCreate() throws Exception {
		mockMvc().perform(fileUpload("/admin/brands")
				.file("file", new byte[]{})
				.param("name", "ACME"))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo("Brand created")))
			.andExpect(redirectedUrl("/admin/brands"));
	}
	
//	@Test(expected = BindException.class)
//	public void shouldRedirectAfterCreateValidationError() throws Exception {
//		mockMvc().perform(fileUpload("/admin/brands"));
//			.andExpect(status().isOk())
//			.andExpect(model().size(1))
//			.andExpect(model().attributeHasErrors("brand"))
//			.andExpect(forwardedUrl(view("brand", "new")));
//	}
	
	@Test
	public void shouldRenderTheEditBrandForm() throws Exception {
		when(mockService.findById(eq(OID))).thenReturn(new Brand());
		mockMvc().perform(get("/admin/brands/{id}/edit", ID))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("brand"))
			.andExpect(model().attributeExists("countries"))
			.andExpect(forwardedUrl(view("brand", "edit")));
	}

	@Test
	public void shouldSaveBrandChanges() throws Exception {
		mockMvc().perform(put("/admin/brands").param("name", "ACME"))
			.andExpect(status().isOk())
			.andExpect(redirectedUrl("/admin/brands"));
	}
	
	@Test
	public void shouldDeleteABrand() throws Exception {
		when(mockService.findById(eq(OID))).thenReturn(new Brand());
		mockMvc().perform(delete("/admin/brands/{id}", ID))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo("Brand deleted")))
			.andExpect(redirectedUrl("/admin/brands"));
	}

}
