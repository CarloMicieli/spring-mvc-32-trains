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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;

import com.trenako.entities.Railway;
import com.trenako.repositories.RailwaysRepository;
import com.trenako.services.RailwaysServiceImpl;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RailwaysServiceTests {

	@Mock Pageable paging;
	@Mock RailwaysRepository repo;
	RailwaysServiceImpl service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new RailwaysServiceImpl(repo);
	}

	@Test
	public void shouldFindAllRailways() {
		service.findAll(paging);
		verify(repo, times(1)).findAll(eq(paging));
	}
	
	@Test
	public void shouldFindRailwaysById() {
		ObjectId id = new ObjectId();
		service.findById(id);
		verify(repo, times(1)).findOne(eq(id));
	}

	@Test
	public void shouldFindRailwaysByName() {
		String name = "DB";
		service.findByName(name);
		verify(repo, times(1)).findByName(eq(name));
	}

	@Test
	public void shouldFindRailwaysBySlug() {
		String slug = "die-bahn";
		service.findBySlug(slug);
		verify(repo, times(1)).findBySlug(eq(slug));
	}
	
	@Test
	public void shouldFindRailwaysByCountry() {
		String country = "DEU";
		service.findByCountry(country);
		verify(repo, times(1)).findByCountryOrderByNameAsc(eq(country));
	}

	@Test
	public void shouldSaveRailways() {
		Railway railway = new Railway("DB");
		service.save(railway);
		verify(repo, times(1)).save(eq(railway));
	}

	@Test
	public void shouldRemoveRailways() {
		Railway railway = new Railway("DB");
		service.remove(railway);
		verify(repo, times(1)).delete(eq(railway));
	}

}
