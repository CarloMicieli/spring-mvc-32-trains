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
package com.trenako.web.controllers.form;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.trenako.results.RangeRequest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ResultsOptionsForm {
	
	private String sort;
	private String dir;
	private int size;
	
	private static final Iterable<String> RESULT_SORT_CRITERIA = 
			Collections.unmodifiableList(Arrays.asList("lastModified"));
	private static final Iterable<Integer> RESULT_SIZES = 
			Collections.unmodifiableList(Arrays.asList(5, 10, 25, 50, 100));
	
	public ResultsOptionsForm() {
	}
	
	private ResultsOptionsForm(String sort, String dir, int size) {
		this.sort = sort;
		this.dir = dir;
		this.size = size;
	}
	
	public static ResultsOptionsForm buildFor(RangeRequest range) {
		return new ResultsOptionsForm(range.getSortProperty(),
				range.getDirection(),
				range.getSize());
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Iterable<Integer> getResultSizes() {
		return RESULT_SIZES;
	}
	
	public Iterable<String> getResultSortCriteria() {
		return RESULT_SORT_CRITERIA;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof ResultsOptionsForm)) return false;
		
		ResultsOptionsForm other = (ResultsOptionsForm) obj;
		
		return new EqualsBuilder()
			.append(this.size, other.size)
			.append(this.dir, other.dir)
			.append(this.sort, other.sort)
			.isEquals();
	}
}
