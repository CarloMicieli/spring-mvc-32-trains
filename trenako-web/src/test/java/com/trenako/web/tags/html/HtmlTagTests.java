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
package com.trenako.web.tags.html;

import static com.trenako.web.tags.html.HtmlBuilder.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class HtmlTagTests {

	@Test
	public void shouldRenderDivs() {
		String html = "";
		
		html = div().build();
		assertEquals("<div></div>", html);
		
		html = div("text").build();
		assertEquals("<div>text</div>", html);
		
		html = div(a("link").href("http://localhost")).build();
		assertEquals("<div>\n<a href=\"http://localhost\">link</a></div>", html);
	}
	
	@Test
	public void shouldRenderUnorderedLists() {
		String html = "";
		
		html = HtmlBuilder.ul().build();
		assertEquals("<ul></ul>", html);
		
		html = HtmlBuilder.ul().cssClass("style").build();
		assertEquals("<ul class=\"style\"></ul>", html);
		
		html = HtmlBuilder.ul(li("item1"), li("item2")).build();
		assertEquals("<ul>\n<li>item1</li>\n<li>item2</li></ul>", html);
	}
	
	@Test
	public void shouldRenderALink() {
		
		String html = HtmlBuilder.a("link")
			.href("http://localhost")
			.title("title")
			.build();
			
		assertEquals("<a href=\"http://localhost\" title=\"title\">link</a>", html);
	}
}
