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
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.*;

import java.io.IOException;

import org.bson.types.ObjectId;
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

import com.trenako.entities.Brand;
import com.trenako.services.BrandsService;
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
public class AdminBrandsControllerTests {
	@Mock RedirectAttributes mockRedirectAtts;
	@Mock WebImageService imgService;
	@Mock BindingResult mockResult;
	@Mock BrandsService service;
	MultipartFileValidator validator = new MultipartFileValidator();
	
	private AdminBrandsController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new AdminBrandsController(service, imgService);
		controller.setMultipartFileValidator(validator);
	}
	
	@Test
	public void shouldListBrands() {
		Pageable paging = mock(Pageable.class);
		
		ModelAndView mav = controller.list(paging);
		
		verify(service, times(1)).findAll(eq(paging));
		assertViewName(mav, "brand/list");
		assertModelAttributeAvailable(mav, "brands");
	}
	
	@Test
	public void shouldShowBrands() {
		String slug = "brand-slug";
		ModelAndView mav = controller.show(slug);
		
		verify(service, times(1)).findBySlug(eq(slug));
		assertViewName(mav, "brand/show");
		assertModelAttributeAvailable(mav, "brand");
	}
	
	@Test
	public void shouldRenderNewBrandForm() {
		ModelAndView mav = controller.newForm();
		
		assertViewName(mav, "brand/new");
		assertModelAttributeAvailable(mav, "brand");
	}
	
	@Test
	public void shouldCreateBrands() throws IOException {
		Brand brand = new Brand.Builder("ACME").build();
		MultipartFile file = buildFile(MediaType.IMAGE_JPEG);
		UploadRequest req = UploadRequest.create(brand, file);
		when(mockResult.hasErrors()).thenReturn(false);
		
		String viewName = controller.create(brand, mockResult, file, mockRedirectAtts);

		assertEquals("redirect:/admin/brands", viewName);
		verify(service, times(1)).save(eq(brand));
		verify(imgService, times(1)).saveImageWithThumb(eq(req), eq(50));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_CREATED_MSG));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringBrandCreation() throws IOException {
		Brand brand = new Brand.Builder("ACME").build();
		MultipartFile file = buildFile(MediaType.IMAGE_JPEG);
		when(mockResult.hasErrors()).thenReturn(true);
		
		String viewName = controller.create(brand, mockResult, file, mockRedirectAtts);
		
		assertEquals("brand/new", viewName);
		verify(service, times(0)).save(eq(brand));
		verify(mockRedirectAtts, times(1)).addAttribute(eq(brand));
	}
	
	@Test
	public void shouldRenderEditBrandForms() {
		String slug = "brand-slug";
		when(service.findBySlug(eq(slug))).thenReturn(new Brand());
		
		ModelAndView mav = controller.editForm(slug);
		
		assertViewName(mav, "brand/edit");
		assertModelAttributeAvailable(mav, "brand");
	}
	
	@Test
	public void shouldSaveBrandChanges() {
		Brand brand = new Brand.Builder("ACME").build();
		when(mockResult.hasErrors()).thenReturn(false);
		
		String viewName = controller.save(brand, mockResult, mockRedirectAtts);
		assertEquals("redirect:/admin/brands", viewName);
		verify(service, times(1)).save(eq(brand));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_SAVED_MSG));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringChangeSaving() {
		Brand brand = new Brand.Builder("ACME").build();
		when(mockResult.hasErrors()).thenReturn(true);
		
		String viewName = controller.save(brand, mockResult, mockRedirectAtts);
		
		assertEquals("brand/edit", viewName);
		verify(service, times(0)).save(eq(brand));
		verify(mockRedirectAtts, times(1)).addAttribute(eq(brand));
	}
	
	@Test
	public void shouldDeleteBrands() {
		Brand value = new Brand();

		String viewName = controller.delete(value, mockRedirectAtts);
		
		assertEquals("redirect:/admin/brands", viewName);
		verify(service, times(1)).remove(eq(value));		
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_DELETED_MSG));
	}
	
	@Test
	public void shouldUploadNewBrandLogos() throws IOException {
		Brand brand = new Brand.Builder("ACME").build();
		MultipartFile file = buildFile(MediaType.IMAGE_JPEG);
		UploadRequest req = UploadRequest.create(brand, file);
		
		String viewName = controller.uploadImage(brand, mockResult, file, mockRedirectAtts);
		
		assertEquals("redirect:/admin/brands/{slug}", viewName);
		verify(imgService, times(1)).saveImageWithThumb(eq(req), eq(50));
		verify(mockRedirectAtts, times(1)).addAttribute(eq("slug"), eq("acme"));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_LOGO_UPLOADED_MSG));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringBrandLogoUploads() throws IOException {
		Brand brand = new Brand.Builder("ACME")
			.id(new ObjectId())
			.build();
		MultipartFile file = buildFile(MediaType.IMAGE_JPEG);
		when(mockResult.hasErrors()).thenReturn(true);
		
		String viewName = controller.uploadImage(brand, mockResult, file, mockRedirectAtts);
		
		assertEquals("redirect:/admin/brands/{slug}", viewName);
		verify(mockRedirectAtts, times(1)).addAttribute(eq("slug"), eq(brand.getSlug()));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_INVALID_UPLOAD_MSG));
	}
	
	@Test
	public void shouldReturnValidatioErrorWhenProvidedBrandLogoFileIsEmpty() throws IOException {
		Brand brand = new Brand.Builder("ACME")
			.id(new ObjectId())
			.build();
		MultipartFile file = mock(MultipartFile.class);
		when(file.isEmpty()).thenReturn(true);
		
		String viewName = controller.uploadImage(brand, mockResult, file, mockRedirectAtts);
		
		assertEquals("redirect:/admin/brands/{slug}", viewName);
		verify(mockRedirectAtts, times(1)).addAttribute(eq("slug"), eq(brand.getSlug()));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_INVALID_UPLOAD_MSG));
	}
	
	@Test
	public void shouldDeleteBrandLogos() {
		Brand brand = new Brand.Builder("ACME").build();
		ImageRequest req = ImageRequest.create(brand);
		
		String viewName = controller.deleteImage(brand, mockRedirectAtts);
		
		assertEquals("redirect:/admin/brands/{slug}", viewName);
		verify(imgService, times(1)).deleteImage(eq(req));
		verify(mockRedirectAtts, times(1)).addAttribute(eq("slug"), eq("acme"));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_LOGO_DELETED_MSG));
	}
	
	private MultipartFile buildFile(MediaType mediaType) {
		byte[] content = "file content".getBytes();
		return new MockMultipartFile("image.jpg", "image.jpg", mediaType.toString(), content);
	}
}
