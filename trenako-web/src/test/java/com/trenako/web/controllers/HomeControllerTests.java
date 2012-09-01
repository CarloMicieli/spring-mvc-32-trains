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

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;

import com.trenako.activities.Activity;
import com.trenako.entities.Account;
import com.trenako.entities.RollingStock;
import com.trenako.security.AccountDetails;
import com.trenako.services.HomeService;
import com.trenako.services.view.HomeView;
import com.trenako.web.security.UserContext;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTests {

	HomeController controller;
	private @Mock HomeService mockService;
	private @Mock UserContext mockUserContext;
	
	@Before
	public void setup() {
		controller = new HomeController(mockService, mockUserContext);
	}
	
	@Test
	public void shouldRenderHomepageForAnonymousUsers() {
		when(mockService.getHomeContent((Account)isNull()))
			.thenReturn(homeContent(false));
		ModelMap model = new ModelMap();
		
		String viewName = controller.home(model);
		
		assertEquals("home/index", viewName);
		assertEquals(homeContent(false), model.get("content"));
	}
	
	@Test
	public void shouldRenderHomepageForLoggedUsers() {
		when(mockUserContext.getCurrentUser()).thenReturn(new AccountDetails(loggedUser()));
		when(mockService.getHomeContent(eq(loggedUser())))
			.thenReturn(homeContent(true));
		ModelMap model = new ModelMap();
		
		String viewName = controller.home(model);
		
		assertEquals("home/index", viewName);
		assertEquals(homeContent(true), model.get("content"));
	}

	@Test
	public void shouldRenderDefaultPage() {
		String viewName = controller.defaultAction();
		assertEquals("home/index", viewName);
	}

	@Test
	public void shouldRenderExplorePage() {
		String viewName = controller.explore();
		assertEquals("home/explore", viewName);
	}
	
	@Test
	public void shouldRenderDevelopersPage() {
		String viewName = controller.developers();
		assertEquals("home/developers", viewName);
	}
	
	HomeView homeContent(boolean isLogged) {
		Iterable<RollingStock> rollingStocks = Collections.emptyList();
		Iterable<Activity> activityStream = Collections.emptyList();
		
		return new HomeView(isLogged, rollingStocks, activityStream);
	}
	
	Account loggedUser() {
		return new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.build();
	}
	
}
