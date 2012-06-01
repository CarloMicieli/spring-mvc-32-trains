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
import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CommentsEventListenerTests {
	@Test
	public void shouldFillTheFieldsBeforeSave() {
		CommentsEventListener lis = new CommentsEventListener();
		
		Account author = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		RollingStock rollingStock = new RollingStock.Builder("ACME", "123456").build();
		Comment comment = new Comment(author, rollingStock, "Comment");
	
		DBObject dbo = new BasicDBObject();
		lis.onBeforeSave(comment, dbo);
		
		assertEquals("user-name", dbo.get("authorName"));
		assertNotNull(dbo.get("postedAt"));
	}
}
