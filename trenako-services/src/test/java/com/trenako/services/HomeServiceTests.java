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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.activities.Activity;
import com.trenako.activities.ActivityStream;
import com.trenako.entities.Account;
import com.trenako.entities.RollingStock;
import com.trenako.services.options.HomepageOptions;
import com.trenako.services.view.HomeView;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeServiceTests {
	
	private @Mock RollingStocksService mockRsService;
	private @Mock ActivityStream mockActivityStream;
	
	Iterable<RollingStock> rollingStocks = Collections.emptyList();
	Iterable<Activity> activities = Collections.emptyList();
	
	HomeService service;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new HomeServiceImpl(mockRsService, mockActivityStream);
	}
	
	@Test
	public void shouldLoadHomepageContentForAnonymousUsers() {
		HomepageOptions options = new HomepageOptions(15, 15);
		
		when(mockRsService.findLatestModified(eq(options.getNumberOfRollingStocks())))
			.thenReturn(rollingStocks);
		when(mockActivityStream.recentActivity(eq(options.getActivityStreamSize())))
			.thenReturn(activities);
		
		HomeView homeContent = service.getHomeContent(null, options);
		
		assertEquals(false, homeContent.isLogged());
		assertEquals(rollingStocks, homeContent.getRollingStocks());
		assertEquals(activities, homeContent.getActivityStream());
		
		verify(mockActivityStream, times(1)).recentActivity(eq(options.getActivityStreamSize()));
	}
	
	@Test
	public void shouldLoadHomepageContentForLoggedUsers() {
		HomepageOptions defaultOpts = HomepageOptions.defaultHomepageOptions();
		
		when(mockRsService.findLatestModified(eq(defaultOpts.getNumberOfRollingStocks())))
			.thenReturn(rollingStocks);
		when(mockActivityStream.recentActivity(eq(defaultOpts.getActivityStreamSize())))
			.thenReturn(activities);
		
		HomeView homeContent = service.getHomeContent(loggedUser());
		
		assertEquals(true, homeContent.isLogged());
		assertEquals(rollingStocks, homeContent.getRollingStocks());
		assertEquals(activities, homeContent.getActivityStream());
		
		verify(mockActivityStream, times(1)).recentActivity(eq(defaultOpts.getActivityStreamSize()));
	}
	
	Account loggedUser() {
		return new Account.Builder("mail@mail.com")
			.displayName("bob")
			.build();
	}
}
