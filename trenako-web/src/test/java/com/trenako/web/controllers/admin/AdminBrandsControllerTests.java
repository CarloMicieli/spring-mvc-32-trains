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

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Brand;
import com.trenako.services.BrandsService;
import com.trenako.services.FormValuesService;
import com.trenako.web.controllers.form.BrandForm;
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
	
	@Mock BrandForm form;
	@Mock RedirectAttributes redirectAtts;
	@Mock BindingResult bindingResults;

	@Mock BrandsService service;
	@Mock WebImageService imgService;
	@Mock FormValuesService formService;
	
	private ModelMap model = new ModelMap();
	
	MultipartFileValidator validator = new MultipartFileValidator();
	
	private AdminBrandsController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new AdminBrandsController(service, formService, imgService);
		controller.setMultipartFileValidator(validator);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldShowTheBrandsList() {
		
		Pageable pageable = new PageRequest(0, 10);
		Page<Brand> results = new PageImpl<Brand>(Arrays.asList(acme(), roco()));
		when(service.findAll(eq(pageable))).thenReturn(results);
		
		String viewName = controller.list(pageable, model);
		
		verify(service, times(1)).findAll(eq(pageable));
		assertEquals(viewName, "brand/list");
		assertTrue(model.containsAttribute("brands"));
		assertEquals(results, (Page<Brand>) model.get("brands"));
	}
		
	@Test
	public void shouldShowBrands() {
		String slug = "acme";
		when(service.findBySlug(eq(slug))).thenReturn(acme());

		String viewName = controller.show(slug, model, redirectAtts);
		
		verify(service, times(1)).findBySlug(eq(slug));
		assertEquals(viewName, "brand/show");
		assertTrue(model.containsAttribute("brand"));
		assertEquals(acme(), (Brand) model.get("brand"));
	}
	
	@Test
	public void shouldRedirectToBrandsListWhenBrandToShowWasNoFound() {
		String slug = "not-found";
		when(service.findBySlug(eq(slug))).thenReturn(null);

		String viewName = controller.show(slug, model, redirectAtts);

		verify(service, times(1)).findBySlug(eq(slug));
		assertEquals(viewName, "redirect:/admin/brands");
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_NOT_FOUND_MSG));
	}
	
	@Test
	public void shouldRenderNewBrandForm() {
		BrandForm newForm = BrandForm.newForm(new Brand(), formService);
		
		String viewName = controller.newForm(model);
		
		assertEquals("brand/new", viewName);
		assertTrue(model.containsAttribute("brandForm"));
		assertEquals(newForm, (BrandForm) model.get("brandForm"));
	}
	
	@Test
	public void shouldCreateBrands() throws IOException {
		when(postedForm().getBrand()).thenReturn(acme());
		when(postedForm().getFile()).thenReturn(jpegFile());
		when(bindingResults.hasErrors()).thenReturn(false);
		
		String viewName = controller.create(postedForm(), bindingResults, model, redirectAtts);

		assertEquals("redirect:/admin/brands", viewName);
		verify(service, times(1)).save(eq(acme()));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_CREATED_MSG));
		
		ArgumentCaptor<UploadRequest> arg = ArgumentCaptor.forClass(UploadRequest.class);
		verify(imgService, times(1)).saveImageWithThumb(arg.capture(), eq(50));
		assertEquals(jpegFile(), arg.getValue().getFile());
		assertEquals("acme", arg.getValue().getSlug());
		assertEquals("brand", arg.getValue().getEntity());
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringBrandCreation() throws IOException {
		when(postedForm().getBrand()).thenReturn(acme());
		when(bindingResults.hasErrors()).thenReturn(true);
		
		String viewName = controller.create(postedForm(), bindingResults, model, redirectAtts);
		
		verify(service, times(0)).save(eq(acme()));
		assertEquals("brand/new", viewName);
		assertBrandForm(model, "brandForm");
	}

	@Test
	public void shouldRedirectAfterDuplicatedKeyErrorsDuringBrandCreation() throws IOException {
		when(postedForm().getBrand()).thenReturn(acme());
		when(bindingResults.hasErrors()).thenReturn(false);
		doThrow(new DuplicateKeyException("duplicated key"))
			.when(service).save(eq(acme()));

		String viewName = controller.create(postedForm(), bindingResults, model, redirectAtts);
		
		assertEquals("brand/new", viewName);
		assertBrandForm(model, "brandForm");
		verify(bindingResults, times(1)).rejectValue("brand.name", "brand.name.already.used");
	}
	
	@Test
	public void shouldRedirectAfterDatabaseErrorsDuringBrandCreation() throws IOException {
		when(postedForm().getBrand()).thenReturn(acme());
		when(bindingResults.hasErrors()).thenReturn(false);
		doThrow(new FakeDataAccessException())
			.when(service).save(eq(acme()));

		String viewName = controller.create(postedForm(), bindingResults, model, redirectAtts);
		
		assertEquals("brand/new", viewName);
		assertEquals(AdminBrandsController.BRAND_DB_ERROR_MSG, model.get("message"));
		assertBrandForm(model, "brandForm");
	}
	
	@Test
	public void shouldRenderEditBrandForms() {
		String slug = "acme";
		when(service.findBySlug(eq(slug))).thenReturn(acme());
		
		String viewName = controller.editForm(slug, model, redirectAtts);
		
		assertEquals(viewName, "brand/edit");
		assertTrue("Edit form not found", model.containsAttribute("brandForm"));

		BrandForm editForm = (BrandForm) model.get("brandForm");
		assertNotNull(editForm);
		assertEquals(acme(), editForm.getBrand());
	}
	
	@Test
	public void shouldRedirectToBrandsListWhenBrandToEditWasNoFound() {
		String slug = "not-found";
		when(service.findBySlug(eq(slug))).thenReturn(null);

		String viewName = controller.editForm(slug, model, redirectAtts);

		assertEquals(viewName, "redirect:/admin/brands");
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_NOT_FOUND_MSG));
	}
	
	@Test
	public void shouldSaveBrandChanges() {
		when(postedForm().getBrand()).thenReturn(acme());
		when(bindingResults.hasErrors()).thenReturn(false);

		String viewName = controller.save(postedForm(), bindingResults, model, redirectAtts);

		assertEquals("redirect:/admin/brands", viewName);
		verify(service, times(1)).save(eq(acme()));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_SAVED_MSG));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringChangeSaving() {
		when(postedForm().getBrand()).thenReturn(acme());
		when(bindingResults.hasErrors()).thenReturn(true);
		
		String viewName = controller.save(postedForm(), bindingResults, model, redirectAtts);
		
		assertEquals("brand/edit", viewName);
		
		BrandForm editForm = (BrandForm) model.get("brandForm");
		assertNotNull(editForm);
		assertEquals(acme(), editForm.getBrand());
	}
	
	@Test
	public void shouldRedirectAfterDatabaseErrorsDuringChangeSaving() {
		when(postedForm().getBrand()).thenReturn(acme());
		when(bindingResults.hasErrors()).thenReturn(false);
		doThrow(new FakeDataAccessException())
			.when(service).save(eq(acme()));
		
		String viewName = controller.save(postedForm(), bindingResults, model, redirectAtts);
		
		assertEquals("brand/edit", viewName);
		BrandForm editForm = (BrandForm) model.get("brandForm");
		assertNotNull(editForm);
		assertEquals(acme(), editForm.getBrand());
		assertEquals(AdminBrandsController.BRAND_DB_ERROR_MSG, model.get("message"));
	}
	
	@Test
	public void shouldDeleteBrands() {
		Brand value = new Brand();

		String viewName = controller.delete(value, redirectAtts);
		
		assertEquals("redirect:/admin/brands", viewName);
		verify(service, times(1)).remove(eq(value));		
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_DELETED_MSG));
	}
	
	@Test
	public void shouldUploadNewBrandLogos() throws IOException {
		Brand brand = new Brand.Builder("ACME").build();
		MultipartFile file = buildFile(MediaType.IMAGE_JPEG);
		UploadRequest req = UploadRequest.create(brand, file);
		
		String viewName = controller.uploadImage(brand, bindingResults, file, redirectAtts);
		
		assertEquals("redirect:/admin/brands/{slug}", viewName);
		verify(imgService, times(1)).saveImageWithThumb(eq(req), eq(50));
		verify(redirectAtts, times(1)).addAttribute(eq("slug"), eq("acme"));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_LOGO_UPLOADED_MSG));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringBrandLogoUploads() throws IOException {
		Brand brand = new Brand.Builder("ACME")
			.id(new ObjectId())
			.build();
		MultipartFile file = buildFile(MediaType.IMAGE_JPEG);
		when(bindingResults.hasErrors()).thenReturn(true);
		
		String viewName = controller.uploadImage(brand, bindingResults, file, redirectAtts);
		
		assertEquals("redirect:/admin/brands/{slug}", viewName);
		verify(redirectAtts, times(1)).addAttribute(eq("slug"), eq(brand.getSlug()));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_INVALID_UPLOAD_MSG));
	}
	
	@Test
	public void shouldReturnValidatioErrorWhenProvidedBrandLogoFileIsEmpty() throws IOException {
		Brand brand = new Brand.Builder("ACME")
			.id(new ObjectId())
			.build();
		MultipartFile file = mock(MultipartFile.class);
		when(file.isEmpty()).thenReturn(true);
		
		String viewName = controller.uploadImage(brand, bindingResults, file, redirectAtts);
		
		assertEquals("redirect:/admin/brands/{slug}", viewName);
		verify(redirectAtts, times(1)).addAttribute(eq("slug"), eq(brand.getSlug()));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_INVALID_UPLOAD_MSG));
	}
	
	@Test
	public void shouldDeleteBrandLogos() {
		Brand brand = new Brand.Builder("ACME").build();
		ImageRequest req = ImageRequest.create(brand);
		
		String viewName = controller.deleteImage(brand, redirectAtts);
		
		assertEquals("redirect:/admin/brands/{slug}", viewName);
		verify(imgService, times(1)).deleteImage(eq(req));
		verify(redirectAtts, times(1)).addAttribute(eq("slug"), eq("acme"));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminBrandsController.BRAND_LOGO_DELETED_MSG));
	}
	
	private BrandForm postedForm() {
		return form;
	}
	
	private static void assertBrandForm(ModelMap model, String key) {
		assertNotNull("Brand form is null", model.containsKey(key));
		BrandForm form = (BrandForm) model.get(key);
		assertEquals(acme(), form.getBrand());
	}
	
	private static final MultipartFile JPEG_FILE = buildFile(MediaType.IMAGE_JPEG); 
	private MultipartFile jpegFile() {
		return JPEG_FILE;
	}
	
	private static MultipartFile buildFile(MediaType mediaType) {
		byte[] content = "file content".getBytes();
		return new MockMultipartFile("image.jpg", "image.jpg", mediaType.toString(), content);
	}

	@SuppressWarnings("serial")
	private static class FakeDataAccessException extends DataAccessException {
		public FakeDataAccessException() {
			super("Fake database error");
		}
	}
}
