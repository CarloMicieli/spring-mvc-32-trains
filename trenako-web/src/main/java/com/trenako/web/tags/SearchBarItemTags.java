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
package com.trenako.web.tags;

import static org.apache.commons.beanutils.BeanUtils.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.trenako.SearchCriteria;
import com.trenako.results.RangeRequest;
import com.trenako.services.BrowseService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@SuppressWarnings("serial")
public abstract class SearchBarItemTags extends SpringTagSupport {

	private @Autowired MessageSource messageSource;
	private @Autowired BrowseService service;
	
	private String label;
	
	public void setService(BrowseService service) {
		this.service = service;
	}

	public BrowseService getService() {
		return service;
	}
	
	void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public SearchCriteria getCriteria() {
		if (getParent() != null &&
			getParent().getClass().isAssignableFrom(SearchBarTags.class)) {
			
			SearchBarTags searchBar = (SearchBarTags) getParent();
			return searchBar.getCriteria();
		}
		
		return new SearchCriteria();
	}
	
	public RangeRequest getRange() {
		if (getParent() != null &&
			getParent().getClass().isAssignableFrom(SearchBarTags.class)) {
			
			SearchBarTags searchBar = (SearchBarTags) getParent();
			return searchBar.getRange();
		}
		
		return null;
	}
	
	public String labelFor(Object obj) {
		if (label!=null && !label.isEmpty()) {
			try {
				return getProperty(obj, label);
			} catch (Exception e) {
			}
		}
		
		return obj.toString();
	}
}
