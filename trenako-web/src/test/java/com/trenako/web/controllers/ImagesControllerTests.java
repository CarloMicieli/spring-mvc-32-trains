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
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new ImagesController(mockService);
	}
	
	@Test
	public void shouldRenderTheBrandLogoImages() throws IOException {
		ObjectId id = new ObjectId();
		ResponseEntity<byte[]> value = new ResponseEntity<byte[]>(HttpStatus.CREATED);
		when(mockService.renderImageFor(eq(id))).thenReturn(value);

		ResponseEntity<byte[]> resp = controller.renderBrandLogo(id);

		verify(mockService, times(1)).renderImageFor(eq(id));
		assertNotNull(resp);
		assertEquals(HttpStatus.CREATED, resp.getStatusCode());
	}
}
