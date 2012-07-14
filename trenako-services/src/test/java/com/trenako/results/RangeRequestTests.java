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

import static org.junit.Assert.*;

import org.junit.Test;

import com.trenako.AppGlobals;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RangeRequestTests {

	@Test
	public void shouldSanitizeRangeInputs() {
		RangeRequest range = new RangeRequest();
		
		range.setCount(1000);
		range.sanitizeInput();
		assertEquals(AppGlobals.MAX_RESULT_SET_SIZE, range.getCount());		
	
		range.setCount(0);
		range.sanitizeInput();
		assertEquals(10, range.getCount());		
		
		range.setCount(-10);
		range.sanitizeInput();
		assertEquals(10, range.getCount());	
	}

	@Test
	public void shouldReturnTheDefaultSort() {
		RangeRequest range = new RangeRequest();
		assertEquals(RangeRequest.DEFAULT_SORT, range.getSort());
	}
	
}
