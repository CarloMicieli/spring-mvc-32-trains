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

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CommentTests {
	
	private final Account author() {
		return new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.build();
	}
	
	@Test
	public void shouldCheckWhetherTwoUserCommentsAreEquals() {
		Comment x = new Comment(author(), "Comment content", date("2012/1/1"));
		Comment y = new Comment(author(), "Comment content", date("2012/1/1"));
		
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoUserCommentsAreDifferent() {
		Comment x = new Comment(author(), "Comment content 1");
		Comment y = new Comment(author(), "Comment content 2");
		assertFalse(x.equals(y));
		
		Comment z = new Comment(author(), "Comment content 1", date("2012/1/1"));
		assertFalse(x.equals(z));
	}
	
	@Test
	public void shouldProduceCommentIds() {
		Comment x = new Comment(author(), "Comment content 1", fulldate("2012/06/01 10:30:00.500"));
		assertEquals("12-06-01-10-30-00_bob", x.getCommentId());
	}
	
	@Test
	public void shouldProduceStringRepresentationFromComments() {
		Comment c = new Comment(author(), "Comment content", date("2012/1/1"));
		assertEquals("comment{author: bob, content: Comment content, postedAt: Sun Jan 01 00:00:00 CET 2012}", c.toString());
	}
}