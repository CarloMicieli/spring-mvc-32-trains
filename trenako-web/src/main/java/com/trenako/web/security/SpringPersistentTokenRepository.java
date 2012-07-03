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

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.trenako.entities.PersistentLogin;
import com.trenako.repositories.RememberMeRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class SpringPersistentTokenRepository implements
		PersistentTokenRepository {

	private final RememberMeRepository repo;
	
	@Autowired
	public SpringPersistentTokenRepository(RememberMeRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		PersistentLogin pl = new PersistentLogin(token.getUsername(),
				token.getSeries(),
				token.getDate(),
				token.getTokenValue());
		repo.save(pl);	
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		PersistentLogin pl = repo.findBySeries(seriesId);
		return new PersistentRememberMeToken(pl.getUsername(),
				pl.getSeries(),
				pl.getTokenValue(),
				pl.getDate());
	}

	@Override
	public void removeUserTokens(String username) {
		repo.deleteByUsername(username);
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		PersistentLogin token = new PersistentLogin();
		token.setDate(lastUsed);
		token.setTokenValue(tokenValue);
		token.setSeries(series);
		repo.save(token);
	}

}
