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
import java.util.Collections;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockReviewsTests {
	private RollingStock rs = new RollingStock.Builder(acme(), "123456")
		.scale(scaleH0())
		.railway(db())
		.build();
	
	private RollingStockReviews rsReviews = new RollingStockReviews(rs);

	private Account alice = new Account.Builder("alice@mail.com")
		.displayName("Alice")
		.build();
	
	private Account bob = new Account.Builder("bob@mail.com")
		.displayName("Bob")
		.build();
	
	@Test
	public void shouldProduceReviewsSlug() {
		assertEquals("acme-123456", rsReviews.getSlug());
	}
	
	@Test
	public void shouldProduceStringRepresentations() {
		assertEquals("review{rs: acme-123456, no of review(s): 0, total rating: 0}", 
				rsReviews.toString());
	}
	
	@Test
	public void shouldCalculateNumberOfReviewsAndRatingForEmptyReview() {
		assertEquals(0, rsReviews.getNumberOfReviews());
		assertEquals(0, rsReviews.getTotalRating());
		assertEquals(Collections.emptyList(), rsReviews.getItems());
	}
	
	@Test
	public void shouldFillRollingStockAndReviewAuthor() {
		Review review1 = new Review(alice, "Review1 title", "Review1 content", 5, null);

		rsReviews.setItems(Arrays.asList(review1));

		assertEquals("{slug: acme-123456, label: ACME 123456}", rsReviews.getRollingStock().toCompleteString());
		assertEquals("alice", rsReviews.getItems().get(0).getAuthor().toString());
	}
	
	@Test
	public void shouldCalculateRollingStockReviewsRating() {
		Review review1 = new Review(alice, "Review1 title", "Review1 content", 5, null);
		Review review2 = new Review(bob, "Review2 title", "Review2 content", 4, null);
		
		rsReviews.setItems(Arrays.asList(review1, review2));
		rsReviews.setNumberOfReviews(2);
		rsReviews.setTotalRating(9);
		
		assertEquals(2, rsReviews.getItems().size());
		assertEquals(2, rsReviews.getNumberOfReviews());
		assertEquals(9, rsReviews.getTotalRating());
		assertEquals(BigDecimal.valueOf(4.5), rsReviews.getRating());
	}
	
	@Test
	public void shouldCheckWhetherTwoRollingStockReviewListsAreEquals() {
		RollingStockReviews x = new RollingStockReviews(rs, 3, 12);
		RollingStockReviews y = new RollingStockReviews(rs, 3, 12);
		assertTrue("Rolling stocks are different", x.equals(x));
		assertTrue("Rolling stocks are different", x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoRollingStockReviewListsAreDifferent() {
		RollingStockReviews x = new RollingStockReviews(rs, 3, 12);
		RollingStockReviews y = new RollingStockReviews(rs, 1, 10);
		assertFalse("Rolling stocks are equals", x.equals(y));
	}
}
