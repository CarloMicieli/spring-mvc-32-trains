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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.trenako.test.AbstractValidationTests;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CommentValidationTests extends AbstractValidationTests<Comment> {
	Account author = new Account.Builder("mail@mailcom")
		.displayName("Bob")
		.build();
	
	@Before
	public void initValidator() {
		super.init(Comment.class);
	}

	@Test
	public void shouldValidateValidComments() {
		Comment c = new Comment(author, "Comment content");
		Map<String, String> errors = validate(c);
		
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidComments() {
		Comment c = new Comment();
		Map<String, String> errors = validate(c);
		
		assertEquals(2, errors.size());
		assertEquals("comment.author.required", errors.get("author"));
		assertEquals("comment.content.required", errors.get("content"));
	}
	
	@Test
	public void shouldValidateCommentAnswers() {
		Comment c = new Comment(author, "Comment content");
		List<Comment> answers = Arrays.asList(
				new Comment(author, "my first comment"),
				new Comment(author, "my second comment"));
		c.setAnswers(answers);
		
		Map<String, String> errors = validate(c);
		
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateWrongCommentAnswers() {
		Comment c = new Comment(author, "Comment content");
		List<Comment> answers = Arrays.asList(
				new Comment(author, "my first comment"),
				new Comment(author, null));
		c.setAnswers(answers);
		
		Map<String, String> errors = validate(c);
		
		assertEquals(1, errors.size());
		assertEquals("comment.content.required", errors.get("answers[1].content"));
	}
}
