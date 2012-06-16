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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.trenako.entities.Brand;
import com.trenako.services.BrandsService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BrandControllerTests {
	
	@Mock Model mockModel;
	@Mock BindingResult mockResult;
	@Mock BrandsService service;
	BrandController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new BrandController(service);
	}
	
	@Test
	public void shouldListAllBrands() {
		
		List<Brand> value = Arrays.asList(new Brand("AAA"), new Brand("BBB"));
		when(service.findAll()).thenReturn(value);
		
		Model model = new ExtendedModelMap();
		String viewName = controller.list(model);
		
		assertEquals("brand/list", viewName);
		assertEquals(true, model.containsAttribute("brands"));
		
		Object brands = ((ExtendedModelMap)model).get("brands");
		assertSame(value, brands);
	}
	
	@Test
	public void shouldCreateNewBrandForm() {
		Model model = new ExtendedModelMap();
		
		String viewName = controller.newBrand(model);
		
		assertEquals("brand/edit", viewName);
		assertEquals(true, model.containsAttribute("brand"));
	}
	
	@Test
	public void shouldCreateBrands() {
		Brand brand = new Brand();
		when(mockResult.hasErrors()).thenReturn(false);
		RedirectAttributes redirectAtt = new RedirectAttributesModelMap();
		
		String redirect = controller.create(brand, mockResult, redirectAtt);
		assertEquals("redirect:/brands", redirect);
		verify(service, times(1)).save(eq(brand));
	}	
}
