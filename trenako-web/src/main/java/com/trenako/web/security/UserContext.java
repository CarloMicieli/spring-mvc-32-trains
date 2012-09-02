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
import com.trenako.security.AccountDetails;

/**
 * It represent a security context, used to retrieve the current 
 * authenticated user (if any).
 * 
 * @author Carlo Micieli
 *
 */
public abstract class UserContext {

    /**
     * Gets the currently logged in {@code Account} or {@code null} if not logged in.
     * @return
     */
    public abstract AccountDetails getCurrentUser();
    
    /**
     * Returns the authenticated {@code Account} or {@code null} if there is no 
     * user logged it or the security context is null.
     * @param context the security content or {@code null}
     * @return the authenticated {@code Account}
     */
    public static Account authenticatedUser(UserContext context) {
		if (context == null) {
			return null;
		}

		AccountDetails accountDetails = context.getCurrentUser();
		if (accountDetails == null) {
			return null;
		}
		
		return accountDetails.getAccount();
	}
}
