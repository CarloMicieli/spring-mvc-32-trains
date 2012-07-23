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
package com.trenako.repositories.mongo;

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.repositories.BrowseRepository;
import com.trenako.results.PaginatedResults;
import com.trenako.results.RangeRequest;
import com.trenako.results.RangeRequestImpl;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BrowseRepositoryTests {	
		
	@Mock MongoTemplate mongo;
	BrowseRepository repo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repo = new BrowseRepositoryImpl(mongo);
	}
	
	@Test
	public void shouldGetBrands() {
		List<Brand> value = Arrays.asList(acme(), roco());

		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		when(mongo.find(isA(Query.class), eq(Brand.class))).thenReturn(value);

		Iterable<Brand> brands = repo.getBrands();

		assertNotNull(brands);
		verify(mongo, times(1)).find(arg.capture(), eq(Brand.class));
		Query q = arg.getValue();
		assertEquals("{ \"name\" : 1}", q.getSortObject().toString());
	}

	@Test
	public void shouldGetScales() {
		List<Scale> value = Arrays.asList(scaleH0(), scaleN());

		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		when(mongo.find(isA(Query.class), eq(Scale.class))).thenReturn(value);

		Iterable<Scale> scales = repo.getScales();

		assertNotNull(scales);
		verify(mongo, times(1)).find(arg.capture(), eq(Scale.class));
		Query q = arg.getValue();
		assertEquals("{ \"name\" : 1}", q.getSortObject().toString());
	}

	@Test
	public void shouldGetRailways() {
		List<Railway> value = Arrays.asList(fs(), db());

		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		when(mongo.find(isA(Query.class), eq(Railway.class))).thenReturn(value);

		Iterable<Railway> railways = repo.getRailways();

		assertNotNull(railways);
		verify(mongo, times(1)).find(arg.capture(), eq(Railway.class));
		Query q = arg.getValue();
		assertEquals("{ \"name\" : 1}", q.getSortObject().toString());
	}
	

	private RangeRequest buildRange(int count) {
		RangeRequestImpl range = new RangeRequestImpl();
		range.setCount(count);
		return range;
	}
	
	private void mockFindResults() {
		List<RollingStock> value = Collections.unmodifiableList(
				Arrays.asList(new RollingStock(), new RollingStock()));
		when(mongo.find(isA(Query.class), eq(RollingStock.class))).thenReturn(value);	
	}
	
	private void verifyMongoQuery(String expected, String sortExpected) {
		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		verify(mongo, times(1)).find(arg.capture(), eq(RollingStock.class));
		assertEquals(expected, arg.getValue().getQueryObject().toString());
		assertEquals(sortExpected, arg.getValue().getSortObject().toString());
	}
	
	@Test
	public void shouldFindRollingStocksByBrand() {
		mockFindResults();
		
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(acme())
			.buildImmutable();
		RangeRequest range = buildRange(10);
		
		PaginatedResults<RollingStock> results = repo.findByCriteria(sc, range);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"brandName\" : \"acme\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByEra() {
		mockFindResults();
		SearchCriteria sc = new SearchCriteria.Builder()
			.era(eraIII())
			.buildImmutable();
		RangeRequest range = buildRange(10);
		
		PaginatedResults<RollingStock> results = repo.findByCriteria(sc, range);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"era\" : \"iii\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByScale() {
		mockFindResults();
		SearchCriteria sc = new SearchCriteria.Builder()
			.scale(scaleH0())
			.buildImmutable();
		RangeRequest range = buildRange(10);
		
		PaginatedResults<RollingStock> results = repo.findByCriteria(sc, range);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"scaleName\" : \"h0\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByCategory() {
		mockFindResults();
		
		SearchCriteria sc = new SearchCriteria.Builder()
			.category(electricLocomotives())
			.buildImmutable();
		RangeRequest range = buildRange(10);
		
		PaginatedResults<RollingStock> results = repo.findByCriteria(sc, range);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"category\" : \"electric-locomotives\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByRailway() {
		mockFindResults();

		SearchCriteria sc = new SearchCriteria.Builder()
			.railway(db())
			.buildImmutable();
		RangeRequest range = buildRange(10);
		
		PaginatedResults<RollingStock> results = repo.findByCriteria(sc, range);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"railwayName\" : \"db\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByPowerMethod() {
		mockFindResults();
		
		SearchCriteria sc = new SearchCriteria.Builder()
			.powerMethod(ac())
			.buildImmutable();
		RangeRequest range = buildRange(10);
		
		PaginatedResults<RollingStock> results = repo.findByCriteria(sc, range);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"powerMethod\" : \"ac\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByTag() {
		mockFindResults();
		String tag = "tagval";
		RangeRequest range = buildRange(10);
		
		PaginatedResults<RollingStock> results = repo.findByTag(tag, range);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"tag\" : \"tagval\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByCat() {
		mockFindResults();
		SearchCriteria sc = new SearchCriteria.Builder()
			.cat(acElectricLocomotives())
			.build();
		RangeRequest range = buildRange(10);
		
		PaginatedResults<RollingStock> results = repo.findByCriteria(sc, range);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"category\" : \"electric-locomotives\" , \"powerMethod\" : \"ac\"}", 
				"{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldLoadBrandsBySlug() {
		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		
		repo.findBySlug("acme", Brand.class);
		
		verify(mongo, times(1)).findOne(arg.capture(), eq(Brand.class));
		assertEquals("{ \"slug\" : \"acme\"}", arg.getValue().getQueryObject().toString());
	}
	
	@Test
	public void shouldLoadRailwaysBySlug() {
		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		
		repo.findBySlug("db", Railway.class);
		
		verify(mongo, times(1)).findOne(arg.capture(), eq(Railway.class));
		assertEquals("{ \"slug\" : \"db\"}", arg.getValue().getQueryObject().toString());
	}
	
	@Test
	public void shouldLoadScalesBySlug() {
		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		
		repo.findBySlug("h0", Scale.class);
		
		verify(mongo, times(1)).findOne(arg.capture(), eq(Scale.class));
		assertEquals("{ \"slug\" : \"h0\"}", arg.getValue().getQueryObject().toString());
	}
}
