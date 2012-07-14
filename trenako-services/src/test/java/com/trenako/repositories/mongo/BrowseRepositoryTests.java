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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.trenako.entities.RollingStock;
import com.trenako.repositories.BrowseRepository;
import com.trenako.results.PaginatedResults;
import com.trenako.results.RangeRequest;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BrowseRepositoryTests {
	
	private final ObjectId MAX_ID = new ObjectId();
	private final ObjectId SINCE_ID = new ObjectId();
		
	@Mock MongoTemplate mongo;
	BrowseRepository repo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repo = new BrowseRepositoryImpl(mongo);
	}
	
	private RangeRequest buildRange() {
		RangeRequest range = new RangeRequest();
		range.setMaxId(MAX_ID);
		range.setCount(10);
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
		String brandName = "ACME";
		RangeRequest range = buildRange();
		
		PaginatedResults<RollingStock> results = repo.findByBrand(brandName, range);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"brandName\" : \"ACME\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByBrandAndEra() {
		mockFindResults();
		String brand = "ACME";
		String era = "IV";
		
		Iterable<RollingStock> results = repo.findByBrandAndEra(brand, era);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"brandName\" : \"ACME\" , \"era\" : \"IV\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByBrandAndScale() {
		mockFindResults();
		String brand = "ACME";
		String scale = "H0";
		
		Iterable<RollingStock> results = repo.findByBrandAndScale(brand, scale);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"brandName\" : \"ACME\" , \"scaleName\" : \"H0\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByBrandAndCategory() {
		mockFindResults();
		String brand = "ACME";
		String category = "electric-locomotives";
		
		Iterable<RollingStock> results = repo.findByBrandAndCategory(brand, category);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"brandName\" : \"ACME\" , \"category\" : \"electric-locomotives\"}", 
				"{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByBrandAndRailway() {
		mockFindResults();
		String brand = "ACME";
		String railway = "DB";
		
		Iterable<RollingStock> results = repo.findByBrandAndRailway(brand, railway);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"brandName\" : \"ACME\" , \"railwayName\" : \"DB\"}", 
				"{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByEra() {
		mockFindResults();
		String era = "IV";
		
		Iterable<RollingStock> results = repo.findByEra(era);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"era\" : \"IV\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByScale() {
		mockFindResults();
		String scale = "H0";
		
		Iterable<RollingStock> results = repo.findByScale(scale);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"scaleName\" : \"H0\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByCategory() {
		mockFindResults();
		String category = "electric-locomotives";
		
		Iterable<RollingStock> results = repo.findByCategory(category);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"category\" : \"electric-locomotives\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByRailway() {
		mockFindResults();
		String railway = "db";
		
		Iterable<RollingStock> results = repo.findByRailway(railway);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"railwayName\" : \"db\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByPowerMethod() {
		mockFindResults();
		String powerMethod = "dc";
		
		Iterable<RollingStock> results = repo.findByPowerMethod(powerMethod);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"powerMethod\" : \"dc\"}", "{ \"lastModified\" : -1}");
	}
	
	@Test
	public void shouldFindRollingStocksByTag() {
		mockFindResults();
		String tag = "tagval";
		
		Iterable<RollingStock> results = repo.findByTag(tag);
		
		assertNotNull("Results is empty", results);
		verifyMongoQuery("{ \"tag\" : \"tagval\"}", "{ \"lastModified\" : -1}");
	}
}
