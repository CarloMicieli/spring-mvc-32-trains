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

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.trenako.test.AbstractValidationTests;

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ReviewValidationTests extends AbstractValidationTests<Review> {
	
	private RollingStock rs = new RollingStock.Builder(acme(), "123456")
		.scale(scaleH0())
		.railway(db())
		.build();
	
	@Before
	public void initValidator() {
		super.init(Review.class);
	}

	@Test
	public void shouldValidateValidReviews() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		
		Review review = new Review(author, rs, "Title", "Review content");
		Map<String, String> errors = validate(review);
		
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidReviews() {
		Review review = new Review();
		Map<String, String> errors = validate(review);
		
		assertEquals(4, errors.size());
		assertEquals("review.author.required", errors.get("author"));
		assertEquals("review.title.required", errors.get("title"));
		assertEquals("review.rollingStock.required", errors.get("rollingStock"));
		assertEquals("review.content.required", errors.get("content"));
	}
	
	@Test
	public void shouldValidateRatings() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		
		Review review = new Review(author, rs, "Title", "Review content");
		review.setRating(6);
		
		Map<String, String> errors = validate(review);

		assertEquals(1, errors.size());
		assertEquals("review.rating.range.notmet", errors.get("rating"));
	}
}
