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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trenako.web.images.WebImageService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ImagesControllerTests {
	
	@Mock WebImageService mockService;
	ImagesController controller;
	
	private final static ResponseEntity<byte[]> resp = new ResponseEntity<byte[]>(HttpStatus.CREATED);
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new ImagesController(mockService);
	}
	
	@Test
	public void shouldRenderImages() {
		String imgSlug = "brand_ls-models";
		when(mockService.renderImage(eq(imgSlug))).thenReturn(resp);
		
		ResponseEntity<byte[]> value = controller.renderImage(imgSlug);
		
		verify(mockService, times(1)).renderImage(eq(imgSlug));
		assertNotNull(value);
	}
	
	@Test
	public void shouldRenderThumbs() {
		String thumbSlug = "th_brand_ls-models";
		when(mockService.renderImage(eq(thumbSlug))).thenReturn(resp);
		
		ResponseEntity<byte[]> value = controller.renderImage(thumbSlug);
		
		verify(mockService, times(1)).renderImage(eq(thumbSlug));
		assertNotNull(value);
	}
}
