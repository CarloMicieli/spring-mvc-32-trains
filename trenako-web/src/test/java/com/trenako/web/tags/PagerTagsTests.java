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

import java.io.UnsupportedEncodingException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.trenako.results.RollingStockResults;
import com.trenako.results.SearchRange;
import com.trenako.web.tags.html.HtmlTag;
import com.trenako.web.test.AbstractSpringTagsTest;

import static com.trenako.web.tags.html.HtmlBuilder.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class PagerTagsTests extends AbstractSpringTagsTest {

	private PagerTags tag;
	
	static final ObjectId SINCE = new ObjectId("5011996c575e5447d5375afa");
	static final ObjectId MAX = new ObjectId("5011996c575e5447d5375afb");
	
	@Override
	protected void setupTag(PageContext pageContext, MessageSource messageSource) {
		tag = new PagerTags();
		tag.setPageContext(pageContext);
		tag.setMessageSource(messageSource);
	}

	@Test
	public void shouldRenderEmptyPagers() throws JspException, UnsupportedEncodingException {
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);

		String output = renderTag();
		assertEquals("", output);
	}
	
	@Test
	public void shouldRenderPagersWithBothNextAndPreviousDisabled() throws JspException, UnsupportedEncodingException {
		
		RollingStockResults results = mock(RollingStockResults.class);
		when(results.isEmpty()).thenReturn(false);
		when(results.hasPreviousPage()).thenReturn(false);
		when(results.hasNextPage()).thenReturn(false);

		tag.setResults(results);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);

		HtmlTag html = ul(
				li(a("&larr; Older").href("/trenako-web")).cssClass("previous .disabled"),
				li(a("Newer &rarr;").href("/trenako-web")).cssClass("next .disabled")
				).cssClass("pager");
		
		String output = renderTag();
		assertEquals(html.build(), output);
	}
	
	@Test
	public void shouldRenderPagersWithBothNextAndPrevious() throws JspException, UnsupportedEncodingException {

		SearchRange range = new SearchRange(20, new Sort(Direction.ASC, "name"), SINCE, MAX);
		
		RollingStockResults results = mock(RollingStockResults.class);
		when(results.isEmpty()).thenReturn(false);
		when(results.hasPreviousPage()).thenReturn(true);
		when(results.hasNextPage()).thenReturn(true);
		when(results.getRange()).thenReturn(range);

		tag.setResults(results);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);

		HtmlTag html = ul(
				li(a("&larr; Older").href("/trenako-web?dir=ASC&max=5011996c575e5447d5375afb&size=20&sort=name")).cssClass("previous"),
				li(a("Newer &rarr;").href("/trenako-web?dir=ASC&since=5011996c575e5447d5375afa&size=20&sort=name")).cssClass("next")
				).cssClass("pager");
		
		String output = renderTag();
		assertEquals(html.build(), output);
	}
}
