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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Account;
import com.trenako.entities.Brand;
import com.trenako.entities.Comment;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.mapping.LocalizedField;
import com.trenako.mapping.WeakDbRef;
import com.trenako.security.AccountDetails;
import com.trenako.services.FormValuesService;
import com.trenako.services.RollingStocksService;
import com.trenako.services.view.RollingStockView;
import com.trenako.values.Category;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;
import com.trenako.web.controllers.form.RollingStockForm;
import com.trenako.web.errors.NotFoundException;
import com.trenako.web.images.UploadRequest;
import com.trenako.web.images.WebImageService;
import com.trenako.web.security.UserContext;

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
	@Mock FormValuesService valuesService;
	
	RollingStocksController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new RollingStocksController(service, valuesService, imgService);
		
		when(valuesService.brands()).thenReturn(BRANDS);
		when(valuesService.railways()).thenReturn(RAILWAYS);
		when(valuesService.scales()).thenReturn(SCALES);
		
		when(valuesService.categories()).thenReturn(CATEGORIES);
		when(valuesService.eras()).thenReturn(ERAS);
		when(valuesService.powerMethods()).thenReturn(POWERMETHODS);
		
		when(valuesService.getBrand(eq(acme().getSlug()))).thenReturn(acme());
		when(valuesService.getRailway(eq(fs().getSlug()))).thenReturn(fs());
		when(valuesService.getScale(eq(scaleH0().getSlug()))).thenReturn(scaleH0());
	}
	
	@Test
	public void shouldRenderRollingStockViews() {
		String slug = "rs-slug";
		RollingStockView value = new RollingStockView(new RollingStock(), null, null);
		when(service.findViewBySlug(eq(slug))).thenReturn(value);
		
		ModelMap model = new ExtendedModelMap();
		
		String viewName = controller.show(slug, model);
		
		assertEquals("rollingstock/show", viewName);
		assertTrue(model.containsAttribute("result"));
		assertEquals(value, model.get("result"));
	}
	
	@Test
	public void shouldInitNewCommentsWhenShowingRollingStocks() {
		String slug = "rs-slug";
		RollingStockView value = new RollingStockView(rollingStock(), null, null);
		when(service.findViewBySlug(eq(slug))).thenReturn(value);
		
		ModelMap model = new ExtendedModelMap();
		
		controller.setUserContext(mockSecurity());
		
		@SuppressWarnings("unused")
		String viewName = controller.show(slug, model);
		
		Comment newComment = (Comment) model.get("newComment");
		assertNotNull("Comment is null", newComment);
		assertEquals("acme-123456", newComment.getRollingStock().getSlug());
		assertEquals("bob", newComment.getAuthor().getSlug());
	}
	
	@Test(expected = NotFoundException.class)
	public void shouldThrowsExceptionIfRollingStockNotFound() {
		String slug = "rs-slug";
		when(service.findBySlug(eq(slug))).thenReturn(null);
		
		controller.show(slug, new ExtendedModelMap());
	}
	
	@Test
	public void shouldRenderRollingStockCreationViews() {
		ModelMap model = new ModelMap();
		
		String viewName = controller.createNew(model);
		
		assertEquals("rollingstock/new", viewName);

		RollingStockForm form = (RollingStockForm) model.get("rollingStockForm");
		assertEquals(new RollingStock(), form.getRs());
	}
	
	@Test
	public void shouldCreateNewRollingStocks() {
		when(mockResult.hasErrors()).thenReturn(false);
		
		MultipartFile file = buildFile(MediaType.IMAGE_JPEG);
		UploadRequest req = UploadRequest.create(rollingStock(), file);
		RollingStockForm form = rsForm(file);
		form.setTags("one, two");
		
		ModelMap model = new ModelMap();
		
		String viewName = controller.create(form, mockResult, model, mockRedirect);
		
		assertEquals("redirect:/rollingstocks/{slug}", viewName);
		
		ArgumentCaptor<RollingStock> arg = ArgumentCaptor.forClass(RollingStock.class);
		verify(service, times(1)).save(arg.capture());
		
		RollingStock savedRs = arg.getValue();
		assertEquals(rollingStock(), savedRs);
		assertTrue("Brand not loaded", savedRs.getBrand().isLoaded());
		assertTrue("Scale not loaded", savedRs.getRailway().isLoaded());
		assertTrue("Scale not loaded", savedRs.getScale().isLoaded());
		assertEquals("[one, two]", savedRs.getTags().toString());
		
		verify(imgService, times(1)).saveImageWithThumb(eq(req), eq(100));
		verify(mockRedirect, times(1)).addAttribute(eq("slug"), eq("acme-123456"));
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), 
				eq(RollingStocksController.ROLLING_STOCK_CREATED_MSG));
	}

	@Test
	public void shouldRedirectAfterValidationErrorsDuringRollingStocksCreation() {
		when(mockResult.hasErrors()).thenReturn(true);
		when(mockFile.isEmpty()).thenReturn(true);
		RollingStockForm form = rsForm(mockFile);
		
		ModelMap model = new ModelMap();
		
		String viewName = controller.create(form, mockResult, model, mockRedirect);
		
		assertEquals("rollingstock/new", viewName);
		assertNotNull("Form is null", model.get("rollingStockForm"));
	}
	
	@Test
	public void shouldShowErrorMessageAfterDuplicatedKeyErrorsDuringCreation() {
		doThrow(new DuplicateKeyException("Duplicate key error"))
			.when(service).save(eq(rollingStock()));
		
		when(mockResult.hasErrors()).thenReturn(false);
		when(mockFile.isEmpty()).thenReturn(true);
		RollingStockForm form = rsForm(mockFile);
		
		ModelMap model = new ModelMap();
		
		String viewName = controller.create(form, mockResult, model, mockRedirect);
		
		assertEquals("rollingstock/new", viewName);
		assertNotNull("Form is null", model.get("rollingStockForm"));
		assertEquals(RollingStocksController.ROLLING_STOCK_DUPLICATED_VALUE_MSG, 
				(ControllerMessage) model.get("message"));
	}
	
	@Test
	public void shouldShowErrorMessageAfterDatabaseErrorsDuringCreation() {
		doThrow(new RecoverableDataAccessException("Database error"))
			.when(service).save(eq(rollingStock()));
		
		when(mockResult.hasErrors()).thenReturn(false);
		when(mockFile.isEmpty()).thenReturn(true);
		RollingStockForm form = rsForm(mockFile);
		
		ModelMap model = new ModelMap();
		
		String viewName = controller.create(form, mockResult, model, mockRedirect);
		
		assertEquals("rollingstock/new", viewName);
		assertNotNull("Form is null", model.get("rollingStockForm"));
		assertEquals(RollingStocksController.ROLLING_STOCK_DATABASE_ERROR_MSG, 
				(ControllerMessage) model.get("message"));
	}
		
	@Test 
	public void shouldRenderRollingStockEditingViews() {
		String slug = "rs-slug";
		when(service.findBySlug(eq(slug))).thenReturn(rollingStock());
		
		ModelMap model = new ModelMap();
		String viewName = controller.editForm(slug, model);
		
		verify(service, times(1)).findBySlug(slug);
		assertEquals("rollingstock/edit", viewName);
		
		RollingStockForm form = (RollingStockForm) model.get("rollingStockForm");
		assertEquals(rollingStock(), form.getRs());
	}
	
	@Test
	public void shouldSaveRollingStocks() {
		when(mockResult.hasErrors()).thenReturn(false);
		RollingStockForm form = rsForm(null);
		form.setTags("two, one");
		ModelMap model = new ModelMap();
		
		String viewName = controller.save(form, mockResult, model, mockRedirect);
		
		assertEquals("redirect:/rollingstocks/{slug}", viewName);
		ArgumentCaptor<RollingStock> arg = ArgumentCaptor.forClass(RollingStock.class);
		verify(service, times(1)).save(arg.capture());
		
		RollingStock savedRs = arg.getValue();
		assertEquals(rollingStock(), savedRs);
		assertTrue("Brand not loaded", savedRs.getBrand().isLoaded());
		assertTrue("Scale not loaded", savedRs.getRailway().isLoaded());
		assertTrue("Scale not loaded", savedRs.getScale().isLoaded());
		assertEquals("[one, two]", savedRs.getTags().toString());
		
		verify(mockRedirect, times(1)).addAttribute(eq("slug"), eq("acme-123456"));
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), 
				eq(RollingStocksController.ROLLING_STOCK_SAVED_MSG));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringSave() {
		when(mockResult.hasErrors()).thenReturn(true);
		RollingStockForm form = rsForm(null);
		ModelMap model = new ModelMap();
		
		String viewName = controller.save(form, mockResult, model, mockRedirect);
		
		assertEquals("rollingstock/edit", viewName);
		assertNotNull("Form is null", model.get("rollingStockForm"));
	}

	@Test
	public void shouldShowErrorMessageAfterDatabaseErrorsDuringSave() {
		doThrow(new RecoverableDataAccessException("Database error"))
			.when(service).save(eq(rollingStock()));
		
		when(mockResult.hasErrors()).thenReturn(false);
		when(mockFile.isEmpty()).thenReturn(true);
		RollingStockForm form = rsForm(mockFile);
		
		ModelMap model = new ModelMap();
		
		String viewName = controller.save(form, mockResult, model, mockRedirect);
		
		assertEquals("rollingstock/edit", viewName);
		assertNotNull("Form is null", model.get("rollingStockForm"));
		assertEquals(RollingStocksController.ROLLING_STOCK_DATABASE_ERROR_MSG, 
				(ControllerMessage) model.get("message"));
	}
	
	@Test
	public void shouldDeleteRollingStocks() {
		String viewName = controller.delete(rollingStock(), mockRedirect);
		
		assertEquals("redirect:/rs", viewName);
		verify(service, times(1)).remove(eq(rollingStock()));
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), 
				eq(RollingStocksController.ROLLING_STOCK_DELETED_MSG));
	}
	
	// helper methods

	static final RollingStock RS = new RollingStock.Builder(acme(), "123456")
		.scale(scaleH0())
		.railway(fs())
		.description("desc")
		.build();
	
	private RollingStockForm rsForm(MultipartFile file) {
		RollingStock in = new RollingStock();
		in.setItemNumber("123456");
		in.setBrand(WeakDbRef.buildFromSlug("acme", Brand.class));
		in.setRailway(WeakDbRef.buildFromSlug("fs", Railway.class));
		in.setScale(WeakDbRef.buildFromSlug("h0", Scale.class));
		in.setDescription(LocalizedField.localize("desc"));
		
		RollingStockForm form = new RollingStockForm(in, valuesService, file);
		return form;
	}
	
	private UserContext mockSecurity() {
		UserContext mockSecurity = mock(UserContext.class);
		Account user = new Account.Builder("mail@mail.com")
			.displayName("bob")
			.build();
		when(mockSecurity.getCurrentUser()).thenReturn(new AccountDetails(user));
		
		return mockSecurity;
	}
	
	private RollingStock rollingStock() {
		return RS;	
	}
	
	private MultipartFile buildFile(MediaType mediaType) {
		byte[] content = "file content".getBytes();
		return new MockMultipartFile("image.jpg", "image.jpg", mediaType.toString(), content);
	}
}