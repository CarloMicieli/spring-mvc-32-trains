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

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.*;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.trenako.entities.Brand;
import com.trenako.services.BrandsService;
import com.trenako.web.images.WebImageService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminBrandsControllerTests {
	@Mock RedirectAttributes mockRedirectAtts;
	@Mock WebImageService imgUtils;
	@Mock BindingResult mockResult;
	@Mock BrandsService service;
	AdminBrandsController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new AdminBrandsController(service, imgUtils);
	}
	
	@Test
	public void shouldListBrands() {
		Pageable paging = mock(Pageable.class);
		
		ModelAndView mav = controller.list(paging);
		
		verify(service, times(1)).findAll(eq(paging));
		assertViewName(mav, "brand/list");
		assertModelAttributeAvailable(mav, "brands");
	}
	
	@Test
	public void shouldShowBrands() {
		ObjectId id = new ObjectId();
		ModelAndView mav = controller.show(id);
		
		verify(service, times(1)).findById(eq(id));
		assertViewName(mav, "brand/show");
		assertModelAttributeAvailable(mav, "brand");
	}
	
	@Test
	public void shouldCreateNewBrandForm() {
		ModelAndView mav = controller.newForm();
		
		assertViewName(mav, "brand/new");
		assertModelAttributeAvailable(mav, "brand");
	}
	
	@Test
	public void shouldCreateBrands() throws IOException {
 		Brand brand = new Brand();
		when(mockResult.hasErrors()).thenReturn(false);
		RedirectAttributes redirectAtt = new RedirectAttributesModelMap();
		MultipartFile file = buildFile(MediaType.IMAGE_JPEG);
		//when(imgUtils.createImage(eq(file))).thenReturn(new Image(MediaType.IMAGE_JPEG_VALUE, value));

		String redirect = controller.create(brand, mockResult, file, redirectAtt);

		assertEquals("redirect:/admin/brands", redirect);
		//assertNotNull(brand.getLogo());
		verify(service, times(1)).save(eq(brand));
	}
	
	@Test
	public void shouldFillEditBrandForm() {
		ObjectId id = new ObjectId();
		Brand value = new Brand();
		when(service.findById(eq(id))).thenReturn(value);
		
		ModelAndView mav = controller.editForm(id);
		
		assertViewName(mav, "brand/edit");
		assertModelAttributeValue(mav, "brand", value);
	}
	
	@Test
	public void shouldDeleteBrands() {
		ObjectId id = new ObjectId();
		Brand value = new Brand();
		when(service.findById(eq(id))).thenReturn(value);

		controller.delete(id, mockRedirectAtts);
		
		verify(service, times(1)).remove(eq(value));		
	}
	
	private MultipartFile buildFile(MediaType mediaType) {
		byte[] content = "file content".getBytes();
		return new MockMultipartFile("image.jpg", "image.jpg", mediaType.toString(), content);
	}
}
