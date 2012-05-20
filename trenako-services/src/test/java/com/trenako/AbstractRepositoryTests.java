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
package com.trenako;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.QueryUtils;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractRepositoryTests<T> {

	private Class<T> classType;
	
	@Mock protected MongoTemplate mockMongo;
	
	protected void init(Class<T> clazz) {
		this.classType = clazz;
		
		//This method has to be called to initialize annotated fields.
		MockitoAnnotations.initMocks(this);
	}
	
	protected void mockFindById(final ObjectId id, final T entity) {
		when(mockMongo.findById(id, classType)).thenReturn(entity);
	}
	
	protected void verifyFindById(final ObjectId id) {
		verify(mockMongo, times(1)).findById(eq(id), eq(classType));
	}
	
	protected void mockFindOneByQuery(final T value) {
		when(mockMongo.findOne(isA(Query.class), eq(classType))).thenReturn(value);
	}
	
	protected Map<String, Criteria> verifyFindOneByQuery(Query query) {
		ArgumentCaptor<Query> argument = ArgumentCaptor.forClass(Query.class);
		verify(mockMongo).findOne(argument.capture(), eq(classType));
		
		query = argument.getValue();
		
		Map<String, Criteria> map = new HashMap<String, Criteria>();
		List<Criteria> criterias = QueryUtils.getCriteria(query);
		for( Criteria crit : criterias ) {
			map.put(crit.getKey(), crit);
		}
		
		return map;
	}
	
	protected void mockFindByQuery(final List<T> values) {
		when(mockMongo.find(isA(Query.class), eq(classType))).thenReturn(values);
	}
	
	protected Map<String, Criteria> verifyFindByQuery(Query query) {
		ArgumentCaptor<Query> argument = ArgumentCaptor.forClass(Query.class);
		verify(mockMongo).find(argument.capture(), eq(classType));
		
		query = argument.getValue();
		
		Map<String, Criteria> map = new HashMap<String, Criteria>();
		List<Criteria> criterias = QueryUtils.getCriteria(query);
		for( Criteria crit : criterias ) {
			map.put(crit.getKey(), crit);
		}
		
		return map;
	}
}
