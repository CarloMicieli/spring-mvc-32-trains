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

	private static final List<Brand> BRANDS = Arrays.asList(acme(), marklin(), roco());
	private static final List<Railway> RAILWAYS = Arrays.asList(db(), fs());
	private static final List<Scale> SCALES = Arrays.asList(scaleH0(), scaleN());
	
	private RollingStock rs = new RollingStock.Builder(acme(), "123456")
		.railway(fs())
		.scale(scaleH0())
		.build();
	
	@Mock RollingStocksRepository repo;
	@Mock BrandsRepository brandsRepo;
	@Mock RailwaysRepository railwaysRepo;
	@Mock ScalesRepository scalesRepo;
	RollingStocksService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		// mocking repository methods
		when(brandsRepo.findBySlug(eq(acme().getSlug()))).thenReturn(acme());
		when(brandsRepo.findAll()).thenReturn(BRANDS);
		
		when(railwaysRepo.findBySlug(eq(fs().getSlug()))).thenReturn(fs());
		when(railwaysRepo.findAll()).thenReturn(RAILWAYS);
		
		when(scalesRepo.findBySlug(eq(scaleH0().getSlug()))).thenReturn(scaleH0());
		when(scalesRepo.findAll()).thenReturn(SCALES);
		
		service = new RollingStocksServiceImpl(repo, 
				brandsRepo,
				railwaysRepo,
				scalesRepo);
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
		RollingStock newRs = new RollingStock();
		newRs.setItemNumber("123456");
		newRs.setBrand(acme().getSlug());
		newRs.setRailway(fs().getSlug());
		newRs.setScale(scaleH0().getSlug());
		
		service.save(rs);
		
		verify(repo, times(1)).save(eq(rs));
		assertEquals("{label=ACME, slug=acme}", rs.getBrand().toString());
		assertEquals("{label=FS (Ferrovie dello stato), slug=fs}", rs.getRailway().toString());
		assertEquals("{label=H0 (1:87), slug=h0}", rs.getScale().toString());
	}

	@Test
	public void shouldRemoveRollingStocks() {
		service.remove(rs);
		verify(repo, times(1)).delete(eq(rs));
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
