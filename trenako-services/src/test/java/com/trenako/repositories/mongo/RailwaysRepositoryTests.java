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

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.query.Order;

import com.trenako.entities.Railway;
import com.trenako.repositories.RailwaysRepository;
import com.trenako.repositories.mongo.core.MongoRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RailwaysRepositoryTests {

	@Mock MongoRepository<Railway> mongo;
	RailwaysRepository repo;
	
	@Before
	public void setUp() {
		//This method has to be called to initialize annotated fields.
		MockitoAnnotations.initMocks(this);
		repo = new RailwaysRepositoryImpl(mongo);
	}

	@Test
	public void shouldFindRailwaysByCountry() {
		
		List<Railway> railways = Arrays.asList(new Railway("DRG"), new Railway("DB"));
		when(mongo.findOrderBy(eq("country"), eq("DEU"), eq("name"), eq(Order.ASCENDING))).thenReturn(railways);
		
		List<Railway> results = (List<Railway>) repo.findByCountry("DEU");
		
		verify(mongo, times(1)).findOrderBy(eq("country"), eq("DEU"), eq("name"), eq(Order.ASCENDING));
		assertNotNull(results);
		assertEquals(2, results.size());		
	}

	@Test
	public void shouldFindRailwaysByName() {

		Railway value = new Railway("DB");
		when(mongo.findOne(eq("name"), eq("DB"))).thenReturn(value);
		
		Railway railway = repo.findByName("DB");
		
		verify(mongo, times(1)).findOne(eq("name"), eq("DB"));
		assertNotNull(railway);
		assertEquals("DB", railway.getName());
	}

	@Test
	public void shouldFindRailwaysBySlug() {

		Railway value = new Railway("DB");
		when(mongo.findOne(eq("slug"), eq("db"))).thenReturn(value);
		
		Railway railway = repo.findBySlug("db");
		
		verify(mongo, times(1)).findOne(eq("slug"), eq("db"));
		assertNotNull(railway);
		assertEquals("DB", railway.getName());
	}

	@Test
	public void shouldFindAllTheRailways() {
		List<Railway> value = Arrays.asList(new Railway("DRG"), new Railway("DB"));
		when(mongo.findAllOrderBy("name", Order.ASCENDING)).thenReturn(value);
		
		List<Railway> railways = (List<Railway>) repo.findAll();
		
		verify(mongo, times(1)).findAllOrderBy("name", Order.ASCENDING);
		assertNotNull(railways);
		assertEquals(2, railways.size());
	}

	@Test
	public void shouldFindRailwaysById() {
		ObjectId id = new ObjectId();
		Railway value = new Railway("DB");
		when(mongo.findById(eq(id))).thenReturn(value);
		
		Railway railway = repo.findById(id);
		
		verify(mongo, times(1)).findById(eq(id));
		assertNotNull(railway);
		assertEquals("DB", railway.getName());
	}

	@Test
	public void shouldSaveRailways() {
		Railway railway = new Railway("DB");
		
		repo.save(railway);
		
		verify(mongo, times(1)).save(eq(railway));
	}

	@Test
	public void shouldRemoveRailways() {
		Railway railway = new Railway("DB");
		
		repo.remove(railway);
		
		verify(mongo, times(1)).remove(eq(railway));
	}
}
