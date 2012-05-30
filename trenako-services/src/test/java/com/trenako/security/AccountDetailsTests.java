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
package com.trenako.security;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.trenako.entities.Account;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class AccountDetailsTests {

	@Test
	public void shouldCreateDetailsFromAccount() {
		Account user = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		AccountDetails userDetails = new AccountDetails(user);
		
		assertEquals(user.getEmailAddress(), userDetails.getUsername());
		assertEquals(user.getPassword(), userDetails.getPassword());
		assertEquals(user.isEnabled(), userDetails.isEnabled());
		assertTrue(userDetails.isAccountNonExpired());
		assertTrue(userDetails.isAccountNonLocked());
		assertTrue(userDetails.isCredentialsNonExpired());
	}
	
	@Test
	public void shouldManageLockedAccounts() {
		Account user = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.locked(true)
			.build();
		
		AccountDetails userDetails = new AccountDetails(user);
		assertFalse(userDetails.isAccountNonLocked());
	}
	
	@Test
	public void shouldManageExpiredAccounts() {
		Account user = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.expired(true)
			.build();
		
		AccountDetails userDetails = new AccountDetails(user);
		assertFalse(userDetails.isAccountNonExpired());
	}
	
	@Test
	public void shouldHaveTheUserRoleByDefault() {
		Account user = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();

		AccountDetails userDetails = new AccountDetails(user);

		assertEquals("[ROLE_USER]", userDetails.getAuthorities().toString());
	}
	
	@Test
	public void shouldAssignRolesToUsers() {
		Account user = new Account.Builder("mail@mail.com")
			.displayName("User Name")
			.build();
		user.setRoles(Arrays.asList("ROLE_ADMIN", "ROLE_USER"));
		
		AccountDetails userDetails = new AccountDetails(user);

		assertEquals("[ROLE_ADMIN, ROLE_USER]", userDetails.getAuthorities().toString());
	}
}
