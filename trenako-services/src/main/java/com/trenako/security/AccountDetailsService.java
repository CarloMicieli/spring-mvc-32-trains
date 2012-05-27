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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trenako.entities.Account;
import com.trenako.services.AccountsService;

/**
 * The concrete implementation for the service that allows 
 * for retrieving a UserDetails object based on the email address.
 * 
 * @author Carlo Micieli
 *
 */
@Service
public class AccountDetailsService implements UserDetailsService {

	private final AccountsService service;
	
	/**
	 * Creates the accounts details service.
	 * @param service the accounts service.
	 */
	@Autowired
	public AccountDetailsService(AccountsService service) {
		this.service = service;
	}
	
	/**
	 * Locates the user based on the email address.
	 * @param emailAddress the email address.
	 * @return the user details.
	 */
	@Override
	public UserDetails loadUserByUsername(String emailAddress)
			throws UsernameNotFoundException {

		Account account = service.findByEmailAddress(emailAddress);
		if( account==null ) {
            throw new UsernameNotFoundException(
            	String.format("Could not find user with email '%s'", emailAddress));
		}
		return new AccountDetails(account);
	}
}
