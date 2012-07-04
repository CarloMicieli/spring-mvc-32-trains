/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.web.security;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.trenako.entities.Account;
import com.trenako.repositories.AccountsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountDetailsServiceTests {
	@Mock AccountsRepository repo;
	AccountDetailsService service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new AccountDetailsService(repo);
	}
	
	@Test
	public void shouldFindUsers() {
		String username = "user";
		Account value = new Account.Builder("mail@mail.com")
			.password("pa$$word")
			.enabled(true)
			.locked(false)
			.roles("ROLE_USER")
			.build();
		when(repo.findByEmailAddress(eq(username))).thenReturn(value);
		
		UserDetails userDetails = service.loadUserByUsername(username);
		
		assertNotNull("Account not found", userDetails);
		assertEquals("mail@mail.com", userDetails.getUsername());
		assertEquals("pa$$word", userDetails.getPassword());
		assertEquals(true, userDetails.isEnabled());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void shouldThrowExceptionIfUserNotFound() {
		String username = "user";
		when(repo.findByEmailAddress(eq(username))).thenReturn(null);
		
		service.loadUserByUsername(username);
	}
}