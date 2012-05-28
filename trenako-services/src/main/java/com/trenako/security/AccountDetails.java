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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.trenako.entities.Account;

/**
 * It represents a user details wrapper for the Authentication object.
 * @author Carlo Micieli
 *
 */
public class AccountDetails implements UserDetails {
	private static final long serialVersionUID = 1L;

	private final Account account;

	/**
	 * Creates a new {@link AccountDetails} instance.
	 * @param account the account wrapped by this details.
	 */
	public AccountDetails(Account account) {
		this.account = account;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<String> l = account.getRoles();
		String[] roles = l==null ?
				new String[] {"ROLE_USER"} : l.toArray(new String[l.size()]);

		return Collections.unmodifiableList(
			AuthorityUtils.createAuthorityList(roles));
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getEmailAddress();
	}

	@Override
	public boolean isAccountNonExpired() {
		return !account.isExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return !account.isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return account.isEnabled();
	}
}
