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
package com.trenako.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import org.junit.After;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.entities.Account;
import com.trenako.web.security.SignupService;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class AuthControllerMappingTests extends AbstractSpringControllerTests {
	private @Autowired SignupService mockService;
	
	@After
	public void cleanUp() {
		reset(mockService);
	}
	
	@Test
	public void shouldRenderLoginForm() throws Exception {
		mockMvc().perform(get("/auth/login"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl(view("auth", "login")));
	}
	
	@Test
	public void shouldRenderSignupForm() throws Exception {
		mockMvc().perform(get("/auth/signup"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("account"))
			.andExpect(forwardedUrl(view("auth", "signup")));
	}
	
	@Test
	public void shouldCreateNewAccounts() throws Exception {
		mockMvc().perform(post("/auth/signup")
				.param("emailAddress", "mail@mail.com")
				.param("password", "pa$$word")
				.param("displayName", "User"))
			.andExpect(status().isOk())
			.andExpect(redirectedUrl("/default"));
		
		// should authenticate the newly created account
		ArgumentCaptor<Account> arg = ArgumentCaptor.forClass(Account.class);
		verify(mockService, times(1)).createAccount(arg.capture());
		assertEquals("mail@mail.com", arg.getValue().getEmailAddress());
		assertEquals("pa$$word", arg.getValue().getPassword());
	}
}
