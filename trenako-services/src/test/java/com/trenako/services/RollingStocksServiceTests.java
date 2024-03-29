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
import java.util.List;
import java.util.Collections;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.trenako.entities.Account;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockComments;
import com.trenako.entities.RollingStockReviews;
import com.trenako.entities.WishList;
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

	Account loggedUser() {
		return new Account.Builder("mail@mail.com")
			.displayName("bob")
			.build();
	}
	
	private final static RollingStock RS = new RollingStock.Builder(acme(), "123456")
		.railway(fs())
		.scale(scaleH0())
		.build();
	
	@Mock WishListsService wishlistsService;
	
	@Mock RollingStocksRepository repo;
	@Mock CommentsService commentsService;
	@Mock ReviewsService reviewsService;
	
	@Mock Page<RollingStock> results;
	
	RollingStocksService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new RollingStocksServiceImpl(repo, commentsService, reviewsService, wishlistsService);
	}

	@Test
	public void shouldReturnTheLatestModifiedRollingStocks() {
		int numberOfItems = 10;
		
		Pageable pageable = new PageRequest(0, numberOfItems, Direction.DESC, "lastModified");
		
		List<RollingStock> rollingStocks = Collections.emptyList();
		when(results.getContent()).thenReturn(rollingStocks);
		when(repo.findAll(pageable)).thenReturn(results);
				
		Iterable<RollingStock> results = service.findLatestModified(numberOfItems);
		assertNotNull("Rolling stocks result is empty", results);
		
		ArgumentCaptor<Pageable> arg = ArgumentCaptor.forClass(Pageable.class);
		verify(repo, times(1)).findAll(arg.capture());
		assertEquals(0, arg.getValue().getPageNumber());
		assertEquals(numberOfItems, arg.getValue().getPageSize());
		assertEquals("lastModified: DESC", arg.getValue().getSort().toString());
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
		
		RollingStockView view = service.findRollingStockView(slug, null);
		
		assertNull("Rolling stock view is not null", view);
	}
	
	@Test
	public void shouldFillRollingStockViewsForTheProvidedSlug() {
		RollingStockReviews reviews = new RollingStockReviews();
		RollingStockComments comments = new RollingStockComments(RS);
		Iterable<WishList> wishLists = Arrays.asList(new WishList(), new WishList());
		String slug = "acme-123456";

		when(repo.findBySlug(eq(slug))).thenReturn(RS);
		when(commentsService.findByRollingStock(eq(RS))).thenReturn(comments);
		when(reviewsService.findByRollingStock(eq(RS))).thenReturn(reviews);
		when(wishlistsService.findByOwner(eq(loggedUser()))).thenReturn(wishLists);
		
		RollingStockView view = service.findRollingStockView(slug, loggedUser());
		
		assertNotNull("Rolling stock view is null", view);
		assertEquals(RS, view.getRs());
		assertEquals(comments.getItems(), view.getComments());
		assertEquals(reviews, view.getReviews());
		assertEquals(wishLists, view.getWishLists());
	}
	
	@Test
	public void shouldReturnEmptyWishListsWhenNoUserIsLogged() {
		String slug = "acme-123456";
		when(repo.findBySlug(eq(slug))).thenReturn(RS);
		
		RollingStockView view = service.findRollingStockView(slug, null);
		
		assertNull(view.getWishLists());
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
