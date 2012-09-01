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

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.activities.Activity;
import com.trenako.entities.Account;
import com.trenako.entities.RollingStock;
import com.trenako.services.HomeService;
import com.trenako.services.view.HomeView;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class HomeControllerMappingTests extends AbstractSpringControllerTests {
	
	private @Autowired HomeService service;
	
	@Override
	protected void init() {
		when(service.getHomeContent((Account)isNull())).thenReturn(homeContent(false));
	}
	
	@After
	public void cleanUp() {
		reset(service);
	}
	
	@Test
	public void shouldRenderHomepage() throws Exception {
		mockMvc().perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl(view("home", "index")));

		mockMvc().perform(get("/home"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl(view("home", "index")));
	}
	
	@Test
	public void shouldRenderHomepageWithContent() throws Exception {
		mockMvc().perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("content"))
			.andExpect(forwardedUrl(view("home", "index")));
	}
	
	@Test
	public void shouldRenderExplorePage() throws Exception {
		mockMvc().perform(get("/explore"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl(view("home", "explore")));
	}
	
	@Test
	public void shouldRenderDeveloperPage() throws Exception {
		mockMvc().perform(get("/developers"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl(view("home", "developers")));
	}
	
	HomeView homeContent(boolean isLogged) {
		Iterable<RollingStock> rollingStocks = Collections.emptyList();
		Iterable<Activity> activityStream = Collections.emptyList();
		
		return new HomeView(isLogged, rollingStocks, activityStream);
	}
}
