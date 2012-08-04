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

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ReviewTests {
	
	private RollingStockReviews rsReviews = new RollingStockReviews(
			new RollingStock.Builder(acme(), "123456")
				.scale(scaleH0())
				.railway(db())
				.build());

	private Account bob = new Account.Builder("bob@mail.com")
		.displayName("Bob")
		.build();
	private Account alice = new Account.Builder("alice@mail.com")
		.displayName("Alice")
		.build();
	
	@Test
	public void shouldProduceReviewsSlug() {
		assertEquals("acme-123456", rsReviews.getSlug());
	}
	
	@Test
	public void shouldProduceStringRepresentations() {
		assertEquals("review{rs: {label=ACME 123456, slug=acme-123456}, no of review(s): 0, total rating: 0}", 
				rsReviews.toString());
	}
	
	@Test
	public void shouldCalculateNumberOfReviewsAndRatingForEmptyReview() {
		assertEquals(0, rsReviews.getNumberOfReviews());
		assertEquals(0, rsReviews.getTotalRating());
		assertEquals(null, rsReviews.getReviews());
	}
	
	@Test
	public void shouldFillRollingStockAndReviewAuthor() {
		Review review1 = new Review(alice, "Review1 title", "Review1 content", 5);

		rsReviews.setReviews(Arrays.asList(review1));

		assertEquals("{label=ACME 123456, slug=acme-123456}", rsReviews.getRollingStock().toString());
		assertEquals("alice", rsReviews.getReviews().get(0).getAuthor().toString());
		
	}
	
	@Test
	public void shouldCalculateRollingStockRating() {
		Review review1 = new Review(alice, "Review1 title", "Review1 content", 5);
		Review review2 = new Review(bob, "Review2 title", "Review2 content", 4);
		
		rsReviews.setReviews(Arrays.asList(review1, review2));
		rsReviews.setNumberOfReviews(2);
		rsReviews.setTotalRating(9);
		
		assertEquals(2, rsReviews.getReviews().size());
		assertEquals(2, rsReviews.getNumberOfReviews());
		assertEquals(9, rsReviews.getTotalRating());
		assertEquals(BigDecimal.valueOf(4.5), rsReviews.getRating());
	}

	@Test
	public void shouldCheckWheterTwoReviewsAreEquals() {
		Review x = new Review(alice, "Title", "Review content", 1);
		Review y = new Review(alice, "Title", "Review content", 1);
		
		assertTrue(x.equals(x));
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldCheckWheterTwoReviewsAreDifferent() {
		Review x = new Review(alice, "Title", "Review content 1", 1);
		Review y = new Review(bob, "Title", "Review content 2", 2);
		
		assertFalse(x.equals(y));
		
		Review z = new Review(alice, "Title", "Review content 1", 2);
		assertFalse(x.equals(z));
	}

	@Test
	public void shouldReturnsAReviewSummary() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		
		Review r1 = new Review(author, "Title", "Review content", 5);
		assertEquals("Review content", r1.getSummary());
		
		String content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "+
				"Proin nisl erat, mattis commodo iaculis a, hendrerit at turpis. Fusce odio dui, "+
				"feugiat sit amet placerat vitae, hendrerit non ipsum cras amet.";
		Review r2 = new Review(author, "Title", content, 5);
		assertEquals(150 + "..".length(), r2.getSummary().length());
		assertTrue(r2.getSummary().endsWith(".."));
	}
}
