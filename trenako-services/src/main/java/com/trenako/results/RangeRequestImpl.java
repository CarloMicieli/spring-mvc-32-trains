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

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.trenako.AppGlobals;

/**
 * It represents the interface for a range information.
 * @author Carlo Micieli
 *
 */
public class RangeRequestImpl implements RangeRequest {
	
	private ObjectId sinceId;
	private ObjectId maxId;
	private Sort sort;
	private int count;
	
	/**
	 * Creates an empty {@code RangeRequest}.
	 */
	public RangeRequestImpl() {
	}

	/**
	 * Returns the minimum {@code id} in the result page.
	 * @return the minimum {@code id}
	 */
	@Override
	public ObjectId getSinceId() {
		return sinceId;
	}

	/**
	 * Sets the minimum {@code id} in the result page.
	 * @param sinceId the minimum {@code id}
	 */
	public void setSinceId(ObjectId sinceId) {
		this.sinceId = sinceId;
	}

	/**
	 * Returns the maximum {@code id} in the result page.
	 * @return the maximum {@code id}
	 */
	@Override
	public ObjectId getMaxId() {
		return maxId;
	}

	/**
	 * Sets the maximum {@code id} in the result page.
	 * @param maxId the maximum {@code id}
	 */
	public void setMaxId(ObjectId maxId) {
		this.maxId = maxId;
	}

	/**
	 * Returns the first {@code Sort} set for the current range.
	 * @return the {@code Sort}
	 */
	@Override
	public Order getFirstOrder() {
		Iterator<Order> it = getSort().iterator();
		while (it.hasNext()) {
			return it.next();
		}
		
		return null;
	}
	
	/**
	 * Returns the sorting parameters.
	 * @return the sorting
	 */
	@Override
	public Sort getSort() {
		if (sort==null) return DEFAULT_SORT;
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
	@Override
	public int getCount() {
		return validateCount(count, 10);
	}

	/**
	 * Sets the page size.
	 * @param count the size
	 */
	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public RangeRequest immutableRange() {
		return new ImmutableRangeRequest(count, sinceId, maxId, sort);
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("maxid=")
			.append(getMaxId())
			.append(",sinceid=")
			.append(getSinceId())
			.append(",count=")
			.append(getCount())
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
