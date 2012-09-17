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
	private final static String ACME = "acme";
	private final static byte[] EMPTY_FILE = new byte[] {};
	
	@Override
	protected void init() {
		super.init();
		when(mockService.findBySlug(eq(ACME))).thenReturn(new Brand());
	}
	
	@After
	public void cleanUp() {
		reset(mockService);
	}
	
	@Test
	public void shouldShowABrand() throws Exception {
		mockMvc().perform(get("/admin/brands/{slug}", ACME))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("brand"))
			.andExpect(forwardedUrl(view("brand", "show")));
	}
	
	@Test
	public void shouldShowTheBrandsList() throws Exception {
		when(mockService.findAll(Mockito.isA(Pageable.class)))
			.thenReturn(new PageImpl<Brand>(Arrays.asList(new Brand(), new Brand())));
		
		mockMvc().perform(get("/admin/brands"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
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
	public void shouldProcessBrandsPagingParameters() throws Exception {
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
	public void shouldRenderBrandCreationForms() throws Exception {
		mockMvc().perform(get("/admin/brands/new"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("brandForm"))
			.andExpect(forwardedUrl(view("brand", "new")));
	}
	
	@Test
	public void shouldRedirectAfterBrandsWereCreated() throws Exception {
		mockMvc().perform(fileUpload("/admin/brands")
				.file("file", new byte[]{})
				.param("brand.name", "ACME")
				.param("brand.description['en']", "ACME description"))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(AdminBrandsController.BRAND_CREATED_MSG)))
			.andExpect(redirectedUrl("/admin/brands"));
	}
		
	@Test
	public void shouldRenderTheBrandEditingForms() throws Exception {
		mockMvc().perform(get("/admin/brands/{slug}/edit", ACME))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("brandForm"))
			.andExpect(forwardedUrl(view("brand", "edit")));
	}

	@Test
	public void shouldSaveBrandChanges() throws Exception {
		mockMvc().perform(put("/admin/brands")
				.param("brand.id", ID)
				.param("brand.name", "ACME")
				.param("brand.description['en']", "ACME description"))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(AdminBrandsController.BRAND_SAVED_MSG)))
			.andExpect(redirectedUrl("/admin/brands"));
	}
	
	@Test
	public void shouldDeleteBrands() throws Exception {
		mockMvc().perform(delete("/admin/brands/{id}", ID))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(AdminBrandsController.BRAND_DELETED_MSG)))
			.andExpect(redirectedUrl("/admin/brands"));
	}
	
	@Test
	public void shouldUploadNewBrandImages() throws Exception {
		mockMvc().perform(fileUpload("/admin/brands/{slug}/upload", ACME)
				.file("file", "file content".getBytes()))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(AdminBrandsController.BRAND_LOGO_UPLOADED_MSG)))
			.andExpect(redirectedUrl("/admin/brands/acme"));
	}
	
	@Test
	public void shouldRedirectIfBrandImagesAreNotValid() throws Exception {
		mockMvc().perform(fileUpload("/admin/brands/{slug}/upload", ACME)
				.file("file", EMPTY_FILE))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(AdminBrandsController.BRAND_INVALID_UPLOAD_MSG)))
			.andExpect(redirectedUrl("/admin/brands/acme"));
	}

	@Test
	public void shouldDeleteBrandImages() throws Exception {
		mockMvc().perform(delete("/admin/brands/{slug}/upload", ACME))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(AdminBrandsController.BRAND_LOGO_DELETED_MSG)))
			.andExpect(redirectedUrl("/admin/brands/acme"));
	}
}
