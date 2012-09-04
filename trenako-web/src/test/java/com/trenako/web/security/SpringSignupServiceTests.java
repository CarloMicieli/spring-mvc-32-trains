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

import org.bson.types.ObjectId;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

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
		return new Account.Builder("mail@mail.com")
		.password("pa$$word")
		.displayName("Bob")
		.roles("ROLE_USER")
		.build();
	}

	@Test
	public void shouldCreateNewUserAccounts() {
		Account account = buildAccount();
		when(mockEncoder.encodePassword(eq(account.getPassword()), eq(null)))
			.thenReturn("a1b2c3d4");
		
		service.createAccount(account);

		ArgumentCaptor<Account> arg = ArgumentCaptor.forClass(Account.class);
		verify(mockRepo, times(1)).save(arg.capture());
		Account newAccount = arg.getValue();
		assertEquals("Password has not been encoded", "a1b2c3d4", newAccount.getPassword());
		assertEquals("Account has not been enabled", true, newAccount.isEnabled());
		assertEquals("[ROLE_USER]", newAccount.getRoles().toString());
	}
	
	@Test
	public void shouldFillSlugAndRolesCreatingNewAccounts() {
		Account user = new Account.Builder("mail@mail.com")
			.password("pa$$word")
			.displayName("George Stephenson")
			.build();
		when(mockEncoder.encodePassword(eq(user.getPassword()), eq(null)))
			.thenReturn("a1b2c3d4");
		when(mockRepo.save(isA(Account.class))).thenAnswer(new Answer<Account>() {
			@Override
			public Account answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				Account newUser = (Account) args[0];
				newUser.setId(new ObjectId());
				return newUser;
			}
		});
		
		Account newUser = service.createAccount(user);
		
		assertEquals("george-stephenson", newUser.getSlug());
		assertEquals("[ROLE_USER]", newUser.getRoles().toString());
	}
	
	@Test
	public void shouldAuthenticateAccounts() {
		SecurityContext mockContext = mock(SecurityContext.class);

		Account account = buildAccount();
		AccountDetails accountDetails = new AccountDetails(account);
		
		// inject the mock security context
		service.setSecurityContext(mockContext);
		service.authenticate(account);

		ArgumentCaptor<Authentication> arg = ArgumentCaptor.forClass(Authentication.class);
		verify(mockContext, times(1)).setAuthentication(arg.capture());
		Authentication auth = arg.getValue();
		assertEquals("pa$$word", auth.getCredentials());
		assertEquals(accountDetails, auth.getPrincipal());
		assertEquals(account.getRoles().toString(), auth.getAuthorities().toString());
	}
	
}