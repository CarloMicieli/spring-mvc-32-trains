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
package com.trenako.services;

import org.bson.types.ObjectId;

import com.trenako.entities.Account;

/**
 * The interface for the accounts service.
 * <p>
 * The concrete classes that implements the {@code AccountsService} interface will provide
 * the following functionalities:
 * <ul>
 * <li>finds the {@code Account} by id;</li>
 * <li>finds the {@code Account} by email address;</li>
 * <li>finds the {@code Account} by slug (unique, URL friendly value);</li>
 * <li>saves/removes an {@code Account}.</li>
 * </ul>
 * </p>
 *
 * @author Carlo Micieli
 * @see com.trenako.entities.Account
 * @see com.trenako.repositories.AccountsRepository
 */
public interface AccountsService {
	/**
	 * Finds the {@link Account} with the provided id.
	 * @param id the unique id
	 * @return an {@code Account} if found; {@code null} otherwise
	 */
	Account findById(ObjectId id);
	
	/**
	 * Finds the {@link Account} with the provided email address.
	 * @param emailAddress the email address
	 * @return an {@code Account} if found; {@code null} otherwise
	 */
	Account findByEmailAddress(String emailAddress);
	
	/**
	 * Finds the {@link Account} with the provided slug.
	 * @param slug the user slug
	 * @return an {@code Account} if found; {@code null} otherwise
	 * @see com.trenako.entities.Account#getSlug()
	 */	
	Account findBySlug(String slug);
	
	/**
	 * Persists the {@link Account} changes in the data store.
	 * <p>
	 * This method performs a "upsert": if the {@code Account} is not present in the data store
	 * a new {@code Account} is created; otherwise the method will update the existing {@code Account}. 
	 * </p>	 
	 * @param account the {@code Account} to be saved
	 */
	void save(Account account);
	
	/**
	 * Removes a {@link Account} from the data store.
	 * @param account the {@code Account} to be removed
	 */
	void remove(Account account);
}
