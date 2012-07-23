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

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.PropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.trenako.criteria.SearchRequest;

/**
 * Resolves {@code SearchRequest} method arguments for controller actions.
 * 
 * @author Carlo Micieli
 * @see com.trenako.criteria.SearchRequest
 *
 */
public class SearchRequestArgumentResolver implements HandlerMethodArgumentResolver {
	
	private final SearchRequest searchRequest;
	
	public SearchRequestArgumentResolver() {
		this(new SearchRequest());
	}
	
	public SearchRequestArgumentResolver(SearchRequest failbackCriteria) {
		this.searchRequest = failbackCriteria;
	}
	
	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, 
			NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {

		WebDataBinder webBinder = binderFactory.createBinder(webRequest, searchRequest, "");
		
		HttpServletRequest request = 
				(HttpServletRequest) webRequest.getNativeRequest();
		PropertyValues pvs = new ServletRequestPathVariablesPropertyValues(request);
		webBinder.bind(pvs);
		return searchRequest;
	}

	@Override
	public boolean supportsParameter(MethodParameter par) {
		Class<?> paramType = par.getParameterType();
		return SearchRequest.class.isAssignableFrom(paramType);
	}
}
