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
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockReviews;
import com.trenako.repositories.CommentsRepository;
import com.trenako.repositories.ReviewsRepository;
import com.trenako.repositories.RollingStocksRepository;
import com.trenako.services.RollingStocksServiceImpl;
import com.trenako.services.view.RollingStockView;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class RollingStocksServiceTests {

	private final static RollingStock RS = new RollingStock.Builder(acme(), "123456")
		.railway(fs())
		.scale(scaleH0())
		.build();
	
	@Mock RollingStocksRepository repo;
	@Mock CommentsRepository commentsRepo;
	@Mock ReviewsRepository reviewsRepo;
	RollingStocksService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new RollingStocksServiceImpl(repo, commentsRepo, reviewsRepo);
	}

	@Test
	public void shouldFindRollingStocksById() {
		ObjectId id = new ObjectId();
		service.findById(id);
		verify(repo, times(1)).findOne(eq(id));
	}

	@Test
	public void shouldFindRollingStocksBySlug() {
		String slug = "slug";
		service.findBySlug(slug);
		verify(repo, times(1)).findBySlug(eq(slug));
	}

	@Test
	public void shouldReturnNullViewsWhenRollingStocksWereNotFound() {
		String slug = "not-found";
		when(repo.findBySlug(eq(slug))).thenReturn(null);
		
		RollingStockView view = service.findViewBySlug(slug);
		
		assertNull("Rolling stock view is not null", view);
	}
	
	@Test
	public void shouldFillRollingStockViewsForTheProvidedSlug() {
		RollingStockReviews reviews = new RollingStockReviews();
		Iterable<Comment> comments = Arrays.asList(new Comment(), new Comment());
		String slug = "acme-123456";

		when(repo.findBySlug(eq(slug))).thenReturn(RS);
		when(commentsRepo.findByRollingStock(eq(RS))).thenReturn(comments);
		when(reviewsRepo.findByRollingStock(eq(RS))).thenReturn(reviews);
		
		RollingStockView view = service.findViewBySlug(slug);
		
		assertNotNull("Rolling stock view is null", view);
		assertEquals(RS, view.getRs());
		assertEquals(comments, view.getComments());
		assertEquals(reviews, view.getReviews());
	}

	@Test
	public void shouldCreateNewRollingStocks() {
		RollingStock newRs = new RollingStock.Builder(acme(), "123456")
			.railway(fs())
			.scale(scaleH0())
			.description("Description")
			.build();
	
		service.createNew(newRs);
		
		verify(repo, times(1)).save(eq(newRs));
	}
	
	@Test
	public void shouldSaveRollingStocks() {
		RollingStock newRs = new RollingStock.Builder(acme(), "123456")
			.id(new ObjectId())
			.railway(fs())
			.scale(scaleH0())
			.description("Description")
			.build();
	
		service.save(newRs);
		
		verify(repo, times(1)).save(eq(newRs));
	}

	@Test
	public void shouldRemoveRollingStocks() {
		service.remove(RS);
		verify(repo, times(1)).delete(eq(RS));
	}
}
