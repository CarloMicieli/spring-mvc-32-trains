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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 
 * @author Carlo Micieli
 *
 */
public abstract class HtmlTag {
	enum TagType {
		EMPTY,
		TEXT_BODY,
		INNER_TAGS
	};
	
	private final String name;
	private final TagType tagType;
	private SortedMap<String, String> attributes;

	private List<HtmlTag> innerTags;
	private String body;
	
	public HtmlTag(String name, TagType tagType) {
		this.name = name;
		this.tagType = tagType;
	}
	
	@Override
	public String toString() {
		return this.build();
	}
	
	public String build() {
		StringBuilder sb = new StringBuilder();
		if (name == null || name.isEmpty()) {
			sb.append(appendBody());
		}
		else if (tagType == TagType.EMPTY) {
			sb.append("<")
				.append(name)
				.append(appendAttributes())
				.append("/>");
		}
		else {
			sb.append("<")
				.append(name)
				.append(appendAttributes())
				.append(">")
				.append(appendBody())
				.append("</")
				.append(name)
				.append(">");				
		}
	
		return sb.toString();
	}
	
	public HtmlTag setBody(String body) {
		this.body = body;
		return this;
	}

	public HtmlTag setInnerTags(HtmlTag... tags) {
		this.innerTags = Arrays.asList(tags);
		return this;
	}
	
	SortedMap<String, String> getAttributes() {
		if (attributes==null) attributes = new TreeMap<String, String>();
		return attributes;
	}

	void addAttribute(String name, String value) {
		getAttributes().put(name, value);
	}
	
	String appendBody() {
		if (tagType == TagType.TEXT_BODY) {
			return body;
		}
		else if (tagType == TagType.INNER_TAGS) {
			StringBuilder sb = new StringBuilder();
			for (HtmlTag t : innerTags) {
				sb.append("\n" + t.build());
			}
			return sb.toString();
		}
		
		return "";
	}
	
	boolean hasAttributes() {
		return getAttributes().size() > 0;
	}
	
	String appendAttributes() {
		if (!hasAttributes()) return "";
 		
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : attributes.entrySet()) {
			sb.append(" ");
			sb.append(entry.getKey())
				.append("=\"")
				.append(entry.getValue())
				.append("\"");
		}

		return sb.toString();
	}
}
