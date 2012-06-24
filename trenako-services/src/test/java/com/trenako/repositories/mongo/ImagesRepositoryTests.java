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
package com.trenako.repositories.mongo;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.trenako.entities.Brand;
import com.trenako.entities.Image;
import com.trenako.repositories.ImagesRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ImagesRepositoryTests {

	@Mock MongoTemplate mongo;
	ImagesRepository repo;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		repo = new ImagesRepositoryImpl(mongo);
	}
	
	@Test
	public void shouldFindBrandsLogoImage() {
		ObjectId id = new ObjectId();
		
		Image logo = new Image("images/jpg", new byte[]{});
		Brand value = new Brand();
		value.setLogo(logo);
		when(mongo.findById(eq(id), eq(Brand.class))).thenReturn(value);
		
		Image img = repo.findBrandImage(id);
		assertNotNull(img);
	}
	
	@Test
	public void shouldReturnNullIfBrandLogoIsNotFound() {
		ObjectId id = new ObjectId();
		when(mongo.findById(eq(id), eq(Brand.class))).thenReturn(null);
		
		Image img = repo.findBrandImage(id);
		assertNull(img);
	}
}
