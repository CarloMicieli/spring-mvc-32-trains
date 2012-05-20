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
package com.trenako.repositories;

import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.trenako.AbstractRepositoryTests;
import com.trenako.entities.Brand;
import com.trenako.repositories.mongo.BrandsRepositoryImpl;

import static org.junit.Assert.*;

public class BrandsRepositoryTests extends AbstractRepositoryTests<Brand> {

	@InjectMocks private BrandsRepositoryImpl repo;
		
	@Before
	public void setUp() {
		super.init(Brand.class);
	}
	
	@Test 
	public void shouldFindBrandsById() {
		final ObjectId id = new ObjectId();
		mockFindById(id, new Brand());
		
		Brand brand = repo.findById(id);
		
		verifyFindById(id);
		assertNotNull("Brand not found.", brand);
	}

	@Test
	public void shouldFindBrandsByName() {
		
		mockFindOneByQuery(new Brand());
		
		Brand brand = repo.findByName("AAA");		
		
		Query query = null;
		Map<String, Criteria> criterias = verifyFindOneByQuery(query);
		
		assertNotNull("Criteria for 'name' not found.", criterias.get("name"));
		assertNotNull("Brand not found.", brand);
	}
}
