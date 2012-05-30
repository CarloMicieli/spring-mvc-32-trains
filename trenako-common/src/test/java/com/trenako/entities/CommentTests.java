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

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CommentTests {

	@Test
	public void shouldReturnsAuthorAndRollingStockSlugs() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		
		Comment c = new Comment(author, rs, "Comment content");
		
		assertEquals("user-name", c.getAuthorName());
		assertEquals("acme-123456", c.getRsSlug());
	}
	
	@Test
	public void shouldReturnsTrueIfTwoCommentsAreEquals() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();

		Comment x = new Comment(author, rs, "Comment content");
		Comment y = new Comment(author, rs, "Comment content");
		
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldReturnsFalseIfTwoCommentsAreDifferent() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();

		Comment x = new Comment(author, rs, "Comment content 1");
		Comment y = new Comment(author, rs, "Comment content 2");
		
		assertFalse(x.equals(y));
	}
	
	@Test
	public void shouldReturnAStringForComments() {
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();

		Comment c = new Comment(author, rs, "Comment content");
		assertEquals("user-name posted 'Comment content' on acme-123456", c.toString());
	}
}
