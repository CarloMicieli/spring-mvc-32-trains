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
package com.trenako.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Image;
import com.trenako.repositories.ImagesRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ImagesServiceTests {

	@Mock ImagesRepository mockRepo;
	ImagesService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new ImagesServiceImpl(mockRepo);	
	}
	
	@Test
	public void shouldReturnNullIfBrandImageIsNotFound() {
		ObjectId id = new ObjectId();
		Image img = service.getBrandImage(id);
		assertNull(img);
	}
	
	@Test
	public void shouldReturnAnImageForBrand() {
		ObjectId id = new ObjectId();
		Image value = new Image("image/jpg", new byte[]{});
		when(mockRepo.findBrandImage(eq(id))).thenReturn(value);
		
		Image img = service.getBrandImage(id);
		
		assertNotNull("Image is null", img);
		verify(mockRepo, times(1)).findBrandImage(eq(id));
	}
}
