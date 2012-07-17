/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.web.infrastructure;

import com.trenako.SearchCriteria;

/**
 * It represents a Url builder for {@code SearchCriteria}.
 * @author Carlo Micieli
 *
 */
public class SearchCriteriaUrlBuilder {
	
	private SearchCriteriaUrlBuilder() {
	}

	/**
	 * Builds the context path for the provided {@code SearchCriteria}.
	 * 
	 * @param sc the {@code SearchCriteria}
	 * @return the context path
	 */
	public static String buildUrl(SearchCriteria sc) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("/rs");
		
		if (sc.hasBrand()) {
			append(sb, "brand", sc.getBrand());
		}
		
		if (sc.hasScale()) {
			append(sb, "scale", sc.getScale());
		}

		if (sc.hasCat()) {
			append(sb, "cat", sc.getCat().toString());
		}
		
		if (sc.hasRailway()) {
			append(sb, "railway", sc.getRailway());
		}
		
		if (sc.hasEra()) {
			append(sb, "era", sc.getEra());
		}
		
		if (sc.hasPowerMethod()) {
			append(sb, "powermethod", sc.getPowerMethod());
		}
		
		if (sc.hasCategory()) {
			append(sb, "category", sc.getCategory());
		}
		
		return sb.toString();
	}
	
	private static void append(StringBuilder sb, String key, String value) {
		sb.append("/");
		sb.append(key);
		sb.append("/");
		sb.append(value);
	}

}
