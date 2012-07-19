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

import static com.trenako.web.infrastructure.SearchCriteriaUrlBuilder.buildUrlAdding;
import static com.trenako.web.infrastructure.SearchCriteriaUrlBuilder.buildUrlRemoving;
import static com.trenako.web.tags.html.HtmlBuilder.a;
import static com.trenako.web.tags.html.HtmlBuilder.li;
import static com.trenako.web.tags.html.HtmlBuilder.snippet;
import static com.trenako.web.tags.html.HtmlBuilder.tags;
import static org.apache.commons.beanutils.BeanUtils.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.trenako.SearchCriteria;
import com.trenako.results.RangeRequest;
import com.trenako.services.BrowseService;
import com.trenako.web.tags.html.HtmlTag;

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
	private SearchCriteria criteria;
	
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
		if (criteria != null) return criteria;
		
		if (getParent() != null &&
			getParent().getClass().isAssignableFrom(SearchBarTags.class)) {
			
			SearchBarTags searchBar = (SearchBarTags) getParent();
			criteria = searchBar.getCriteria();
			return criteria;
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
	
	protected <E> HtmlTag render(Iterable<E> items, String criteriaName, String contextPath) {
		List<HtmlTag> tagsList = new ArrayList<HtmlTag>();
		String label = getMessageSource().getMessage("searchBar." + criteriaName + ".title.label", 
				null, 
				criteriaName, 
				null);
		
		tagsList.add(li(label).cssClass("nav-header"));
		
		// check if a criteria for this class has been already selected
		if (getCriteria().has(criteriaName)) {
			String url = buildUrlRemoving(getCriteria(), criteriaName);
			
			tagsList.add(a(getCriteria().get(criteriaName).getValue()).href("#").cssClass("active"));
			tagsList.add(a("remove").href(contextPath, url));
		}
		else {
			for (E it : items) {
				String url = buildUrlAdding(getCriteria(), criteriaName, it);
				
				tagsList.add(snippet(li(a(labelFor(it)).href(contextPath, url))));
			}
		}
		
		return snippet(tags(tagsList));
	}
	
	// for testing
	void setCriteria(SearchCriteria criteria) {
		this.criteria = criteria;
	}
}
