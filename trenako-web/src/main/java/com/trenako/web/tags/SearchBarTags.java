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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.trenako.SearchCriteria;
import com.trenako.results.RangeRequest;

/**
 * It represents an enclosing tag for the rolling stock search bar.
 * <p>
 * Example usage:
 * <blockquote>
 * <pre>
 * <tk:searchBar criteria="${searchCriteria}" range="${rangeRequest}">
 *    76p
 * </tk:searchBar>
 * </pre>
 * </blockquote>
 * </p>
 * @author Carlo Micieli
 *
 */
@SuppressWarnings("serial")
public class SearchBarTags extends SpringTagSupport {
	
	private SearchCriteria searchCriteria;
	private RangeRequest rangeRequest;
	
	public SearchCriteria getCriteria() {
		return searchCriteria;
	}

	public void setCriteria(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public RangeRequest getRange() {
		return rangeRequest;
	}

	public void setRange(RangeRequest rangeRequest) {
		this.rangeRequest = rangeRequest;
	}

	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {
		
		try {
			jspWriter.write("<div class=\"well\" style=\"padding: 8px 0;\"><ul class=\"nav nav-list\">");
		} catch (IOException ex) {
			throw new JspException("Unable to write to JspWriter", ex);
		}
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().append("</ul></div>");
		} catch (IOException ex) {
			throw new JspException("Unable to write to JspWriter", ex);
		}
		return EVAL_PAGE;
	}

}
