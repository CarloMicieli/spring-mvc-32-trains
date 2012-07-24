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
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.trenako.entities.Brand;
import com.trenako.repositories.BrandsCustomRepository;
import com.trenako.repositories.BrandsRepositoryImpl;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BrandsCustomRepositoryTests {

	@Mock MongoTemplate mongo;
	BrandsCustomRepository repo;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		repo = new BrandsRepositoryImpl(mongo);
	}

	@Test
	public void shouldAddNewScaleToBrands() {
		
		repo.updateScales(acme(), scaleH0());
		ArgumentCaptor<Query> arg1 = ArgumentCaptor.forClass(Query.class);
		ArgumentCaptor<Update> arg2 = ArgumentCaptor.forClass(Update.class);
		
		verify(mongo, times(1)).updateFirst(arg1.capture(), arg2.capture(), eq(Brand.class));
		
		assertEquals("{ \"slug\" : \"acme\"}", arg1.getValue().getQueryObject().toString());
		assertEquals("{ \"$push\" : { \"scales\" : \"h0\"}}", arg2.getValue().getUpdateObject().toString());
	}

}
