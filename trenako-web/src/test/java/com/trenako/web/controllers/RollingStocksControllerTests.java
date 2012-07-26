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
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.*;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.services.RollingStocksService;
import com.trenako.values.Category;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;
import com.trenako.web.errors.NotFoundException;
import com.trenako.web.images.WebImageService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RollingStocksControllerTests {
	
	static final List<Brand> BRANDS = Arrays.asList(acme(), marklin(), roco());
	static final List<Railway> RAILWAYS = Arrays.asList(db(), fs());
	static final List<Scale> SCALES = Arrays.asList(scaleH0(), scaleN());

	static final List<LocalizedEnum<Era>> ERAS = (List<LocalizedEnum<Era>>) LocalizedEnum.list(Era.class); 
	static final List<LocalizedEnum<PowerMethod>> POWERMETHODS = (List<LocalizedEnum<PowerMethod>>) LocalizedEnum.list(PowerMethod.class);
	static final List<LocalizedEnum<Category>> CATEGORIES = (List<LocalizedEnum<Category>>) LocalizedEnum.list(Category.class);

	@Mock MultipartFile mockFile;
	@Mock RedirectAttributes mockRedirect;
	@Mock BindingResult mockResult;
	@Mock WebImageService imgService;
	@Mock RollingStocksService service;
	
	RollingStocksController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new RollingStocksController(service, imgService);
		
		when(service.brands()).thenReturn(BRANDS);
		when(service.railways()).thenReturn(RAILWAYS);
		when(service.scales()).thenReturn(SCALES);
		
		when(service.categories()).thenReturn(CATEGORIES);
		when(service.eras()).thenReturn(ERAS);
		when(service.powerMethods()).thenReturn(POWERMETHODS);

	}
	
	@Test
	public void shouldShowRollingStocks() {
		String slug = "rs-slug";
		RollingStock value = new RollingStock();
		when(service.findBySlug(eq(slug))).thenReturn(value);
		
		ModelMap model = new ExtendedModelMap();
		
		String viewName = controller.show(slug, model);
		
		assertEquals("rollingstock/show", viewName);
		assertTrue(model.containsAttribute("rollingStock"));
		assertEquals(value, model.get("rollingStock"));
	}
	
	@Test(expected = NotFoundException.class)
	public void shouldThrowsExceptionIfRollingStockNotFound() {
		String slug = "rs-slug";
		when(service.findBySlug(eq(slug))).thenReturn(null);
		
		controller.show(slug, new ExtendedModelMap());
	}
	
	@Test
	public void shouldRenderNewRollingStockForms() {		
		
		ModelAndView mav = controller.createNew();
		
		assertViewName(mav, "rollingstock/new");
		assertAndReturnModelAttributeOfType(mav, "rollingStock", RollingStock.class);
		assertCompareListModelAttribute(mav, "brands", BRANDS);
		assertCompareListModelAttribute(mav, "railways", RAILWAYS);
		assertCompareListModelAttribute(mav, "scales", SCALES);
		assertCompareListModelAttribute(mav, "categories", CATEGORIES);
		assertCompareListModelAttribute(mav, "eras", ERAS);
		assertCompareListModelAttribute(mav, "powerMethods", POWERMETHODS);
	}
	
	@Test
	public void shouldRedirectAfterCreateValidationErrors() {
		when(mockResult.hasErrors()).thenReturn(true);
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		
		String viewName = controller.create(rs, mockResult, mockFile, mockRedirect);
		
		verify(mockRedirect, times(1)).addAttribute(eq(rs));
		assertEquals("rollingstock/new", viewName);
	}
		
	RollingStock build(ObjectId rsId, ObjectId brandId, ObjectId railwayId, ObjectId scaleId) {
		when(service.findBrand(eq(brandId))).thenReturn(new Brand("ACME"));
		when(service.findRailway(eq(railwayId))).thenReturn(new Railway("DB"));
		when(service.findScale(eq(scaleId))).thenReturn(new Scale("H0"));
	
		return new RollingStock.Builder(new Brand(brandId), "123456")
			.id(rsId)
			.railway(new Railway(railwayId))
			.scale(new Scale(scaleId))
			.build();
	}	
	
	@Test
	public void shouldCreateRollingStocks() {
		when(mockResult.hasErrors()).thenReturn(false);
		when(mockFile.isEmpty()).thenReturn(false);
		
		ObjectId brandId = new ObjectId();
		ObjectId railwayId = new ObjectId();
		ObjectId scaleId = new ObjectId();
		ObjectId rsId = new ObjectId();
		RollingStock rs = build(rsId, brandId, railwayId, scaleId);
		
		String viewName = controller.create(rs, mockResult, mockFile, mockRedirect);
		
		assertEquals("redirect:/rollingstocks/{slug}", viewName);
		verify(service, times(1)).save(eq(rs));
		verify(imgService, times(1)).saveImage(eq(rsId), eq(mockFile));
		verify(mockRedirect, times(1)).addAttribute(eq("slug"), eq("acme-123456"));
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), eq("rolling.stock.created"));
	}
	
	@Test 
	public void shouldRenderEditRollingStockForms() {
		String slug = "rs-slug";
		RollingStock value = new RollingStock.Builder("ACME", "123456").build();
		when(service.findBySlug(eq(slug))).thenReturn(value);
		
		ModelAndView mav = controller.editForm(slug);
		
		verify(service, times(1)).findBySlug(slug);
		assertViewName(mav, "rollingstock/edit");
		assertModelAttributeValue(mav, "rollingStock", value);
		assertCompareListModelAttribute(mav, "brands", BRANDS);
		assertCompareListModelAttribute(mav, "railways", RAILWAYS);
		assertCompareListModelAttribute(mav, "scales", SCALES);
		assertCompareListModelAttribute(mav, "categories", CATEGORIES);
		assertCompareListModelAttribute(mav, "eras", ERAS);
		assertCompareListModelAttribute(mav, "powerMethods", POWERMETHODS);
	}
	
	@Test
	public void shouldSaveRollingStocks() {
		when(mockResult.hasErrors()).thenReturn(false);
		ObjectId brandId = new ObjectId();
		ObjectId railwayId = new ObjectId();
		ObjectId scaleId = new ObjectId();
		ObjectId rsId = new ObjectId();
		RollingStock rs = build(rsId, brandId, railwayId, scaleId);
		
		String viewName = controller.save(rs, mockResult, mockRedirect);
		
		assertEquals("redirect:/rollingstocks/{slug}", viewName);
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), eq("rolling.stock.saved"));
	}
	
	@Test
	public void shouldRedirectAfterSaveValidationErrors() {
		when(mockResult.hasErrors()).thenReturn(true);
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		
		String viewName = controller.save(rs, mockResult, mockRedirect);
		
		assertEquals("rollingstock/edit", viewName);
		verify(mockRedirect, times(1)).addAttribute(eq("rollingStock"), eq(rs));
	}
	
	@Test(expected = NotFoundException.class)
	public void shouldThrowsExceptionIfRollingStockNotFoundToEdit() {
		String slug = "rs-slug";
		when(service.findBySlug(eq(slug))).thenReturn(null);
		
		controller.editForm(slug);
	}
	
	@Test
	public void shouldDeleteRollingStocks() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		
		String viewName = controller.delete(rs, mockRedirect);
		
		assertEquals("redirect:/rs", viewName);
		verify(service, times(1)).remove(eq(rs));
	}
}
