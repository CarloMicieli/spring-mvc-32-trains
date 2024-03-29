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
 *
 * @param <T> the item type
 * @author Carlo Micieli
 */
public interface PaginatedResults<T> {

    /**
     * Returns the {@code SearchCriteria} that produced this result set.
     *
     * @return the search criteria
     */
    SearchCriteria getCriteria();

    /**
     * Returns the {@code RangeRequest} that produced this result set.
     *
     * @return the range request
     */
    SearchRange getRange();

    /**
     * Indicates whether the result set has a previous page.
     *
     * @return {@code true} if the previous page exists; {@code false} otherwise
     */
    boolean hasPreviousPage();

    /**
     * Indicates whether the result set has a next page.
     *
     * @return {@code true} if the next page exists; {@code false} otherwise
     */
    boolean hasNextPage();

    /**
     * Returns the items for the current result set page.
     *
     * @return the items
     */
    Iterable<T> getItems();

    /**
     * Checks whether the results set is empty.
     *
     * @return {@code true} if empty; {@code false} otherwise
     */
    boolean isEmpty();
}
