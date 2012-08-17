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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

	private RollingStock rs = new RollingStock.Builder(acme(), "123456")
		.railway(fs())
		.scale(scaleH0())
		.build();
	
	@Mock RollingStocksRepository repo;
	RollingStocksService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new RollingStocksServiceImpl(repo);
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
		RollingStock newRs = new RollingStock.Builder(acme(), "123456")
			.railway(fs())
			.scale(scaleH0())
			.description("Description")
			.build();
	
		service.save(newRs);
		
		verify(repo, times(1)).save(eq(newRs));
	}

	@Test
	public void shouldRemoveRollingStocks() {
		service.remove(rs);
		verify(repo, times(1)).delete(eq(rs));
	}
}
