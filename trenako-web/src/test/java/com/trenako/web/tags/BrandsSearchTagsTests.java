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
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.junit.Test;
import org.springframework.context.MessageSource;

import com.trenako.SearchCriteria;
import com.trenako.entities.Brand;
import com.trenako.services.BrowseService;
import com.trenako.web.tags.html.HtmlTag;
import com.trenako.web.test.AbstractSpringTagsTest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class BrandsSearchTagsTests extends AbstractSpringTagsTest {

	BrandsSearchTags tag;
	BrowseService service;
	
	List<Brand> brands = Collections.unmodifiableList(Arrays.asList(
			new Brand.Builder("ACME").slug("acme").build(),
			new Brand.Builder("Roco").slug("roco").build()));
	
	@Override
	protected void setupTag(PageContext pageContext, MessageSource messageSource) {
		// mocking service
		service = mock(BrowseService.class);
		when(service.brands()).thenReturn(brands);		
		
		tag = new BrandsSearchTags();
		tag.setPageContext(pageContext);
		tag.setMessageSource(messageSource);
		tag.setService(service);
	}
	
	@Test
	public void shouldRenderBrandsNavigationList() throws JspException, UnsupportedEncodingException {
		SearchCriteria sc = new SearchCriteria.Builder()
			.railway("DB")
			.build();
		
		tag.setCriteria(sc);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		HtmlTag html = snippet(
			li("brand").cssClass("nav-header"),
			snippet(li(a("ACME").href("/trenako-web", "/rs/brand/acme/railway/DB"))),
			snippet(li(a("Roco").href("/trenako-web", "/rs/brand/roco/railway/DB")))
				);
		
		String output = renderTag();
		assertEquals(html.toString(), output);
	}
	
	@Test
	public void shouldRenderNavigationListWhenBrandAlreadySelected() throws JspException, UnsupportedEncodingException {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand("ACME")
			.railway("DB")
			.build();
		
		tag.setCriteria(sc);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		HtmlTag html = snippet(
				li("brand").cssClass("nav-header"),
				li(a("ACME").href("#")).cssClass("active"),
				li(a("remove").href("/trenako-web/rs/railway/DB"))
			);
		
		String output = renderTag();
		assertEquals(html.toString(), output);
	}
}
