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
package com.trenako.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.trenako.listeners.BrandsEventListener;
import com.trenako.listeners.RailwaysEventListener;
import com.trenako.listeners.RollingStocksEventListener;
import com.trenako.listeners.ScalesEventListener;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Configuration
public class EventListenerConfiguration {

	public @Bean BrandsEventListener brandsEventListener() {
		return new BrandsEventListener();
	}
	
	public @Bean RailwaysEventListener railwaysEventListener() {
		return new RailwaysEventListener();
	}

	public @Bean RollingStocksEventListener rollingStocksEventListener() {
		return new RollingStocksEventListener();
	}
	
	public @Bean ScalesEventListener scalesEventListener() {
		return new ScalesEventListener();
	}
	
}
