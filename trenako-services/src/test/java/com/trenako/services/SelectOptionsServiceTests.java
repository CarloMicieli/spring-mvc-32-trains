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

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.repositories.SelectOptionsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectOptionsServiceTests {
	
	@Mock SelectOptionsRepository repo;
	SelectOptionsService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new SelectOptionsServiceImpl(repo);
	}

	@Test
	public void shouldReturnBrandsList() {
		when(repo.getBrands()).thenReturn(Arrays.asList(new Brand(), new Brand()));
		
		Iterable<Brand> brands = service.brands();
		
		assertNotNull(brands);
		verify(repo, times(1)).getBrands();
	}

	@Test
	public void shouldReturnRailwaysList() {
		when(repo.getRailways()).thenReturn(Arrays.asList(new Railway(), new Railway()));
		
		Iterable<Railway> railways = service.railways();
		
		assertNotNull(railways);
		verify(repo, times(1)).getRailways();
	}
	
	@Test
	public void shouldReturnScalesList() {
		when(repo.getScales()).thenReturn(Arrays.asList(new Scale(), new Scale()));
		
		Iterable<Scale> scales = service.scales();
		
		assertNotNull(scales);
		verify(repo, times(1)).getScales();
	}
	
	@Test
	public void shouldReturnErasList() {
		
	}
}
