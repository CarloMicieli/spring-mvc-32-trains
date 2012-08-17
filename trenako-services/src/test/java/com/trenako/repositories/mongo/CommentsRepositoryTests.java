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

import static com.trenako.test.TestDataBuilder.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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

	private RollingStock rollingStock = new RollingStock.Builder(acme(), "123456")
		.railway(fs())
		.scale(scaleH0())
		.build();
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
	public void shouldFindCommentsByAuthor() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();

		List<Comment> value = Arrays.asList(newComment(), newComment(), newComment());
		when(mongo.find(isA(Query.class), eq(Comment.class))).thenReturn(value);
		
		List<Comment> results = (List<Comment>) repo.findByAuthor(author);
		
		assertEquals(3, results.size());
		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		verify(mongo, times(1)).find(arg.capture(), eq(Comment.class));
		
		assertEquals("{ \"author.slug\" : \"user-name\"}", arg.getValue().getQueryObject().toString());
		assertEquals("{ \"postedAt\" : -1}", arg.getValue().getSortObject().toString());
	}


	@Test
	public void shouldFindCommentsByRollingStock() {
		List<Comment> value = 
				Arrays.asList(newComment(), newComment(), newComment());
		when(mongo.find(isA(Query.class), eq(Comment.class))).thenReturn(value);
		
		List<Comment> results = (List<Comment>) repo.findByRollingStock(rollingStock);
		
		assertEquals(3, results.size());
		ArgumentCaptor<Query> arg = ArgumentCaptor.forClass(Query.class);
		verify(mongo, times(1)).find(arg.capture(), eq(Comment.class));
		assertEquals("{ \"rollingStock.slug\" : \"acme-123456\"}", arg.getValue().getQueryObject().toString());
		assertEquals("{ \"postedAt\" : -1}", arg.getValue().getSortObject().toString());
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
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();

		final Comment c = new Comment(author, rollingStock, "Comment");
		return c;
	}
}
