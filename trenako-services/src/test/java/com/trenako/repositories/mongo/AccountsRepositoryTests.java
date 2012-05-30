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

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.trenako.entities.Account;
import com.trenako.repositories.AccountsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountsRepositoryTests {

	@Mock MongoTemplate mongo;
	AccountsRepository repo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repo = new AccountsRepositoryImpl(mongo);
	}

	@Test
	public void shouldFindAccountsById() {
		ObjectId id = new ObjectId();
		Account value = new Account.Builder("mail@mail.com").build();
		when(mongo.findById(eq(id), eq(Account.class))).thenReturn(value);
		
		Account user = repo.findById(id);
		
		assertNotNull(user);
		verify(mongo, times(1)).findById(eq(id), eq(Account.class));
	}

	@Test
	public void shouldFindAccountsByEmailAddress() {
		String emailAddress = "mail@mail.com";
		Account value = new Account.Builder("mail@mail.com").build();
		when(mongo.findOne(isA(Query.class), eq(Account.class))).thenReturn(value);
		
		Account user = repo.findByEmailAddress(emailAddress);
		
		assertNotNull(user);
		verify(mongo, times(1)).findOne(isA(Query.class), eq(Account.class));
	}

	@Test
	public void shouldFindAccountsBySlug() {
		String slug = "user-slug";
		Account value = new Account.Builder("mail@mail.com").build();
		when(mongo.findOne(isA(Query.class), eq(Account.class))).thenReturn(value);
		
		Account user = repo.findBySlug(slug);
		
		assertNotNull(user);
		verify(mongo, times(1)).findOne(isA(Query.class), eq(Account.class));
	}
	
	@Test
	public void shouldSaveAccounts() {
		Account account = new Account.Builder("mail@mail.com").build();
		repo.save(account);
		verify(mongo, times(1)).save(eq(account));
	}

	@Test
	public void shouldRemoveAccounts() {
		Account account = new Account.Builder("mail@mail.com").build();
		repo.remove(account);
		verify(mongo, times(1)).remove(eq(account));
	}
}
