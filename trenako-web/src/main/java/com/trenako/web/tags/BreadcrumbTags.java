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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import com.trenako.SearchCriteria;
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

	@Override
	protected int writeTagContent(JspWriter out, String contextPath) throws JspException {
		try {
			
			List<HtmlTag> items = new ArrayList<HtmlTag>();
			if (criteria.hasBrand()) {
				items.add(listItem(contextPath, "brand", criteria.getBrand()));
			}
			if (criteria.hasScale()) {
				items.add(listItem(contextPath, "scale", criteria.getScale()));
			}
			if (criteria.hasCat()) {
				items.add(listItem(contextPath, "cat", criteria.getCat().toString()));
			}
			if (criteria.hasPowerMethod()) {
				items.add(listItem(contextPath, "powerMethod", criteria.getPowerMethod()));
			}
			if (criteria.hasCategory()) {
				items.add(listItem(contextPath, "category", criteria.getCategory()));
			}
			if (criteria.hasRailway()) {
				items.add(listItem(contextPath, "railway", criteria.getRailway()));
			}
			if (criteria.hasEra()) {
				items.add(listItem(contextPath, "era", criteria.getEra()));
			}
			
			HtmlTag list = ul(tags(items)).cssClass("breadcrumb");
			out.println(list.toString());
			
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return Tag.SKIP_BODY;
	}
	
	private HtmlTag listItem(String contextPath, String key, String value) {
		String path = new StringBuilder()
			.append("/rs/")
			.append(key)
			.append("/")
			.append(value).toString();

		String label = messageSource.getMessage("breadcrumb."+key+".label", null, key, null);
		String title = messageSource.getMessage("breadcrumb."+key+".title.label", null, key, null);
		
		 return snippet(
			li(
				plain(label + " "),
				span("/").cssClass("divider")
					).cssClass("active"),
			
			li(
				a(value).href(contextPath, path).title(title),
				plain(" "),
				span("/").cssClass("divider")
					));
	}

}
