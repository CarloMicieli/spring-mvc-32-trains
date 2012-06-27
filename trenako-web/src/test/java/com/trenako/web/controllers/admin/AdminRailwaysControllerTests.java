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

import java.io.IOException;
import java.util.Arrays;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.trenako.entities.Image;
import com.trenako.entities.Railway;
import com.trenako.services.RailwaysService;
import com.trenako.web.errors.NotFoundException;
import com.trenako.web.images.ImageProcessingAdapter;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminRailwaysControllerTests {
	@Mock ImageProcessingAdapter mockImgUtil;
	@Mock BindingResult mockResult;
	@Mock MultipartFile mockFile;
	@Mock RedirectAttributes mockRedirect;
	@Mock RailwaysService mockService;
	AdminRailwaysController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new AdminRailwaysController(mockService, mockImgUtil);
	}
	
	@Test
	public void listActionShouldListAllRailways() {
		Iterable<Railway> value = Arrays.asList(new Railway(), new Railway());
		when(mockService.findAll()).thenReturn(value);

		ModelAndView mav = controller.list();
	
		verify(mockService, times(1)).findAll();
		assertViewName(mav, "railway/list");
		assertModelAttributeAvailable(mav, "railways");
	}
	
	@Test
	public void showActionShouldShowARailway() {
		ObjectId id = new ObjectId();
		Railway value = new Railway();
		when(mockService.findById(eq(id))).thenReturn(value);
		
		ModelAndView mav = controller.show(id);
		
		verify(mockService, times(1)).findById(eq(id));
		assertViewName(mav, "railway/show");
		assertModelAttributeAvailable(mav, "railway");
	}
	
	@Test(expected = NotFoundException.class)
	public void showActionShouldThrowsExceptionIfRailwayNotFound() {
		ObjectId id = new ObjectId();
		when(mockService.findById(eq(id))).thenReturn(null);
		
		controller.show(id);
	}

	@Test
	public void newActionShouldInitCreationForm() {
		ModelAndView mav = controller.newForm();
		
		assertViewName(mav, "railway/new");
		assertModelAttributeAvailable(mav, "railway");
	}
	
	@Test
	public void createActionShouldRedirectAfterValidationErrors() throws IOException {
		Railway railway = new Railway();
		when(mockResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAtts = new RedirectAttributesModelMap();
		
		String viewName = controller.create(railway, mockFile, mockResult, redirectAtts);
		
		verify(mockService, times(0)).save(eq(railway));
		assertEquals("railway/new", viewName);
		assertEquals(true, redirectAtts.containsAttribute("railway"));
	}
	
	@Test
	public void createActionShouldCreateNewRailways() throws IOException {
		Railway railway = new Railway();
		when(mockResult.hasErrors()).thenReturn(false);
		RedirectAttributes redirectAtts = new RedirectAttributesModelMap();
		
		String viewName = controller.create(railway, mockFile, mockResult, redirectAtts);
		
		verify(mockService, times(1)).save(eq(railway));
		assertEquals("redirect:/admin/railways", viewName);
		assertEquals(1, redirectAtts.getFlashAttributes().size());
		assertEquals("Railway created", redirectAtts.getFlashAttributes().get("message"));
	}
	
	@Test
	public void createActionShouldCreateNewRailwaysWithALogo() throws IOException {
		Railway railway = new Railway();
		when(mockResult.hasErrors()).thenReturn(false);
		
		when(mockFile.isEmpty()).thenReturn(false);
		when(mockImgUtil.createImage(eq(mockFile))).thenReturn(new Image());
		
		controller.create(railway, mockFile, mockResult, mockRedirect);
		
		verify(mockImgUtil, times(1)).createImage(eq(mockFile));
		assertNotNull("Railway logo is null", railway.getImage());
	}
	
	@Test
	public void editActionShouldInitEditingForm() {
		ObjectId id = new ObjectId();
		Railway value = new Railway();
		when(mockService.findById(eq(id))).thenReturn(value);
		
		ModelAndView mav = controller.editForm(id);
		
		assertViewName(mav, "railway/edit");
		assertModelAttributeAvailable(mav, "railway");
	}
	
	@Test(expected = NotFoundException.class)
	public void editActionShouldThrowsExceptionIfRailwayNotFound() {
		ObjectId id = new ObjectId();
		when(mockService.findById(eq(id))).thenReturn(null);
		
		controller.editForm(id);
	}
	
	@Test
	public void saveActionShouldRedirectAfterValidationErrors() throws IOException {
		Railway railway = new Railway();
		when(mockResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAtts = new RedirectAttributesModelMap();
		
		String viewName = controller.save(railway, mockResult, redirectAtts);
		
		verify(mockService, times(0)).save(eq(railway));
		assertEquals("railway/edit", viewName);
		assertEquals(true, redirectAtts.containsAttribute("railway"));
	}
	
	@Test
	public void saveActionShouldCreateNewRailways() throws IOException {
		RedirectAttributes redirectAtts = new RedirectAttributesModelMap();
		Railway railway = new Railway();
		when(mockResult.hasErrors()).thenReturn(false);
				
		String viewName = controller.save(railway, mockResult, redirectAtts);
		
		verify(mockService, times(1)).save(eq(railway));
		assertEquals("redirect:/admin/railways", viewName);
		assertEquals(1, redirectAtts.getFlashAttributes().size());
		assertEquals("Railway saved", redirectAtts.getFlashAttributes().get("message"));
	}
			
	@Test(expected = NotFoundException.class)
	public void deleteActionShouldThrowsExceptionIfRailwayNotFound() {
		ObjectId id = new ObjectId();
		when(mockService.findById(eq(id))).thenReturn(null);
		
		controller.delete(id, mockRedirect);
	}
	
	@Test
	public void deleteActionShouldDeleteRailways() {
		Railway value = new Railway();
		ObjectId id = new ObjectId();
		when(mockService.findById(eq(id))).thenReturn(value);
		
		RedirectAttributes redirectAtts = new RedirectAttributesModelMap();
		
		String viewName = controller.delete(id, redirectAtts);
		
		verify(mockService, times(1)).remove(eq(value));
		assertEquals("redirect:/admin/railways", viewName);
		assertEquals(1, redirectAtts.getFlashAttributes().size());
		assertEquals("Railway deleted", redirectAtts.getFlashAttributes().get("message"));
	}
}
