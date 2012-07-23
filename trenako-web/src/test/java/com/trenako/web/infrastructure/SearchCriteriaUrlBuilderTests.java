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

import static com.trenako.test.TestDataBuilder.*;
import static com.trenako.web.infrastructure.SearchCriteriaUrlBuilder.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.trenako.criteria.SearchCriteria;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class SearchCriteriaUrlBuilderTests {

	@Test
	public void shouldExtractPropertyValueFromObjects() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(acme()).build();
		
		String url = buildUrlAdding(sc, "scale", scaleH0());
		
		assertEquals("/rs/brand/acme/scale/h0", url);
	}
	
	@Test
	public void shouldBuildUrlsReplacingValues() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(acme()).build();
		
		String url = buildUrlAdding(sc, "brand", roco());
		
		assertEquals("/rs/brand/roco", url);
	}
	
	@Test
	public void shouldBuildUrlsRemovingValues() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(acme())
			.railway(db())
			.build();
		assertEquals("/rs/brand/acme/railway/db", buildUrl(sc));
		
		String url = buildUrlRemoving(sc, "brand");
		
		assertEquals("/rs/railway/db", url);
	}
	
	@Test
	public void shouldBuildUrlsForEmptySearch() {
		SearchCriteria sc = new SearchCriteria();
		
		String url = SearchCriteriaUrlBuilder.buildUrl(sc);
		
		assertEquals("/rs", url);
	}
	
	@Test
	public void shouldBuildBrandUrls() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(acme()).build();
		
		String url = SearchCriteriaUrlBuilder.buildUrl(sc);
		
		assertEquals("/rs/brand/acme", url);
	}

	@Test
	public void shouldBuildBrandAndScaleUrls() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(acme())
			.scale(scaleH0())
			.build();
		
		String url = SearchCriteriaUrlBuilder.buildUrl(sc);
		
		assertEquals("/rs/brand/acme/scale/h0", url);
	}
	
	@Test
	public void shouldBuildBrandAndScaleAndCatUrls() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(acme())
			.scale(scaleH0())
			.cat(dcElectricLocomotives())
			.build();
		
		String url = SearchCriteriaUrlBuilder.buildUrl(sc);
		
		assertEquals("/rs/brand/acme/scale/h0/cat/dc-electric-locomotives", url);
	}
	
	@Test
	public void shouldBuildBrandAndScaleAndCatAndRailwayUrls() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(acme())
			.scale(scaleH0())
			.cat(dcElectricLocomotives())
			.railway(db())
			.build();
		
		String url = SearchCriteriaUrlBuilder.buildUrl(sc);
		
		assertEquals("/rs/brand/acme/scale/h0/cat/dc-electric-locomotives/railway/db", url);
	}
	
	@Test
	public void shouldBuildBrandAndScaleAndCatAndRailwayAndEraUrls() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.brand(acme())
			.scale(scaleH0())
			.cat(dcElectricLocomotives())
			.railway(db())
			.era(eraIII())
			.build();
		
		String url = SearchCriteriaUrlBuilder.buildUrl(sc);
		
		assertEquals("/rs/brand/acme/scale/h0/cat/dc-electric-locomotives/railway/db/era/iii", url);
	}
	
	@Test
	public void shouldBuildPowerMethodAndCategoryUrls() {
		SearchCriteria sc = new SearchCriteria.Builder()
			.powerMethod(ac())
			.category(electricLocomotives())
			.build();
		
		String url = SearchCriteriaUrlBuilder.buildUrl(sc);
		
		assertEquals("/rs/powermethod/ac/category/electric-locomotives", url);
	}
}
