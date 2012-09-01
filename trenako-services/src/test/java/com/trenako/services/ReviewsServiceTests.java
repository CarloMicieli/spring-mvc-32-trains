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
package com.trenako.services;

import static com.trenako.test.TestDataBuilder.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockReviews;
import com.trenako.repositories.ReviewsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewsServiceTests {
	
	private RollingStock rollingStock = new RollingStock.Builder(acme(), "123456")
		.railway(fs())
		.scale(scaleH0())
		.build();
	private @Mock ReviewsRepository repo;
	private ReviewsService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new ReviewsServiceImpl(repo);
	}
	
	@Test
	public void shouldFindReviewsBySlug() {
		String slug = "review-slug";
		when(repo.findBySlug(eq(slug))).thenReturn(new RollingStockReviews());
		
		RollingStockReviews reviews = service.findBySlug(slug);
		
		assertNotNull(reviews);
		verify(repo, times(1)).findBySlug(eq(slug));
	}
	
	@Test
	public void shouldFindReviewsByRollingStock() {
		when(repo.findByRollingStock(eq(rollingStock))).thenReturn(new RollingStockReviews());
		
		RollingStockReviews reviews = service.findByRollingStock(rollingStock);
		
		assertNotNull(reviews);
		verify(repo, times(1)).findByRollingStock(eq(rollingStock));
	}
	
	@Test
	public void shouldReturnEmptyReviewsWhenNoReviewWasPostedYet() {
		when(repo.findByRollingStock(eq(rollingStock))).thenReturn(null);
		
		RollingStockReviews reviews = service.findByRollingStock(rollingStock);
		
		assertNotNull("Reviews are null", reviews);
	}
	
	@Test
	public void shouldPostNewRollingStockReviews() {
		Review review = new Review();
		
		service.postReview(rollingStock, review);
		
		verify(repo, times(1)).addReview(eq(rollingStock), eq(review));
	}
	
	@Test
	public void shouldDeleteRollingStockReviews() {
		Review review = new Review();
		
		service.deleteReview(rollingStock, review);
		
		verify(repo, times(1)).removeReview(eq(rollingStock), eq(review));
	}
}
