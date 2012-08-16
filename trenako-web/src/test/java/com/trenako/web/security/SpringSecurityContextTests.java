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
package com.trenako.web.security;

import static org.powermock.api.mockito.PowerMockito.*;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.trenako.entities.Account;
import com.trenako.security.AccountDetails;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( {SecurityContextHolder.class} )
public class SpringSecurityContextTests {
	
	private UserContext userContext = new SpringUserContext();
		
	@Test
	public void shouldReturnTheCurrentAuthenticatedUser() {
		SecurityContext secContext = securityContext(usernamePasswordAuthToken());
		mockStatic(SecurityContextHolder.class);
		when(SecurityContextHolder.getContext()).thenReturn(secContext);
		
		AccountDetails user = userContext.getCurrentUser();

		assertNotNull("No authentication object found", user);
		assertEquals("mail@mail.com", user.getUsername());
		assertEquals("pa$$word", user.getPassword());
	}
	
	@Test
	public void shouldReturnTheAnonymousTokenWhenNoUserWasLogged() {
		SecurityContext secContext = securityContext(anonymousAuthToken());
		mockStatic(SecurityContextHolder.class);
		when(SecurityContextHolder.getContext()).thenReturn(secContext);
		
		AccountDetails user = userContext.getCurrentUser();
		
		assertNull("Authentication object was found", user);
	}
	
	private SecurityContext securityContext(Authentication authObj) {
		SecurityContext sc = Mockito.mock(SecurityContext.class);
		Mockito.when(sc.getAuthentication()).thenReturn(authObj);
		return sc;
	}
	
	private Authentication usernamePasswordAuthToken() {
		Account user = new Account.Builder("mail@mail.com")
			.displayName("bob")
			.password("pa$$word")
			.build();
		AccountDetails principal = new AccountDetails(user);
		return new UsernamePasswordAuthenticationToken(principal, null);
	}
	
	private Authentication anonymousAuthToken() {
		return new AnonymousAuthenticationToken("123456", "anonymousUser", authorities("ROLE_ANONYMOUS"));
	}
	
	private Collection<? extends GrantedAuthority> authorities(String... roles) {
		return Collections.unmodifiableList(
				AuthorityUtils.createAuthorityList(roles));
	}
	
}
