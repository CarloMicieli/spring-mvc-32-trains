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

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.Category;
import com.trenako.Era;
import com.trenako.repositories.BrowseRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BrowseServiceTests {

	@Mock BrowseRepository repo;
	BrowseService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new BrowseServiceImpl(repo);
	}

	@Test
	public void shouldGetAllEras() {
		List<String> eras = (List<String>) service.eras();
		assertEquals(Era.list(), eras);
	}

	@Test
	public void shouldGetAllScales() {
		service.scales();
		verify(repo, times(1)).getScales();
	}

	@Test
	public void shouldGetAllRailways() {
		service.railways();
		verify(repo, times(1)).getRailways();
	}

	@Test
	public void shouldGetAllBrands() {
		service.brands();
		verify(repo, times(1)).getBrands();
	}

	@Test
	public void shouldGetAllCategories() {
		List<String> categories = (List<String>) service.categories();
		assertEquals(Category.list(), categories);
	}
}
