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

import org.springframework.data.domain.Page;

import com.trenako.web.tags.html.HtmlTag;
import static com.trenako.web.tags.html.HtmlBuilder.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
@SuppressWarnings("serial")
public class PaginationTags extends SpringTagSupport {

	private Page<?> page;
		
	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}

	@Override
	protected int writeTagContent(JspWriter jspWriter, String contextPath)
			throws JspException {
		
		String path = getRequestContext().getPathToServlet();
		
		int page = getPage().getNumber() + 1;
		int numberOfPages = getPage().getTotalPages();
		
		List<HtmlTag> items = new ArrayList<HtmlTag>();
		for (Integer p = 1; p <= numberOfPages; p++) {
			HtmlTag a = a(p.toString()).href(path + "?page.page=" + p);
			
			if (page == p) {
				items.add(li(a).cssClass("active"));
			}
			else {
				items.add(li(a));
			}
		}
		
		HtmlTag pagination = div(ul(tags(items))).cssClass("pagination pagination-centered");
		
		try {
			jspWriter.append(pagination.build());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}

}
