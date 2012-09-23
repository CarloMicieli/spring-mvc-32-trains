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

import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.RollingStock;

/**
 * The concrete implementation for MongoDB of the {@code PaginatedResults}.
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
	private final SearchRange range;
	private final SearchCriteria criteria;
	
	private final boolean hasNext;
	private final boolean hasPrevious;
	
	/**
	 * Creates a new {@code RollingStockResults}.
	 * @param results the result items
	 * @param range the result range
	 */
	public RollingStockResults(List<RollingStock> results, SearchCriteria criteria, RangeRequest range) {
		
		int size = results.size() > range.getSize()
				? range.getSize() : results.size();
		
		this.results = results.subList(0, size);
		this.criteria = criteria;
		
		if (!isEmpty()) {
			final RollingStock minRs = results.get(0);
			final RollingStock maxRs = results.get(size - 1);
		
			final Object since = safeGetProperty(minRs, range.getSortProperty());
			final Object max = safeGetProperty(maxRs, range.getSortProperty());
			
			this.hasPrevious = range.getSince() != null;
			this.hasNext = results.size() > range.getSize();

			this.range = new SearchRange(range.getSize(), range.getSort(), since, max);
		}
		else {
			this.range = null;
			this.hasPrevious = false;
			this.hasNext = false;
		}
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

	@Override
	public SearchCriteria getCriteria() {
		return criteria;
	}
	
	@Override
	public SearchRange getRange() {
		return range;
	}

	@Override
	public boolean isEmpty() {
		return (results == null || results.size() == 0);
	}
	
	protected Object safeGetProperty(Object bean, String name) {
		try {
			PropertyUtilsBean pb = BeanUtilsBean.getInstance().getPropertyUtils();
			return pb.getProperty(bean, name);
		} catch (Exception e) {
			return null;
		}
	}
}
