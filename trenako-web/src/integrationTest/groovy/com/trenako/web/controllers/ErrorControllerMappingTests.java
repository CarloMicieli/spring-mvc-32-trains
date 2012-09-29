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

import org.junit.Test;

import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ErrorControllerMappingTests extends AbstractSpringControllerTests {

	@Test
	public void shouldRenderAccessDeniedPage() throws Exception {
		mockMvc().perform(get("/error/denied"))
			.andExpect(forwardedUrl(view("error", "denied")));
	}
	
	@Test
	public void shouldRenderNotFoundPage() throws Exception {
		mockMvc().perform(get("/error/notfound"))
			.andExpect(forwardedUrl(view("error", "notfound")));
	}
	
//	@Test
//	public void shouldRenderErrorPage() throws Exception {
//		mockMvc().perform(get("/error/server-error"))
//			.andExpect(forwardedUrl(view("error", "error")));
//	}
}
