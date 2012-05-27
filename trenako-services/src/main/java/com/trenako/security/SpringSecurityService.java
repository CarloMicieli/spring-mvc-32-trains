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

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.trenako.entities.Account;

/**
 * The concrete implementation for the service.
 * @author Carlo Micieli
 *
 */
@Service("securityServive")
public class SpringSecurityService implements SecurityService {

	private SecurityContext getSecContext() {
		return SecurityContextHolder.getContext();
	}
	
	@Override
	public void authenticate(Account user) {
		AccountDetails userDetails = new AccountDetails(user);
		Authentication auth = new UsernamePasswordAuthenticationToken(
				userDetails,
				userDetails.getPassword(),
				userDetails.getAuthorities());
		getSecContext().setAuthentication(auth);
	}

	@Override
	public AccountDetails getCurrentUser() {
		Authentication auth = getSecContext().getAuthentication();
		return auth==null ? null : (AccountDetails) auth.getPrincipal();
	}
}
