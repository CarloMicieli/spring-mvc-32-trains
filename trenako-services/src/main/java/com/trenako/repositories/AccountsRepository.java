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

import org.bson.types.ObjectId;

import com.trenako.entities.Account;

/**
 * The interface for the user accounts repository.
 * @author Carlo Micieli
 *
 */
public interface AccountsRepository {
	/**
	 * Finds a user account by id.
	 * @param id the user id
	 * @return the account
	 */
	Account findById(ObjectId id);
	
	/**
	 * Finds a user account by email address.
	 * @param emailAddress the email address
	 * @return the account
	 */
	Account findByEmailAddress(String emailAddress);
	
	/**
	 * Finds a user account by slug.
	 * @param slug the user slug
	 * @return the account
	 */
	Account findBySlug(String slug);
	
	/**
	 * Saves the account.
	 * @param account the account to be saved
	 */
	void save(Account account);
	
	/**
	 * Removes the account.
	 * @param account the account to be deleted
	 */
	void remove(Account account);
}
