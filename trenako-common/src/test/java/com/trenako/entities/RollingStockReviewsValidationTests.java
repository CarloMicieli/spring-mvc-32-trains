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
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.trenako.test.AbstractValidationTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockReviewsValidationTests 
	extends AbstractValidationTests<RollingStockReviews>{

	private Account author = new Account.Builder("mail@mail.com")
		.displayName("User Name")
		.build();
	private RollingStock rs = new RollingStock.Builder(acme(), "123456")
		.scale(scaleH0())
		.railway(db())
		.build();

	@Before
	public void initValidator() {
		super.init(RollingStockReviews.class);
	}

	@Test
	public void shouldValidateValidRollingStockReviews() {
		RollingStockReviews rsReviews = new RollingStockReviews(rs);
		rsReviews.setItems(Arrays.asList(new Review(author, "Title", "Review content", 1, null)));
		rsReviews.setNumberOfReviews(1);
		
		Map<String, String> errors = validate(rsReviews);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidRollingStockReviews() {
		RollingStockReviews rsReviews = new RollingStockReviews();
		
		Map<String, String> errors = validate(rsReviews);
		assertEquals(2, errors.size());
		assertEquals("reviews.rollingStock.required", errors.get("rollingStock"));
		assertEquals("reviews.numberOfReviews.range.notmet", errors.get("numberOfReviews"));
	}
	
	@Test
	public void shouldValidateRollingStockReviewsList() {
		RollingStockReviews rsReviews = new RollingStockReviews(rs);
		rsReviews.setNumberOfReviews(1);
		rsReviews.setItems(Arrays.asList(
				new Review(author, null, null, -1, null)));
		
		Map<String, String> errors = validate(rsReviews);
		assertEquals(3, errors.size());
		assertEquals("review.title.required", errors.get("items[0].title"));
		assertEquals("review.content.required", errors.get("items[0].content"));
		assertEquals("review.rating.range.notmet", errors.get("items[0].rating"));
	}
}
