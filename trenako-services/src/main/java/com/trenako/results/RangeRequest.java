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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * 
 * @author Carlo Micieli
 *
 */
public interface RangeRequest {

	/**
	 * The name for the sort order as used for the query parameters.
	 */
	public final static String ORDER_NAME = "order"; 
	
	/**
	 * The default {@code Sort} for a range request.
	 */
	public final static Sort DEFAULT_SORT = new Sort(Direction.DESC, "lastModified");
	
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
	 * Returns the first {@code Sort} set for the current range.
	 * @return the {@code Sort}
	 */
	Order getFirstOrder();

	/**
	 * Returns the sorting parameters.
	 * @return the sorting
	 */
	Sort getSort();

	/**
	 * Returns the page size.
	 * @return the size
	 */
	int getCount();

	/**
	 * 
	 * @return
	 */
	RangeRequest immutableRange();

}