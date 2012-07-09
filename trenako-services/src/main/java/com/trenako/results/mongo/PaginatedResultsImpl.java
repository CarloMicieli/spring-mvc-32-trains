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

import com.trenako.results.PaginatedResults;

/**
 * 
 * @author Carlo Micieli
 *
 * @param <T>
 */
public class PaginatedResultsImpl<T> implements PaginatedResults<T, ObjectId> {

	private final List<T> results;
	
	public PaginatedResultsImpl(List<T> results) {
		this.results = results;
	}
	
	@Override
	public ObjectId getSinceId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ObjectId getMaxId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getTotalSize() {
		return 0;
	}

	@Override
	public int getPageSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasPreviousPage() {
		return true;
	}

	@Override
	public boolean hasNextPage() {
		return true;
	}

	@Override
	public Iterable<T> getItems() {
		return results;
	}

}
