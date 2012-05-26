package com.trenako.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.trenako.entities.Account;

/**
 * It represents a user details wrapper for the Authentication object.
 * @author Carlo Micieli
 *
 */
public class AccountDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	private final Account account;

	public AccountDetails(Account account) {
		this.account = account;
	}
	
	public AccountDetails(String emailAddress, String password) {
		account = new Account(emailAddress, password, "");
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<String> l = account.getRoles();
		String[] roles = l==null ?
				new String[] {"ROLE_USER"} :
				l.toArray(new String[l.size()]);

		return Collections.unmodifiableList(
				AuthorityUtils.createAuthorityList(roles));
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getEmailAddress();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return account.isEnabled();
	}
}
