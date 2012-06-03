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

import static org.mockito.Mockito.*;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Brand;
import com.trenako.repositories.BrandsRepository;
import com.trenako.services.BrandsServiceImpl;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BrandsServiceTests {

	@Mock BrandsRepository repo;
	@InjectMocks public BrandsServiceImpl service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldFindBrandsById() {
		ObjectId id = new ObjectId();
		service.findById(id);
		verify(repo, times(1)).findById(eq(id));
	}

	@Test
	public void shouldFindBrandsBySlug() {
		service.findBySlug("slug");
		verify(repo, times(1)).findBySlug(eq("slug"));
	}

	@Test
	public void shouldFindBrandsByName() {
		service.findByName("name");
		verify(repo, times(1)).findByName(eq("name"));
	}

	@Test
	public void shouldSaveBrands() {
		Brand brand = new Brand("ACME");
		service.save(brand);
		verify(repo, times(1)).save(eq(brand));
	}

	@Test
	public void shouldRemoveBrands() {
		Brand brand = new Brand("ACME");
		service.remove(brand);
		verify(repo, times(1)).remove(eq(brand));
	}

}
