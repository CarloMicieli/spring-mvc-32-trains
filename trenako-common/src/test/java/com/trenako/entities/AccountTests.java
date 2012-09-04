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

import org.junit.Test;

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class AccountTests {
	
	@Test
	public void shouldProduceStringRepresentationForAccounts() {
		Account user = new Account.Builder("mail@mail.com")
			.displayName("Nickname")
			.build();
		assertEquals("account{emailAddress; mail@mail.com, displayName: Nickname}", user.toString());
	}
	
	@Test
	public void shouldCreateNewAccountsWithUserRoleByDefault() {
		Account user = new Account("mail@mail.com", 
			"$ecret", 
			"Nickname", 
			date("2012/09/01"), 
			null);

		assertEquals("[ROLE_USER]", user.getRoles().toString());
	}

	@Test
	public void shouldCreateNewAccounts() {
		Account user = new Account("mail@mail.com", 
			"$ecret", 
			"Nickname", 
			date("2012/09/01"), 
			Arrays.asList("ROLE_STAFF"));
		
		assertEquals("mail@mail.com", user.getEmailAddress());
		assertEquals("$ecret", user.getPassword());
		assertEquals("Nickname", user.getDisplayName());
		assertEquals("[ROLE_STAFF]", user.getRoles().toString());
		assertEquals(date("2012/09/01"), user.getMemberSince());
		assertFalse(user.isExpired());
		assertFalse(user.isLocked());
		assertTrue(user.isEnabled());
	}
	
	@Test
	public void shouldCheckWhetherTwoAccountsAreEquals() {
		Account x = new Account.Builder("mail@mail.com")
			.password("$ecret")
			.displayName("Bob")
			.build();
		Account y = new Account.Builder("mail@mail.com")
			.password("$ecret")
			.displayName("Bob")
			.build();
		assertTrue("Accounts are different", x.equals(x));
		assertTrue("Accounts are different", x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoAccountsAreDifferent() {
		Account x = new Account.Builder("bob@mail.com")
			.password("$ecret")
			.displayName("Bob")
			.build();
		Account y = new Account.Builder("alice@mail.com")
			.password("$ecret")
			.displayName("Alice")
			.build();
		assertFalse("Accounts are equals", x.equals(y));

		Account z = new Account.Builder("bob@mail.com")
			.password("$ecret")
			.displayName("Alice")
			.build();
		assertFalse("Accounts are equals", x.equals(z));
	}
	
	@Test
	public void shouldFillTheAccountSlug() {
		Account user = new Account.Builder("mail@mail.com")
			.password("$ecret")
			.displayName("User name")
			.build();
		assertEquals("user-name", user.getSlug());
	}
}