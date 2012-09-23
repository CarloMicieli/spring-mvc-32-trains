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
package com.trenako.results;

import java.util.Iterator;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.trenako.AppGlobals;

/**
 * It represents the range request for rolling stock searches.
 * @author Carlo Micieli
 *
 */
public class RangeRequest {
	
	public enum RangeTypes {
		STRINGS,
		NUMBERS,
		DATES
	};
		
	/**
	 * The {@code since} query parameter name.
	 */
	public final static String SINCE_NAME = "since"; 
	
	/**
	 * The {@code max} query parameter name.
	 */
	public final static String MAX_NAME = "max"; 
	
	/**
	 * The {@code sort} query parameter name.
	 */
	public final static String SORT_NAME = "sort"; 
	
	/**
	 * The {@code sort direction} query parameter name.
	 */
	public final static String ORDER_NAME = "dir"; 
	
	/**
	 * The {@code page size} query parameter name.
	 */
	public final static String SIZE_NAME = "size"; 
	
	/**
	 * The default {@code Sort} for a range request.
	 */
	public final static Sort DEFAULT_SORT = new Sort(Direction.DESC, "lastModified");
	
	private Object since;
	private Object max;
	private Sort sort;
	private int size;
	
	/**
	 * Creates an empty {@code RangeRequest}.
	 */
	public RangeRequest() {
	}

	/**
	 * Creates an {@code RangeRequest}.
	 * @param sort the sort
	 * @param size the max number of items
	 * @param since
	 * @param max
	 */
	public RangeRequest(Sort sort, int size, Object since, Object max) {
		super();
		this.sort = sort;
		this.size = size;
		this.since = since;
		this.max = max;
	}
	
	public RangeTypes getRangeType() {
		if (getSortProperty().equals("lastModified")) return RangeTypes.DATES;
		return RangeTypes.STRINGS;
	}

	/**
	 * Returns the minimum {@code id} in the result page.
	 * @return the minimum {@code id}
	 */
	public Object getSince() {
		return since;
	}
	
	/**
	 * Sets the minimum {@code id} in the result page.
	 * @param sinceId the minimum {@code id}
	 */
	public void setSince(Object sinceId) {
		this.since = sinceId;
	}

	/**
	 * Returns the maximum {@code id} in the result page.
	 * @return the maximum {@code id}
	 */
	public Object getMax() {
		return max;
	}

	/**
	 * Sets the maximum {@code id} in the result page.
	 * @param maxId the maximum {@code id}
	 */
	public void setMax(Object maxId) {
		this.max = maxId;
	}

	/**
	 * Returns the first {@code Sort} set for the current range.
	 * @return the {@code Sort}
	 */
	public Order getFirstOrder() {
		Iterator<Order> it = getSort().iterator();
		while (it.hasNext()) {
			return it.next();
		}
		
		return null;
	}
	
	/**
	 * Returns the property name used to sort the results.
	 * @return the property name for sorting
	 */
	public String getSortProperty() {
		return getFirstOrder().getProperty();
	}
	
	/**
	 * Returns the sorting parameters.
	 * @return the sorting
	 */
	public Sort getSort() {
		if (sort == null) return DEFAULT_SORT;
		return sort;
	}

	/**
	 * Sets the sorting parameters.
	 * @param sort the sorting
	 */
	public void setSort(Sort sort) {
		this.sort = sort;
	}

	/**
	 * Returns the page size.
	 * @return the size
	 */
	public int getSize() {
		return validateCount(size, AppGlobals.PAGE_SIZE);
	}

	/**
	 * Sets the page size.
	 * @param size the size
	 */
	public void setSize(int size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("max=")
			.append(getMax())
			.append(",since=")
			.append(getSince())
			.append(",size=")
			.append(getSize())
			.append(",sort=")
			.append(getFirstOrder()).toString();
	}
	
	private static int validateCount(int value, int defaultValue) {
		if (value > AppGlobals.MAX_RESULT_SET_SIZE) {
			return AppGlobals.MAX_RESULT_SET_SIZE;
		}
		
		if (value <= 0) {
			return defaultValue;
		}
		
		return value;
	}
}
