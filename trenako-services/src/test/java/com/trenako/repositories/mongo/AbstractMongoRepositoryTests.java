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

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.QueryMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.DBObject;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractMongoRepositoryTests {

	private MappingMongoConverter converter;
	private @Mock MongoTemplate mongo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	
		this.converter = new MappingMongoConverter(mock(MongoDbFactory.class), new MongoMappingContext());
		initRepository(mongo);
	}
	
	abstract void initRepository(MongoTemplate mongo);
	
	protected MongoTemplate mongo() {
		return mongo;
	}
	
	protected DBObject queryObject(ArgumentCaptor<Query> arg) {
		return arg.getValue().getQueryObject();
	}
	
	protected DBObject updateObject(ArgumentCaptor<Update> arg) {
		QueryMapper queryMapper = new QueryMapper(converter);
		return queryMapper.getMappedObject(arg.getValue().getUpdateObject(), null);
	}
}
