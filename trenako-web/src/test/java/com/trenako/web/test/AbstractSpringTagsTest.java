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
package com.trenako.web.test;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.junit.Before;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;

import com.trenako.entities.RollingStock;
import com.trenako.criteria.SearchCriteria;
import com.trenako.results.RangeRequest;
import com.trenako.results.RollingStockResults;

/**
 * Helper class to initialize all the infrastructure needed
 * for custom JSP tags unit testing.
 *  
 * @author Carlo Micieli
 *
 */
public abstract class AbstractSpringTagsTest {

	private MockServletContext mockServletContext;
	private MockPageContext mockPageContext;
	private WebApplicationContext mockWebApplicationContext;
	private MockHttpServletRequest mockRequest;
	private MessageSource mockMessageSource;
	
	protected abstract void setupTag(PageContext pageContext, MessageSource messageSource);
	
	@Before
	public void setup() throws Exception {
		mockServletContext = new MockServletContext();
		
		// mocking the spring web application context
		mockWebApplicationContext = mock(WebApplicationContext.class);
		when(mockWebApplicationContext.getServletContext()).thenReturn(mockServletContext);
		when(mockWebApplicationContext.getAutowireCapableBeanFactory()).thenReturn(mock(AutowireCapableBeanFactory.class));
		
		mockServletContext.setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
                mockWebApplicationContext);
		
		// mocking the servlet request
		mockRequest = new MockHttpServletRequest();
		mockRequest.setContextPath("/trenako-web");
		mockPageContext = new MockPageContext(mockServletContext, mockRequest);
				
		// mocking the message source to always return the default value
		mockMessageSource = mock(MessageSource.class);
		when(mockMessageSource.getMessage(anyString(), any(Object[].class), anyString(), any(Locale.class))).thenAnswer(new Answer<String>() {
		    @Override
		    public String answer(InvocationOnMock invocation) throws Throwable {
		      Object[] args = invocation.getArguments();
		      return (String) args[2];
		    }
		  });
		
		// give the tag under test the chance to setup itself
		setupTag(mockPageContext, mockMessageSource);
	}
	
	public String renderTag() throws UnsupportedEncodingException {
		return ((MockHttpServletResponse) pageContext().getResponse()).getContentAsString();
	}
	
	public ServletContext servletContext() {
		return mockServletContext;
	}
	
	public PageContext pageContext() {
		return mockPageContext;
	}

	public WebApplicationContext webApplicationContext() {
		return mockWebApplicationContext;
	}
	
	public HttpServletRequest servletRequest() {
		return mockRequest;
	}
	
	public MessageSource messageSource() {
		return mockMessageSource;
	}
	
	protected RangeRequest mockRange() {
		return new RangeRequest();
	}
	
	protected RollingStockResults mockResults(int numberOfItems, SearchCriteria sc, RangeRequest range) {
		List<RollingStock> results = new ArrayList<RollingStock>();
		for (int i=0; i<numberOfItems; i++) {
			results.add(new RollingStock());
		}
		return new RollingStockResults(results, sc, range);
	}
}
