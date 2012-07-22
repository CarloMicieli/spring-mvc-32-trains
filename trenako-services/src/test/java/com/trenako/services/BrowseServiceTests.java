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
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.repositories.BrowseRepository;
import com.trenako.results.RangeRequestImpl;
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
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new BrowseServiceImpl(repo);
	}

	@Test
	public void shouldGetAllEras() {
		Iterable<LocalizedEnum<Era>> eras = service.eras();
		assertNotNull(eras);
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
		List<LocalizedEnum<Category>> categories = (List<LocalizedEnum<Category>>) service.categories();
		assertNotNull(categories);
		assertEquals(Category.values().length, categories.size());
	}
	
	@Test
	public void shouldFindRollingStocks() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand("ACME")
			.railway("FS")
			.build();
		RangeRequestImpl range = new RangeRequestImpl();
		range.setCount(10);
		
		service.findByCriteria(sc, range);
		
		verify(repo, times(1)).findByCriteria(eq(sc), eq(range));		
	}

	@Test
	public void shouldReturnEmptySearchCriteriaIfOriginalIfEmptyToo() {
		SearchCriteria sc = new SearchCriteria();
		
		SearchCriteria dbSc = service.loadSearchCriteria(sc);
		
		assertTrue(dbSc.isEmpty());
		verify(repo, times(0)).findBySlug(isA(String.class), eq(Brand.class));
		verify(repo, times(0)).findBySlug(isA(String.class), eq(Railway.class));
		verify(repo, times(0)).findBySlug(isA(String.class), eq(Scale.class));
	}
	
	@Test
	public void shouldLoadSearchCriteriaInformationFromDB() {
		
		Brand brand = new Brand.Builder("ACME").slug("acme").build();
		Railway railway = new Railway.Builder("FS").companyName("Ferrovie dello stato").build();
		Scale scale = new Scale.Builder("H0").ratio(870).build();
		
		when(repo.findBySlug(eq(brand.getSlug()), eq(Brand.class))).thenReturn(brand);
		when(repo.findBySlug(eq(railway.getSlug()), eq(Railway.class))).thenReturn(railway);
		when(repo.findBySlug(eq(scale.getSlug()), eq(Scale.class))).thenReturn(scale);
		
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(brand.getSlug())
			.railway(railway.getSlug())
			.scale(scale.getSlug())
			.powerMethod("ac")
			.era("iii")
			.category("electric-locomotives")
			.build();
		SearchCriteria dbSc = service.loadSearchCriteria(sc);
		
		assertNotNull(dbSc);
		assertEquals("(acme,ACME)", dbSc.get(Brand.class).toString());
		assertEquals("(fs,FS (Ferrovie dello stato))", dbSc.get(Railway.class).toString());
		assertEquals("(h0,H0 (1:87))", dbSc.get(Scale.class).toString());
		assertEquals("(ac,ac)", dbSc.get(PowerMethod.class).toString());
		assertEquals("(iii,iii)", dbSc.get(Era.class).toString());
		assertEquals("(electric-locomotives,electric-locomotives)", dbSc.get(Category.class).toString());
	}
	
	@Test
	public void shouldFindBrandsBySlug() {
		Brand value = new Brand.Builder("ACME").slug("acme").build();
		when(repo.findBySlug(eq(value.getSlug()), eq(Brand.class))).thenReturn(value);
		
		Brand brand = service.findBrand("acme");
		
		verify(repo, times(1)).findBySlug(eq(value.getSlug()), eq(Brand.class));
		assertNotNull(brand);
		assertEquals(value.getSlug(), brand.getSlug());
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
		Scale value = new Scale.Builder("H0").ratio(870).build();
		when(repo.findBySlug(eq(value.getSlug()), eq(Scale.class))).thenReturn(value);
		
		Scale scale = service.findScale("h0");
		
		verify(repo, times(1)).findBySlug(eq(value.getSlug()), eq(Scale.class));
		assertNotNull(scale);
		assertEquals(value.getSlug(), scale.getSlug());
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
