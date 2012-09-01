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
package com.trenako.services.view;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.trenako.activities.Activity;
import com.trenako.entities.RollingStock;

/**
 * It represents the immutable content for the homepage.
 * @author Carlo Micieli
 *
 */
public class HomeView {
	private final boolean isLogged;
	private final Iterable<RollingStock> rollingStocks;
	private final Iterable<Activity> activityStream;
	
	public HomeView(boolean isLogged,
			Iterable<RollingStock> rollingStocks,
			Iterable<Activity> activityStream) {
		this.isLogged = isLogged;
		this.rollingStocks = rollingStocks;
		this.activityStream = activityStream;
	}

	/**
	 * Returns whether the current user is logged.
	 * @return {@code true} if the user is logged; {@code false} otherwise
	 */
	public boolean isLogged() {
		return isLogged;
	}

	/**
	 * Returns the last updated {@code RollingStocks}.
	 * @return the {@code RollingStock} list
	 */
	public Iterable<RollingStock> getRollingStocks() {
		return rollingStocks;
	}

	/**
	 * Returns the activity stream.
	 * @return the activity stream
	 */
	public Iterable<Activity> getActivityStream() {
		return activityStream;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof HomeView)) return false;
		
		HomeView other = (HomeView) obj;
		return new EqualsBuilder()
			.append(this.isLogged, other.isLogged)
			.append(this.rollingStocks, other.rollingStocks)
			.append(this.activityStream, other.activityStream)
			.isEquals();
	}	
}
