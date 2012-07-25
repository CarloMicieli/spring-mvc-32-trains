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

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import static com.trenako.test.TestDataBuilder.*;
import static com.trenako.web.tags.html.HtmlBuilder.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.MessageSource;

import com.trenako.criteria.SearchCriteria;
import com.trenako.web.test.AbstractSpringTagsTest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class BreadcrumbTagsTests extends AbstractSpringTagsTest {

    private BreadcrumbTags tag;
	
    @Override
    protected void setupTag(PageContext pageContext, MessageSource messageSource) {
		tag = new BreadcrumbTags();
		tag.setPageContext(pageContext);
		tag.setMessageSource(messageSource);
	}
	
	@Test
	public void shouldRenderEmptyBreadcrumbTags() throws Exception {

		SearchCriteria sc = new SearchCriteria();
		tag.setCriteria(sc);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		String output = renderTag();
		assertEquals("", output);
	}
	
	@Test
	public void shouldRenderBreadcrumbTagsWithOneCriteria() throws Exception {

		SearchCriteria sc = new SearchCriteria.Builder().brand(acme()).build();
		tag.setCriteria(sc);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);

		 String expected =
			ul(snippet(
				li(
					plain("brand" + " "),
					span("/").cssClass("divider")
						).cssClass("active"),
				
				li(
					a("ACME").href(servletRequest(), "/rs/brand/acme").title("brand"),
					plain(" "),
					span("/").cssClass("divider")
						))
			).cssClass("breadcrumb").build();
				
		String output = renderTag();
		assertEquals(expected+"\n", output);
	}
	
	@Test
	public void shouldRenderBreadcrumbTagsWithMoreCriterias() throws Exception {

		SearchCriteria sc = new SearchCriteria.Builder().brand(acme()).railway(db()).build();
		tag.setCriteria(sc);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);

		 String expected =
			ul(snippet(
				li(
					plain("brand" + " "),
					span("/").cssClass("divider")
						).cssClass("active"),
				
				li(
					a("ACME").href(servletRequest(), "/rs/brand/acme").title("brand"),
					plain(" "),
					span("/").cssClass("divider")
						)),
				snippet(
					li(
						plain("railway" + " "),
						span("/").cssClass("divider")
							).cssClass("active"),
					
					li(
						a("DB (Die bahn)").href(servletRequest(), "/rs/railway/db").title("railway"),
						plain(" "),
						span("/").cssClass("divider")
							))
			).cssClass("breadcrumb").build();
				
		String output = renderTag();
		assertEquals(expected+"\n", output);
	}
}
