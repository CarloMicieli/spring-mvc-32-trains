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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ReviewTests {

	@Test
	public void shouldFillAuthorAndRollingStockSlugs() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		
		Review r = new Review(author, rs, "Comment content");
		
		assertEquals("user-name", r.getAuthorName());
		assertEquals("acme-123456", r.getRsSlug());
	}

	@Test
	public void shouldReturnsTrueIfTwoReviewsAreEquals() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();

		Review x = new Review(author, rs, "Review content");
		Review y = new Review(author, rs, "Review content");
		
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldReturnsFalseIfTwoReviewsAreDifferent() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();

		Review x = new Review(author, rs, "Review content 1");
		Review y = new Review(author, rs, "Review content 2");
		
		assertFalse(x.equals(y));
	}
}
