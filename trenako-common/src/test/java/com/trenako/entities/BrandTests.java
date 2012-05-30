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
package com.trenako.entities;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class BrandTests {

	@Test
	public void equalsShouldFalseForDifferentBrands() {
		Brand x = new Brand("AAAA");
		Brand y = new Brand("BBBB");
		
		assertFalse(x.equals(y));
	}
	
	@Test
	public void equalsShouldTrueForEqualBrands() {
		Brand x = new Brand("AAAA");
		Brand y = new Brand("AAAA");
		
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldProduceTheSameHashCodeForTwoEgualsBrands() {
		Brand x = new Brand.Builder("AAAA")
			.description("Desc")
			.emailAddress("mail@mail.com")
			.website("http://localhost")
			.industrial(true)
			.build();
		
		Brand y = new Brand.Builder("AAAA")
			.description("Desc")
			.emailAddress("mail@mail.com")
			.website("http://localhost")
			.industrial(true)
			.build();	
		
		assertTrue(x.equals(y));
		assertEquals(x.hashCode(), y.hashCode());		
	}

	@Test
	public void shouldBuildNewBrands() {
		Brand b = new Brand.Builder("ACME")
			.website("http://localhost")
			.emailAddress("mail@mail.com")
			.description("Description")
			.industrial(true)
			.build();
			
		assertEquals("ACME", b.getName());
		assertEquals("http://localhost", b.getWebsite());
		assertEquals("mail@mail.com", b.getEmailAddress());
		assertEquals("Description", b.getDescription());
		assertEquals(true, b.isIndustrial());
	}

}
