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

import static com.trenako.web.infrastructure.SearchCriteriaUrlBuilder.*;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.trenako.entities.Scale;

import static com.trenako.web.tags.html.HtmlBuilder.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
@SuppressWarnings("serial")
public class ScalesSearchTags extends SearchBarItemTags {
	
	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {

		try {
			String label = getMessageSource().getMessage("searchBar.scales.title.label", null, "Scales", null);
			
			jspWriter.append(li(label).cssClass("nav-header").toString());
						
			Iterable<Scale> scales = getService().scales();
			for (Scale s : scales) {
				String url = buildUrl(getCriteria().appendScale(s));
				
				jspWriter.append(
						snippet(li(a(labelFor(s)).href(contextPath, url))).toString());
			}
			
			jspWriter.append("</li>");
			
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return SKIP_BODY;
	}
}