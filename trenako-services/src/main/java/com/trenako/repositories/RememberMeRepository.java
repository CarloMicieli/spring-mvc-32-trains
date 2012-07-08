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
package com.trenako.repositories;

import com.trenako.entities.PersistentLogin;

/**
 * It represents a {@code PersistentLogin} repository to implement
 * the "remember me" functionality.
 * 
 * @author Carlo Micieli
 *
 */
public interface RememberMeRepository {
	/**
	 * Returns the {@code PersistentLogin} token with the provided series value.
	 * @param seriesId the series value
	 * @return a {@code PersistentLogin} if found; {@code null} otherwise
	 */
	PersistentLogin findBySeries(String seriesId);
	
	/**
	 * Creates a new {@code PersistentLogin} token.
	 * @param token the {@code PersistentLogin} to be created
	 */
	void createNew(PersistentLogin token);
	
	/**
	 * Saves the {@code PersistentLogin} token changes.
	 * @param token the {@code PersistentLogin} to be saved
	 */
	void save(PersistentLogin token);
	
	/**
	 * Deletes {@code PersistentLogin} tokens for the provided username.
	 * @param username the user name
	 */
	void deleteByUsername(String username);
}
