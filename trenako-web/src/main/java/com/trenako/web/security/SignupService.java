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

import com.trenako.entities.Account;

/**
 *
 * @author Carlo Micieli
 *
 */
public interface SignupService {
	/**
	 * Creates a new {@code Account}.
	 * <p>
	 * This method is enabling the account by default and encoding the provided password
	 * using a {@link PasswordEncoder} instance.
	 * </p>
	 * <p>
	 * If the account is without authorities, then the {@code ROLE_USER} role
	 * is assigned automatically.
	 * </p>
	 *
	 * @param account the new {@code Account} to be saved
	 */
	void createAccount(Account account);
	
	/**
	 * Authenticates the {@code Account} after it has been created.
	 * @param account the {@code Account} to be authenticated
	 */
	void authenticate(Account account);
}
