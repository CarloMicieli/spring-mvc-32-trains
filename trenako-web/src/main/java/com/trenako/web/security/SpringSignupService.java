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
import org.springframework.stereotype.Service;

import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.AuthorityUtils;

import com.trenako.repositories.AccountsRepository;
import com.trenako.security.AccountDetails;
import com.trenako.entities.Account;

/**
 * A concrete implementation for the {@code SignupService} interface based on Spring security.
 * <p>
 * This class provides two basic functionalities: create new accounts and authenticate them
 * after their creation. Thanks to the latter, the users are automatically logged in after
 * they sign up.
 * </p>
 *
 * @author Carlo Micieli
 * @see PasswordEncoder
 * @see AccountRepository
 */
@Service("signupService")
public class SpringSignupService implements SignupService {

	private final PasswordEncoder passwordEncoder;
	private final AccountsRepository repo;
	private SecurityContext securityContext;
	
	/**
	 * Creates a new {@code SpringSignupService}.
	 *
	 * @param repo the {@code Account} repository
	 * @param passwordEncoder the password encoder
	 */
	@Autowired
	public SpringSignupService(AccountsRepository repo, PasswordEncoder passwordEncoder) {
		this.repo = repo;
		this.passwordEncoder = passwordEncoder;
	}
	
	// sets the current SecurityContext (for testing)
	protected void setSecurityContext(SecurityContext securityContext) {
		this.securityContext = securityContext;
	}
	
	// returns the current SecurityContext
	SecurityContext getSecurityContext() {
		if (securityContext != null) {
			return securityContext;
		}
		return SecurityContextHolder.getContext();
	}
	
	@Override
	public Account createAccount(Account a) {
		String encodedPwd =  passwordEncoder.encodePassword(a.getPassword(), null);		
		Account account = new Account(a.getEmailAddress(),
				encodedPwd,
				a.getDisplayName(),
				a.getRoles());
		return repo.save(account);
	}
	
	@Override
	public void authenticate(Account account) {
		final Collection<? extends GrantedAuthority> authorities = Collections.unmodifiableList(
				AuthorityUtils.createAuthorityList("ROLE_USER"));

		// must authenticate 'AccountDetails'
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				new AccountDetails(account),
				account.getPassword(),
				authorities);
		getSecurityContext().setAuthentication(authentication);
	}
}