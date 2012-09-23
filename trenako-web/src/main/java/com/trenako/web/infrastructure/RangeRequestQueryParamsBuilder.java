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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.trenako.results.RangeRequest;
import com.trenako.results.SearchRange;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RangeRequestQueryParamsBuilder {

	private RangeRequestQueryParamsBuilder() {
	}
	
	/**
	 * Builds the query parameters for the provided {@code SearchRange}.
	 * @param searchRange the {@code SearchRange}
	 * @return the query parameters
	 */
	public static String buildQueryParamsNext(SearchRange searchRange) {
		try {
			return buildQueryParams(searchRange.asMap(), RangeRequest.SINCE_NAME);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	/**
	 * Builds the query parameters for the provided {@code SearchRange}.
	 * @param searchRange the {@code SearchRange}
	 * @return the query parameters
	 */
	public static String buildQueryParamsPrevious(SearchRange searchRange) {
		try {
			return buildQueryParams(searchRange.asMap(), RangeRequest.MAX_NAME);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	/**
	 * Builds the query parameters for the provided parameters {@code Map}.
	 * @param params the parameters
	 * @param range range id
	 * @return the query parameters
	 * @throws UnsupportedEncodingException 
	 */
	static String buildQueryParams(Map<String, Object> params, String range) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		
		boolean first = true;
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			
			if (RANGE_NAMES.contains(entry.getKey()) && !entry.getKey().equals(range)) {
				continue;
			}
			
			if (first) {
				sb.append("?");
				first = false;
			}
			else {
				sb.append("&");
			}
			
			String val = ""; 
			if (entry.getValue() instanceof Date) {
				val = DateFormatUtils.format((Date) entry.getValue(), DateFormatUtils.ISO_DATETIME_FORMAT.getPattern());
			}
			else {
				val = entry.getValue().toString();
			}
			sb.append(entry.getKey()).append("=").append(URLEncoder.encode(val, "UTF-8"));
		}
		
		return sb.toString();
		
	}
	
	static final List<String> RANGE_NAMES = 
			Collections.unmodifiableList(Arrays.asList(RangeRequest.MAX_NAME, RangeRequest.SINCE_NAME));
}
