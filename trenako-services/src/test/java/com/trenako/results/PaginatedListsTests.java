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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class PaginatedListsTests {
	@Test
	public void shouldPaginateListOfValuesForPageInBetween() {
		Page<String> page = PaginatedLists.paginate(values(50), paging(3, 10));

		assertNotNull(page);
		assertTrue("Without a next page", page.hasNextPage());
		assertTrue("Without a previous page", page.hasPreviousPage());
		assertTrue("Without content", page.hasContent());

		assertEquals(50, page.getTotalElements());
		assertEquals(10, page.getContent().size());
		assertEquals("item21", page.getContent().get(0));
		assertEquals("item30", page.getContent().get(9));
	}

	@Test
	public void shouldPaginateListOfValuesForFirstPage() {
		Page<String> page = PaginatedLists.paginate(values(50), paging(1, 10));

		assertNotNull(page);
		assertFalse("With a previous page", page.hasPreviousPage());
		assertTrue("Without a next page", page.hasNextPage());
		assertTrue("Without content", page.hasContent());

		assertEquals(50, page.getTotalElements());
		assertEquals(10, page.getContent().size());
		assertEquals("item1", page.getContent().get(0));
		assertEquals("item10", page.getContent().get(9));
	}

	@Test
	public void shouldPaginateListOfValuesForLastPage() {
		Page<String> page = PaginatedLists.paginate(values(50), paging(5, 10));

		assertNotNull(page);
		assertFalse("With a next page", page.hasNextPage());
		assertTrue("Without a previous page", page.hasPreviousPage());
		assertTrue("Without content", page.hasContent());

		assertEquals(50, page.getTotalElements());
		assertEquals(10, page.getContent().size());
		assertEquals("item41", page.getContent().get(0));
		assertEquals("item50", page.getContent().get(9));
	}

	@Test
	public void shouldPaginateListOfValuesForLastPageWhenIncomplete() {
		Page<String> page = PaginatedLists.paginate(values(47), paging(5, 10));

		assertNotNull(page);
		assertFalse("With a next page", page.hasNextPage());
		assertTrue("Without a previous page", page.hasPreviousPage());
		assertTrue("Without content", page.hasContent());

		assertEquals(47, page.getTotalElements());
		assertEquals(7, page.getContent().size());
		assertEquals("item41", page.getContent().get(0));
		assertEquals("item47", page.getContent().get(6));
	}

	@Test
	public void shouldReturnEmptyPagesWhenPaginationIsOutOfBound() {
		Page<String> page = PaginatedLists.paginate(values(47), paging(8, 10));

		assertNotNull(page);
		assertEquals(0, page.getTotalElements());
		assertEquals(0, page.getContent().size());
	}

	private Pageable paging(int page, int size) {
		return new PageRequest(page - 1, size); 	
	}

	private List<String> values(int numberOfItems) {
		String[] arr = new String[numberOfItems];
		for (int i = 0; i < numberOfItems; i++) {
			arr[i] = "item" + (i+1); 
		}

		return new ArrayList<String>(Arrays.asList(arr));
	} 
}
