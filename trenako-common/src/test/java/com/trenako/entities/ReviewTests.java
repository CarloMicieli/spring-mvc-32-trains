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

	private Account bob = new Account.Builder("bob@mail.com")
		.displayName("Bob")
		.build();
	private Account alice = new Account.Builder("alice@mail.com")
		.displayName("Alice")
		.build();
	
	@Test
	public void shouldCheckWheterTwoReviewsAreEquals() {
		Review x = new Review(alice, "Title", "Review content", 1, null);
		Review y = new Review(alice, "Title", "Review content", 1, null);
		
		assertTrue(x.equals(x));
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldCheckWheterTwoReviewsAreDifferent() {
		Review x = new Review(alice, "Title", "Review content 1", 1, null);
		Review y = new Review(bob, "Title", "Review content 2", 2, null);
		
		assertFalse(x.equals(y));
		
		Review z = new Review(alice, "Title", "Review content 1", 2, null);
		assertFalse(x.equals(z));
	}

	@Test
	public void shouldReturnsAReviewSummary() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		
		Review r1 = new Review(author, "Title", "Review content", 5, null);
		assertEquals("Review content", r1.getSummary());
		
		String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "+
				"Proin nisl erat, mattis commodo iaculis a, hendrerit at turpis. Fusce odio dui, "+
				"feugiat sit amet placerat vitae, hendrerit non ipsum cras amet.";
		Review r2 = new Review(author, "Title", content, 5, null);
		assertEquals(150 + "..".length(), r2.getSummary().length());
		assertTrue(r2.getSummary().endsWith(".."));
	}
}
