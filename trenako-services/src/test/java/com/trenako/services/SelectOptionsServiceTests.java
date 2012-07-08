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

import org.bson.types.ObjectId;
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
	public void shouldFindBrands() {
		ObjectId brandId = new ObjectId();
		Brand value = new Brand();
		when(repo.findBrand(eq(brandId))).thenReturn(value);
		
		Brand brand = service.findBrand(brandId);
		assertNotNull(brand);
		verify(repo, times(1)).findBrand(eq(brandId));
	}
	
	@Test
	public void shouldFindScales() {
		ObjectId scaleId = new ObjectId();
		Scale value = new Scale();
		when(repo.findScale(eq(scaleId))).thenReturn(value);
		
		Scale scale = service.findScale(scaleId);
		assertNotNull(scale);
		verify(repo, times(1)).findScale(eq(scaleId));
	}
	
	
	@Test
	public void shouldFindRailways() {
		ObjectId railwayId = new ObjectId();
		Railway value = new Railway();
		when(repo.findRailway(eq(railwayId))).thenReturn(value);
		
		Railway railway = service.findRailway(railwayId);
		assertNotNull(railway);
		verify(repo, times(1)).findRailway(eq(railwayId));
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
}
