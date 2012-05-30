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
package com.trenako.entities;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trenako.utility.Slug;

/**
 * It represents a user account for the application.
 * @author Carlo Micieli
 *
 */
@Document(collection = "accounts")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private ObjectId id;

	@NotEmpty(message = "account.emailAddress.required")
	@Email(message = "account.emailAddress.email.invalid")
	@Indexed(unique = true)
	private String emailAddress;

	@NotEmpty(message = "account.password.required")
	@Length(min = 5, max = 25, message = "account.password.size.notmet")
	private String password;

	@NotEmpty(message = "account.displayName.required")
	@Length(min = 3, max = 25, message = "account.displayName.size.notmet")
	private String displayName;
	
	@Indexed(unique = true)
	private String slug;

	private boolean expired;
	private boolean locked;
	private boolean enabled;
	private List<String> roles;
	
	// required by spring data
	Account() {
	}
	
	private Account(Builder b) {
		this.emailAddress = b.emailAddress;
		this.password = b.password;
		this.displayName = b.displayName;
		this.expired = b.expired;
		this.enabled = b.enabled;
		this.locked = b.locked;
		this.roles = b.roles;
	}
	
	/**
	 * The user account builder class
	 * @author Carlo Micieli
	 *
	 */
	public static class Builder {
		private final String emailAddress;
		
		private String password = null;
		private String displayName = null;
		private boolean expired = false;
		private boolean enabled = true;
		private boolean locked = false;
		
		private List<String> roles = null;
		
		public Builder(String emailAddress) {
			this.emailAddress = emailAddress;
		}

		public Builder password(String pwd) {
			password = pwd;
			return this;
		}

		public Builder displayName(String dn) {
			displayName = dn;
			return this;
		}

		public Builder expired(boolean b) {
			expired = b;
			return this;
		}

		public Builder enabled(boolean b) {
			enabled = b;
			return this;
		}

		public Builder locked(boolean b) {
			locked = b;
			return this;
		}

		public Builder roles(List<String> lr) {
			roles = lr;
			return this;
		}
		
		public Account build() {
			return new Account(this);
		}
	}

	/**
	 * Returns the account unique id.
	 * @return the unique id
	 */
	public ObjectId getId() {
		return id;
	}

	void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Returns the user email address.
	 * @return the user email address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the user email address.
	 * @param emailAddress the user email address
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Returns the password.
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * @param password the password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the user display name.
	 * @return the user display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the user display name.
	 * @param displayName the user display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Returns the user slug.
	 * @return the slug
	 */
	public String getSlug() {
		if( slug==null ) slug = Slug.encode(displayName);
		return slug;
	}

	/**
	 * Sets the user slug.
	 * @param slug the slug
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}

	/**
	 * Indicates whether the user is enabled or disabled.
	 * @return <em>true</em> if the account is enabled; <em>false</em> otherwise
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Indicates whether the user is enabled or disabled.
	 * @param isEnabled <em>true</em> if the account is enabled; <em>false</em> otherwise
	 */
	public void setEnabled(boolean isEnabled) {
		this.enabled = isEnabled;
	}
	
	/**
	 * Returns if the account is expired.
	 * @return <em>true</em> if the account is expired; <em>false</em> otherwise
	 */
	public boolean isExpired() {
		return expired;
	}

	/**
	 * Indicates whether the account's credentials (password) has expired.
	 * @param expired <em>true</em> if the account is expired; <em>false</em> otherwise
	 */
	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	/**
	 * Indicates whether the user is locked or unlocked.
	 * @return <em>true</em> if the account is locked; <em>false</em> otherwise
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * Indicates whether the user is locked or unlocked.
	 * @param locked <em>true</em> if the account is locked; <em>false</em> otherwise
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	/**
	 * Returns the roles granted to the user.
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles granted to the user.
	 * @param roles the roles
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
