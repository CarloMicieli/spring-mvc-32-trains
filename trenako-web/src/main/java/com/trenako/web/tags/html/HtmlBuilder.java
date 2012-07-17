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

/**
 * It represents a HTML tags builder.
 * @author Carlo Micieli
 *
 */
public class HtmlBuilder {
	/**
	 * An anchor tag ({@code <a>}).
	 * @param body the String body
	 * @return a tag
	 */
	public static a a(String body) {
		return (HtmlBuilder.a) new a(body);
	}
	
	/**
	 * An unordered list ({@code <ul>}).
	 * @param items the list of items in the list
	 * @return a tag
	 */
	public static ul ul(li... items) {
		return (HtmlBuilder.ul) new ul(items);
	}

	/**
	 * A document section ({@code <div>}).
	 * @param body the tag inner text
	 * @return a tag
	 */
	public static div div(String body) {
		return (HtmlBuilder.div) new div(body);
	}
	
	/**
	 * A document section ({@code <div>}).
	 * @param innerTags the list of inner tags
	 * @return a tag
	 */
	public static div div(HtmlTag... innerTags) {
		return (HtmlBuilder.div) new div(innerTags);
	}
	
	/**
	 * A list item ({@code <li>}).
	 * @param body the item inner text
	 * @return a tag
	 */
	public static li li(String body) {
		return (HtmlBuilder.li) new li(body);
	}
	
	static class ul extends HtmlTag {
		public ul(li... tags) {
			super("ul", TagType.INNER_TAGS);
			setInnerTags(tags);
		}
		
		public ul cssClass(String value) {
			addAttribute("class", value);
			return this;
		}
	}
	
	static class div extends HtmlTag {
		public div(String body) {
			super("div", TagType.TEXT_BODY);
			setBody(body);
		}
		
		public div(HtmlTag... tags) {
			super("div", TagType.INNER_TAGS);
			setInnerTags(tags);
		}
		
		public div cssClass(String value) {
			addAttribute("class", value);
			return this;
		}
	}
	
	static class li extends HtmlTag {
		public li(String body) {
			super("li", TagType.TEXT_BODY);
			setBody(body);
		}
		
		public li cssClass(String value) {
			addAttribute("class", value);
			return this;
		}
	}
	
	static class a extends HtmlTag {
		public a(String body) {
			super("a", TagType.TEXT_BODY);
			setBody(body);
		}

		public a href(String value) {
			addAttribute("href", value);
			return this;
		}

		public a title(String value) {
			addAttribute("title", value);
			return this;
		}
	}
}
