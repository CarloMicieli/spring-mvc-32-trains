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

import org.junit.Test;

import com.trenako.SearchCriteria;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class SearchCriteriaUrlBuilderTests {

	@Test
	public void shouldBuildUrlsForEmptySearch() {
		SearchCriteria sc = new SearchCriteria();
		
		String url = SearchCriteriaUrlBuilder.buildUrl(sc);
		
		assertEquals("/rs", url);
	}
	
	@Test
	public void shouldBuildBrandUrls() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand("ACME").build();
		
		String url = SearchCriteriaUrlBuilder.buildUrl(sc);
		
		assertEquals("/rs/brand/ACME", url);
	}

	@Test
	public void shouldBuildBrandAndScaleUrls() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand("ACME")
			.scale("H0")
			.build();
		
		String url = SearchCriteriaUrlBuilder.buildUrl(sc);
		
		assertEquals("/rs/brand/ACME/scale/H0", url);
	}
	
	@Test
	public void shouldBuildBrandAndScaleAndCatUrls() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand("ACME")
			.scale("H0")
			.cat("ac-electric-locomotives")
			.build();
		
		String url = SearchCriteriaUrlBuilder.buildUrl(sc);
		
		assertEquals("/rs/brand/ACME/scale/H0/cat/ac-electric-locomotives", url);
	}
	
	@Test
	public void shouldBuildBrandAndScaleAndCatAndRailwayUrls() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand("ACME")
			.scale("H0")
			.cat("ac-electric-locomotives")
			.railway("DB")
			.build();
		
		String url = SearchCriteriaUrlBuilder.buildUrl(sc);
		
		assertEquals("/rs/brand/ACME/scale/H0/cat/ac-electric-locomotives/railway/DB", url);
	}
	
	@Test
	public void shouldBuildBrandAndScaleAndCatAndRailwayAndEraUrls() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand("ACME")
			.scale("H0")
			.cat("ac-electric-locomotives")
			.railway("DB")
			.era("IV")
			.build();
		
		String url = SearchCriteriaUrlBuilder.buildUrl(sc);
		
		assertEquals("/rs/brand/ACME/scale/H0/cat/ac-electric-locomotives/railway/DB/era/IV", url);
	}
	
	@Test
	public void shouldBuildPowerMethodAndCategoryUrls() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.powerMethod("ac")
			.category("electric-locomotives")
			.build();
		
		String url = SearchCriteriaUrlBuilder.buildUrl(sc);
		
		assertEquals("/rs/powermethod/ac/category/electric-locomotives", url);
	}
}
