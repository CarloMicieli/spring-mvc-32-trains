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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.trenako.entities.PersistentLogin;
import com.trenako.repositories.RememberMeRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RememberMeRepositoryTests {

	@Mock MongoTemplate mongo;
	RememberMeRepository repo;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		repo = new RememberMeRepositoryImpl(mongo);
	}
	
	@Test
	public void shouldCreateNewTokens() {
		final PersistentLogin token = new PersistentLogin();
		repo.createNew(token);
		verify(mongo, times(1)).insert(eq(token));
	}
	
	@Test
	public void shouldSaveTokens() {
		final PersistentLogin token = new PersistentLogin();
		repo.save(token);
		verify(mongo, times(1)).save(eq(token));
	}
	
	@Test
	public void shouldFindTokensBySeries() {
		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		String seriesId = "seriesid";
		
		repo.findBySeries(seriesId);
		
		verify(mongo, times(1)).findOne(arg.capture(), eq(PersistentLogin.class));
		String expected = "{ \"series\" : \"seriesid\"}";
		assertEquals(expected, arg.getValue().getQueryObject().toString());
	}
	
	@Test
	public void shouldDeleteByUsername() {
		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		String username = "user";
		
		repo.deleteByUsername(username);
		
		verify(mongo, times(1)).remove(arg.capture(), eq(PersistentLogin.class));
		String expected = "{ \"username\" : \"user\"}";
		assertEquals(expected, arg.getValue().getQueryObject().toString());		
	}
}
