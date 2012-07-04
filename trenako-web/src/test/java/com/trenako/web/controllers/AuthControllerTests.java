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
package com.trenako.web.controllers;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.ModelAndViewAssert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.ui.ModelMap;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.trenako.entities.Account;
import com.trenako.web.security.SignupService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTests {
	
	@Mock ModelMap mockModel;
	@Mock BindingResult mockResult;
	
	@Mock SignupService mockService;
	AuthController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new AuthController(mockService);
	}
	
	@Test
	public void shouldRenderLoginForm() {
		String viewName = controller.login();
		assertEquals("auth/login", viewName);
	}
	
	@Test
	public void shouldRenderSignupForm() {
		ModelAndView mav = controller.signUp();
		assertViewName(mav, "auth/signup");
		assertModelAttributeAvailable(mav, "account");
	}
	
	@Test
	public void shouldRedirectAfterAccountValidationErrors() {
		when(mockResult.hasErrors()).thenReturn(true);
		ModelMap model = new ExtendedModelMap();
		
		String viewName = controller.createUser(new Account(), mockResult, model);
		
		assertEquals("auth/signup", viewName);
		assertTrue(model.containsAttribute("account"));
	}
	
	@Test
	public void shouldCreateNewAccounts() {
		when(mockResult.hasErrors()).thenReturn(false);
		Account newAccount = new Account();

		String viewName = controller.createUser(newAccount, mockResult, mockModel);

		assertEquals("redirect:/default", viewName);
		verify(mockService, times(1)).createAccount(eq(newAccount));
		verify(mockService, times(1)).authenticate(eq(newAccount));
	}
}