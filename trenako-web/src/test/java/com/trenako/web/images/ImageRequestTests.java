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
package com.trenako.web.images;

import static org.junit.Assert.*;

import org.junit.Test;

import com.trenako.entities.Brand;

/**
 *
 * @author Carlo Micieli
 *
 */
public class ImageRequestTests {

	@Test
	public void shouldCreateNewImageRequests() {
		ImageRequest req = ImageRequest.createNew(new Brand("ACME"));
		assertEquals("acme", req.getSlug());
		assertEquals("brand", req.getEntityName());
	}

	@Test
	public void shouldReturnTheCorrectImageNames() {
		ImageRequest req = new ImageRequest("brand", "acme");
		assertEquals("brand_acme", req.getFilename());
		assertEquals("th_brand_acme", req.getThumbFilename());
	}
	
	@Test
	public void shouldFillFileMetadata() {
		ImageRequest req = new ImageRequest("brand", "acme");
		assertEquals("{slug=brand_acme}", req.asMetadata(false).toString());
		assertEquals("{slug=th_brand_acme}", req.asMetadata(true).toString());
	}
	
	@Test
	public void shouldCheckWhetherTwoRequestsAreEquals() {
		ImageRequest x = new ImageRequest("entity", "slug");
		ImageRequest y = new ImageRequest("entity", "slug");
		assertTrue("Image requests are different", x.equals(x));
		assertTrue("Image requests are different", x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoRequestsAreDifferent() {
		ImageRequest x = new ImageRequest("entity", "slug");
		ImageRequest y = new ImageRequest("entity", "slug-1");
		assertFalse("Image requests are equals", x.equals(y));

		ImageRequest z = new ImageRequest("entity-1", "slug");
		assertFalse("Image requests are equals", x.equals(z));
	}
}