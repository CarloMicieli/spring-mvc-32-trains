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

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import com.trenako.criteria.SearchRequest;
import com.trenako.results.RangeRequest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class SearchRequestArgumentResolverTests {
	SearchRequestArgumentResolver resolver = new SearchRequestArgumentResolver();
	
	MethodParameter parSearchRequest;
	MethodParameter parRangeRequest;
	
	@Before
	public void setup() throws Exception {
		Method method = getClass().getMethod("testmethod", SearchRequest.class, RangeRequest.class);
		parSearchRequest = new MethodParameter(method, 0);
		parRangeRequest = new MethodParameter(method, 1);
	}
	
	@Test
	public void shouldSupportsSearchCriteriaParameters() {
		assertTrue(resolver.supportsParameter(parSearchRequest));
		assertFalse(resolver.supportsParameter(parRangeRequest));
	}
	
	@Test
	public void shouldResolveRequestAsSearchRequest() throws Exception {
		HttpServletRequest request = new MockHttpServletRequest();
		((MockHttpServletRequest)request).setRequestURI("/trenako-web/rs/brand/acme/railway/fs");
		NativeWebRequest webRequest = mock(NativeWebRequest.class);
		when(webRequest.getNativeRequest()).thenReturn(request);
		
		WebDataBinderFactory binderFactory = mock(WebDataBinderFactory.class);
		when(binderFactory.createBinder(eq(webRequest), isA(SearchRequest.class), eq("")))
		.thenAnswer(new Answer<ExtendedServletRequestDataBinder>() {
			@Override
			public ExtendedServletRequestDataBinder answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				SearchRequest req = (SearchRequest) args[1];
				return new ExtendedServletRequestDataBinder(req, "");				}
		});
		
		
		Object obj = resolver.resolveArgument(parSearchRequest,
				null, 
				webRequest,
				binderFactory);
		
		assertNotNull(obj);
		assertTrue(obj instanceof SearchRequest);
		
		SearchRequest expected = new SearchRequest();
		expected.setBrand("acme");
		expected.setRailway("fs");
		
		assertEquals(expected, (SearchRequest) obj);
	}
	
	// helper method for testing
	public void testmethod(SearchRequest sc, RangeRequest range) {
	}
}
