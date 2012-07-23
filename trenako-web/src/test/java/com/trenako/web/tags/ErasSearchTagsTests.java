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
import static com.trenako.web.tags.html.HtmlBuilder.a;
import static com.trenako.web.tags.html.HtmlBuilder.li;
import static com.trenako.web.tags.html.HtmlBuilder.snippet;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.junit.Test;
import org.springframework.context.MessageSource;

import com.trenako.criteria.SearchCriteria;
import com.trenako.results.RollingStockResults;
import com.trenako.services.BrowseService;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.web.tags.html.HtmlTag;
import com.trenako.web.test.AbstractSpringTagsTest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ErasSearchTagsTests extends AbstractSpringTagsTest {

	ErasSearchTags tag;
	BrowseService service;
	
	List<LocalizedEnum<Era>> eras = 
			(List<LocalizedEnum<Era>>) LocalizedEnum.list(Era.class);
	
	@Override
	protected void setupTag(PageContext pageContext, MessageSource messageSource) {
		// mocking service
		service = mock(BrowseService.class);
		when(service.eras()).thenReturn(eras);	
		
		tag = new ErasSearchTags();
		tag.setPageContext(pageContext);
		tag.setMessageSource(messageSource);
		tag.setService(service);
	}

	private void setResults(RollingStockResults results) {
		SearchBarTags parent = new SearchBarTags();
		parent.setResults(results);
		tag.setParent(parent);
	}
	
	@Test
	public void shouldRenderErasNavigationList() throws JspException, UnsupportedEncodingException {
		SearchCriteria sc = new SearchCriteria.Builder()
			.railway(db())
			.build();
		
		RollingStockResults results = mockResults(3, sc, mockRange());
		setResults(results);
		tag.setLabel("label");
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		HtmlTag html = snippet(
			li("era").cssClass("nav-header"),
			snippet(li(a("i").href("/trenako-web", "/rs/railway/db/era/i"))),
			snippet(li(a("ii").href("/trenako-web", "/rs/railway/db/era/ii"))),
			snippet(li(a("iii").href("/trenako-web", "/rs/railway/db/era/iii"))),
			snippet(li(a("iv").href("/trenako-web", "/rs/railway/db/era/iv"))),
			snippet(li(a("v").href("/trenako-web", "/rs/railway/db/era/v"))),
			snippet(li(a("vi").href("/trenako-web", "/rs/railway/db/era/vi")))
				);
		
		String output = renderTag();
		assertEquals(html.toString(), output);
	}
	
	@Test
	public void shouldRenderNavigationListWhenEraAlreadySelected() throws JspException, UnsupportedEncodingException {
		SearchCriteria sc = new SearchCriteria.Builder()
			.era(eraIII())
			.railway(db())
			.build();
		
		RollingStockResults results = mockResults(3, sc, mockRange());
		setResults(results);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);
		
		HtmlTag html = snippet(
				li("era").cssClass("nav-header"),
				li(a("iii").href("#")).cssClass("active"),
				li("").cssClass("divider"),
				li(a("remove").href("/trenako-web/rs/railway/db"))
			);
		
		String output = renderTag();
		assertEquals(html.toString(), output);
	}
}
