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

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.trenako.AbstractValidationTests;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class AccountValidationTests extends AbstractValidationTests<Account> {
	
	@Before
	public void initValidator() {
		super.init(Account.class);
	}

	@Test
	public void shouldValidateValidAccounts() {
		Account user = new Account("mail@mail.com", "$ecret", "User name");
		Map<String, String> errors = validate(user);
		
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidAccounts() {
		Account user = new Account();
		Map<String, String> errors = validate(user);
		
		assertEquals(3, errors.size());
		assertEquals("account.emailAddress.required", errors.get("emailAddress"));
		assertEquals("account.password.required", errors.get("password"));
		assertEquals("account.displayName.required", errors.get("displayName"));
	}
	
	@Test
	public void shouldValidatePasswords() {
		Account user = new Account("mail@mail.com", "p", "User name");
		Map<String, String> errors = validate(user);
		
		assertEquals(1, errors.size());
		assertEquals("account.password.size.notmet", errors.get("password"));
	}
	
	@Test
	public void shouldValidateEmailAddresses() {
		Account user = new Account("mail", "$egret", "User name");
		Map<String, String> errors = validate(user);
		
		assertEquals(1, errors.size());
		assertEquals("account.emailAddress.email.invalid", errors.get("emailAddress"));		
	}
	
	@Test
	public void shouldValidateDisplayNames() {
		Account user = new Account("mail@mail.com", "$egret", "us");
		Map<String, String> errors = validate(user);
		
		assertEquals(1, errors.size());
		assertEquals("account.displayName.size.notmet", errors.get("displayName"));	
	}
}
