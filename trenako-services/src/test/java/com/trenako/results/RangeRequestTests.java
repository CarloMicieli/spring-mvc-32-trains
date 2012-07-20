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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.trenako.AppGlobals;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RangeRequestTests {
	RangeRequest range = new RangeRequestImpl();
	
	@Test
	public void shouldSetOnlyValidCountValues() {
		((RangeRequestImpl)range).setCount(1000);
		assertEquals(AppGlobals.MAX_RESULT_SET_SIZE, range.getCount());		
	
		((RangeRequestImpl)range).setCount(0);
		assertEquals(10, range.getCount());		
		
		((RangeRequestImpl)range).setCount(-10);
		assertEquals(10, range.getCount());	
	}

	@Test
	public void shouldReturnTheDefaultSort() {
		assertEquals(RangeRequestImpl.DEFAULT_SORT, range.getSort());
	}
	
	@Test
	public void shouldReturnTheFirstOrder() {
		((RangeRequestImpl)range).setSort(new Sort(Direction.ASC, "name"));
		assertEquals("name", range.getFirstOrder().getProperty());
		assertEquals(Direction.ASC, range.getFirstOrder().getDirection());
	}
	
	@Test
	public void shouldCreateImmutableObjects() {
		RangeRequest immutable = range.immutableRange();
		assertNotNull(immutable);
		assertTrue(immutable instanceof ImmutableRangeRequest);
	}
}
