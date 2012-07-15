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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.repositories.BrowseRepository;

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
		List<Brand> value = Arrays.asList(new Brand(), new Brand());

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
		List<Scale> value = Arrays.asList(new Scale(), new Scale());

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
		List<Railway> value = Arrays.asList(new Railway(), new Railway());

		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		when(mongo.find(isA(Query.class), eq(Railway.class))).thenReturn(value);

		Iterable<Railway> railways = repo.getRailways();

		assertNotNull(railways);
		verify(mongo, times(1)).find(arg.capture(), eq(Railway.class));
		Query q = arg.getValue();
		assertEquals("{ \"name\" : 1}", q.getSortObject().toString());
	}
}
