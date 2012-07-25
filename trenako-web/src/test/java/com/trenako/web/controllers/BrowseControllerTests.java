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

import static org.springframework.test.web.ModelAndViewAssert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.services.BrowseService;
import com.trenako.values.Category;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BrowseControllerTests {

	static final List<LocalizedEnum<Era>> ERAS = 
			(List<LocalizedEnum<Era>>) LocalizedEnum.list(Era.class);
	static final List<LocalizedEnum<Category>> CATEGORIES =
			(List<LocalizedEnum<Category>>) LocalizedEnum.list(Category.class);
	static final List<Scale> SCALES = Collections.unmodifiableList(
			Arrays.asList(new Scale("H0"), new Scale("N"), new Scale("0")));
	static final List<Railway> RAILWAYS = Collections.unmodifiableList(
			Arrays.asList(new Railway("DB"), new Railway("Scnf")));
	static final List<Brand> BRANDS = Collections.unmodifiableList(
			Arrays.asList(new Brand("Maerklin"), new Brand("Roco")));
	
	@Mock BrowseService mockService;
	BrowseController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		initService();
		controller = new BrowseController(mockService);
	}

	private void initService() {
		when(mockService.eras()).thenReturn(ERAS);
		when(mockService.categories()).thenReturn(CATEGORIES);
		when(mockService.scales()).thenReturn(SCALES);
		when(mockService.railways()).thenReturn(RAILWAYS);
		when(mockService.brands()).thenReturn(BRANDS);
	}
	
	@Test
	public void shouldRenderTheIndexPage() {
		
		ModelAndView mav = controller.index();
		
		assertViewName(mav, "browse/index");
		assertModelAttributeValue(mav, "eras", ERAS);
		assertModelAttributeValue(mav, "scales", SCALES);
		assertModelAttributeValue(mav, "railways", RAILWAYS);
		assertModelAttributeValue(mav, "brands", BRANDS);
		assertModelAttributeValue(mav, "categories", CATEGORIES);
	}

	@Test
	public void shouldRenderTheBrandsPage() {
		
		ModelAndView mav = controller.brands();
		
		assertViewName(mav, "browse/brands");
		assertModelAttributeValue(mav, "brands", BRANDS);
	}
	
	@Test
	public void shouldRenderTheBrandHomepage() {
		String slug = "acme";
		Brand value = new Brand();
		when(mockService.findBrand(eq(slug))).thenReturn(value);
		
		ModelAndView mav = controller.brand(slug);
		
		assertViewName(mav, "browse/brand");
		assertModelAttributeValue(mav, "brand", value);
	}
	
	@Test
	public void shouldRenderTheErasPage() {
		
		ModelAndView mav = controller.eras();
		
		assertViewName(mav, "browse/eras");
		assertModelAttributeValue(mav, "eras", ERAS);
	}
	
	@Test
	public void shouldRenderTheEraHomepage() {
		String slug = "iii";
		LocalizedEnum<Era> value = new LocalizedEnum<Era>(Era.III);
		when(mockService.findEra(eq(slug))).thenReturn(value);
		
		ModelAndView mav = controller.era(slug);
		
		assertViewName(mav, "browse/era");
		assertModelAttributeValue(mav, "era", value);
	}
	
	@Test
	public void shouldRenderTheRailwaysPage() {
		
		ModelAndView mav = controller.railways();
		
		assertViewName(mav, "browse/railways");
		assertModelAttributeValue(mav, "railways", RAILWAYS);
	}

	@Test
	public void shouldRenderTheRailwayHomepage() {
		String slug = "fs";
		Railway value = new Railway();
		when(mockService.findRailway(eq(slug))).thenReturn(value);
		
		ModelAndView mav = controller.railway(slug);
		
		assertViewName(mav, "browse/railway");
		assertModelAttributeValue(mav, "railway", value);
		assertModelAttributeValue(mav, "categories", CATEGORIES);
		assertModelAttributeValue(mav, "eras", ERAS);
	}
	
	@Test
	public void shouldRenderTheScalesPage() {
		
		ModelAndView mav = controller.scales();
		
		assertViewName(mav, "browse/scales");
		assertModelAttributeValue(mav, "scales", SCALES);
	}
	
	@Test
	public void shouldRenderTheScaleHomepage() {
		String slug = "fs";
		Scale value = new Scale();
		when(mockService.findScale(eq(slug))).thenReturn(value);
		
		ModelAndView mav = controller.scale(slug);
		
		assertViewName(mav, "browse/scale");
		assertModelAttributeValue(mav, "scale", value);
	}
	
	@Test
	public void shouldRenderTheCategoriesPage() {
		
		ModelAndView mav = controller.categories();
		
		assertViewName(mav, "browse/categories");
		assertModelAttributeValue(mav, "categories", CATEGORIES);
	}
	
	@Test
	public void shouldRenderTheCategoryHomepage() {
		String slug = "electric-locomotives";
		LocalizedEnum<Category> value = new LocalizedEnum<Category>(Category.ELECTRIC_LOCOMOTIVES);
		when(mockService.findCategory(eq(slug))).thenReturn(value);
		
		ModelAndView mav = controller.category(slug);
		
		assertViewName(mav, "browse/category");
		assertModelAttributeValue(mav, "category", value);
	}
}
