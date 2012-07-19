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
import com.trenako.entities.Railway;
import com.trenako.services.BrowseService;
import com.trenako.web.tags.html.HtmlTag;
import com.trenako.web.test.AbstractSpringTagsTest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RailwaysSearchTagsTests extends AbstractSpringTagsTest {

	RailwaysSearchTags tag;
	BrowseService service;
	
	List<Railway> railways = Collections.unmodifiableList(Arrays.asList(
			new Railway.Builder("DB").slug("db").build(),
			new Railway.Builder("FS").slug("fs").build()));
	
	@Override
	protected void setupTag(PageContext pageContext, MessageSource messageSource) {
		// mocking service
		service = mock(BrowseService.class);
		when(service.railways()).thenReturn(railways);		
		
		tag = new RailwaysSearchTags();
		tag.setPageContext(pageContext);
		tag.setMessageSource(messageSource);
		tag.setService(service);
	}
	
	@Test
	public void shouldRenderRailwaysNavigationList() throws JspException, UnsupportedEncodingException {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand("ACME")
			.build();
		
		tag.setCriteria(sc);
		tag.setLabel("name");
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		HtmlTag html = snippet(
			li("railway").cssClass("nav-header"),
			snippet(li(a("DB").href("/trenako-web", "/rs/brand/ACME/railway/db"))),
			snippet(li(a("FS").href("/trenako-web", "/rs/brand/ACME/railway/fs")))
				);
		
		String output = renderTag();
		assertEquals(html.toString(), output);
	}
	
	@Test
	public void shouldRenderNavigationListWhenScaleAlreadySelected() throws JspException, UnsupportedEncodingException {
		SearchCriteria sc = new SearchCriteria.Builder()
			.scale("H0")
			.railway("DB")
			.build();
		
		tag.setCriteria(sc);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		HtmlTag html = snippet(
				li("railway").cssClass("nav-header"),
				li(a("DB").href("#")).cssClass("active"),
				li(a("remove").href("/trenako-web/rs/scale/H0"))
			);
		
		String output = renderTag();
		assertEquals(html.toString(), output);
	}
}
