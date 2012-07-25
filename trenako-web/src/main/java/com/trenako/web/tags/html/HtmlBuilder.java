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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * It represents a HTML tags builder.
 * @author Carlo Micieli
 *
 */
public class HtmlBuilder {
	
	/**
	 * Converts a {@code List} of tags to an array.
	 * @param list the tags list
	 * @return a tags array
	 */
	public static HtmlTag[] tags(List<HtmlTag> list) {
		return list.toArray(new HtmlTag[list.size()]);
	}
	
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
	public static ul ul(HtmlTag... items) {
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
	 * A document section ({@code <span>}).
	 * @param body the tag inner text
	 * @return a tag
	 */
	public static span span(String body) {
		return (HtmlBuilder.span) new span(body);
	}
	
	/**
	 * A document section ({@code <span>}).
	 * @param innerTags the list of inner tags
	 * @return a tag
	 */
	public static span span(HtmlTag... innerTags) {
		return (HtmlBuilder.span) new span(innerTags);
	}

	/**
	 * A plain text content.
	 * @param body the text to be rendered
	 * @return a tag
	 */
	public static plain plain(String body) {
		return (HtmlBuilder.plain) new plain(body);
	}
	
	/**
	 * A list item ({@code <li>}).
	 * @param body the item inner text
	 * @return a tag
	 */
	public static li li(String body) {
		return (HtmlBuilder.li) new li(body);
	}

	/**
	 * A list item ({@code <li>}).
	 * @param innerTags the list of inner tags
	 * @return a tag
	 */
	public static li li(HtmlTag... innerTags) {
		return (HtmlBuilder.li) new li(innerTags);
	}
	
	/**
	 * A header ({@code <h1>}).
	 * @param body the item inner text
	 * @return a tag
	 */
	public static h1 h1(String body) {
		return (HtmlBuilder.h1) new h1(body);
	}
	
	/**
	 * A header ({@code <h2>}).
	 * @param body the item inner text
	 * @return a tag
	 */
	public static h2 h2(String body) {
		return (HtmlBuilder.h2) new h2(body);
	}
	
	/**
	 * A header ({@code <h3>}).
	 * @param body the item inner text
	 * @return a tag
	 */
	public static h3 h3(String body) {
		return (HtmlBuilder.h3) new h3(body);
	}
	
	/**
	 * A HTML snippet; not included in any tag.
	 * @param innerTags the list of inner tags
	 * @return a tag
	 */
	public static snippet snippet(HtmlTag... innerTags) {
		return (HtmlBuilder.snippet) new snippet(innerTags);
	}
	
	public static class ul extends HtmlTag {
		public ul(HtmlTag... tags) {
			super("ul", TagType.INNER_TAGS);
			setInnerTags(tags);
		}
		
		public ul cssClass(String value) {
			addAttribute("class", value);
			return this;
		}
	}
	
	public static class plain extends HtmlTag {
		public plain(String body) {
			super("", TagType.TEXT_BODY);
			setBody(body);
		}
	}
	
	public static class div extends HtmlTag {
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
	
	public static class h1 extends HtmlTag {
		public h1(String body) {
			super("h1", TagType.TEXT_BODY);
			setBody(body);
		}
	}
	
	public static class h2 extends HtmlTag {
		public h2(String body) {
			super("h2", TagType.TEXT_BODY);
			setBody(body);
		}
	}
	
	public static class h3 extends HtmlTag {
		public h3(String body) {
			super("h3", TagType.TEXT_BODY);
			setBody(body);
		}
	}

	public static class span extends HtmlTag {
		public span(String body) {
			super("span", TagType.TEXT_BODY);
			setBody(body);
		}
		
		public span(HtmlTag... tags) {
			super("span", TagType.INNER_TAGS);
			setInnerTags(tags);
		}
		
		public span cssClass(String value) {
			addAttribute("class", value);
			return this;
		}
	}
	
	public static class li extends HtmlTag {
		public li(String body) {
			super("li", TagType.TEXT_BODY);
			setBody(body);
		}

		public li(HtmlTag... tags) {
			super("li", TagType.INNER_TAGS);
			setInnerTags(tags);
		}
		
		public li cssClass(String value) {
			addAttribute("class", value);
			return this;
		}
	}
	
	public static class a extends HtmlTag {
		public a(String body) {
			super("a", TagType.TEXT_BODY);
			setBody(body);
		}

		public a href(String value) {
			addAttribute("href", value);
			return this;
		}

		public a href(String context, String path) {		
			StringBuilder url = new StringBuilder()
				.append(context)
				.append(path);
			addAttribute("href", url.toString());
			return this;
		}
		
		public a href(HttpServletRequest request, String path) {		
			StringBuilder url = new StringBuilder()
				.append(request.getContextPath())
				.append(path);
			addAttribute("href", url.toString());
			return this;
		}	
		
		public a cssClass(String value) {
			addAttribute("class", value);
			return this;
		}
		
		public a title(String value) {
			addAttribute("title", value);
			return this;
		}
	}
	
	public static class snippet extends HtmlTag {
		public snippet(HtmlTag... tags) {
			super("", TagType.INNER_TAGS);
			setInnerTags(tags);
		}
	}
}
