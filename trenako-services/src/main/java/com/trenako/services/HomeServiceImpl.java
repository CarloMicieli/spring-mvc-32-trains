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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenako.activities.Activity;
import com.trenako.activities.ActivityStream;
import com.trenako.entities.Account;
import com.trenako.entities.RollingStock;
import com.trenako.services.options.HomepageOptions;
import com.trenako.services.view.HomeView;

/**
 * The concrete implementation for the homepage service.
 * @author Carlo Micieli
 *
 */
@Service("homeService")
public class HomeServiceImpl implements HomeService {

	private final RollingStocksService rsService;
	private final ActivityStream activityStream;
	
	@Autowired
	public HomeServiceImpl(RollingStocksService rsService, ActivityStream activityStream) {
		this.rsService = rsService;
		this.activityStream = activityStream;
	}

	@Override
	public HomeView getHomeContent(Account loggedUser) {
		return getHomeContent(loggedUser, HomepageOptions.defaultHomepageOptions());
	}
	
	@Override
	public HomeView getHomeContent(Account loggedUser, HomepageOptions options) {
		boolean isLogged = loggedUser != null;
		
		Iterable<RollingStock> rollingStocks = rsService.findLatestModified(options.getNumberOfRollingStocks());
		Iterable<Activity> stream = activityStream.recentActivity(options.getActivityStreamSize());
		
		return new HomeView(isLogged, rollingStocks, stream);
	}
}
