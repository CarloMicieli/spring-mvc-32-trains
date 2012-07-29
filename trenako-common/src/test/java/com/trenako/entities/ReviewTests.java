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

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ReviewTests {

	private RollingStock rs = new RollingStock.Builder(acme(), "123456")
		.scale(scaleH0())
		.railway(db())
		.build();
		
	@Test
	public void shouldReturnAStringRepresentation() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		Review r = new Review(author, rs, "Title", "Review content");
	
		assertEquals("user-name: acme-123456 (Title)", r.toString());
	}
	
	@Test
	public void shouldFillAuthorAndRollingStockSlugs() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		
		Review r = new Review(author, rs, "Title", "Review content");
		
		assertEquals("user-name", r.getAuthor().getSlug());
		assertEquals("acme-123456", r.getRollingStock().getSlug());
	}

	@Test
	public void shouldReturnsTrueIfTwoReviewsAreEquals() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();

		Review x = new Review(author, rs, "Title", "Review content");
		Review y = new Review(author, rs, "Title", "Review content");
		
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldReturnsFalseIfTwoReviewsAreDifferent() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();

		Review x = new Review(author, rs, "Title", "Review content 1");
		Review y = new Review(author, rs, "Title", "Review content 2");
		
		assertFalse(x.equals(y));
	}
	
	@Test
	public void shouldReturnsAReviewSummary() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		
		Review r1 = new Review(author, rs, "Title", "Review content");
		assertEquals("Review content", r1.getSummary());
		
		String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "+
				"Proin nisl erat, mattis commodo iaculis a, hendrerit at turpis. Fusce odio dui, "+
				"feugiat sit amet placerat vitae, hendrerit non ipsum cras amet.";
		Review r2 = new Review(author, rs, "Title", content);
		assertEquals(150 + "..".length(), r2.getSummary().length());
		assertTrue(r2.getSummary().endsWith(".."));
	}
}
