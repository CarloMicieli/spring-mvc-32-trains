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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.trenako.SearchCriteria;

/**
 * It represents a Url builder for {@code SearchCriteria}.
 * @author Carlo Micieli
 *
 */
public class SearchCriteriaUrlBuilder {
	
	private SearchCriteriaUrlBuilder() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Builds the context path for the provided {@code SearchCriteria}.
	 * 
	 * @param sc the {@code SearchCriteria}
	 * @return the context path
	 */
	public static String buildUrl(SearchCriteria sc) {
		return buildInternal(sc, null, null, null);
	}
		
	public static String buildUrlAdding(SearchCriteria sc, String criteriaName, Object obj) {
		return buildInternal(sc, criteriaName, obj, null);
	}
	
	public static String buildUrlRemoving(SearchCriteria sc, String criteriaName) {
		return buildInternal(sc, null, null, criteriaName);
	}
	
	private static String extractValue(Object obj) {
		String val = "";
		if (obj.getClass().equals(String.class)) {
			// don't use reflection for strings
			return obj.toString();
		}
		else if (obj.getClass().isEnum()) {
			val = safeGetProperty(obj, "label");
		}
		else {
			val = safeGetProperty(obj, "slug");
		}
		
		return val;
	}
	
	private static String safeGetProperty(Object bean, String propertyName) {
		String val = null;
		try {
			val = BeanUtils.getProperty(bean, propertyName);
		} catch (Exception e) {
			val = bean.toString();
		}
		
		return val;
	}
		
	private static String buildInternal(SearchCriteria sc,
			String addedCriteriaName,
			Object addedObj,
			String removedCriteriaName) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("/rs");
		
		String newValue = "";
		for (String criteria : SearchCriteria.KEYS) {
			// added/replace a criteria with the provided value
			if (addedCriteriaName != null && addedCriteriaName.equals(criteria)) {
				newValue = extractValue(addedObj);
				append(sb, addedCriteriaName, newValue);
			}
			// remove the criteria
			else if (removedCriteriaName != null && removedCriteriaName.equals(criteria)) {
				continue;
			}
			else {
				append(sc, sb, criteria);
			}
		}
			
		return sb.toString();
	}
	
	private static void append(SearchCriteria sc, StringBuilder sb, String criteriaName) {
		Pair<String, String> criteria = sc.get(criteriaName);
		if (criteria == null) return;
		
		sb.append("/")
			.append(criteriaName)
			.append("/")
			.append(criteria.getKey());
	}
	
	private static void append(StringBuilder sb, String criteriaName, String criteriaValue) {
		sb.append("/")
			.append(criteriaName)
			.append("/")
			.append(criteriaValue);
	}
}
