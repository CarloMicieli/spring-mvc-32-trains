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

import static org.mockito.Mockito.*;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.RollingStock;
import com.trenako.repositories.RollingStocksRepository;
import com.trenako.services.RollingStocksServiceImpl;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RollingStocksServiceTests {

	@Mock RollingStocksRepository repo;
	@InjectMocks RollingStocksServiceImpl service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldFindAllRollingStocks() {
		service.findAll(null);
		verify(repo, times(1)).findAll();
	}
	
	@Test
	public void shouldFindRollingStocksById() {
		ObjectId id = new ObjectId();
		service.findById(id);
		verify(repo, times(1)).findById(eq(id));
	}

	@Test
	public void shouldFindRollingStocksBySlug() {
		String slug = "slug";
		service.findBySlug(slug);
		verify(repo, times(1)).findBySlug(eq(slug));
	}

	@Test
	public void shouldFindRollingStocksByBrand() {
		String brandName = "brandName";
		service.findByBrand(brandName);
		verify(repo, times(1)).findByBrand(eq(brandName));
	}

	@Test
	public void shouldFindRollingStocksByEra() {
		String era = "IV";
		service.findByEra(era);
		verify(repo, times(1)).findByEra(eq(era));
	}

	@Test
	public void shouldFindRollingStocksByScale() {
		String scale = "H0";
		service.findByScale(scale);
		verify(repo, times(1)).findByScale(eq(scale));
	}

	@Test
	public void shouldFindRollingStocksByCategory() {
		String cat = "cat";
		service.findByCategory(cat);
		verify(repo, times(1)).findByCategory(eq(cat));
	}

	@Test
	public void shouldFindRollingStocksByPowerMethod() {
		String pm = "DC";
		service.findByPowerMethod(pm);
		verify(repo, times(1)).findByPowerMethod(eq(pm));
	}

	@Test
	public void shouldFindRollingStocksByRailwayName() {
		String railway = "DB";
		service.findByRailwayName(railway);
		verify(repo, times(1)).findByRailwayName(eq(railway));
	}

	@Test
	public void shouldFindRollingStocksByBrandAndEra() {
		String brand = "ACME";
		String era = "IV";
		service.findByBrandAndEra(brand, era);
		verify(repo, times(1)).findByBrandAndEra(eq(brand), eq(era));	
	}

	@Test
	public void shouldFindRollingStocksByBrandAndScale() {
		String brand = "ACME";
		String scale = "H0";
		service.findByBrandAndScale(brand, scale);
		verify(repo, times(1)).findByBrandAndScale(eq(brand), eq(scale));	
	}

	@Test
	public void shouldFindRollingStocksByBrandAndCategory() {
		String brand = "ACME";
		String cat = "cat";
		service.findByBrandAndCategory(brand, cat);
		verify(repo, times(1)).findByBrandAndCategory(eq(brand), eq(cat));
	}

	@Test
	public void shouldFindRollingStocksByBrandAndRailway() {
		String brand = "ACME";
		String railway = "DB";
		service.findByBrandAndRailway(brand, railway);
		verify(repo, times(1)).findByBrandAndRailway(eq(brand), eq(railway));
	}

	@Test
	public void shouldFindRollingStocksByTag() {
		String tag = "tag";
		service.findByTag(tag);
		verify(repo, times(1)).findByTag(eq(tag));
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
		verify(repo, times(1)).remove(eq(rs));
	}

}
