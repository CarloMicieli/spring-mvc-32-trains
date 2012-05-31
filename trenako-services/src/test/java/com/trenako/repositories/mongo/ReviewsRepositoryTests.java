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
package com.trenako.repositories.mongo;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.trenako.entities.Account;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.ReviewsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewsRepositoryTests {

	Class<?> reviews = Review.class;
	
	@Mock MongoTemplate mongo;
	ReviewsRepository repo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repo = new ReviewsRepositoryImpl(mongo);
	}

	@Test
	public void shouldFindReviewsById() {
		ObjectId id = new ObjectId();
		Review value = newReview();
		when(mongo.findById(eq(id), eq(Review.class))).thenReturn(value);
		
		Review review = repo.findById(id);
		
		assertNotNull(review);
		verify(mongo, times(1)).findById(eq(id), eq(Review.class));
	}

	@Test
	public void shouldFindReviewsByAccount() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();

		List<Review> value = 
				Arrays.asList(newReview(), newReview(), newReview());
		when(mongo.find(isA(Query.class), eq(Review.class))).thenReturn(value);
		
		List<Review> results = (List<Review>) repo.findByAuthor(author);
		
		assertEquals(3, results.size());
		verify(mongo, times(1)).find(isA(Query.class), eq(Review.class));
	}

	@Test
	public void shouldFindReviewsByAuthorName() {
		List<Review> value = 
				Arrays.asList(newReview(), newReview(), newReview());
		when(mongo.find(isA(Query.class), eq(Review.class))).thenReturn(value);
		
		List<Review> results = (List<Review>) repo.findByAuthor("author");
		
		assertEquals(3, results.size());
		verify(mongo, times(1)).find(isA(Query.class), eq(Review.class));
	}

	@Test
	public void shouldFindReviewsByRollingStock() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		List<Review> value = 
				Arrays.asList(newReview(), newReview(), newReview());
		when(mongo.find(isA(Query.class), eq(Review.class))).thenReturn(value);
		
		List<Review> results = (List<Review>) repo.findByRollingStock(rs);
		
		assertEquals(3, results.size());
		verify(mongo, times(1)).find(isA(Query.class), eq(Review.class));
	}

	@Test
	public void shouldFindReviewsByRollingStockSlug() {
		List<Review> value = 
				Arrays.asList(newReview(), newReview(), newReview());
		when(mongo.find(isA(Query.class), eq(Review.class))).thenReturn(value);
		
		List<Review> results = (List<Review>) repo.findByRollingStock("rs");
		
		assertEquals(3, results.size());
		verify(mongo, times(1)).find(isA(Query.class), eq(Review.class));
	}

	@Test
	public void shouldSaveReviews() {
		Review r = newReview();
		repo.save(r);
		verify(mongo, times(1)).save(eq(r));
	}

	@Test
	public void shouldRemoveReviews() {
		Review r = newReview();
		repo.remove(r);
		verify(mongo, times(1)).remove(eq(r));
	}
	
	private Review newReview() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		RollingStock rollingStock = new RollingStock.Builder("ACME", "123456").build();
		final Review rev = new Review(author, rollingStock, "Review");
		return rev;
	}

}
