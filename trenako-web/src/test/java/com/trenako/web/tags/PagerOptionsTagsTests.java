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

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.junit.Test;
import org.springframework.context.MessageSource;

import com.trenako.web.test.AbstractSpringTagsTest;

import static com.trenako.web.tags.html.HtmlBuilder.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class PagerOptionsTagsTests extends AbstractSpringTagsTest {

	private PagerOptionsTags tag;
	
	@Override
	protected void setupTag(PageContext pageContext, MessageSource messageSource) {
		tag = new PagerOptionsTags();
		tag.setPageContext(pageContext);
		tag.setMessageSource(messageSource);		
	}
	
	@Test
	public void shouldRenderTheTag() throws JspException, UnsupportedEncodingException {
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		StringBuilder select = new StringBuilder();
		select.append("\n<select id=\"items\" name=\"items\">");
		select.append("\n<option value=\"10\">10 items</option>");
		select.append("\n<option value=\"25\">25 items</option>");
		select.append("\n<option value=\"50\">50 items</option>");
		select.append("\n<option value=\"100\">100 items</option>");
		select.append("\n</select>");
		
		String expected = form(div(select.toString()).cssClass("pull-right")).cssClass("well form-inline").build();
		String output = renderTag();
		assertEquals(expected, output);
	}
}
