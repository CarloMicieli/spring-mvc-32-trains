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

import java.util.Arrays;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.trenako.entities.Scale;
import com.trenako.services.ScalesService;
import com.trenako.web.errors.NotFoundException;

/**
 * @author Carlo Micieli
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminScalesControllerTests {

	@Mock RedirectAttributes mockRedirect;
	@Mock BindingResult mockResult;
	@Mock RedirectAttributes mockRedirectAtts;
	@Mock ScalesService mockService;
	AdminScalesController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new AdminScalesController(mockService);
	}
	
	@Test
	public void listActionShouldListAllScales() {
		Iterable<Scale> value = Arrays.asList(new Scale(), new Scale());
		when(mockService.findAll()).thenReturn(value);

		ModelAndView mav = controller.list();
		
		verify(mockService, times(1)).findAll();
		assertViewName(mav, "scale/list");
		assertModelAttributeAvailable(mav, "scales");
	}
			
	@Test
	public void showActionShouldShowABrand() {
		ObjectId id = new ObjectId();
		Scale value = new Scale();
		when(mockService.findById(eq(id))).thenReturn(value);
		
		ModelAndView mav = controller.show(id);
		
		verify(mockService, times(1)).findById(eq(id));
		assertViewName(mav, "scale/show");
		assertModelAttributeAvailable(mav, "scale");
	}

	@Test(expected = NotFoundException.class)
	public void showActionShouldThrowsExceptionIfScaleNotFound() {
		ObjectId id = new ObjectId();
		when(mockService.findById(eq(id))).thenReturn(null);
		
		controller.show(id);
	}

	@Test
	public void newActionShouldInitCreationForm() {
		ModelAndView mav = controller.newForm();
		
		assertViewName(mav, "scale/new");
		assertModelAttributeAvailable(mav, "scale");
	}

	@Test
	public void createActionShouldRedirectAfterValidationErrors() {
		Scale scale = new Scale();
		when(mockResult.hasErrors()).thenReturn(true);
		
		ModelAndView mav = controller.create(scale, mockResult, mockRedirect);
		
		verify(mockService, times(0)).save(eq(scale));
		assertViewName(mav, "scale/new");
		assertModelAttributeAvailable(mav, "scale");
	}
	
	@Test
	public void createActionShouldCreateNewScales() {
		Scale scale = new Scale();
		when(mockResult.hasErrors()).thenReturn(false);
		RedirectAttributes redirectAtts = new RedirectAttributesModelMap();
		
		ModelAndView mav = controller.create(scale, mockResult, redirectAtts);
		
		verify(mockService, times(1)).save(eq(scale));
		assertViewName(mav, "redirect:/admin/scales");
		assertEquals(1, redirectAtts.getFlashAttributes().size());
		assertEquals("Scale created", redirectAtts.getFlashAttributes().get("message"));
	}
	
	@Test
	public void editActionShouldInitEditingForm() {
		ObjectId id = new ObjectId();
		Scale value = new Scale();
		when(mockService.findById(eq(id))).thenReturn(value);
		
		ModelAndView mav = controller.editForm(id);
		
		assertViewName(mav, "scale/edit");
		assertModelAttributeAvailable(mav, "scale");
	}
	
	@Test(expected = NotFoundException.class)
	public void editActionShouldThrowsExceptionIfScaleNotFound() {
		ObjectId id = new ObjectId();
		when(mockService.findById(eq(id))).thenReturn(null);
		
		controller.editForm(id);
	}
	
	@Test
	public void saveActionShouldRedirectAfterValidationErrors() {
		Scale scale = new Scale();
		when(mockResult.hasErrors()).thenReturn(true);
		
		ModelAndView mav = controller.save(scale, mockResult, mockRedirect);
		
		verify(mockService, times(0)).save(eq(scale));
		assertViewName(mav, "scale/edit");
		assertModelAttributeAvailable(mav, "scale");
	}
	
	@Test
	public void saveActionShouldCreateNewScales() {
		RedirectAttributes redirectAtts = new RedirectAttributesModelMap();
		Scale scale = new Scale();
		when(mockResult.hasErrors()).thenReturn(false);
				
		ModelAndView mav = controller.save(scale, mockResult, redirectAtts);
		
		verify(mockService, times(1)).save(eq(scale));
		assertViewName(mav, "redirect:/admin/scales");
		assertEquals(1, redirectAtts.getFlashAttributes().size());
		assertEquals("Scale saved", redirectAtts.getFlashAttributes().get("message"));
	}
			
	@Test(expected = NotFoundException.class)
	public void deleteActionShouldThrowsExceptionIfScaleNotFound() {
		ObjectId id = new ObjectId();
		when(mockService.findById(eq(id))).thenReturn(null);
		
		controller.delete(id, mockRedirect);
	}
	
	@Test
	public void deleteActionShouldDeleteScales() {
		Scale value = new Scale();
		ObjectId id = new ObjectId();
		when(mockService.findById(eq(id))).thenReturn(value);
		
		RedirectAttributes redirectAtts = new RedirectAttributesModelMap();
		
		ModelAndView mav = controller.delete(id, redirectAtts);
		
		verify(mockService, times(1)).remove(eq(value));
		assertViewName(mav, "redirect:/admin/scales");
		assertEquals(1, redirectAtts.getFlashAttributes().size());
		assertEquals("Scale deleted", redirectAtts.getFlashAttributes().get("message"));
	}
}