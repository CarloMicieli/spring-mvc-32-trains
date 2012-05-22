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

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Brand;
import com.trenako.repositories.mongo.BrandsRepositoryImpl;
import com.trenako.repositories.mongo.MongoRepository;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BrandsRepositoryTests {

	@Mock MongoRepository<Brand> mongo;
	BrandsRepositoryImpl repo;

	@Before
	public void setUp() {
		//This method has to be called to initialize annotated fields.
		MockitoAnnotations.initMocks(this);
		repo = new BrandsRepositoryImpl(mongo);
	}
	
	@Test
	public void shouldFindAllTheBrands() {
		List<Brand> brands = Arrays.asList(new Brand("ACME"), new Brand("Roco"));
		when(mongo.findAll()).thenReturn(brands);
		
		List<Brand> results = (List<Brand>) repo.findAll();
		
		verify(mongo, times(1)).findAll();
		assertNotNull(results);
		assertEquals(2, results.size());
	}

	@Test
	public void shouldFindBrandsById() {
		ObjectId id = new ObjectId();
		Brand b = new Brand("ACME");
				
		when(mongo.findById(eq(id))).thenReturn(b);
		
		Brand brand = repo.findById(id);
		
		assertNotNull("Brand not found.", brand);
		assertEquals("ACME", brand.getName());
		verify(mongo, times(1)).findById(eq(id));
	}

	@Test
	public void shouldFindBrandsByName() {
		Brand b = new Brand("ACME");
		
		when(mongo.findOne(eq("name"), eq("ACME"))).thenReturn(b);
		
		Brand brand = repo.findByName("ACME");
		
		assertNotNull("Brand not found.", brand);
		assertEquals("ACME", brand.getName());
		verify(mongo, times(1)).findOne(eq("name"), eq("ACME"));
	}
	
	@Test
	public void shouldFindBrandsBySlug() {
		Brand b = new Brand("ACME");
		
		when(mongo.findOne(eq("slug"), eq("acme"))).thenReturn(b);
		
		Brand brand = repo.findBySlug("acme");
		
		assertNotNull("Brand not found.", brand);
		assertEquals("ACME", brand.getName());
		verify(mongo, times(1)).findOne(eq("slug"), eq("acme"));
	}
	
	@Test
	public void shouldSaveBrands() {
		Brand brand = new Brand("ACME");
		
		repo.save(brand);
		
		verify(mongo, times(1)).save(eq(brand));
	}
	
	@Test
	public void shouldRemoveBrands() {
		Brand brand = new Brand("ACME");
		
		repo.remove(brand);
		
		verify(mongo, times(1)).remove(eq(brand));
	}
}
