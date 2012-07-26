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
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.criteria.SearchCriteria;
import com.trenako.criteria.SearchRequest;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.repositories.BrowseRepository;
import com.trenako.results.RangeRequest;
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
public class BrowseServiceTests {

	@Mock BrowseRepository repo;
	BrowseService service;
	
	static final Iterable<Brand> BRANDS = Arrays.asList(acme(), marklin(), roco());
	static final Iterable<Railway> RAILWAYS = Arrays.asList(db(), fs());
	static final Iterable<Scale> SCALES = Arrays.asList(scaleH0(), scaleN());
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		// mocking repository methods
		when(repo.findBySlug(eq(acme().getSlug()), eq(Brand.class))).thenReturn(acme());
		when(repo.getBrands()).thenReturn(BRANDS);
		
		when(repo.findBySlug(eq(fs().getSlug()), eq(Railway.class))).thenReturn(fs());
		when(repo.getRailways()).thenReturn(RAILWAYS);
		
		when(repo.findBySlug(eq(scaleH0().getSlug()), eq(Scale.class))).thenReturn(scaleH0());
		when(repo.getScales()).thenReturn(SCALES);
		
		service = new BrowseServiceImpl(repo);
	}

	@Test
	public void shouldGetAllEras() {
		List<LocalizedEnum<Era>> eras = (List<LocalizedEnum<Era>>) service.eras();
		assertNotNull(eras);
		assertEquals(Era.values().length, eras.size());
		assertEquals("[(i), (ii), (iii), (iv), (v), (vi)]", eras.toString());
	}
	
	@Test
	public void shouldGetAllCategories() {
		List<LocalizedEnum<Category>> categories = (List<LocalizedEnum<Category>>) service.categories();
		assertNotNull(categories);
		assertEquals(Category.values().length, categories.size());
		
		String expected = "[(steam-locomotives), (diesel-locomotives), "+
				"(electric-locomotives), (railcars), (electric-multiple-unit), " +
				"(freight-cars), (passenger-cars), (train-sets), (starter-sets)]";
		assertEquals(expected, categories.toString());
	}
	
	@Test
	public void shouldGetAllPowerMethods() {
		List<LocalizedEnum<PowerMethod>> powerMethods = (List<LocalizedEnum<PowerMethod>>) service.powerMethods();
		assertNotNull(powerMethods);
		assertEquals(PowerMethod.values().length, powerMethods.size());
		
		String expected = "[(dc), (ac)]";
		assertEquals(expected, powerMethods.toString());
	}
	
	@Test
	public void shouldGetAllScales() {
		Iterable<Scale> scales = service.scales();
		verify(repo, times(1)).getScales();
		assertEquals(SCALES, scales);
	}

	@Test
	public void shouldGetAllRailways() {
		Iterable<Railway> railways = service.railways();
		verify(repo, times(1)).getRailways();
		assertEquals(RAILWAYS, railways);
	}

	@Test
	public void shouldGetAllBrands() {
		Iterable<Brand> brands = service.brands();
		verify(repo, times(1)).getBrands();
		assertEquals(BRANDS, brands);
	}
	
	@Test
	public void shouldFindAllRollingStocks() {
		SearchCriteria searchCriteria = new SearchCriteria.Builder()
			.build();
		RangeRequest range = new RangeRequest();
		range.setSize(10);
		SearchRequest searchReq = new SearchRequest();

		service.findByCriteria(searchReq, range);
		
		verify(repo, times(1)).findByCriteria(eq(searchCriteria), eq(range));		
	}
	
	@Test
	public void shouldFindRollingStocksWithSearchCriteria() {
		SearchCriteria searchCriteria = new SearchCriteria.Builder()
			.brand(acme())
			.railway(fs())
			.scale(scaleH0())
			.powerMethod(ac())
			.era(eraIII())
			.category(electricLocomotives())
			.build();
		
		RangeRequest range = new RangeRequest();
		range.setSize(10);
		
		SearchRequest sr = new SearchRequest(
			acme().getSlug(),
			scaleH0().getSlug(),
			fs().getSlug(),
			eraIII().getKey(),
			null,
			ac().getKey(),
			electricLocomotives().getKey());
		
		service.findByCriteria(sr, range);
		
		verify(repo, times(1)).findByCriteria(eq(searchCriteria), eq(range));		
	}
	
	@Test
	public void shouldFindBrandsBySlug() {
		when(repo.findBySlug(eq(acme().getSlug()), eq(Brand.class))).thenReturn(acme());
		
		Brand brand = service.findBrand("acme");
		
		verify(repo, times(1)).findBySlug(eq(acme().getSlug()), eq(Brand.class));
		assertNotNull(brand);
		assertEquals(acme().getSlug(), brand.getSlug());
	}
	
	@Test
	public void shouldFindRailwaysBySlug() {
		Railway value = new Railway.Builder("FS").companyName("Ferrovie dello stato").build();
		when(repo.findBySlug(eq(value.getSlug()), eq(Railway.class))).thenReturn(value);
		
		Railway railway = service.findRailway("fs");
		
		verify(repo, times(1)).findBySlug(eq(value.getSlug()), eq(Railway.class));
		assertNotNull(railway);
		assertEquals(value.getSlug(), railway.getSlug());
	}
	
	@Test
	public void shouldFindScalesBySlug() {
		when(repo.findBySlug(eq(scaleH0().getSlug()), eq(Scale.class))).thenReturn(scaleH0());
		
		Scale scale = service.findScale("h0");
		
		verify(repo, times(1)).findBySlug(eq(scaleH0().getSlug()), eq(Scale.class));
		assertNotNull(scale);
		assertEquals(scaleH0().getSlug(), scale.getSlug());
	}
	
	@Test
	public void shouldFindCategoriesBySlug() {
		LocalizedEnum<Category> category = service.findCategory("electric-locomotives");
		assertNotNull("Value is null", category);
		assertNotNull("Value is null", category.getKey());
	
		LocalizedEnum<Category> notfound = service.findCategory("not-found");
		assertNull(notfound);
	}
	
	@Test
	public void shouldFindErasBySlug() {
		LocalizedEnum<Era> era = service.findEra("iii");
		assertNotNull("Value is null", era);
		assertNotNull("Value is null", era.getKey());
	
		LocalizedEnum<Era> notfound = service.findEra("not-found");
		assertNull(notfound);
	}
}
