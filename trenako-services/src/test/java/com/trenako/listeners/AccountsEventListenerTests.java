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

/**
 * 
 * @author Carlo Micieli
 *
 */
public class AccountsEventListenerTests {

	@Test
	public void shouldFillValuesBeforeSave() {
		AccountsEventListener lis = new AccountsEventListener();
		
		DBObject dbo = new BasicDBObject();
		Account account = new Account("mail@mail.com", "pa$$word", "The User");
		
		lis.onBeforeSave(account, dbo);
		
		assertEquals("the-user", dbo.get("slug"));
	}
}
