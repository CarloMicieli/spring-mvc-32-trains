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
package com.trenako.web.controllers;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.trenako.entities.Image;
import com.trenako.services.ImagesService;
import com.trenako.web.images.ImageProcessingAdapter;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ImagesControllerTests {
	
	@Mock ImagesService mockService;
	@Mock ImageProcessingAdapter mockImgUtils;
	ImagesController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new ImagesController(mockService, mockImgUtils);
	}
	
	private ResponseEntity<byte[]> buildResponse(MediaType mediaType) {
		final byte[] bytes = "file content".getBytes();
		final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(mediaType);
	    final ResponseEntity<byte[]> resp = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
	    return resp;
	}

	@Test
	public void shouldRenderTheBrandLogoImages() throws IOException {
		ObjectId id = new ObjectId();
		byte[] bytes = "file content".getBytes();
		Image img = new Image(MediaType.IMAGE_JPEG_VALUE, bytes);
		
		ResponseEntity<byte[]> value = buildResponse(MediaType.IMAGE_JPEG);
	
		when(mockService.getBrandImage(eq(id))).thenReturn(img);
		when(mockImgUtils.renderImage(eq(img))).thenReturn(value);
		
		ResponseEntity<byte[]> resp = controller.renderBrandLogo(id);
		assertNotNull(resp);
		assertEquals(HttpStatus.CREATED, resp.getStatusCode());
		assertTrue(resp.hasBody());
		assertEquals(MediaType.IMAGE_JPEG, resp.getHeaders().getContentType());
	}
	
	@Test
	public void shouldReturnNotFoundIfBrandHasNoImage() throws IOException {
		ObjectId id = new ObjectId();
		ResponseEntity<byte[]> resp = controller.renderBrandLogo(id);
		assertNotNull(resp);
		assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
		assertNull("Response body is not null", resp.getBody());
	}
	
}
