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

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Account;
import com.trenako.repositories.AccountsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountsServiceTests {

	@Mock AccountsRepository repo;
	AccountsService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new AccountsServiceImpl(repo);
	}

	@Test
	public void shouldFindAccountsById() {
		ObjectId id = new ObjectId();
		service.findById(id);
		verify(repo, times(1)).findById(eq(id));
	}

	@Test
	public void shouldFindAccountsByEmailAddress() {
		String emailAddress = "mail@mail.com";
		service.findByEmailAddress(emailAddress);
		verify(repo, times(1)).findByEmailAddress(eq(emailAddress));
	}
	
	@Test
	public void shouldFindAccountsBySlug() {
		String slug = "user-slug";
		service.findBySlug(slug);
		verify(repo, times(1)).findBySlug(eq(slug));
	}

	@Test
	public void shouldSaveAccounts() {
		Account account = new Account();
		service.save(account);
		verify(repo, times(1)).save(eq(account));
	}
	
	@Test
	public void shouldRemoveAccounts() {
		Account account = new Account();
		service.remove(account);
		verify(repo, times(1)).remove(eq(account));
	}
}
