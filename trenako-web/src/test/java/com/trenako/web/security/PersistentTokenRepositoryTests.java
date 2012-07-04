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

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.trenako.entities.PersistentLogin;
import com.trenako.repositories.RememberMeRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PersistentTokenRepositoryTests {
	@Mock RememberMeRepository mock;
	PersistentTokenRepository repo;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		repo = new PersistentTokenRepositoryImpl(mock);
	}

	private PersistentLogin buildLogin() {
		return new PersistentLogin("username", "series", new Date(), "tokenValue"); 
	}
	
	private PersistentRememberMeToken buildToken() {
		return new PersistentRememberMeToken("username", "series", "tokenValue", new Date()); 
	}
	
	@Test
	public void shouldCreateNewTokens() {
		ArgumentCaptor<PersistentLogin> arg = ArgumentCaptor.forClass(PersistentLogin.class);
		
		PersistentRememberMeToken token = buildToken();
		repo.createNewToken(token);
		
		verify(mock, times(1)).createNew(arg.capture());
		assertEquals(token.getUsername(), arg.getValue().getUsername());
		assertEquals(token.getSeries(), arg.getValue().getSeries());
		assertEquals(token.getTokenValue(), arg.getValue().getTokenValue());
		assertEquals(token.getDate(), arg.getValue().getDate());
	}
		
	@Test
	public void shouldGetTokenForSeries() {
		String series = "series";
		PersistentLogin value = buildLogin();
		when(mock.findBySeries(eq(series))).thenReturn(value);
		
		PersistentRememberMeToken token = repo.getTokenForSeries(series);
		
		assertEquals(value.getUsername(), token.getUsername());
		assertEquals(value.getSeries(), token.getSeries());
		assertEquals(value.getTokenValue(), token.getTokenValue());
		assertEquals(value.getDate(), token.getDate());
	}
	
	@Test
	public void shouldRemoveUserTokens() {
		String username = "username";
		
		repo.removeUserTokens(username);
		verify(mock, times(1)).deleteByUsername(eq(username));
	}
	
	@Test
	public void shouldUpdateToken() {
		ArgumentCaptor<PersistentLogin> arg = ArgumentCaptor.forClass(PersistentLogin.class);
	
		String series = "series";
		String tokenValue = "tokenValue";
		Date lastUsed = new Date();
		
		repo.updateToken(series, tokenValue, lastUsed);
		
		verify(mock, times(1)).save(arg.capture());
		assertEquals(series, arg.getValue().getSeries());
		assertEquals(tokenValue, arg.getValue().getTokenValue());
		assertEquals(lastUsed, arg.getValue().getDate());
	}
}