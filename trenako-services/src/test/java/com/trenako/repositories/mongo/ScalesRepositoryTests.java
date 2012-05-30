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

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Scale;
import com.trenako.repositories.ScalesRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ScalesRepositoryTests {

	@Mock MongoRepository<Scale> mongo;
	ScalesRepository repo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repo = new ScalesRepositoryImpl(mongo);
	}

	@Test
	public void shouldFindScalesByName() {
		Scale value = new Scale("H0");
		when(mongo.findOne(eq("name"), eq("H0"))).thenReturn(value);
		
		Scale scale = repo.findByName("H0");
		
		verify(mongo, times(1)).findOne(eq("name"), eq("H0"));
		assertNotNull(scale);
	}

	@Test
	public void shouldFindAllTheScales() {
		List<Scale> value = Arrays.asList(new Scale("H0"), new Scale("N"));
		when(mongo.findAll()).thenReturn(value);
		
		List<Scale> results = (List<Scale>) repo.findAll();
		
		verify(mongo, times(1)).findAll();
		assertNotNull(results);
		assertEquals(2, results.size());
	}

	@Test
	public void shouldFindScalesById() {
		ObjectId id = new ObjectId();
		Scale value = new Scale("H0");
		when(mongo.findById(eq(id))).thenReturn(value);
		
		Scale scale = repo.findById(id);
		
		verify(mongo, times(1)).findById(eq(id));
		assertNotNull(scale);
	}

	@Test
	public void shouldSaveScales() {
		Scale scale = new Scale("H0");
		repo.save(scale);
		verify(mongo, times(1)).save(eq(scale));
	}

	@Test
	public void shouldRemoveScales() {
		Scale scale = new Scale("H0");
		repo.remove(scale);
		verify(mongo, times(1)).remove(eq(scale));
	}

}
