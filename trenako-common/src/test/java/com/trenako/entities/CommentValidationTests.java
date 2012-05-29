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

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.trenako.AbstractValidationTests;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CommentValidationTests extends AbstractValidationTests<Comment> {
	
	@Before
	public void initValidator() {
		super.init(Comment.class);
	}

	@Test
	public void shouldValidateValidComments() {
		Account author = new Account();
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		
		Comment c = new Comment(author, rs, "Comment content");
		Map<String, String> errors = validate(c);
		
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidComments() {
		Comment c = new Comment();
		Map<String, String> errors = validate(c);
		
		assertEquals(3, errors.size());
		assertEquals("comment.author.required", errors.get("author"));
		assertEquals("comment.rollingStock.required", errors.get("rollingStock"));
		assertEquals("comment.content.required", errors.get("content"));
	}
}
