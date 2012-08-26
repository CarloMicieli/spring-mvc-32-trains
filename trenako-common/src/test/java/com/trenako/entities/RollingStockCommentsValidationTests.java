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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import com.trenako.test.AbstractValidationTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockCommentsValidationTests extends AbstractValidationTests<RollingStockComments> {
	private RollingStock rs = new RollingStock.Builder(acme(), "123456")
		.scale(scaleH0())
		.railway(db())
		.build();
	
	private final Account author() {
		return new Account.Builder("mail@mail.com")
			.id(new ObjectId())
			.displayName("Bob")
			.build();
	}
	
	@Before
	public void initValidator() {
		super.init(RollingStockComments.class);
	}

	@Test
	public void shouldValidateRollingStockReviews() {
		RollingStockComments rsc = new RollingStockComments(rs);
		
		Map<String, String> errors = validate(rsc);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldFailValidationForNotValidRollingStockComments() {
		RollingStockComments rsc = new RollingStockComments();
		
		Map<String, String> errors = validate(rsc);
		assertEquals(1, errors.size());
		assertEquals("comment.rollingStock.required", errors.get("rollingStock"));
	}
	
	@Test
	public void shouldValidateCommentsList() {
		RollingStockComments rsc = new RollingStockComments(rs);
		List<Comment> comments = Arrays.asList(
				new Comment(author(), "my first comment"),
				new Comment(author(), "my second comment"));
		rsc.setItems(comments);
		
		Map<String, String> errors = validate(rsc);
		assertEquals(0, errors.size());
	}	
}
