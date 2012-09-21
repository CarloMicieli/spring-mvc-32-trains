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
package com.trenako.web.controllers.form;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import com.trenako.web.images.ImageRequest;
import com.trenako.web.images.UploadRequest;

/**
 *
 * @author Carlo Micieli
 *
 */
public class UploadFormTests {

	private final static MultipartFile file = Mockito.mock(MultipartFile.class);
	private MultipartFile file() {
		return file;
	}

	@Test 
	public void shouldCheckWhetherTwoRailwayFormsAreEquals() {
		UploadForm x = new UploadForm("entity", "slug", file());
		UploadForm y = new UploadForm("entity", "slug", file());
		assertTrue("Upload forms are different", x.equals(x));
		assertTrue("Upload forms are different", x.equals(y));
	}

	@Test 
	public void shouldCheckWhetherTwoRailwayFormsAreDifferent() {
		UploadForm x = new UploadForm("entity", "slug", file());
		UploadForm y = new UploadForm("entity", "slug1", file());
		assertFalse("Upload forms are equals", x.equals(y));
		
		UploadForm z = new UploadForm("entity1", "slug", file());
		assertFalse("Upload forms are equals", x.equals(z));
	}
	
	@Test
	public void shouldCheckIfUploadFormsAreEmpty() {
		when(file().isEmpty()).thenReturn(true);
		
		UploadForm x = new UploadForm("entity", "slug", null);
		assertTrue("Upload is not empty", x.isEmpty());
		UploadForm y = new UploadForm("entity", "slug", file());
		assertTrue("Upload is not empty", y.isEmpty());
	}
	
	@Test
	public void shouldCheckIfUploadFormsAreNotEmpty() {
		when(file().isEmpty()).thenReturn(false);
		
		UploadForm x = new UploadForm("entity", "slug", file());
		assertFalse("Upload is empty", x.isEmpty());
	}
	
	@Test
	public void shouldBuildNewUploadRequestUsingFormValues() {
		UploadForm form = new UploadForm("entity", "slug", file());
		
		UploadRequest uploadRequest = form.buildUploadRequest();
		
		assertEquals("entity", uploadRequest.getEntity());
		assertEquals("slug", uploadRequest.getSlug());
		assertEquals(file(), uploadRequest.getFile());
	}
	
	@Test
	public void shouldBuildNewImageRequestUsingFormValues() {
		UploadForm form = new UploadForm("entity", "slug", file());
		
		ImageRequest imgRequest = form.buildImageRequest();
		
		assertEquals("entity", imgRequest.getEntityName());
		assertEquals("slug", imgRequest.getSlug());
	}
}