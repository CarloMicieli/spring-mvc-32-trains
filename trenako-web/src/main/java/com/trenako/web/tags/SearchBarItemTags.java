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

import static com.trenako.web.infrastructure.SearchCriteriaUrlBuilder.*;
import static com.trenako.web.tags.html.HtmlBuilder.*;
import static org.apache.commons.beanutils.BeanUtils.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.trenako.criteria.Criteria;
import com.trenako.criteria.SearchCriteria;
import com.trenako.mapping.DbReferenceable;
import com.trenako.results.SearchRange;
import com.trenako.services.BrowseService;
import com.trenako.values.LocalizedEnum;
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

	void setService(BrowseService service) {
		this.service = service;
	}

	BrowseService getService() {
		return service;
	}
	
	void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	MessageSource getMessageSource() {
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
	
	public SearchRange getRange() {
		if (getParent() != null &&
			getParent().getClass().isAssignableFrom(SearchBarTags.class)) {
			
			SearchBarTags searchBar = (SearchBarTags) getParent();
			return searchBar.getRange();
		}
		
		return null;
	}
	
	public String labelFor(Object obj) {
		if (obj.getClass().equals(LocalizedEnum.class)) {
			LocalizedEnum<?> en = (LocalizedEnum<?>) obj;
			if (en != null) {
				return en.getLabel();
			}
		}
		
		if (!StringUtils.isBlank(label)) {
			return safeGetProperty(obj, label);
		}
		
		if (DbReferenceable.class.isAssignableFrom(obj.getClass())) {
			DbReferenceable entity = (DbReferenceable) obj;
			return entity.getLabel();
		}
				
		return obj.toString();
	}
	
	protected String safeGetProperty(Object bean, String name) {
		try {
			return getProperty(bean, name);
		} catch (Exception e) {
			return bean.toString();
		}
	}
	
	protected <E> HtmlTag render(Iterable<E> items, Criteria criteria, String contextPath) {
		String criteriaName = criteria.criterionName();
		List<HtmlTag> tagsList = new ArrayList<HtmlTag>();
		String label = getMessageSource().getMessage("searchBar." + criteriaName + ".title.label", 
				null, 
				criteriaName, 
				null);
		
		tagsList.add(li(label).cssClass("nav-header"));
		
		SearchCriteria searchCriteria = getCriteria();
		// check if a criteria for this class has been already selected
		if (searchCriteria.has(criteria)) {
			String url = buildUrlRemoving(searchCriteria, criteriaName);
			
			tagsList.add(li(a(searchCriteria.get(criteria).getValue()).href("#")).cssClass("active"));
			tagsList.add(li("").cssClass("divider"));
			tagsList.add(li(a("remove").href(contextPath, url)));
		}
		else {
			for (E it : items) {
				String url = buildUrlAdding(searchCriteria, criteriaName, it);
				
				tagsList.add(snippet(li(a(labelFor(it)).href(contextPath, url))));
			}
		}
		
		return snippet(tags(tagsList));
	}
}
