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

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Account;
import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.CommentsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentsServiceTests {

	private RollingStock rollingStock = new RollingStock.Builder(acme(), "123456")
		.railway(fs())
		.scale(scaleH0())
		.build();
	
	@Mock CommentsRepository repo;
	CommentsService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new CommentsServiceImpl(repo);
	}

	@Test
	public void shouldFindCommentsById() {
		ObjectId id = new ObjectId();
		
		service.findById(id);
		verify(repo, times(1)).findById(eq(id));
	}

	@Test
	public void shouldFindCommentsByAuthor() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		
		service.findByAuthor(author);
		verify(repo, times(1)).findByAuthor(eq(author));
	}

	@Test
	public void shouldFindCommentsByAuthorName() {
		String author = "name";
		service.findByAuthor(author);
		verify(repo, times(1)).findByAuthor(eq(author));
	}

	@Test
	public void shouldFindCommentsByRollingStock() {
		service.findByRollingStock(rollingStock);
		verify(repo, times(1)).findByRollingStock(eq(rollingStock));
	}

	@Test
	public void shouldFindCommentsByRollingStockSlug() {
		String rs = "acme-123456";
		service.findByRollingStock(rs);
		verify(repo, times(1)).findByRollingStock(eq(rs));
	}

	@Test
	public void shouldSaveComments() {
		Comment c = newComment();
		service.save(c);
		verify(repo, times(1)).save(eq(c));
	}

	@Test
	public void shouldRemoveComments() {
		Comment c = newComment();
		service.remove(c);
		verify(repo, times(1)).remove(eq(c));
	}

	private Comment newComment() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();

		final Comment c = new Comment(author, rollingStock, "Comment");
		return c;
	}
}
