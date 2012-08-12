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

import static org.springframework.test.web.ModelAndViewAssert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import com.trenako.entities.Account;
import com.trenako.security.AccountDetails;
import com.trenako.web.security.UserContext;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class YouControllerTests {

	private @Mock UserContext secContext;
	private YouController controller;
	
	private final static AccountDetails USER = 
			new AccountDetails(new Account.Builder("mail@mail.com").build());
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new YouController(secContext);
	}
	
	@Test
	public void shouldRenderIndex() {
		when(secContext.getCurrentUser()).thenReturn(USER);
		
		ModelAndView mav = controller.index();
		assertViewName(mav, "you/index");
		assertModelAttributeValue(mav, "user", USER);
	}
}
