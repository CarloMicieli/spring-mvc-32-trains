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

import static org.springframework.test.web.ModelAndViewAssert.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ErrorControllerTests {

	ErrorController controller = new ErrorController();
		
	@Test
	public void shouldShowJustTheErrorPageForRemoteClients() {
		MockHttpServletRequest request = mockRequest("173.194.35.50", new Exception());
		
		ModelAndView mav = controller.resolveException(request);
		
		assertViewName(mav, "error/error");
	}

	@Test
	public void shouldShowStackTraceForLocalhostClients() {
		MockHttpServletRequest request = mockRequest("127.0.0.1", new Exception());
		
		ModelAndView mav = controller.resolveException(request);
		
		assertViewName(mav, "error/debug");
		assertModelAttributeAvailable(mav, "error");
	}
	
	@Test
	public void shouldRenderDeniedPage() {
		String viewName = controller.denied();
		assertEquals("error/denied", viewName);
	}


	private MockHttpServletRequest mockRequest(String remoteAddress, Exception ex) {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr(remoteAddress);
		request.setAttribute("javax.servlet.error.exception", ex);
		return request;
	}
}
