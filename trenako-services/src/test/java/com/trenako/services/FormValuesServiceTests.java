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

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.repositories.BrandsRepository;
import com.trenako.repositories.RailwaysRepository;
import com.trenako.repositories.ScalesRepository;
import com.trenako.values.Category;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class FormValuesServiceTests {

	private static final List<Brand> BRANDS = Arrays.asList(acme(), marklin(), roco());
	private static final List<Railway> RAILWAYS = Arrays.asList(db(), fs());
	private static final List<Scale> SCALES = Arrays.asList(scaleH0(), scaleN());
	
	@Mock BrandsRepository brandsRepo;
	@Mock RailwaysRepository railwaysRepo;
	@Mock ScalesRepository scalesRepo;
	private FormValuesService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		// mocking repository methods
		when(brandsRepo.findAll()).thenReturn(BRANDS);
		when(railwaysRepo.findAll()).thenReturn(RAILWAYS);
		when(scalesRepo.findAll()).thenReturn(SCALES);
		
		service = new FormValuesServiceImpl(brandsRepo, railwaysRepo, scalesRepo);
	}
	

	@Test
	public void shouldReturnTheBrandsList() {
		Sort sort = new Sort(Direction.ASC, "name");
		when(brandsRepo.findAll(eq(sort))).thenReturn(Arrays.asList(new Brand(), new Brand()));

		Iterable<Brand> brands = service.brands();

		assertNotNull(brands);
		verify(brandsRepo, times(1)).findAll(eq(sort));
	}

	@Test
	public void shouldReturnTheRailwaysList() {
		Sort sort = new Sort(Direction.ASC, "name");
		when(railwaysRepo.findAll(eq(sort))).thenReturn(Arrays.asList(new Railway(), new Railway()));

		Iterable<Railway> railways = service.railways();

		assertNotNull(railways);
		verify(railwaysRepo, times(1)).findAll(eq(sort));
	}

	@Test
	public void shouldReturnTheScalesList() {
		Sort sort = new Sort(new Sort.Order(Direction.ASC, "ratio"), new Sort.Order(Direction.DESC, "gauge"));
		when(scalesRepo.findAll(eq(sort))).thenReturn(Arrays.asList(new Scale(), new Scale()));

		Iterable<Scale> scales = service.scales();

		assertNotNull(scales);
		verify(scalesRepo, times(1)).findAll(eq(sort));
	}

	@Test
	public void shouldReturnTheCategoriesList() {
		Iterable<LocalizedEnum<Category>> categories = service.categories();

		String expected = "[(steam-locomotives), (diesel-locomotives), (electric-locomotives), " +
				"(railcars), (electric-multiple-unit), (freight-cars), "+
				"(passenger-cars), (train-sets), (starter-sets)]";
		assertEquals(expected, categories.toString());
	}

	@Test
	public void shouldReturnTheErasList() {
		Iterable<LocalizedEnum<Era>> eras = service.eras();

		String expected = "[(i), (ii), (iii), (iv), (v), (vi)]";
		assertEquals(expected, eras.toString());
	}

	@Test
	public void shouldReturnThePowerMethodsList() {
		Iterable<LocalizedEnum<PowerMethod>> powerMethods = service.powerMethods();

		String expected = "[(dc), (ac)]";
		assertEquals(expected, powerMethods.toString());
	}
}
