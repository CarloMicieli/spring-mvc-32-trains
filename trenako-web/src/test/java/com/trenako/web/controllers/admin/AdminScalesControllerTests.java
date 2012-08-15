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
import static org.springframework.test.web.ModelAndViewAssert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Scale;
import com.trenako.services.ScalesService;

/**
 * @author Carlo Micieli
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminScalesControllerTests {

	@Mock Pageable mockPaging;
	@Mock BindingResult mockResult;
	@Mock RedirectAttributes mockRedirect;
	@Mock ScalesService mockService;
	
	private AdminScalesController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new AdminScalesController(mockService);
	}
	
	@Test
	public void shouldListAllScales() {
		ModelAndView mav = controller.list(mockPaging);
		
		verify(mockService, times(1)).findAll(eq(mockPaging));
		assertViewName(mav, "scale/list");
		assertModelAttributeAvailable(mav, "scales");
	}
			
	@Test
	public void shouldShowABrand() {
		String slug = "scale-slug";
		Scale value = new Scale();
		when(mockService.findBySlug(eq(slug))).thenReturn(value);
		
		ModelAndView mav = controller.show(slug);
		
		verify(mockService, times(1)).findBySlug(eq(slug));
		assertViewName(mav, "scale/show");
		assertModelAttributeAvailable(mav, "scale");
	}

	@Test
	public void shouldRenderScaleCreationForms() {
		ModelAndView mav = controller.newForm();
		
		assertViewName(mav, "scale/new");
		assertModelAttributeAvailable(mav, "scale");
	}

	@Test
	public void shouldRedirectAfterValidationErrorsDuringScaleCreation() {
		Scale scale = new Scale();
		when(mockResult.hasErrors()).thenReturn(true);
		
		String viewName = controller.create(scale, mockResult, mockRedirect);
		
		assertEquals("scale/new", viewName);
		verify(mockService, times(0)).save(eq(scale));
		verify(mockRedirect, times(1)).addAttribute(eq(scale));
	}
	
	@Test
	public void shouldCreateNewScales() {
		Scale scale = new Scale();
		when(mockResult.hasErrors()).thenReturn(false);
		
		String viewName = controller.create(scale, mockResult, mockRedirect);

		assertEquals("redirect:/admin/scales", viewName);
		verify(mockService, times(1)).save(eq(scale));
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), 
				eq(AdminScalesController.SCALE_CREATED_MSG));
	}
	
	@Test
	public void shouldRenderScaleEditingForm() {
		String slug = "scale-slug";
		Scale value = new Scale();
		when(mockService.findBySlug(eq(slug))).thenReturn(value);
		
		ModelAndView mav = controller.editForm(slug);
		
		verify(mockService, times(1)).findBySlug(eq(slug));
		assertViewName(mav, "scale/edit");
		assertModelAttributeAvailable(mav, "scale");
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsSavingScaleChanges() {
		Scale scale = new Scale();
		when(mockResult.hasErrors()).thenReturn(true);
		
		String viewName = controller.save(scale, mockResult, mockRedirect);

		assertEquals("scale/edit", viewName);
		verify(mockService, times(0)).save(eq(scale));
		verify(mockRedirect, times(1)).addAttribute(eq(scale));
	}
	
	@Test
	public void shouldSaveScaleChanges() {
		Scale scale = new Scale();
		when(mockResult.hasErrors()).thenReturn(false);
				
		String viewName = controller.save(scale, mockResult, mockRedirect);
		
		verify(mockService, times(1)).save(eq(scale));
		assertEquals("redirect:/admin/scales", viewName);
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), 
				eq(AdminScalesController.SCALE_SAVED_MSG));
	}
			
	@Test
	public void shouldDeleteScales() {
		String slug = "scale-slug";
		Scale value = new Scale();
		when(mockService.findBySlug(eq(slug))).thenReturn(value);
		
		String viewName = controller.delete(slug, mockRedirect);
		
		assertEquals("redirect:/admin/scales", viewName);
		verify(mockService, times(1)).remove(eq(value));
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), 
				eq(AdminScalesController.SCALE_DELETED_MSG));
	}
}