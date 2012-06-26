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
import org.springframework.mock.web.MockMultipartFile;
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
	
		ModelAndView mav = controller.create(railway, mockFile, mockResult, mockRedirect);
		
		verify(mockService, times(0)).save(eq(railway));
		assertViewName(mav, "railway/new");
		assertModelAttributeAvailable(mav, "railway");
	}
	
	@Test
	public void createActionShouldCreateNewRailways() throws IOException {
		Railway railway = new Railway();
		when(mockResult.hasErrors()).thenReturn(false);
		RedirectAttributes redirectAtts = new RedirectAttributesModelMap();
		
		ModelAndView mav = controller.create(railway, mockFile, mockResult, redirectAtts);
		
		verify(mockService, times(1)).save(eq(railway));
		assertViewName(mav, "redirect:/admin/railways");
		assertEquals(1, redirectAtts.getFlashAttributes().size());
		assertEquals("Railway created", redirectAtts.getFlashAttributes().get("message"));
	}
	
	@Test
	public void createActionShouldCreateNewRailwaysWithALogo() throws IOException {
		Railway railway = new Railway();
		when(mockResult.hasErrors()).thenReturn(false);
		
		Image value = new Image("image/jpeg", new byte[]{});
		MultipartFile file = new MockMultipartFile("file.jpg", "file.jpg", "image/jpeg", new byte[]{});
		when(mockImgUtil.createImage(eq(file))).thenReturn(value);
		
		controller.create(railway, file, mockResult, mockRedirect);
		
		verify(mockImgUtil, times(1)).createImage(eq(file));
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
		
		ModelAndView mav = controller.save(railway, mockResult, mockRedirect);
		
		verify(mockService, times(0)).save(eq(railway));
		assertViewName(mav, "railway/edit");
		assertModelAttributeAvailable(mav, "railway");
	}
	
	@Test
	public void saveActionShouldCreateNewRailways() throws IOException {
		RedirectAttributes redirectAtts = new RedirectAttributesModelMap();
		Railway railway = new Railway();
		when(mockResult.hasErrors()).thenReturn(false);
				
		ModelAndView mav = controller.save(railway, mockResult, redirectAtts);
		
		verify(mockService, times(1)).save(eq(railway));
		assertViewName(mav, "redirect:/admin/railways");
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
		
		ModelAndView mav = controller.delete(id, redirectAtts);
		
		verify(mockService, times(1)).remove(eq(value));
		assertViewName(mav, "redirect:/admin/railways");
		assertEquals(1, redirectAtts.getFlashAttributes().size());
		assertEquals("Railway deleted", redirectAtts.getFlashAttributes().get("message"));
	}
}
