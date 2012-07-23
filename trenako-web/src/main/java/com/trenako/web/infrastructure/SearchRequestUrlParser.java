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
package com.trenako.web.infrastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.util.Assert;

import com.trenako.criteria.Criteria;

/**
 * It represents a parser for a servlet path based representation 
 * of {@code SearchRequest} objects.
 * 
 * @author Carlo Micieli
 *
 */
public class SearchRequestUrlParser {

	private static final List<String> SEARCH_CRITERIA_KEYS = (List<String>) Criteria.keys();
	
	/**
	 * Parses the {@code path} string, matching the {@code SearchCriteria} property names.
	 * <p>
	 * This method is able to manage paths with wrong sequences, in this case
	 * the values outside the correct sequence are simply ignored.
	 * </p>
	 * 
	 * @param path the {@code path} string
	 * @return a {@code Map} with the extracted values 
	 */
	public static Map<String, String> parseUrl(String path) {
		Assert.notNull(path, "Path must be not null");
		
		Map<String, String> values = new HashMap<String, String>();
		Stack<String> stack = new Stack<String>();
		String[] tokens = path.split("/");
		for (String tk : tokens) {
			if (SEARCH_CRITERIA_KEYS.contains(tk)) {
				if (!stack.isEmpty()) {
					// a different key name was found, but no value was provided
					// (ie /key1/key2/value2)
					stack.pop();
				}
				stack.push(tk);
			}
			else {
				if (!stack.isEmpty()) {
					// match this value with the key name in the stack
					values.put(stack.pop(), tk);					
				}
			}
		}
		
		return values;
	}
}
