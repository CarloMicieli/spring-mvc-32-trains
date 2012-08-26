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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockComments;
import com.trenako.repositories.CommentsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CommentsServiceTests {

	private RollingStock rollingStock() {
		return new RollingStock.Builder(acme(), "123456")
		.railway(fs())
		.scale(scaleH0())
		.description("desc")
		.build();
	}
	
	private Comment comment() {
		return new Comment();
	}
	
	private Comment answer() {
		return new Comment();
	}
	
	@Mock CommentsRepository repo;
	CommentsService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new CommentsServiceImpl(repo);
	}

	@Test
	public void shouldFindCommentsByRollingStock() {
		@SuppressWarnings("unused")
		RollingStockComments comments = service.findByRollingStock(rollingStock());
		verify(repo, times(1)).findByRollingStock(eq(rollingStock()));
	}
	
	@Test
	public void shouldPostNewComments() {
		service.postComment(rollingStock(), comment());
		verify(repo, times(1)).createNew(eq(rollingStock()), eq(comment()));
	}

	@Test
	public void shouldPostNewCommentAnswers() {
		service.postAnswer(rollingStock(), comment(), answer());
		verify(repo, times(1)).createAnswer(eq(rollingStock()), eq(comment()), eq(answer()));
	}
	
	@Test
	public void shouldDeleteComments() {
		service.deleteComment(rollingStock(), comment());
		verify(repo, times(1)).remove(eq(rollingStock()), eq(comment()));
	}
	
	@Test
	public void shouldDeleteCommentAnswers() {
		service.deleteAnswer(rollingStock(), comment(), answer());
		verify(repo, times(1)).removeAnswer(eq(rollingStock()), eq(comment()), eq(answer()));
	}
}
