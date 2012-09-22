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
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Scale;
import com.trenako.services.ScalesService;
import com.trenako.web.test.DatabaseError;

/**
 * @author Carlo Micieli
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminScalesControllerTests {

	private final static Page<Scale> SCALES = 
		new PageImpl<Scale>(Arrays.asList(new Scale(), new Scale()));
	private ModelMap model = new ModelMap();
		
	@Mock BindingResult bindingResult;
	@Mock RedirectAttributes redirectAtts;
	@Mock ScalesService service;
	
	private AdminScalesController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new AdminScalesController(service);
	}
	
	@Test
	public void shouldShowTheScalesListWithPagination() {
		Pageable pageable = new PageRequest(0, 10);
		when(service.findAll(eq(pageable))).thenReturn(SCALES);
		
		String viewName = controller.list(pageable, model);
		
		verify(service, times(1)).findAll(eq(pageable));
		assertEquals(viewName, "scale/list");
		assertEquals(SCALES, model.get("scales"));
	}
			
	@Test
	public void shouldShowABrand() {
		String slug = "scale-slug";
		Scale scale = new Scale();
		when(service.findBySlug(eq(slug))).thenReturn(scale);
		
		String viewName = controller.show(slug, model, redirectAtts);
		
		verify(service, times(1)).findBySlug(eq(slug));
		assertEquals(viewName, "scale/show");
		assertEquals(scale, model.get("scale"));
	}

	@Test
	public void shouldRedirectToScalesListWhenScaleWasNotFound() {
		String slug = "not-found";
		when(service.findBySlug(eq(slug))).thenReturn(null);
		
		String viewName = controller.show(slug, model, redirectAtts);
		
		assertEquals(viewName, "redirect:/admin/scales");
		verify(service, times(1)).findBySlug(eq(slug));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), 
			eq(AdminScalesController.SCALE_NOT_FOUND_MSG));
	}

	@Test
	public void shouldRenderTheScaleCreationForms() {
		String viewName = controller.newForm(model);
		
		assertEquals(viewName, "scale/new");
		assertTrue(model.containsAttribute("scale"));
	}
	
	@Test
	public void shouldCreateNewScales() {
		Scale scale = new Scale();
		when(bindingResult.hasErrors()).thenReturn(false);
		
		String viewName = controller.create(scale, bindingResult, model, redirectAtts);

		assertEquals("redirect:/admin/scales", viewName);
		verify(service, times(1)).save(eq(scale));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), 
				eq(AdminScalesController.SCALE_CREATED_MSG));
	}

	@Test
	public void shouldRedirectAfterValidationErrorsDuringScaleCreation() {
		Scale scale = new Scale();
		when(bindingResult.hasErrors()).thenReturn(true);
		
		String viewName = controller.create(scale, bindingResult, model, redirectAtts);
		
		assertEquals("scale/new", viewName);
		assertEquals(scale, model.get("scale"));
		verify(service, times(0)).save(eq(scale));
	}
	
	@Test
	public void shouldRedirectAfterDatabaseErrorsDuringScaleCreation() {
		Scale scale = new Scale();
		when(bindingResult.hasErrors()).thenReturn(false);
		doThrow(new DatabaseError())
			.when(service)
			.save(eq(scale));
		
		String viewName = controller.create(scale, bindingResult, model, redirectAtts);
		
		verify(service, times(1)).save(eq(scale));
		assertEquals("scale/new", viewName);
		assertEquals(scale, model.get("scale"));
		assertEquals(AdminScalesController.SCALE_DB_ERROR_MSG, model.get("message"));
	}
	
	@Test
	public void shouldRenderScaleEditingForm() {
		String slug = "scale-slug";
		Scale scale = new Scale();
		when(service.findBySlug(eq(slug))).thenReturn(scale);
		
		String viewName = controller.editForm(slug, model, redirectAtts);
		
		verify(service, times(1)).findBySlug(eq(slug));
		assertEquals(viewName, "scale/edit");
		assertEquals(scale, model.get("scale"));
	}
	
	@Test
	public void shouldRedirectToScalesListWhenTheScaleToEditWasNotFound() {
		String slug = "not-found";
		when(service.findBySlug(eq(slug))).thenReturn(null);
		
		String viewName = controller.editForm(slug, model, redirectAtts);
		
		verify(service, times(1)).findBySlug(eq(slug));
		assertEquals(viewName, "redirect:/admin/scales");
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), 
				eq(AdminScalesController.SCALE_NOT_FOUND_MSG));	
	}
	
	@Test
	public void shouldSaveScaleChanges() {
		Scale scale = new Scale();
		when(bindingResult.hasErrors()).thenReturn(false);
				
		String viewName = controller.save(scale, bindingResult, model, redirectAtts);
		
		verify(service, times(1)).save(eq(scale));
		assertEquals("redirect:/admin/scales", viewName);
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), 
				eq(AdminScalesController.SCALE_SAVED_MSG));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsSavingScaleChanges() {
		Scale scale = new Scale();
		when(bindingResult.hasErrors()).thenReturn(true);

		String viewName = controller.save(scale, bindingResult, model, redirectAtts);

		assertEquals("scale/edit", viewName);
		assertEquals(scale, model.get("scale"));
		verify(service, times(0)).save(eq(scale));
	}
	
	@Test
	public void shouldRedirectAfterDatabaseErrorsSavingScaleChanges() {
		Scale scale = new Scale();
		when(bindingResult.hasErrors()).thenReturn(false);
		doThrow(new DatabaseError())
			.when(service)
			.save(eq(scale));
			
		String viewName = controller.save(scale, bindingResult, model, redirectAtts);

		verify(service, times(1)).save(eq(scale));
		assertEquals("scale/edit", viewName);
		assertEquals(scale, model.get("scale"));
		assertEquals(AdminScalesController.SCALE_DB_ERROR_MSG, model.get("message"));
	}
				
	@Test
	public void shouldDeleteScales() {
		String slug = "scale-slug";
		Scale value = new Scale();
		when(service.findBySlug(eq(slug))).thenReturn(value);
		
		String viewName = controller.delete(slug, redirectAtts);
		
		assertEquals("redirect:/admin/scales", viewName);
		verify(service, times(1)).remove(eq(value));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), 
				eq(AdminScalesController.SCALE_DELETED_MSG));
	}
}