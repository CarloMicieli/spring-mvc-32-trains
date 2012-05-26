package com.trenako.security;

import com.trenako.entities.Account;

/**
 * 
 * @author Carlo Micieli
 *
 */
public interface SecurityService {
	void authenticate(Account user);
	AccountDetails getCurrentUser();
}
