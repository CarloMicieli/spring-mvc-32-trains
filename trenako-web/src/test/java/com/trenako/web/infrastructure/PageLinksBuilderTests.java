/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.web.infrastructure;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PageLinksBuilderTests {

	@Test
	public void previousAndNextLinksAreNotShownForOnePageResult() {
		int page = 1;
		long nrOfElements = 2;

		List<Object> movies = Arrays.asList(new Object(), new Object());
		PageLinksBuilder<Object> pr = new PageLinksBuilder<Object>(movies, 
				nrOfElements, 
				page);
		assertFalse(pr.showNextLink());
		assertFalse(pr.showPreviousLink());
	}

	@Test
	public void showNextLink() {

		PageLinksBuilder<Object> pr = new PageLinksBuilder<Object>(new ArrayList<Object>(), 
				16, 
				1,
				2);
		assertFalse(pr.showPreviousLink());
		assertTrue(pr.showNextLink());
	}

	@Test
	public void showPreviousLink() {

		PageLinksBuilder<Object> pr = new PageLinksBuilder<Object>(new ArrayList<Object>(), 
				20, 
				2,
				10);
		assertTrue(pr.showPreviousLink());
		assertFalse(pr.showNextLink());
	}

	@Test
	public void calculatingTheNumberOfPages() {
		PageLinksBuilder<Object> pr = new PageLinksBuilder<Object>(null, 100, 0, 25);
		assertEquals(4, pr.getPageCount());

		PageLinksBuilder<Object> pr2 = new PageLinksBuilder<Object>(null, 110, 0, 25);
		assertEquals(5, pr2.getPageCount());
	}

	@Test(expected = IllegalArgumentException.class)
	public void invalidPageSizeThrowsAnException() {
		int pageSize = 0;
		new PageLinksBuilder<Object>(null, 100, 0, pageSize);
	}

	@Test
	public void pageLinksForOnePage() {
		int page = 1;
		long nrOfElements = 2;
		int pageSize = 25;

		List<Object> movies = Arrays.asList(new Object(), new Object());

		PageLinksBuilder<Object> pr = new PageLinksBuilder<Object>(movies, 
				nrOfElements, 
				page, 
				pageSize);

		assertEquals(1, pr.getFirstPageLink());
		assertEquals(1, pr.getLastPageLink());

		List<Integer> links = pr.getPageLinks();
		assertNotNull(links);
		assertEquals(1, links.size());

		List<Integer> expected = Arrays.asList(1);
		assertArrayEquals(expected.toArray(), links.toArray());
	}

	@Test
	public void paginateResultForTheOnePage() {
		int page = 1;
		long nrOfElements = 25;
		int pageSize = 25;

		PageLinksBuilder<Object> pr = new PageLinksBuilder<Object>(new ArrayList<Object>(), 
				nrOfElements, 
				page, 
				pageSize);

		List<Object> movies = pr.getData();
		assertNotNull("Objects list is empty", movies);
		assertEquals(1, pr.getPage());
		assertEquals(25, pr.getNrOfElements());
		assertEquals(true, pr.isFirstPage());
		assertEquals(true, pr.isLastPage());
	}

	@Test
	public void paginateResultForTheLastPage() {
		int page = 5;
		long nrOfElements = 110;
		int pageSize = 25;

		PageLinksBuilder<Object> pr = new PageLinksBuilder<Object>(new ArrayList<Object>(), 
				nrOfElements, 
				page, 
				pageSize);

		List<Object> movies = pr.getData();
		assertNotNull("Objects list is empty", movies);
		assertEquals(5, pr.getPage());
		assertEquals(110, pr.getNrOfElements());
		assertEquals(false, pr.isFirstPage());
		assertEquals(true, pr.isLastPage());
	}

	@Test
	public void paginateResultForTheMiddlePage() {
		int page = 3;
		long nrOfElements = 110;
		int pageSize = 25;

		PageLinksBuilder<Object> pr = new PageLinksBuilder<Object>(new ArrayList<Object>(), 
				nrOfElements, 
				page, 
				pageSize);

		List<Object> movies = pr.getData();
		assertNotNull("Objects list is empty", movies);
		assertEquals(3, pr.getPage());
		assertEquals(110, pr.getNrOfElements());
		assertEquals(false, pr.isFirstPage());
		assertEquals(false, pr.isLastPage());
	}

	@Test
	public void linksPageNumberForFirst10Pages() {
		int page = 3;
		long nrOfElements = 200;
		int pageSize = 10;

		PageLinksBuilder<Object> pr = new PageLinksBuilder<Object>(new ArrayList<Object>(), 
				nrOfElements, 
				page, 
				pageSize);
		assertEquals(3, pr.getPage());
		assertEquals(20, pr.getPageCount());
		assertEquals(1, pr.getFirstPageLink());
		assertEquals(10, pr.getLastPageLink());
		List<Integer> pageLinks = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		assertEquals(pageLinks, pr.getPageLinks());
	}

	@Test
	public void linksPageNumberForTheMiddlePages() {
		int page = 10;
		long nrOfElements = 200;
		int pageSize = 10;

		PageLinksBuilder<Object> pr = new PageLinksBuilder<Object>(new ArrayList<Object>(), 
				nrOfElements, 
				page, 
				pageSize);
		assertEquals(10, pr.getPage());
		assertEquals(20, pr.getPageCount());
		assertEquals(4, pr.getFirstPageLink());
		assertEquals(13, pr.getLastPageLink());
		List<Integer> pageLinks = Arrays.asList(4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
		assertEquals(pageLinks, pr.getPageLinks());
	}

	@Test
	public void linksPageNumberForLast10Pages() {
		int page = 17;
		long nrOfElements = 200;
		int pageSize = 10;

		PageLinksBuilder<Object> pr = new PageLinksBuilder<Object>(new ArrayList<Object>(), 
				nrOfElements, 
				page, 
				pageSize);
		assertEquals(17, pr.getPage());
		assertEquals(20, pr.getPageCount());
		assertEquals(11, pr.getFirstPageLink());
		assertEquals(20, pr.getLastPageLink());
		List<Integer> pageLinks = Arrays.asList(11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
		assertEquals(pageLinks, pr.getPageLinks());
	}
}