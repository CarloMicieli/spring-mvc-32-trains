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

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static com.trenako.web.infrastructure.SearchCriteriaUrlBuilder.*;
import static com.trenako.web.tags.html.HtmlBuilder.*;
import static com.trenako.test.TestDataBuilder.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.Brand;
import com.trenako.entities.Scale;
import com.trenako.services.ScalesService;
import com.trenako.utility.Cat;
import com.trenako.values.Category;
import com.trenako.values.PowerMethod;
import com.trenako.web.tags.html.HtmlTag;
import com.trenako.web.test.AbstractSpringTagsTest;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoriesListTagsTests  extends AbstractSpringTagsTest {

	@Mock ScalesService service;
	CategoriesListTags tag;
	
	@Override
	protected void setupTag(PageContext pageContext, MessageSource messageSource) {
		MockitoAnnotations.initMocks(this);
		
		when(service.findBySlug(eq("h0"))).thenReturn(scaleH0());
		when(service.findBySlug(eq("n"))).thenReturn(scaleN());
		when(service.findBySlug(eq("tt"))).thenReturn(scaleTT());
		
		tag = new CategoriesListTags();
		tag.setPageContext(pageContext);
		tag.setService(service);
		tag.setMessageSource(messageSource);
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
						div(categories(roco, scaleTT())).cssClass("span4"),
						div(categories(roco, scaleN())).cssClass("span4"),
						div(categories(roco, scaleH0())).cssClass("span4")
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
						div(categories(roco, scaleTT())).cssClass("span4"),
						div(categories(roco, scaleN())).cssClass("span4"),
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
						div(categories(roco, scaleTT())).cssClass("span6"),
						div(categories(roco, scaleN())).cssClass("span6")
						).cssClass("row-fluid"),
				div(
						div(categories(roco, scaleH0())).cssClass("span6"),
						div().cssClass("span6")
						).cssClass("row-fluid")).toString();
		
		String output = renderTag();
		assertEquals(expected, output);
	}
	
	private HtmlTag categories(Brand brand, Scale scale) {
		List<HtmlTag> list = new ArrayList<HtmlTag>();
		list.add(h2(scale.label()));
		for (PowerMethod pm : PowerMethod.values()) {
			if (scale.getPowerMethods().contains(pm.label())) {
				buildList(list, brand, scale, pm);
			}
		}
		return snippet(tags(list));
	}
	
	private void buildList(List<HtmlTag> list, Brand brand, Scale scale, PowerMethod pm) {
		List<HtmlTag> items = new ArrayList<HtmlTag>();
		for (Category c : Category.values()) {
			items.add(item(pm.label() + " " + c.label(), brand, scale, Cat.buildCat(pm, c)));
		}
		list.add(ul(tags(items)).cssClass("unstyled"));
	}
	
	private HtmlTag item(String label, Brand brand, Scale scale, Cat cat) {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(brand)
			.scale(scale)
			.build();
		return li(a(label).href("/trenako-web", buildUrlAdding(sc, "cat", cat)));
	}
}
