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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
import com.trenako.web.images.MultipartFileValidator;
import com.trenako.web.images.UploadRequest;
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
		controller.setMultipartFileValidator(new MultipartFileValidator());
		
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
		when(mockFile.isEmpty()).thenReturn(true);
		RollingStock rs = new RollingStock();
		
		String viewName = controller.create(rs, mockResult, mockFile, mockRedirect);
		
		verify(mockRedirect, times(1)).addAttribute(eq(rs));
		assertEquals("rollingstock/new", viewName);
	}
		
	@Test
	public void shouldCreateRollingStocks() {
		when(mockResult.hasErrors()).thenReturn(false);
		
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.scale(scaleH0())
			.railway(fs())
			.build();
		MultipartFile file = buildFile(MediaType.IMAGE_JPEG);
		UploadRequest req = UploadRequest.create(rs, file);
		
		String viewName = controller.create(rs, mockResult, file, mockRedirect);
		
		assertEquals("redirect:/rollingstocks/{slug}", viewName);
		verify(service, times(1)).save(eq(rs));
		verify(imgService, times(1)).saveImageWithThumb(eq(req), eq(100));
		verify(mockRedirect, times(1)).addAttribute(eq("slug"), eq("acme-123456"));
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), 
				eq(RollingStocksController.ROLLING_STOCK_CREATED_MSG));
	}
	
	@Test 
	public void shouldRenderEditRollingStockForms() {
		String slug = "rs-slug";
		RollingStock value = new RollingStock.Builder(acme(), "123456")
			.scale(scaleH0())
			.railway(fs())
			.description("desc")
			.build();
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
		ObjectId rsId = new ObjectId();
		RollingStock rs = build(rsId, acme(), db(), scaleH0());
		
		String viewName = controller.save(rs, mockResult, mockRedirect);
		
		assertEquals("redirect:/rollingstocks/{slug}", viewName);
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), 
				eq(RollingStocksController.ROLLING_STOCK_SAVED_MSG));
	}
	
	@Test
	public void shouldRedirectAfterSaveValidationErrors() {
		when(mockResult.hasErrors()).thenReturn(true);
		RollingStock rs = new RollingStock();
		
		String viewName = controller.save(rs, mockResult, mockRedirect);
		
		assertEquals("rollingstock/edit", viewName);
		verify(mockRedirect, times(1)).addAttribute(eq("rollingStock"), eq(rs));
	}
	
	@Test
	public void shouldDeleteRollingStocks() {
		RollingStock rs = new RollingStock();
		
		String viewName = controller.delete(rs, mockRedirect);
		
		assertEquals("redirect:/rs", viewName);
		verify(service, times(1)).remove(eq(rs));
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), 
				eq(RollingStocksController.ROLLING_STOCK_DELETED_MSG));
	}
	
	// helper methods
	
	RollingStock build(ObjectId rsId, Brand brand, Railway railway, Scale scale) {
		RollingStock rs = new RollingStock();
		rs.setId(rsId);
		rs.setItemNumber("123456");
		rs.setBrand(brand);
		rs.setRailway(railway);
		rs.setScale(scale);
		return rs;
	}
	
	private MultipartFile buildFile(MediaType mediaType) {
		byte[] content = "file content".getBytes();
		return new MockMultipartFile("image.jpg", "image.jpg", mediaType.toString(), content);
	}
}