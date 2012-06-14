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

import static org.springframework.test.web.server.setup.MockMvcBuilders.*;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.server.MockMvc;

import com.trenako.web.config.WebConfig;

/**
 * It includes the unit tests for {@code Spring MVC} annotations.
 *
 * @author Carlo Micieli
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		loader = MockWebApplicationContextLoader.class,
		classes = {WebConfig.class, TestConfiguration.class})
@ActiveProfiles("test")
@MockWebApplication(name = "appServlet")		
public abstract class AbstractSpringControllerTests {

	public final static String VIEWS_PATH = "/WEB-INF/views/";
	
	private MockMvc mockMvc;
	public MockMvc mockMvc() {
		return mockMvc;
	}

	@Before
	public void setup() {
		mockMvc = annotationConfigSetup(WebConfig.class)
			.configureWebAppRootDir("src/main/webapp", false)
			.build();
	}
}