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

import static com.trenako.web.tags.html.HtmlBuilder.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.trenako.results.RollingStockResults;


/**
 * 
 * @author Carlo Micieli
 *
 */
public class PagerOptionsTags extends SpringTagSupport {

	private RollingStockResults results;
	private static List<Integer> ITEMS_VALUES = Arrays.asList(10, 25, 50, 100);
	private static final long serialVersionUID = 1L;
	private @Autowired MessageSource messageSource;
	
	public MessageSource getMessageSource() {
		return messageSource;
	}
	
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
		
		try {
			
			StringBuilder body = new StringBuilder();
			body.append("\n<select id=\"items\" name=\"items\">");
			for (int val : ITEMS_VALUES) {
				String label = messageSource.getMessage("pager.option.label", new Object[] {val}, val + " items", null);
				body.append("\n<option value=\"").append(val).append("\">").append(label).append("</option>");
			}
			body.append("\n</select>");

			jspWriter.write(form(div(body.toString()).cssClass("pull-right")).cssClass("well form-inline").build());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}

}
