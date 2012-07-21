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
import com.trenako.repositories.BrandsRepository;
import com.trenako.repositories.RailwaysRepository;
import com.trenako.repositories.RollingStocksSearchRepository;
import com.trenako.repositories.ScalesRepository;
import com.trenako.results.RangeRequestImpl;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RollingStockSearchServiceTests {

	@Mock BrandsRepository brands;
	@Mock RailwaysRepository railways;
	@Mock ScalesRepository scales;
	@Mock RollingStocksSearchRepository repo;
	RollingStocksSearchService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new RollingStocksSearchServiceImpl(repo, brands, railways, scales);
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
		verify(brands, times(0)).findBySlug(isA(String.class));
		verify(railways, times(0)).findBySlug(isA(String.class));
		verify(scales, times(0)).findBySlug(isA(String.class));
	}
	
	@Test
	public void shouldLoadSearchCriteriaInformationFromDB() {
		
		Brand brand = new Brand.Builder("ACME").slug("acme").build();
		Railway railway = new Railway.Builder("FS").companyName("Ferrovie dello stato").build();
		Scale scale = new Scale.Builder("H0").ratio(870).build();
		
		when(brands.findBySlug(eq(brand.getSlug()))).thenReturn(brand);
		when(railways.findBySlug(eq(railway.getSlug()))).thenReturn(railway);
		when(scales.findBySlug(eq(scale.getSlug()))).thenReturn(scale);
		
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(brand.getSlug())
			.railway(railway.getSlug())
			.scale(scale.getSlug())
			.build();
		SearchCriteria dbSc = service.loadSearchCriteria(sc);
		
		assertNotNull(dbSc);
		assertEquals("(acme,ACME)", dbSc.get(SearchCriteria.BRAND_KEY).toString());
		assertEquals("(fs,FS (Ferrovie dello stato))", dbSc.get(SearchCriteria.RAILWAY_KEY).toString());
		assertEquals("(h0,H0 (1:87))", dbSc.get(SearchCriteria.SCALE_KEY).toString());
	}
}
