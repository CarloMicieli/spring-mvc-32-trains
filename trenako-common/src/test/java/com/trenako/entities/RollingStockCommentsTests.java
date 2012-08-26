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

import java.util.Collections;

import org.bson.types.ObjectId;
import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockCommentsTests {
	private static final ObjectId USERID = new ObjectId("5039258b84ae08ae6de15da2");
	
	private final Account author() {
		return new Account.Builder("mail@mail.com")
			.id(USERID)
			.displayName("Bob")
			.build();
	}
	
	private RollingStock rs = new RollingStock.Builder(acme(), "123456")
		.scale(scaleH0())
		.railway(db())
		.build();
	
	@Test
	public void shouldCreateNewRollingStockComments() {
		RollingStockComments comments = new RollingStockComments(rs);
		comments.setItems(Collections.singletonList(new Comment(author(), "Comment content")));
		
		assertEquals("acme-123456", comments.getSlug());
		assertEquals(1, comments.getItems().size());
		assertEquals("acme-123456", comments.getRollingStock().getSlug());
		assertEquals("ACME 123456", comments.getRollingStock().getLabel());
		
		assertEquals(USERID, comments.getItems().get(0).getAuthorId());
		assertEquals("Comment content", comments.getItems().get(0).getContent());		
	}
	
	@Test
	public void shouldProduceStringRepresentationsForRollingStockComments() {
		RollingStockComments comments = new RollingStockComments(rs);
		assertEquals("comments{slug: acme-123456, rollingStock: acme-123456, count: 0}", 
				comments.toString());
	}
	
	@Test
	public void shouldCheckWhetherTwoRollingStockCommentsAreEquals() {
		RollingStockComments x = new RollingStockComments(rs);
		RollingStockComments y = new RollingStockComments(rs);
		assertTrue(x.equals(x));
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoRollingStockCommentsAreDifferent() {
		RollingStockComments x = new RollingStockComments(rs, "slug1");
		RollingStockComments y = new RollingStockComments(rs, "slug2");
		assertFalse(x.equals(y));
	}
}
