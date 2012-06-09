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

import static org.mockito.Mockito.*;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

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
public class ReviewsServiceTests {

	@Mock ReviewsRepository repo;
	ReviewsService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new ReviewsServiceImpl(repo);
	}

	@Test
	public void shouldFindReviewsById() {
		ObjectId id = new ObjectId();
		
		service.findById(id);
		verify(repo, times(1)).findById(eq(id));
	}

	@Test
	public void shouldFindReviewsByAuthor() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		
		service.findByAuthor(author);
		verify(repo, times(1)).findByAuthor(eq(author));
	}

	@Test
	public void shouldFindReviewsByAuthorName() {
		String author = "name";
		service.findByAuthor(author);
		verify(repo, times(1)).findByAuthor(eq(author));
	}

	@Test
	public void shouldFindReviewsByRollingStock() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		service.findByRollingStock(rs);
		verify(repo, times(1)).findByRollingStock(eq(rs));
	}

	@Test
	public void shouldFindReviewsByRollingStockSlug() {
		String rs = "acme-123456";
		service.findByRollingStock(rs);
		verify(repo, times(1)).findByRollingStock(eq(rs));
	}

	@Test
	public void shouldSaveReviews() {
		Review c = newReview();
		service.save(c);
		verify(repo, times(1)).save(eq(c));
	}

	@Test
	public void shouldRemoveReviews() {
		Review c = newReview();
		service.remove(c);
		verify(repo, times(1)).remove(eq(c));
	}

	private Review newReview() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		RollingStock rollingStock = new RollingStock.Builder("ACME", "123456").build();
		final Review c = new Review(author, rollingStock, "Title", "Review");
		return c;
	}
}
