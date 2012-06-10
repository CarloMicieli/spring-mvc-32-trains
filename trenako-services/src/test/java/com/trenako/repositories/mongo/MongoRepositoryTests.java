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

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import com.trenako.AppGlobals;
import com.trenako.entities.Brand;
import com.trenako.repositories.mongo.core.MongoRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MongoRepositoryTests {

	@Mock MongoTemplate mongo;
	MongoRepository<Brand> repo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repo = new MongoRepository<Brand>(mongo, Brand.class);
	}

	@Test
	public void shouldFindAll() {
		repo.findAll();
		verify(mongo, times(1)).findAll(eq(Brand.class));
	}

	@Test
	public void shouldFindAllSorted() {
		ArgumentCaptor<Query> argument = ArgumentCaptor.forClass(Query.class);
		repo.findAllOrderBy("name", Order.ASCENDING);

		verify(mongo, times(1)).find(argument.capture(), eq(Brand.class));

		Query q = argument.getValue();
		assertEquals(true, q.getSortObject().containsField("name"));
		assertEquals(AppGlobals.MAX_RESULT_SET_SIZE, q.getLimit());
	}
	
	@Test
	public void shouldFindById() {
		ObjectId id = new ObjectId();
		
		repo.findById(id);
		verify(mongo, times(1)).findById(eq(id), eq(Brand.class));
	}
	
	@Test
	public void shouldSave() {
		Brand brand = new Brand("ACME");
		repo.save(brand);
		verify(mongo, times(1)).save(eq(brand));
	}

	@Test
	public void shouldRemoveAll() {
		repo.removeAll();
		verify(mongo, times(1)).remove(isA(Query.class), eq(Brand.class));
	}

	@Test
	public void shouldFindOne() {
		String key = "key";
		Object value = "value";
		
		repo.findOne(key, value);
		verify(mongo, times(1)).findOne(isA(Query.class), eq(Brand.class));
	}

	@Test
	public void shouldFindAllStringObject() {
		String key = "key";
		Object value = "value";
		
		repo.find(key, value);
		verify(mongo, times(1)).find(isA(Query.class), eq(Brand.class));
	}

	@Test
	public void shouldFindAllStringObjectStringOrder() {
		
		repo.findOrderBy("key", "value", "sortCriteria", Order.ASCENDING);
		verify(mongo, times(1)).find(isA(Query.class), eq(Brand.class));
	}

	@Test
	public void shouldFindAllStringObjectStringObject() {
		
		repo.find("key1", "value1", "key2", "value2");
		verify(mongo, times(1)).find(isA(Query.class), eq(Brand.class));
	}

	@Test
	public void shouldRemove() {
		Brand brand = new Brand("ACME");
		repo.remove(brand);
		verify(mongo, times(1)).remove(eq(brand));
	}

	@Test
	public void shouldRemoveById() {
		ObjectId id = new ObjectId();
		repo.removeById(id);
		verify(mongo, times(1)).remove(isA(Query.class), eq(Brand.class));
	}

}
