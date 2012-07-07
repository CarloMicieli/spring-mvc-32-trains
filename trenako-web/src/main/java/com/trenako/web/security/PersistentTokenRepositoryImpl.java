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

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import com.trenako.entities.PersistentLogin;
import com.trenako.repositories.RememberMeRepository;

/**
 * Concrete implementation for the {@link PersistentTokenRepository} interface.
 * <p>
 * This class is persisting the tokens for the "remember me" function.
 * </p>
 * 
 * @author Carlo Micieli
 *
 */
@Component
public class PersistentTokenRepositoryImpl implements PersistentTokenRepository {
	
	private final RememberMeRepository repo;
	
	/**
	 * Creates a new {@code PersistentTokenRepositoryImpl}.
	 * @param repo the persistent tokens repository
	 */
	@Autowired
	public PersistentTokenRepositoryImpl(RememberMeRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		PersistentLogin login = new PersistentLogin(
				token.getUsername(),
				token.getSeries(),
				token.getDate(),
				token.getTokenValue());
		repo.createNew(login);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		PersistentLogin login = new PersistentLogin();
		login.setDate(lastUsed);
		login.setTokenValue(tokenValue);
		login.setSeries(series);
		repo.save(login);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String series) {
		PersistentLogin login = repo.findBySeries(series);
		
		return new PersistentRememberMeToken(
			login.getUsername(),
			login.getSeries(),
			login.getTokenValue(),
			login.getDate());
	}

	@Override
	public void removeUserTokens(String username) {
		repo.deleteByUsername(username);
	}
}
