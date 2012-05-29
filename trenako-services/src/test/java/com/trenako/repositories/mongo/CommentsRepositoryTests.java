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
import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.CommentsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentsRepositoryTests {

	@Mock MongoTemplate mongo;
	public CommentsRepository repo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		repo = new CommentsRepositoryImpl(mongo);
	}

	@Test
	public void shouldFindCommentsById() {
		ObjectId id = new ObjectId();
		Comment value = newComment();
		
		when(mongo.findById(eq(id), eq(Comment.class))).thenReturn(value);
		
		Comment c = repo.findById(id);
		
		assertNotNull(c);
		verify(mongo, times(1)).findById(eq(id), eq(Comment.class));
	}

	@Test
	public void shouldFindCommentsByAuthorAccount() {
		Account author = new Account("", "", "author");
		List<Comment> value = 
				Arrays.asList(newComment(), newComment(), newComment());
		when(mongo.find(isA(Query.class), eq(Comment.class))).thenReturn(value);
		
		List<Comment> results = (List<Comment>) repo.findByAuthor(author);
		
		assertEquals(3, results.size());
		verify(mongo, times(1)).find(isA(Query.class), eq(Comment.class));
	}

	@Test
	public void shouldFindCommentsByAuthorString() {
		List<Comment> value = 
				Arrays.asList(newComment(), newComment(), newComment());
		when(mongo.find(isA(Query.class), eq(Comment.class))).thenReturn(value);
		
		List<Comment> results = (List<Comment>) repo.findByAuthor("author");
		
		assertEquals(3, results.size());
		verify(mongo, times(1)).find(isA(Query.class), eq(Comment.class));
	}

	@Test
	public void shouldFindCommentsByRollingStock() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		List<Comment> value = 
				Arrays.asList(newComment(), newComment(), newComment());
		when(mongo.find(isA(Query.class), eq(Comment.class))).thenReturn(value);
		
		List<Comment> results = (List<Comment>) repo.findByRollingStock(rs);
		
		assertEquals(3, results.size());
		verify(mongo, times(1)).find(isA(Query.class), eq(Comment.class));
	}

	@Test
	public void shouldFindByRollingStockSlug() {
		List<Comment> value = 
				Arrays.asList(newComment(), newComment(), newComment());
		when(mongo.find(isA(Query.class), eq(Comment.class))).thenReturn(value);
		
		List<Comment> results = (List<Comment>) repo.findByRollingStock("rslug");
		
		assertEquals(3, results.size());
		verify(mongo, times(1)).find(isA(Query.class), eq(Comment.class));
	}

	@Test
	public void shouldSaveComments() {
		Comment c = newComment();
		repo.save(c);
		verify(mongo, times(1)).save(eq(c));
	}

	@Test
	public void shouldRemoveComments() {
		Comment c = newComment();
		repo.remove(c);
		verify(mongo, times(1)).remove(eq(c));
	}

	private Comment newComment() {
		Account author = new Account("", "", "Name");
		RollingStock rollingStock = new RollingStock.Builder("ACME", "123456").build();
		final Comment c = new Comment(author, rollingStock, "Comment");
		return c;
	}
}
