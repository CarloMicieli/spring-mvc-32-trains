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

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.trenako.AppGlobals;

/**
 * It represents an immutable search range for rolling stocks.
 * @author Carlo Micieli
 *
 */
public final class SearchRange {

	private final ObjectId sinceId;
	private final ObjectId maxId;
	private final Sort sort;
	private final int pageSize;
	
	/**
	 * Creates a new {@code SearchRange}.
	 * @param pageSize the page size
	 * @param sort the sorting information
	 * @param since the first item in the result set
	 * @param max the last item in the result set
	 */
	public SearchRange(int pageSize, Sort sort, ObjectId since, ObjectId max) {
		this.pageSize = pageSize;
		this.sort = sort;
		this.sinceId = since;
		this.maxId = max;
	}
	
	/**
	 * Returns the minimum {@code id} in the result page.
	 * @return the minimum {@code id}
	 */
	public ObjectId getSince() {
		return sinceId;
	}

	/**
	 * Returns the maximum {@code id} in the result page.
	 * @return the maximum {@code id}
	 */
	public ObjectId getMax() {
		return maxId;
	}

	/**
	 * Returns the sort.
	 * @return the sort
	 */
	public Sort getSort() {
		if (sort == null) {
			return RangeRequest.DEFAULT_SORT;
		}
		return sort;
	}

	/**
	 * Returns the page size.
	 * @return the page size
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * Checks whether the current sort has the default value.
	 * @return {@code true} if sort is the default; {@code false} otherwise
	 */
	public boolean isDefaultSort() {
		if (getSort() == null) return true;
		return getSort().equals(RangeRequest.DEFAULT_SORT);
	}
	
	/**
	 * Checks whether the current page size has the default value.
	 * @return {@code true} if page size is the default; {@code false} otherwise
	 */
	public boolean isDefaultPageSize() {
		return getPageSize() == AppGlobals.PAGE_SIZE;
	}

	/**
	 * Returns the current {@code SearchRange} as a {@code Map}.
	 * <p>
	 * The returned maps contain only not default values.
	 * </p>
	 * 
	 * @return the values {@code Map}
	 */
	public Map<String, Object> asMap() {
		Map<String, Object> params = new TreeMap<String, Object>();
		
		if (getSince() != null) {
			params.put(RangeRequest.SINCE_NAME, getSince());
		}
		
		if (getMax() != null) {
			params.put(RangeRequest.MAX_NAME, getMax());
		}
		
		if (getSort() != null && !isDefaultSort()) {
			params.put(RangeRequest.SORT_NAME, getFirstOrder().getProperty());
			params.put(RangeRequest.ORDER_NAME, getFirstOrder().getDirection());
		}

		if (!isDefaultPageSize()) {
			params.put(RangeRequest.SIZE_NAME, getPageSize());
		}
		
		return Collections.unmodifiableMap(params);
	}
		
	@Override
	public String toString() {
		return new StringBuilder()
			.append("max=")
			.append(getMax())
			.append(",since=")
			.append(getSince())
			.append(",size=")
			.append(getPageSize())
			.append(",size=")
			.append(getSort().toString()).toString();
	}
	
	private Order getFirstOrder() {
		Iterator<Order> it = getSort().iterator();
		while (it.hasNext()) {
			return it.next();
		}

		return null;
	}
}
