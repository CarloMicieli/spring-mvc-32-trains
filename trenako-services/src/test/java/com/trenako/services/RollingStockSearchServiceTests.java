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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.SearchCriteria;
import com.trenako.repositories.RsRepository;
import com.trenako.results.RangeRequestImpl;

@RunWith(MockitoJUnitRunner.class)
public class RollingStockSearchServiceTests {

	@Mock RsRepository repo;
	RollingStockSearchService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new RollingStockSearchServiceImpl(repo);
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

}
