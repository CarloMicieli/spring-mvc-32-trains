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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.trenako.entities.Railway;
import com.trenako.services.RailwaysService;
import com.trenako.web.images.ImageRequest;
import com.trenako.web.images.MultipartFileValidator;
import com.trenako.web.images.UploadRequest;
import com.trenako.web.images.WebImageService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminRailwaysControllerTests {
	@Mock Pageable mockPaging;
	@Mock WebImageService imgService;
	@Mock BindingResult mockResult;
	@Mock RedirectAttributes mockRedirect;
	@Mock RailwaysService mockService;
	
	private MultipartFileValidator validator = new MultipartFileValidator();
	private AdminRailwaysController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new AdminRailwaysController(mockService, imgService);
		controller.setMultipartFileValidator(validator);
	}
	
	@Test
	public void shouldListAllRailwaysPaginated() {
		ModelAndView mav = controller.list(mockPaging);
	
		verify(mockService, times(1)).findAll(eq(mockPaging));
		assertViewName(mav, "railway/list");
		assertModelAttributeAvailable(mav, "railways");
	}
	
	@Test
	public void shouldShowRailways() {
		String slug = "railway-slug";
		Railway value = new Railway();
		when(mockService.findBySlug(eq(slug))).thenReturn(value);
		
		ModelAndView mav = controller.show(slug);
		
		verify(mockService, times(1)).findBySlug(eq(slug));
		assertViewName(mav, "railway/show");
		assertModelAttributeAvailable(mav, "railway");
	}

	@Test
	public void shouldRenderRailwayCreationForms() {
		ModelAndView mav = controller.newForm();
		
		assertViewName(mav, "railway/new");
		assertModelAttributeAvailable(mav, "railway");
	}
	
	@Test
	public void shouldCreateNewRailways() throws IOException {
		Railway railway = new Railway.Builder("FS").build();
		MultipartFile file = buildFile(MediaType.IMAGE_JPEG);
		UploadRequest req = UploadRequest.create(railway, file);
		when(mockResult.hasErrors()).thenReturn(false);
		
		String viewName = controller.create(railway, mockResult, file, mockRedirect);
		
		assertEquals("redirect:/admin/railways", viewName);
		verify(mockService, times(1)).save(eq(railway));
		verify(imgService, times(1)).saveImageWithThumb(eq(req), eq(50));
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_CREATED_MSG));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringRailwaysCreation() throws IOException {
		Railway railway = new Railway();
		when(mockResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAtts = new RedirectAttributesModelMap();
		MultipartFile file = buildFile(MediaType.IMAGE_JPEG);
		
		String viewName = controller.create(railway, mockResult, file, redirectAtts);
		
		verify(mockService, times(0)).save(eq(railway));
		assertEquals("railway/new", viewName);
		assertEquals(true, redirectAtts.containsAttribute("railway"));
	}
	
	@Test
	public void shouldRenderRailwaysEditingForm() {
		String slug = "railway-slug";
		Railway value = new Railway();
		when(mockService.findBySlug(eq(slug))).thenReturn(value);
		
		ModelAndView mav = controller.editForm(slug);
		
		assertViewName(mav, "railway/edit");
		assertModelAttributeAvailable(mav, "railway");
		verify(mockService, times(1)).findBySlug(eq(slug));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsSavingRailways() {
		Railway railway = new Railway();
		when(mockResult.hasErrors()).thenReturn(true);
		
		String viewName = controller.save(railway, mockResult, mockRedirect);
		
		assertEquals("railway/edit", viewName);		
		verify(mockRedirect, times(1)).addAttribute(eq("railway"), eq(railway));
		verify(mockService, times(0)).save(eq(railway));
	}
	
	@Test
	public void shouldSaveRailwayChanges() throws IOException {
		Railway railway = new Railway();
		when(mockResult.hasErrors()).thenReturn(false);
				
		String viewName = controller.save(railway, mockResult, mockRedirect);
		
		verify(mockService, times(1)).save(eq(railway));
		assertEquals("redirect:/admin/railways", viewName);
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_SAVED_MSG));
	}
			
	@Test
	public void shouldDeleteRailways() {
		Railway railway = new Railway();
		
		String viewName = controller.delete(railway, mockRedirect);
		
		verify(mockService, times(1)).remove(eq(railway));
		assertEquals("redirect:/admin/railways", viewName);
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_DELETED_MSG));
	}
	
	@Test
	public void shouldUploadNewRailwayLogos() throws IOException {
		Railway railway = new Railway.Builder("FS").build();
		MultipartFile file = buildFile(MediaType.IMAGE_JPEG);
		UploadRequest req = UploadRequest.create(railway, file);
		
		String viewName = controller.uploadImage(railway, mockResult, file, mockRedirect);
		
		assertEquals("redirect:/admin/railways/{slug}", viewName);
		verify(imgService, times(1)).saveImageWithThumb(eq(req), eq(50));
		verify(mockRedirect, times(1)).addAttribute(eq("slug"), eq(railway.getSlug()));
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_LOGO_UPLOADED_MSG));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringBrandLogoUploads() throws IOException {
		Railway railway = new Railway.Builder("FS").build();
		MultipartFile file = buildFile(MediaType.IMAGE_JPEG);
		when(mockResult.hasErrors()).thenReturn(true);
		
		String viewName = controller.uploadImage(railway, mockResult, file, mockRedirect);
		
		assertEquals("redirect:/admin/railways/{slug}", viewName);
		verify(mockRedirect, times(1)).addAttribute(eq("slug"), eq(railway.getSlug()));
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_INVALID_UPLOAD_MSG));
	}
	
	@Test
	public void shouldReturnValidatioErrorWhenProvidedBrandLogoFileIsEmpty() throws IOException {
		Railway railway = new Railway.Builder("FS").build();
		MultipartFile file = mock(MultipartFile.class);
		when(file.isEmpty()).thenReturn(true);
		
		String viewName = controller.uploadImage(railway, mockResult, file, mockRedirect);
		
		assertEquals("redirect:/admin/railways/{slug}", viewName);
		verify(mockRedirect, times(1)).addAttribute(eq("slug"), eq(railway.getSlug()));
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_INVALID_UPLOAD_MSG));
	}
	
	@Test
	public void shouldDeleteBrandLogos() {
		Railway railway = new Railway.Builder("FS").build();
		ImageRequest req = ImageRequest.create(railway);
		
		String viewName = controller.deleteImage(railway, mockRedirect);
		
		assertEquals("redirect:/admin/railways/{slug}", viewName);
		verify(imgService, times(1)).deleteImage(eq(req));
		verify(mockRedirect, times(1)).addAttribute(eq("slug"), eq(railway.getSlug()));
		verify(mockRedirect, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_LOGO_DELETED_MSG));
	}
	
	private MultipartFile buildFile(MediaType mediaType) {
		byte[] content = "file content".getBytes();
		return new MockMultipartFile("image.jpg", "image.jpg", mediaType.toString(), content);
	}
}
