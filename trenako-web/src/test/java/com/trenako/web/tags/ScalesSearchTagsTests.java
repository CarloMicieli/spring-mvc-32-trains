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

import static com.trenako.test.TestDataBuilder.*;
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

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.Scale;
import com.trenako.services.BrowseService;
import com.trenako.web.tags.html.HtmlTag;
import com.trenako.web.test.AbstractSpringTagsTest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ScalesSearchTagsTests extends AbstractSpringTagsTest {

	ScalesSearchTags tag;
	BrowseService service;
	
	List<Scale> scales = Collections.unmodifiableList(Arrays.asList(scaleH0(), scaleN()));
	
	@Override
	protected void setupTag(PageContext pageContext, MessageSource messageSource) {
		// mocking service
		service = mock(BrowseService.class);
		when(service.scales()).thenReturn(scales);		
		
		tag = new ScalesSearchTags();
		tag.setPageContext(pageContext);
		tag.setMessageSource(messageSource);
		tag.setService(service);
	}
	
	private void setCriteria(SearchCriteria sc) {
		SearchBarTags parent = new SearchBarTags();
		parent.setCriteria(sc);
		tag.setParent(parent);
	}
	
	@Test
	public void shouldRenderScalesNavigationList() throws JspException, UnsupportedEncodingException {
		SearchCriteria sc = new SearchCriteria.Builder()
			.railway(db())
			.build();
		
		setCriteria(sc);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		HtmlTag html = snippet(
			li("scale").cssClass("nav-header"),
			snippet(li(a("H0 (1:87)").href("/trenako-web", "/rs/scale/h0/railway/db"))),
			snippet(li(a("N (1:160)").href("/trenako-web", "/rs/scale/n/railway/db")))
				);
		
		String output = renderTag();
		assertEquals(html.toString(), output);
	}
	
	@Test
	public void shouldRenderNavigationListWhenScaleAlreadySelected() throws JspException, UnsupportedEncodingException {
		SearchCriteria sc = new SearchCriteria.Builder()
			.scale(scaleH0())
			.railway(db())
			.build();
		
		setCriteria(sc);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		HtmlTag html = snippet(
				li("scale").cssClass("nav-header"),
				li(a("H0 (1:87)").href("#")).cssClass("active"),
				li("").cssClass("divider"),
				li(a("remove").href("/trenako-web/rs/railway/db"))
			);
		
		String output = renderTag();
		assertEquals(html.toString(), output);
	}
}
