/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.web.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.AuthorityUtils;

import com.trenako.repositories.AccountsRepository;
import com.trenako.entities.Account;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Component
public class AccountDetailsService implements UserDetailsService {
    private final AccountsRepository repo;

	/**
	 * Creates a new {@code AccountDetailsService}.
	 * @param repo the {@code Account}s repository
	 */
    @Autowired
    public AccountDetailsService(AccountsRepository repo) {
        this.repo = repo;
    }

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = repo.findByEmailAddress(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user with username '" + username + "'");
        }
        return new AccountDetails(user);
    }

    private static class AccountDetails extends Account implements UserDetails {

		private static final long serialVersionUID = 1L;

		public AccountDetails(Account a) {
			setId(a.getId());
            setEmailAddress(a.getEmailAddress());
            setPassword(a.getPassword());
            setDisplayName(a.getDisplayName());
			setRoles(a.getRoles());
			setExpired(a.isExpired());
			setLocked(a.isLocked());
			setEnabled(a.isEnabled());
        }

		@Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.unmodifiableList(
				AuthorityUtils.createAuthorityList((String[]) getRoles().toArray()));
        }

		@Override
		public String getUsername() {
			return getEmailAddress();
		}
		
		@Override
        public boolean isAccountNonExpired() {
            return !isExpired();
        }

		@Override
        public boolean isAccountNonLocked() {
            return !isLocked();
        }

		@Override
        public boolean isCredentialsNonExpired() {
            return true;
        }
    }
}