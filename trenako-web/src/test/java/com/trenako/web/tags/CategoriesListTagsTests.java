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
import static com.trenako.web.tags.html.HtmlBuilder.*;

import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.junit.Test;
import org.springframework.context.MessageSource;

import com.trenako.entities.Brand;
import com.trenako.web.test.AbstractSpringTagsTest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CategoriesListTagsTests  extends AbstractSpringTagsTest {

	CategoriesListTags tag;
	
	@Override
	protected void setupTag(PageContext pageContext, MessageSource messageSource) {
		tag = new CategoriesListTags();
		tag.setPageContext(pageContext);
	}

	@Test
	public void shouldRenderCategoriesWhenBrandHasNoScale() throws JspException, UnsupportedEncodingException {
		
		Brand roco = new Brand.Builder("Roco").build();
		tag.setBrand(roco);
	
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		String output = renderTag();
		assertEquals(snippet(div().cssClass("row-fluid")).toString(), output);		
	}
		
	
	@Test
	public void shouldRenderCategoriesWhenScaleAndColumnsAreEquals() throws JspException, UnsupportedEncodingException {
		
		Brand roco = new Brand.Builder("Roco").scales("h0", "n", "tt").build();
		tag.setBrand(roco);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		String expected = snippet(
				div(
						div(h2("tt")).cssClass("span4"),
						div(h2("n")).cssClass("span4"),
						div(h2("h0")).cssClass("span4")
						).cssClass("row-fluid")).toString();
		
		String output = renderTag();
		assertEquals(expected, output);
	}

	@Test
	public void shouldRenderCategoriesWhenScalesAreLessThanColumns() throws JspException, UnsupportedEncodingException {
		
		Brand roco = new Brand.Builder("Roco").scales("n", "tt").build();
		tag.setBrand(roco);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		String expected = snippet(
				div(
						div(h2("tt")).cssClass("span4"),
						div(h2("n")).cssClass("span4"),
						div().cssClass("span4")
						).cssClass("row-fluid")).toString();
		
		String output = renderTag();
		assertEquals(expected, output);
	}
	
	@Test
	public void shouldRenderCategoriesWhenScalesAreMoreThanColumns() throws JspException, UnsupportedEncodingException {
		
		Brand roco = new Brand.Builder("Roco").scales("n", "tt", "h0").build();
		tag.setBrand(roco);
		tag.setColumns(2);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		String expected = snippet(
				div(
						div(h2("tt")).cssClass("span6"),
						div(h2("n")).cssClass("span6")
						).cssClass("row-fluid"),
				div(
						div(h2("h0")).cssClass("span6"),
						div().cssClass("span6")
						).cssClass("row-fluid")).toString();
		
		String output = renderTag();
		assertEquals(expected, output);
	}
}
