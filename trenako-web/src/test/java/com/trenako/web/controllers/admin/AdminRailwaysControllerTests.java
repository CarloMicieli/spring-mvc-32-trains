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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
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

import com.trenako.entities.Railway;
import com.trenako.services.RailwaysService;
import com.trenako.web.controllers.form.RailwayForm;
import com.trenako.web.controllers.form.UploadForm;
import com.trenako.web.images.ImageRequest;
import com.trenako.web.images.UploadRequest;
import com.trenako.web.images.WebImageService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminRailwaysControllerTests {
	private final static Page<Railway> RAILWAYS = 
		new PageImpl<Railway>(Arrays.asList(new Railway(), new Railway()));

	private @Mock WebImageService imgService;
	private @Mock BindingResult bindingResult;
	private @Mock RedirectAttributes redirectAtts;
	private @Mock RailwaysService service;
	private ModelMap model = new ModelMap();

	private AdminRailwaysController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new AdminRailwaysController(service, imgService);
	}
	
	@Test
	public void shouldShowTheRailwaysListWithPagination() {
		Pageable pageable = new PageRequest(0, 10);
		when(service.findAll(eq(pageable))).thenReturn(RAILWAYS);

		String viewName = controller.list(pageable, model);
	
		verify(service, times(1)).findAll(eq(pageable));
		assertEquals("railway/list", viewName);
		assertEquals(RAILWAYS, model.get("railways"));
	}
	
	@Test
	public void shouldShowRailways() {
		String slug = "railway-slug";
		Railway railway = new Railway();
		when(service.findBySlug(eq(slug))).thenReturn(railway);
		
		String viewName = controller.show(slug, model, redirectAtts);
		
		verify(service, times(1)).findBySlug(eq(slug));
		assertEquals("railway/show", viewName);
		assertEquals(railway, model.get("railway"));
	}

	@Test
	public void shouldRedirectToRailwaysListIfTheRailwayWasNotFound() {
		String slug = "not-found";
		when(service.findBySlug(eq(slug))).thenReturn(null);
		
		String viewName = controller.show(slug, model, redirectAtts);
		
		assertEquals("redirect:/admin/railways", viewName);
		verify(service, times(1)).findBySlug(eq(slug));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_NOT_FOUND_MSG));
	}

	@Test
	public void shouldRenderRailwayCreationForms() {
		RailwayForm form = RailwayForm.newForm(new Railway());
	
		String viewName = controller.newForm(model);

		assertEquals("railway/new", viewName);
		assertEquals(form, model.get("railwayForm"));
	}
	
	@Test
	public void shouldCreateNewRailways() throws IOException {
		RailwayForm postedForm = new RailwayForm(fs(), jpegFile());
		when(bindingResult.hasErrors()).thenReturn(false);
		
		String viewName = controller.create(postedForm, bindingResult, model, redirectAtts);
		
		assertEquals("redirect:/admin/railways", viewName);
		verify(service, times(1)).save(eq(fs()));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_CREATED_MSG));
		
		ArgumentCaptor<UploadRequest> arg = ArgumentCaptor.forClass(UploadRequest.class);
		verify(imgService, times(1)).saveImageWithThumb(arg.capture(), eq(50));
		UploadRequest req = (UploadRequest) arg.getValue();
		assertEquals("railway", req.getEntity());
		assertEquals("fs", req.getSlug());
		assertEquals(jpegFile(), req.getFile());
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringRailwaysCreation() throws IOException {
		Railway railway = new Railway.Builder("FS").build();
		when(bindingResult.hasErrors()).thenReturn(true);
		
		String viewName = controller.create(fsForm(), bindingResult, model, redirectAtts);
		
		verify(service, times(0)).save(eq(railway));
		assertEquals("railway/new", viewName);
		assertEquals(fsForm(), model.get("railwayForm"));
	}

	@Test
	public void shouldRedirectAfterDatabaseErrorsDuringRailwaysCreation() throws IOException {
		when(bindingResult.hasErrors()).thenReturn(false);
		doThrow(new FakeDataAccessException())
			.when(service)
			.save(eq(fs()));
		
		String viewName = controller.create(fsForm(), bindingResult, model, redirectAtts);
		
		verify(service, times(1)).save(eq(fs()));
		assertEquals("railway/new", viewName);
		assertEquals(fsForm(), model.get("railwayForm"));
		assertEquals(AdminRailwaysController.RAILWAY_DB_ERROR_MSG, model.get("message"));
	}
	
	@Test
	public void shouldRenderRailwaysEditingForm() {
		String slug = "fs";
		when(service.findBySlug(eq(slug))).thenReturn(fs());
		
		String viewName = controller.editForm(slug, model, redirectAtts);
		
		assertEquals("railway/edit", viewName);
		assertEquals(fsForm(), model.get("railwayForm"));
		verify(service, times(1)).findBySlug(eq(slug));
	}

	@Test
	public void shouldRedirectToRailwaysListWhenRailwayToEditWasNotFound() {
		String slug = "not-found";
		when(service.findBySlug(eq(slug))).thenReturn(null);
		
		String viewName = controller.editForm(slug, model, redirectAtts);
		
		assertEquals("redirect:/admin/railways", viewName);
		verify(service, times(1)).findBySlug(eq(slug));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_NOT_FOUND_MSG));
	}
	
	@Test
	public void shouldSaveRailwayChanges() throws IOException {
		when(bindingResult.hasErrors()).thenReturn(false);

		String viewName = controller.save(fsForm(), bindingResult, model, redirectAtts);

		verify(service, times(1)).save(eq(fs()));
		assertEquals("redirect:/admin/railways", viewName);
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_SAVED_MSG));
	}

	@Test
	public void shouldRedirectAfterValidationErrorsSavingRailways() {
		when(bindingResult.hasErrors()).thenReturn(true);
		
		String viewName = controller.save(fsForm(), bindingResult, model, redirectAtts);
		
		assertEquals("railway/edit", viewName);
		assertEquals(fsForm(), model.get("railwayForm"));
		verify(service, times(0)).save(eq(fs()));
	}
	
	@Test
	public void shouldRedirectAfterDatabaseErrorsSavingRailways() {
		when(bindingResult.hasErrors()).thenReturn(false);
		doThrow(new FakeDataAccessException())
			.when(service)
			.save(eq(fs()));
		
		String viewName = controller.save(fsForm(), bindingResult, model, redirectAtts);
		
		assertEquals("railway/edit", viewName);
		assertEquals(fsForm(), model.get("railwayForm"));
		assertEquals(AdminRailwaysController.RAILWAY_DB_ERROR_MSG, model.get("message"));
		verify(service, times(1)).save(eq(fs()));
	}
	
	@Test
	public void shouldDeleteRailways() {
		Railway railway = new Railway();
		
		String viewName = controller.delete(railway, redirectAtts);
		
		verify(service, times(1)).remove(eq(railway));
		assertEquals("redirect:/admin/railways", viewName);
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_DELETED_MSG));
	}
	
	@Test
	public void shouldUploadRailwayLogos() throws IOException {
		String entity = "railway";
		String slug = "railway-slug";
		UploadForm uploadForm = new UploadForm(entity, slug, jpegFile());
		
		String viewName = controller.uploadImage(uploadForm, bindingResult, redirectAtts);
		
		assertEquals("redirect:/admin/railways/{slug}", viewName);
		verify(redirectAtts, times(1)).addAttribute(eq("slug"), eq(slug));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_LOGO_UPLOADED_MSG));
		
		ArgumentCaptor<UploadRequest> arg = ArgumentCaptor.forClass(UploadRequest.class);
		verify(imgService, times(1)).saveImageWithThumb(arg.capture(), eq(50));
		UploadRequest req = (UploadRequest) arg.getValue();
		assertEquals(entity, req.getEntity());
		assertEquals(slug, req.getSlug());
		assertEquals(jpegFile(), req.getFile());
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringRailwayLogoUploads() throws IOException {
		when(bindingResult.hasErrors()).thenReturn(true);
				
		String viewName = controller.uploadImage(uploadForm(), bindingResult, redirectAtts);
		
		assertEquals("redirect:/admin/railways/{slug}", viewName);
		verify(redirectAtts, times(1)).addAttribute(eq("slug"), eq("railway-slug"));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_INVALID_UPLOAD_MSG));
	}
	
	@Test
	public void shouldRedirectToRailwayViewWhenThePostedFileIsEmpty() throws IOException {
		when(bindingResult.hasErrors()).thenReturn(false);
		
		String viewName = controller.uploadImage(emptyUpload(), bindingResult, redirectAtts);
		
		assertEquals("redirect:/admin/railways/{slug}", viewName);
		verify(redirectAtts, times(1)).addAttribute(eq("slug"), eq("railway-slug"));
	}
	
	@Test
	public void shouldDeleteRailwayLogos() {
		String entity = "railway";
		String slug = "railway-slug";
		UploadForm uploadForm = new UploadForm(entity, slug, null);
		
		String viewName = controller.deleteImage(uploadForm, redirectAtts);
		
		assertEquals("redirect:/admin/railways/{slug}", viewName);
		verify(redirectAtts, times(1)).addAttribute(eq("slug"), eq("railway-slug"));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(AdminRailwaysController.RAILWAY_LOGO_DELETED_MSG));
		
		ArgumentCaptor<ImageRequest> arg = ArgumentCaptor.forClass(ImageRequest.class);
		verify(imgService, times(1)).deleteImage(arg.capture());
		
		ImageRequest req = (ImageRequest) arg.getValue();	
		assertEquals(entity, req.getEntityName());
		assertEquals(slug, req.getSlug());
	}

	private final static MultipartFile file = 
			new MockMultipartFile("file", "image.jpg", MediaType.IMAGE_JPEG.toString(), "content".getBytes());
	
	private MultipartFile jpegFile() {
		return file; 
	}

	private RailwayForm fsForm() {
		return new RailwayForm(fs(), jpegFile());
	}

	private UploadForm uploadForm() {
		String entity = "railway";
		String slug = "railway-slug";
		UploadForm uploadForm = new UploadForm(entity, slug, jpegFile());
		return uploadForm;
	}
	
	private UploadForm emptyUpload() {
		String entity = "railway";
		String slug = "railway-slug";
		UploadForm uploadForm = new UploadForm(entity, slug, null);
		return uploadForm;
	}

	@SuppressWarnings("serial")
	private static class FakeDataAccessException extends DataAccessException {
		FakeDataAccessException() {
			super("Db error");
		}
	}
}