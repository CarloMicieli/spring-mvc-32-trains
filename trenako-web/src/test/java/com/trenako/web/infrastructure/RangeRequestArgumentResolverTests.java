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
package com.trenako.web.infrastructure;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import com.trenako.criteria.SearchCriteria;
import com.trenako.results.RangeRequest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RangeRequestArgumentResolverTests {
	
	RangeRequest rangeRequest = new RangeRequest();
	RangeRequestArgumentResolver resolver = new RangeRequestArgumentResolver(rangeRequest);

	MethodParameter parSearchCriteria;
	MethodParameter parRangeRequest;
	
	@Before
	public void setup() throws Exception {
		Method method = getClass().getMethod("testmethod", SearchCriteria.class, RangeRequest.class);
		parSearchCriteria = new MethodParameter(method, 0);
		parRangeRequest = new MethodParameter(method, 1);
	}
	
	@Test
	public void shouldSupportRangeRequestParameters() {
		assertFalse(resolver.supportsParameter(parSearchCriteria));
		assertTrue(resolver.supportsParameter(parRangeRequest));
	}

	@Test
	public void shouldResolveRequestAsRangeRequest() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addParameter("max", "47cc67093475061e3d95369d");
		request.addParameter("since", "47cc67093475061e3d95369e");
		request.addParameter("size", "50");
		request.addParameter("sort", "name");
		request.addParameter("dir", "desc");
		
		NativeWebRequest webRequest = mock(NativeWebRequest.class);
		when(webRequest.getNativeRequest()).thenReturn(request);
		
		WebDataBinderFactory binderFactory = mock(WebDataBinderFactory.class);
		when(binderFactory.createBinder(eq(webRequest), isA(RangeRequest.class), eq("")))
			.thenReturn(new ExtendedServletRequestDataBinder(rangeRequest, ""));
		
		Object obj = resolver.resolveArgument(parRangeRequest,
				null, 
				webRequest,
				binderFactory);
		
		assertNotNull(obj);
		assertTrue(obj instanceof RangeRequest);
		
		assertEquals("max=47cc67093475061e3d95369d,since=47cc67093475061e3d95369e,size=50,sort=name: DESC", 
				obj.toString());
	}
	
	// template method for testing
	public void testmethod(SearchCriteria sc, RangeRequest range) {
	}
}
