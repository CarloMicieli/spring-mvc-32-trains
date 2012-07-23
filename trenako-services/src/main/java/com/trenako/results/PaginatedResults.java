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

import com.trenako.criteria.SearchCriteria;

/**
 * It represents the public interface for paginated result sets.
 * <p>
 * Given the performance issues for pagination, the concrete classes that
 * implement this interface will provide a range based pagination (for instance see how 
 * Twitter api implemented pagination).
 * </p>
 * <p>
 * The pagination will not provide the random access to one of the result set pages, but 
 * only the basic {@code previous} and {@code next} are available. This is not usually a 
 * limit for RESTful api, but it can be a limitation for common web applications.
 * </p>
 * <p>
 * The {@link PaginatedResults#getTotalSize()} implementation could require another trip 
 * to the database in order to count the result set total size. 
 * </p>
 *
 * @param <T> the item type
 *
 * @author Carlo Micieli
 */
public interface PaginatedResults<T> {
	
	/**
	 * Returns the search criteria that produced this result set.
	 * @return the search criteria
	 */
	SearchCriteria getCriteria();

	/**
	 * Returns the minimum {@code id} in the result page.
	 * @return the minimum {@code id}
	 */
	ObjectId getSinceId();

	/**
	 * Returns the maximum {@code id} in the result page.
	 * @return the maximum {@code id}
	 */
	ObjectId getMaxId();
	
	/**
	 * Returns the total number of items in the result set.
	 * @return the number of items
	 */
	long getTotalSize();
	
	/**
	 * Returns the page size.
	 * @return the size
	 */
	int getPageSize();
	
	/**
	 * Indicates whether the result set has a previous page.
	 * @return {@code true} if the previous page exists; {@code false} otherwise
	 */
	boolean hasPreviousPage();
	
	/**
	 * Indicates whether the result set has a next page.
	 * @return {@code true} if the next page exists; {@code false} otherwise
	 */	
	boolean hasNextPage();
	
	/**
	 * Returns the items for the current result set page.
	 * @return the items
	 */
	Iterable<T> getItems();
}
