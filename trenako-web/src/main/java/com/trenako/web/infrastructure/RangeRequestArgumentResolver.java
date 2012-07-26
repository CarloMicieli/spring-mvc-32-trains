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

import java.beans.PropertyEditorSupport;

import javax.servlet.ServletRequest;

import org.bson.types.ObjectId;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.trenako.results.RangeRequest;

/**
 * It represents a web request resolver for {@code RangeRequest}.
 * @author Carlo Micieli
 *
 */
public class RangeRequestArgumentResolver implements HandlerMethodArgumentResolver {
	private static final Object UNRESOLVED = new Object();
	private static final RangeRequest DEFAULT_RANGE_REQUEST = new RangeRequest(); 
	private final RangeRequest failbackRequest;
	
	/**
	 * Creates an empty {@code RangeRequestArgumentResolver}.
	 */
	public RangeRequestArgumentResolver() {
		this(DEFAULT_RANGE_REQUEST);
	}
	
	/**
	 * Creates a new {@code RangeRequestArgumentResolver}.
	 * @param failbackRequest a failback request
	 */
	public RangeRequestArgumentResolver(RangeRequest failbackRequest) {
		this.failbackRequest = failbackRequest;
	}
	
	@Override
	public Object resolveArgument(MethodParameter param,
			ModelAndViewContainer mavContainer, 
			NativeWebRequest webRequest,
			WebDataBinderFactory webBinder) throws Exception {

		if (param.getParameterType().equals(RangeRequest.class)) {
			
			RangeRequest rangeRequest = this.failbackRequest;
			ServletRequest request = 
					(ServletRequest) webRequest.getNativeRequest();
			
			PropertyValues propValues = 
					new ServletRequestParameterPropertyValues(request);
			
			WebDataBinder wdb = 
					webBinder.createBinder(webRequest, rangeRequest, "");
			wdb.registerCustomEditor(Sort.class, new SortPropertyEditor(propValues));
			wdb.registerCustomEditor(ObjectId.class, new ObjectIdPropertyEditor());
			wdb.bind(propValues);
			
			return rangeRequest;
		}
		
		return UNRESOLVED;
	}

	@Override
	public boolean supportsParameter(MethodParameter par) {
		Class<?> paramType = par.getParameterType();
		return RangeRequest.class.isAssignableFrom(paramType);
	}
	
	private static class ObjectIdPropertyEditor extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			if (!StringUtils.hasText(text)) {
				setValue(null);
			}
			
			setValue(new ObjectId(text));
		}
	}
	
	/**
	 * A {@code PropertyEditor} to process the order for the sort criteria.
	 * @author Carlo Micieli
	 *
	 */
	private static class SortPropertyEditor extends PropertyEditorSupport {
		private final PropertyValues values;
		
		public SortPropertyEditor(PropertyValues values) {
			this.values = values;
		}
		
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			PropertyValue rawOrder = values.getPropertyValue(RangeRequest.ORDER_NAME);
	           Direction order = null == rawOrder 
	        		   ? Direction.ASC : Direction.fromString(rawOrder.getValue().toString());
	 
	           setValue(new Sort(order, text));
		}
	}
}
