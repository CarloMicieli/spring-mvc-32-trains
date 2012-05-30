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

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class AccountTests {
	
	@Test
	public void shouldCreateNewAccounts() {
		Account user = new Account.Builder("mail@mail.com")
			.password("$ecret")
			.displayName("Nickname")
			.expired(false)
			.enabled(true)
			.locked(false)
			.build();
		
		assertEquals("mail@mail.com", user.getEmailAddress());
		assertEquals("$ecret", user.getPassword());
		assertEquals("Nickname", user.getDisplayName());
		assertFalse(user.isExpired());
		assertFalse(user.isLocked());
		assertTrue(user.isEnabled());		
	}
	
	@Test
	public void shouldFillTheSlug() {
		Account user = new Account.Builder("mail@mail.com")
			.password("$ecret")
			.displayName("User name")
			.build();
		assertEquals("user-name", user.getSlug());
	}
}
