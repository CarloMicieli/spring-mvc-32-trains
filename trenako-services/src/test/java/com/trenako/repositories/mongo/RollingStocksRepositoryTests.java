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
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.RollingStock;
import com.trenako.repositories.RollingStocksRepository;
import com.trenako.repositories.mongo.core.MongoRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RollingStocksRepositoryTests {

	@Mock MongoRepository<RollingStock> mongo;
	RollingStocksRepository repo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repo = new RollingStocksRepositoryImpl(mongo);
	}

	@Test
	public void shouldFindRollingStocksBySlug() {
		RollingStock value = new RollingStock.Builder("ACME", "123456").build();
		when(mongo.findOne(eq("slug"), eq("acme-123456"))).thenReturn(value);
		
		RollingStock rs = repo.findBySlug("acme-123456");
		
		verify(mongo, times(1)).findOne(eq("slug"), eq("acme-123456"));
		assertNotNull(rs);
		assertEquals("ACME", rs.getBrand().getName());
		assertEquals("123456", rs.getItemNumber());
	}

	@Test
	public void shouldFindRollingStocksByBrand() {
		List<RollingStock> value = Arrays.asList(
				new RollingStock.Builder("ACME", "223456").build(),
				new RollingStock.Builder("ACME", "123456").build());
		when(mongo.find(eq("brandName"), eq("ACME"))).thenReturn(value);
		
		List<RollingStock> results = (List<RollingStock>) repo.findByBrand("ACME");
		
		verify(mongo, times(1)).find(eq("brandName"), eq("ACME"));
		assertNotNull(results);
		assertEquals(2, results.size());
	}
	
	@Test
	public void shouldFindRollingStocksByEra() {
		List<RollingStock> value = Arrays.asList(
				new RollingStock.Builder("ACME", "223456").build(),
				new RollingStock.Builder("ACME", "123456").build());
		when(mongo.find(eq("era"), eq("IV"))).thenReturn(value);
		
		List<RollingStock> results = (List<RollingStock>) repo.findByEra("IV");
		
		verify(mongo, times(1)).find(eq("era"), eq("IV"));
		assertNotNull(results);
		assertEquals(2, results.size());
	}

	@Test
	public void shouldFindRollingStocksScale() {
		List<RollingStock> value = Arrays.asList(
				new RollingStock.Builder("ACME", "223456").build(),
				new RollingStock.Builder("ACME", "123456").build());
		when(mongo.find(eq("scaleName"), eq("H0"))).thenReturn(value);
		
		List<RollingStock> results = (List<RollingStock>) repo.findByScale("H0");
		
		verify(mongo, times(1)).find(eq("scaleName"), eq("H0"));
		assertNotNull(results);
		assertEquals(2, results.size());
	}

	@Test
	public void shouldFindRollingStocksByCategory() {
		List<RollingStock> value = Arrays.asList(
				new RollingStock.Builder("ACME", "223456").build(),
				new RollingStock.Builder("ACME", "123456").build());
		when(mongo.find(eq("category"), eq("electric-locomotive"))).thenReturn(value);
		
		List<RollingStock> results = (List<RollingStock>) repo.findByCategory("electric-locomotive");
		
		verify(mongo, times(1)).find(eq("category"), eq("electric-locomotive"));
		assertNotNull(results);
		assertEquals(2, results.size());
	}

	@Test
	public void shouldFindRollingStocksByPowerMethod() {
		List<RollingStock> value = Arrays.asList(
				new RollingStock.Builder("ACME", "223456").build(),
				new RollingStock.Builder("ACME", "123456").build());
		when(mongo.find(eq("powerMethod"), eq("AC"))).thenReturn(value);
		
		List<RollingStock> results = (List<RollingStock>) repo.findByPowerMethod("AC");
		
		verify(mongo, times(1)).find(eq("powerMethod"), eq("AC"));
		assertNotNull(results);
		assertEquals(2, results.size());
	}

	@Test
	public void shouldFindRollingStocksByRailway() {
		List<RollingStock> value = Arrays.asList(
				new RollingStock.Builder("ACME", "223456").build(),
				new RollingStock.Builder("ACME", "123456").build());
		when(mongo.find(eq("railwayName"), eq("DB"))).thenReturn(value);
		
		List<RollingStock> results = (List<RollingStock>) repo.findByRailwayName("DB");
		
		verify(mongo, times(1)).find(eq("railwayName"), eq("DB"));
		assertNotNull(results);
		assertEquals(2, results.size());
	}
	
	@Test
	public void shouldFindRollingStocksByBrandAndEra() {
		List<RollingStock> value = Arrays.asList(
				new RollingStock.Builder("ACME", "223456").build(),
				new RollingStock.Builder("ACME", "123456").build());
		when(mongo.find(eq("brandName"), eq("ACME"), eq("era"), eq("IV"))).thenReturn(value);
		
		List<RollingStock> results = (List<RollingStock>) repo.findByBrandAndEra("ACME", "IV");
		
		verify(mongo, times(1)).find(eq("brandName"), eq("ACME"), eq("era"), eq("IV"));
		assertNotNull(results);
		assertEquals(2, results.size());
	}

	@Test
	public void shouldFindRollingStocksByBrandAndScale() {
		List<RollingStock> value = Arrays.asList(
				new RollingStock.Builder("ACME", "223456").build(),
				new RollingStock.Builder("ACME", "123456").build());
		when(mongo.find(eq("brandName"), eq("ACME"), eq("scaleName"), eq("H0"))).thenReturn(value);
		
		List<RollingStock> results = (List<RollingStock>) repo.findByBrandAndScale("ACME", "H0");
		
		verify(mongo, times(1)).find(eq("brandName"), eq("ACME"), eq("scaleName"), eq("H0"));
		assertNotNull(results);
		assertEquals(2, results.size());
	}

	@Test
	public void shouldFindRollingStocksByBrandAndCategory() {
		List<RollingStock> value = Arrays.asList(
				new RollingStock.Builder("ACME", "223456").build(),
				new RollingStock.Builder("ACME", "123456").build());
		when(mongo.find(eq("brandName"), eq("ACME"), eq("category"), eq("electric-locomotives"))).thenReturn(value);
		
		List<RollingStock> results = (List<RollingStock>) repo.findByBrandAndCategory("ACME", "electric-locomotives");
		
		verify(mongo, times(1)).find(eq("brandName"), eq("ACME"), eq("category"), eq("electric-locomotives"));
		assertNotNull(results);
		assertEquals(2, results.size());
	}

	@Test
	public void shouldFindRollingStocksByBrandAndRailway() {
		List<RollingStock> value = Arrays.asList(
				new RollingStock.Builder("ACME", "223456").build(),
				new RollingStock.Builder("ACME", "123456").build());
		when(mongo.find(eq("brandName"), eq("ACME"), eq("railwayName"), eq("DB"))).thenReturn(value);
		
		List<RollingStock> results = (List<RollingStock>) repo.findByBrandAndRailway("ACME", "DB");
		
		verify(mongo, times(1)).find(eq("brandName"), eq("ACME"), eq("railwayName"), eq("DB"));
		assertNotNull(results);
		assertEquals(2, results.size());
	}
	
	@Test
	public void shouldFindRollingStocksByTag() {
		List<RollingStock> value = Arrays.asList(
				new RollingStock.Builder("ACME", "223456").build(),
				new RollingStock.Builder("ACME", "123456").build());
		when(mongo.find(eq("tags"), eq("tag-value"))).thenReturn(value);
		
		List<RollingStock> results = (List<RollingStock>) repo.findByTag("tag-value");
		
		verify(mongo, times(1)).find(eq("tags"), eq("tag-value"));
		assertNotNull(results);
		assertEquals(2, results.size());

	}

	@Test
	public void shouldFindAllTheRollingStocks() {
		List<RollingStock> value = Arrays.asList(
				new RollingStock.Builder("ACME", "223456").build(),
				new RollingStock.Builder("ACME", "123456").build());
		when(mongo.findAll()).thenReturn(value);
		
		List<RollingStock> results = (List<RollingStock>) repo.findAll();
		
		verify(mongo, times(1)).findAll();
		assertNotNull(results);
		assertEquals(2, results.size());
	}

	@Test
	public void shouldFindRollingStocksById() {
		ObjectId id = new ObjectId();
		RollingStock value = new RollingStock.Builder("ACME", "223456").build();
		when(mongo.findById(eq(id))).thenReturn(value);
		
		RollingStock rs = repo.findById(id);
		
		verify(mongo, times(1)).findById(eq(id));
		assertNotNull(rs);
	}

	@Test
	public void shouldSaveRollingStocks() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		repo.save(rs);
		verify(mongo, times(1)).save(eq(rs));
	}

	@Test
	public void shouldRemoveRollingStocks() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		repo.remove(rs);
		verify(mongo, times(1)).remove(eq(rs));
	}

}

