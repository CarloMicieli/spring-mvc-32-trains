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

import static com.trenako.test.TestDataBuilder.acme;
import static com.trenako.test.TestDataBuilder.fs;
import static com.trenako.test.TestDataBuilder.scaleH0;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.entities.RollingStock;
import com.trenako.services.CommentsService;
import com.trenako.services.RollingStocksService;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CommentsControllerMappingTests extends AbstractSpringControllerTests {
	
	private final static String SLUG = "acme-123456";
	private @Autowired CommentsService service;
	private @Autowired RollingStocksService rsService;
	
	@After
	public void cleanup() {
		reset(service);
		reset(rsService);
	}
	
	@Test
	public void shouldPostNewComments() throws Exception {
		when(rsService.findBySlug(eq(SLUG))).thenReturn(rollingStock());
		mockMvc().perform(post("/rollingstocks/{slug}/comments", SLUG)
				.param("author", "bob")
				.param("content", "My comment"))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(CommentsController.COMMENT_POSTED_MSG)))
			.andExpect(redirectedUrl("/rollingstocks/acme-123456"));
	}
	
//	@Test
//	public void shouldRedirectAfterValidationErrorsPostingNewComments() throws Exception {
//		mockMvc().perform(post("/rollingstocks/{slug}/comments", SLUG)
//				.param("author.slug", "bob")
//				.param("author.label", "Bob")
//				.param("rollingStock.slug", "acme-123456")
//				.param("rollingStock.label", "ACME 123456"))
//			.andExpect(status().isOk())
//			.andExpect(model().size(1))
//			.andExpect(model().attributeExists("comment"))
//			.andExpect(redirectedUrl("/rollingstocks/acme-123456"));
//	}
	
	RollingStock rollingStock() {
		return new RollingStock.Builder(acme(), "123456")
			.railway(fs())
			.scale(scaleH0())
			.description("Desc")
			.build();
	}
}
