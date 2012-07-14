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
package com.trenako.results.mongo;

import java.util.List;

import org.bson.types.ObjectId;

import com.trenako.entities.RollingStock;
import com.trenako.results.PaginatedResults;
import com.trenako.results.RangeRequest;

/**
 * The concrete implementation for mongodb of the {@code PaginatedResults}.
 * <p>
 * It is expected the actual result list size will be 
 * {@code RollingStockResults#getPageSize() + 1}; the existence of this element
 * (not shown in the final result) will be used to ensure whether the current
 * result set is the last page or not.
 * </p>
 * <p>
 * Although the objects for this class contain the fields 
 * {@code RollingStockResults#getSinceId()} and {@code RollingStockResults#getMaxId()}
 * they don't contain the values from the {@code RangeRequest} instance used to build the 
 * object. They are filled with the first and the last element in the results set.
 * The clients should use these two fields to implement the pagination.
 * </p>
 * <p>
 * This implementation of {@code PaginatedResults} doesn't provide a meaningful 
 * implementation for the {@code PaginatedResults#getTotalSize()} method.
 * The client classes must not depend on this value.
 * </p>
 * 
 * @author Carlo Micieli
 */
public class RollingStockResults implements PaginatedResults<RollingStock> {

	private final List<RollingStock> results;
	private final RangeRequest range;
	private final ObjectId maxId;
	private final ObjectId sinceId;
	
	private final boolean hasNext;
	private final boolean hasPrevious;
	
	/**
	 * Creates a new {@code RollingStockResults}.
	 * @param results the result items
	 * @param range the result range
	 */
	public RollingStockResults(List<RollingStock> results, RangeRequest range) {
		
		int size = results.size() > range.getCount()
				? range.getCount() : results.size();
		
		this.results = results.subList(0, size);
		this.range = range;

		RollingStock min = results.get(0);
		RollingStock max = results.get(size - 1);
	
		this.sinceId = min.getId();
		this.maxId = max.getId();
		
		this.hasPrevious = range.getSinceId() != null;
		this.hasNext = results.size() > range.getCount();
	}
	
	@Override
	public ObjectId getSinceId() {
		return this.sinceId;
	}

	@Override
	public ObjectId getMaxId() {
		return this.maxId;
	}

	@Override
	public long getTotalSize() {
		return 0;
	}

	@Override
	public int getPageSize() {
		return range.getCount();
	}

	@Override
	public boolean hasPreviousPage() {
		return hasPrevious;
	}

	@Override
	public boolean hasNextPage() {
		return hasNext;
	}

	@Override
	public Iterable<RollingStock> getItems() {
		return results;
	}
}
