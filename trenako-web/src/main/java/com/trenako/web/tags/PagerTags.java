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

import static com.trenako.web.tags.html.HtmlBuilder.a;
import static com.trenako.web.tags.html.HtmlBuilder.li;
import static com.trenako.web.tags.html.HtmlBuilder.ul;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.trenako.results.RollingStockResults;
import com.trenako.web.tags.html.HtmlTag;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class PagerTags extends SpringTagSupport {
	private static final long serialVersionUID = 1L;
	
	private RollingStockResults results;
	private @Autowired MessageSource messageSource;
	
	public void setResults(RollingStockResults results) {
		this.results = results;
	}
	
	public RollingStockResults getResults() {
		return this.results;
	}
	
	void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {
		
		if (getResults() == null  || getResults().isEmpty()) {
			return SKIP_BODY;
		}
		
		try {
			
			String prev = messageSource.getMessage("pages.previous.label", null, "&larr; Older", null);
			String next = messageSource.getMessage("pages.next.label", null, "Newer &rarr;", null);
			

			
			HtmlTag html = ul(
					li(a(prev).href("#")).cssClass("previous"),
					li(a(next).href("#")).cssClass("next")
					).cssClass("pager");
			jspWriter.write(html.build());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}
}
