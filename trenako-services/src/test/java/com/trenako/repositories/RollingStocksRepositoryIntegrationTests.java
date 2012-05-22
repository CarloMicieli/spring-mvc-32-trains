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
package com.trenako.repositories;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.AbstractMongoIntegrationTests;
import com.trenako.entities.Brand;
import com.trenako.entities.Category;
import com.trenako.entities.Era;
import com.trenako.entities.PowerMethod;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;

import static org.junit.Assert.*;

public abstract class RollingStocksRepositoryIntegrationTests 
	extends AbstractMongoIntegrationTests<RollingStock> {

	private @Autowired RollingStocksRepository repo;
	private List<RollingStock> rollingStocks;
	
	public RollingStocksRepositoryIntegrationTests() {
		super(RollingStock.class);
	}
	
	@Before
	public void setup() {
		
		Brand ACME = new Brand("ACME");
		Brand ROCO = new Brand("ROCO");
		Brand BEMO = new Brand("BEMO");
		save(ACME);
		save(BEMO);
		save(ROCO);
		
		Scale H0m = new Scale("H0m");
		Scale H0 = new Scale("H0");
		save(H0m);
		save(H0);
		
		Railway MGB = new Railway("MGB");
		Railway DRG = new Railway("DRG");
		save(DRG);
		save(MGB);
		
		rollingStocks = Arrays.asList(
			new RollingStock.Builder(BEMO, "1262 256")
				.description("MGB HGe 4/4 II 106 \"St.Gotthard/Gottardo\" Zahnradlok \"Glacier-Express\"")
				.category(Category.ELECTRIC_LOCOMOTIVES.keyValue())
				.era(Era.VI.name())
				.powerMethod(PowerMethod.DC.keyValue())
				.railway(MGB)
				.scale(H0m)
				.tags("glacier", "express")
				.build(),
			new RollingStock.Builder(BEMO, "3267 254")
				.description("MGB B 4254 Leichtmetallwagen")
				.category(Category.PASSENGER_CARS.keyValue())
				.era(Era.V.name())
				.railway(MGB)
				.scale(H0m)	
				.build()
				
				);
		super.init(rollingStocks);
	}
	
	@After
	public void cleanUp() {
		dropCollection(RollingStock.class);
		dropCollection(Brand.class);
		dropCollection(Scale.class);
		dropCollection(Railway.class);
	}
	
	@Test
	public void shouldFindAllTheRollingStocks() {
		List<RollingStock> list = (List<RollingStock>) repo.findAll();
		assertNotNull(list);
		assertEquals(2, list.size());
	}
	
	@Test
	public void shouldFindRollingStocksById() {
		RollingStock rs = repo.findById(rollingStocks.get(0).getId());
		assertNotNull(rs);
		assertEquals("bemo-1262-256", rs.getSlug());
		assertEquals("BEMO", rs.getBrand().getName());
		assertEquals("1262 256", rs.getItemNumber());
	}
	
	@Test
	public void shouldFindRollingStocksBySlug() {
		RollingStock rs = repo.findBySlug("bemo-3267-254");
		assertNotNull(rs);
		assertEquals("bemo-3267-254", rs.getSlug());
		assertEquals("BEMO", rs.getBrand().getName());
		assertEquals("3267 254", rs.getItemNumber());
	}

}
