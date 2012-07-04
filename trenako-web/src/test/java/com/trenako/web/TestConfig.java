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
package com.trenako.web;

import static org.mockito.Mockito.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.trenako.services.BrandsService;
import com.trenako.services.RailwaysService;
import com.trenako.services.ScalesService;
import com.trenako.web.images.WebImageService;
import com.trenako.web.security.SignupService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Configuration
@ComponentScan("com.trenako.web.controllers")
@Profile("test")
public class TestConfig {
	public @Bean BrandsService brandsService() {
		return mock(BrandsService.class);
	}
	
	public @Bean WebImageService imagesService() {
		return mock(WebImageService.class);
	}

	public @Bean RailwaysService railwaysService() {
		return mock(RailwaysService.class);
	}

	public @Bean ScalesService scalesService() {
		return mock(ScalesService.class);
	}
	
	public @Bean SignupService signupService() {
		return mock(SignupService.class);
	}
}
