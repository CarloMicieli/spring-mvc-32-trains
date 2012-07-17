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
import org.springframework.data.mongodb.repository.MongoRepository;

import com.trenako.entities.Account;

/**
 * The interface for the user {@code Account}s repository.
 * 
 * @author Carlo Micieli
 *
 */
public interface AccountsRepository extends MongoRepository<Account, ObjectId> {
	
	/**
	 * Returns the {@code Account} with the provided email address.
	 * @param emailAddress the email address
	 * @return an {@code Account} if found; {@code null} otherwise
	 */
	Account findByEmailAddress(String emailAddress);
	
	/**
	 * Returns the {@code Account} with the provided slug.
	 * @param slug the slug
	 * @return an {@code Account} if found; {@code null} otherwise
	 */
	Account findBySlug(String slug);
}
