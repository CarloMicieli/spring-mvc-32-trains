/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.web.test;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.web.WebAppConfiguration;

import com.trenako.web.config.WebConfig;

/**
 * Abstract class for unit tests for {@code Spring MVC} annotations.
 *
 * @author Carlo Micieli
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, TestConfig.class})
@ActiveProfiles("test")
@WebAppConfiguration
public abstract class AbstractSpringControllerTests {

	@Autowired
	private WebApplicationContext wac;
	
	private final static String VIEWS_PATH = "/WEB-INF/views/";
	
	private MockMvc mockMvc;
	protected MockMvc mockMvc() {
		return mockMvc;
	}
	
	protected String view(String controller, String viewName) {
		return new StringBuilder()
			.append(VIEWS_PATH)
			.append(controller)
			.append("/")
			.append(viewName)
			.append(".jsp")
			.toString();
	}

	protected void init() {}
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = webAppContextSetup(this.wac).build();
		init();
	}
}