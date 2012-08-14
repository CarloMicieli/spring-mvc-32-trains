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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import com.trenako.repositories.AccountsRepository;
import com.trenako.security.AccountDetails;
import com.trenako.entities.Account;

/**
 *
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SpringSignupServiceTests {

	@Mock PasswordEncoder mockEncoder;
	@Mock AccountsRepository mockRepo;

	SpringSignupService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new SpringSignupService(mockRepo, mockEncoder);
	}
	
	Account buildAccount() {
		Account a = new Account();
		a.setPassword("pa$$word");
		a.setRoles(Arrays.asList("ROLE_USER"));
		return a;
	}

	@Test
	public void shouldCreateAccounts() {
		Account account = buildAccount();
		ArgumentCaptor<Account> arg = ArgumentCaptor.forClass(Account.class);
		when(mockEncoder.encodePassword(eq(account.getPassword()), eq(null)))
			.thenReturn("a1b2c3d4");
		
		service.createAccount(account);
		
		verify(mockRepo, times(1)).save(arg.capture());
		Account newAccount = arg.getValue();
		assertEquals("Password has not been encoded", "a1b2c3d4", newAccount.getPassword());
		assertEquals("Account has not been enabled", true, newAccount.isEnabled());
		assertEquals("[ROLE_USER]", newAccount.getRoles().toString());
	}
	
	@Test
	public void shouldAuthenticateNewAccounts() {
		SecurityContext mockContext = mock(SecurityContext.class);
		ArgumentCaptor<Authentication> arg = ArgumentCaptor.forClass(Authentication.class);
		Account account = buildAccount();
		AccountDetails accountDetails = new AccountDetails(account);
		
		// inject the mock security context
		service.setSecurityContext(mockContext);
		service.authenticate(account);

		verify(mockContext, times(1)).setAuthentication(arg.capture());
		Authentication auth = arg.getValue();
		assertEquals("pa$$word", auth.getCredentials());
		assertEquals(accountDetails, auth.getPrincipal());
		assertEquals(account.getRoles().toString(), auth.getAuthorities().toString());
	}
	
}