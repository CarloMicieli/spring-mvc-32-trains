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

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;

/**
 * It represents the interface for a range information.
 * @author Carlo Micieli
 *
 */
public class RangeRequest {
		
	private ObjectId sinceId;
	private ObjectId maxId;
	private Sort sort;
	private int count;
	
	/**
	 * Creates an empty {@code RangeRequest}.
	 */
	public RangeRequest() {
	}

	/**
	 * Returns the minimum {@code id} in the result page.
	 * @return the minimum {@code id}
	 */
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
	 * Returns the sorting parameters.
	 * @return the sorting
	 */
	public Sort getSort() {
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
	public int getCount() {
		return count;
	}

	/**
	 * Sets the page size.
	 * @param count the size
	 */
	public void setCount(int count) {
		this.count = count;
	}
}
