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
package com.trenako.web.controllers.admin;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.entities.Option;
import com.trenako.services.OptionsService;
import com.trenako.values.OptionFamily;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class AdminOptionsControllerMappingTests extends AbstractSpringControllerTests {
	
	private @Autowired OptionsService service;
	
	@Override
	protected void init() {
		super.init();
		
		when(service.findByFamily(isA(OptionFamily.class)))
			.thenReturn(Arrays.asList(new Option(), new Option()));
	}

	@After
	public void cleanup() {
		reset(service);
	}
	
	@Test
	public void shouldRenderNewOptionForms() throws Exception {
		mockMvc().perform(get("/admin/options/new"))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("newForm"))
			.andExpect(model().attributeExists("familiesList"))
			.andExpect(forwardedUrl(view("option", "new")));
	}
	
	@Test
	public void shouldRedirectAfterOptionsWereCreated() throws Exception {
		mockMvc().perform(fileUpload("/admin/options")
				.file("file", new byte[]{})
				.param("option.name", "NEM-651")
				.param("family", "dcc-interface"))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(AdminOptionsController.OPTION_CREATED_MSG)))
			.andExpect(redirectedUrl("/admin/options"));
	}
	
	@Test
	public void shouldShowOptionsList() throws Exception {
		mockMvc().perform(get("/admin/options"))
			.andExpect(status().isOk())
			.andExpect(model().size(5))
			.andExpect(model().attributeExists("couplerOptions"))
			.andExpect(model().attributeExists("headlightsOptions"))
			.andExpect(model().attributeExists("transmissionOptions"))
			.andExpect(model().attributeExists("dccOptions"))
			.andExpect(model().attributeExists("familiesList"))
			.andExpect(forwardedUrl(view("option", "list")));
	}
}
