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

import com.trenako.results.RangeRequest;
import com.trenako.results.RollingStockResults;
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
	public void shouldRenderPreviousAndNextPagers() throws JspException, UnsupportedEncodingException {

		RangeRequest range = mock(RangeRequest.class);
		when(range.getSize()).thenReturn(10);
		
		RollingStockResults results = mock(RollingStockResults.class);
		when(results.isEmpty()).thenReturn(false);
		when(results.hasPreviousPage()).thenReturn(true);
		when(results.hasNextPage()).thenReturn(true);
//		when(results.getSinceId()).thenReturn(new ObjectId("47cc67093475061e3d95369d"));
//		when(results.getMaxId()).thenReturn(new ObjectId("47cc67093475061e3d95369e"));
//		when(results.getRange()).thenReturn(range);
		
		tag.setResults(results);
		
		int rv = tag.doStartTag();
		assertEquals(TagSupport.SKIP_BODY, rv);

		HtmlTag html = ul(
				li(a("&larr; Older").href("#")).cssClass("previous"),
				li(a("Newer &rarr;").href("#")).cssClass("next")
				).cssClass("pager");
		
		String output = renderTag();
		assertEquals(html.build(), output);
	}
}
