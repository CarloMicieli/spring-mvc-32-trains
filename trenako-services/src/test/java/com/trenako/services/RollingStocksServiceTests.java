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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.repositories.BrandsRepository;
import com.trenako.repositories.RailwaysRepository;
import com.trenako.repositories.RollingStocksRepository;
import com.trenako.repositories.ScalesRepository;
import com.trenako.services.RollingStocksServiceImpl;
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
public class RollingStocksServiceTests {

	@Mock RollingStocksRepository repo;
	@Mock BrandsRepository brandsRepo;
	@Mock RailwaysRepository railwaysRepo;
	@Mock ScalesRepository scalesRepo;
	RollingStocksService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new RollingStocksServiceImpl(repo, brandsRepo, railwaysRepo, scalesRepo);
	}

	@Test
	public void shouldFindRollingStocksById() {
		ObjectId id = new ObjectId();
		service.findById(id);
		verify(repo, times(1)).findOne(eq(id));
	}

	@Test
	public void shouldFindRollingStocksBySlug() {
		String slug = "slug";
		service.findBySlug(slug);
		verify(repo, times(1)).findBySlug(eq(slug));
	}
	
	@Test
	public void shouldSaveRollingStocks() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		service.save(rs);
		verify(repo, times(1)).save(eq(rs));
	}

	@Test
	public void shouldRemoveRollingStocks() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		service.remove(rs);
		verify(repo, times(1)).delete(eq(rs));
	}

	@Test
	public void shouldFindBrands() {
		ObjectId brandId = new ObjectId();
		Brand value = new Brand();
		when(brandsRepo.findOne(eq(brandId))).thenReturn(value);
		
		Brand brand = service.findBrand(brandId);
		assertNotNull(brand);
		verify(brandsRepo, times(1)).findOne(eq(brandId));
	}
	
	@Test
	public void shouldFindScales() {
		ObjectId scaleId = new ObjectId();
		Scale value = new Scale();
		when(scalesRepo.findOne(eq(scaleId))).thenReturn(value);
		
		Scale scale = service.findScale(scaleId);
		assertNotNull(scale);
		verify(scalesRepo, times(1)).findOne(eq(scaleId));
	}
	
	
	@Test
	public void shouldFindRailways() {
		ObjectId railwayId = new ObjectId();
		Railway value = new Railway();
		when(railwaysRepo.findOne(eq(railwayId))).thenReturn(value);
		
		Railway railway = service.findRailway(railwayId);
		assertNotNull(railway);
		verify(railwaysRepo, times(1)).findOne(eq(railwayId));
	}

	@Test
	public void shouldReturnBrandsList() {
		Sort sort = new Sort(Direction.ASC, "name");
		when(brandsRepo.findAll(eq(sort))).thenReturn(Arrays.asList(new Brand(), new Brand()));
		
		Iterable<Brand> brands = service.brands();
		
		assertNotNull(brands);
		verify(brandsRepo, times(1)).findAll(eq(sort));
	}

	@Test
	public void shouldReturnRailwaysList() {
		Sort sort = new Sort(Direction.ASC, "name");
		when(railwaysRepo.findAll(eq(sort))).thenReturn(Arrays.asList(new Railway(), new Railway()));
		
		Iterable<Railway> railways = service.railways();
		
		assertNotNull(railways);
		verify(railwaysRepo, times(1)).findAll(eq(sort));
	}
	
	@Test
	public void shouldReturnScalesList() {
		Sort sort = new Sort(new Sort.Order(Direction.ASC, "ratio"), new Sort.Order(Direction.DESC, "gauge"));
		when(scalesRepo.findAll(eq(sort))).thenReturn(Arrays.asList(new Scale(), new Scale()));
		
		Iterable<Scale> scales = service.scales();
		
		assertNotNull(scales);
		verify(scalesRepo, times(1)).findAll(eq(sort));
	}
	
	@Test
	public void shouldReturnCategoriesList() {
		
		Iterable<LocalizedEnum<Category>> categories = service.categories();
		
		String expected = "[(steam-locomotives), (diesel-locomotives), (electric-locomotives), " +
				"(railcars), (electric-multiple-unit), (freight-cars), "+
				"(passenger-cars), (train-sets), (starter-sets)]";
		assertEquals(expected, categories.toString());
		
	}
	
	@Test
	public void shouldReturnErasList() {
		
		Iterable<LocalizedEnum<Era>> eras = service.eras();
		
		String expected = "[(i), (ii), (iii), (iv), (v), (vi)]";
		assertEquals(expected, eras.toString());
		
	}
	
	@Test
	public void shouldReturnPowerMethodsList() {
		
		Iterable<LocalizedEnum<PowerMethod>> powerMethods = service.powerMethods();
		
		String expected = "[(dc), (ac)]";
		assertEquals(expected, powerMethods.toString());
		
	}
}
