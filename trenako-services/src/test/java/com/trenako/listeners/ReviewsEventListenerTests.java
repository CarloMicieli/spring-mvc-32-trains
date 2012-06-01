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
package com.trenako.listeners;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.trenako.entities.Account;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ReviewsEventListenerTests {
	@Test
	public void shouldFillFieldsBeforeSave() {
		ReviewsEventListener lis = new ReviewsEventListener();
		
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		RollingStock rollingStock = new RollingStock.Builder("ACME", "123456").build();
		Review review = new Review(author, rollingStock, "Review");
	
		DBObject dbo = new BasicDBObject();
		lis.onBeforeSave(review, dbo);
		
		assertEquals("user-name", dbo.get("authorName"));
		assertEquals("acme-123456", dbo.get("rsSlug"));
		assertNotNull(dbo.get("postedAt"));
	}
}
