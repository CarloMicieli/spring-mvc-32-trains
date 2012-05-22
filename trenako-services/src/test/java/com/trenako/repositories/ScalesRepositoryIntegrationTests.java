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

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.AbstractMongoIntegrationTests;
import com.trenako.entities.Scale;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public abstract class ScalesRepositoryIntegrationTests extends AbstractMongoIntegrationTests<Scale> {
	private @Autowired ScalesRepository repo;
	
	private List<Scale> scales;
	
	public ScalesRepositoryIntegrationTests() {
		super(Scale.class);
	}
	
	@Before
	public void setup() {
		scales = Arrays.asList(
			new Scale.Builder("1")	
				.ratio(32)
				.gauge(44.45)
				.narrow(false).build(),	
			new Scale.Builder("0")
				.ratio(43.5)
				.gauge(32)
				.narrow(false).build(),			
			new Scale.Builder("H0")
				.ratio(87)
				.gauge(16.5)
				.narrow(false).build(),
			new Scale.Builder("N")
				.ratio(160)
				.gauge(9)
				.narrow(false).build()				
				);
		
		super.init(scales);
	}
	
	@After
	public void cleanUp() {
		super.cleanUp();
	}
	
	@Test
	public void shouldFindAllScales() {
		List<Scale> ss = (List<Scale>) repo.findAll();
		assertNotNull(ss);
		assertEquals(4, ss.size());
	}
	
	@Test
	public void shouldFindScalesById() {
		ObjectId id = scales.get(0).getId();
		
		Scale s = repo.findById(id);
		
		assertNotNull(s);
		assertEquals("1", s.getName());
	}
	
	@Test
	public void shouldFindScalesByName() {
		
		Scale s = repo.findByName("H0");
		
		assertNotNull(s);
		assertEquals("H0", s.getName());
	}
	
	@Test
	public void shouldCreateNewScales() {
		Scale scale = new Scale.Builder("H0m")
			.ratio(87)
			.gauge(9)
			.narrow(false).build();
		
		repo.save(scale);
		assertNotNull(scale.getId());
	}
	
	@Test
	public void shouldRemoveScales() {
		Scale s = scales.get(0);
		
		repo.remove(s);
		
		assertNull(repo.findById(s.getId()));
	}
}
