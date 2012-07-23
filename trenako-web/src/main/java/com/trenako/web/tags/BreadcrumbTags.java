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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.trenako.criteria.Criteria;
import com.trenako.criteria.SearchCriteria;
import com.trenako.web.tags.html.HtmlTag;

import static com.trenako.web.tags.html.HtmlBuilder.*;

/**
 * It represent a "breadcrumb" tag.
 * <p>
 * Example usage:
 * <blockquote>
 * <pre>
 * <tk:breadcrumb criteria="${searchCriteria} />
 * </pre>
 * </blockquote>
 * </p>
 * 
 * @author Carlo Micieli
 *
 */
public class BreadcrumbTags extends SpringTagSupport {
	
	private static final long serialVersionUID = 1L;

	private @Autowired MessageSource messageSource;
	
	private SearchCriteria criteria;
	
	public void setCriteria(SearchCriteria criteria) {
		this.criteria = criteria;
	}
	
	// for testing
	void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public SearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	protected int writeTagContent(JspWriter out, String contextPath) throws JspException {

		if (getCriteria().isEmpty()) {
			return Tag.SKIP_BODY;
		}
		
		try {
			List<HtmlTag> items = new ArrayList<HtmlTag>();
			for (Criteria crit : getCriteria().criteria()) {
				addElement(items, contextPath, crit);
			}
			
			HtmlTag list = ul(tags(items)).cssClass("breadcrumb");
			out.println(list.toString());
			
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return Tag.SKIP_BODY;
	}
	
	private void addElement(List<HtmlTag> items, String contextPath, Criteria criterion) {
		Pair<String, String> crit = getCriteria().get(criterion);
		if (crit == null) return;
		
		String criteriaName = criterion.criterionName();
		
		String path = new StringBuilder()
			.append("/rs/")
			.append(criteriaName)
			.append("/")
			.append(crit.getKey()).toString();
		
		String label = messageSource.getMessage("breadcrumb." + criteriaName + ".label", 
				null, criteriaName, null);
		String title = messageSource.getMessage("breadcrumb." + criteriaName + ".title.label", 
				null, criteriaName, null);
		
		items.add(snippet(
				li(
					plain(label + " "),
					span("/").cssClass("divider")
						).cssClass("active"),
					
				li(
					a(crit.getValue()).href(contextPath, path).title(title),
					plain(" "),
					span("/").cssClass("divider")
						)));
	}
}
