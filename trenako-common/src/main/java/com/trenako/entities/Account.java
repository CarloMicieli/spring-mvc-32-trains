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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
 * It represents an account for the application.
 * <p>
 * Although the users have to provide their email address during the 
 * registration to provide a unique value. 
 * </p>
 * <p>
 * The public user name is provided by the 
 * {@link Account#getDisplayName()} method. 
 * </p>
 * <p>
 * The best way to build new {@code Account} objects is using the 
 * {@link Account.Builder} methods.
 * </p>
 * 
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
	public Account() {
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
	 * The user account builder class.
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
		/**
		 * Creates a new {@code Builder} with the provided email address.
		 * @param emailAddress the user email address
		 */
		public Builder(String emailAddress) {
			this.emailAddress = emailAddress;
		}

		/**
		 * Sets the password.
		 * @param password the password
		 * @return an {@code Account} builder
		 */
		public Builder password(String password) {
			this.password = password;
			return this;
		}

		/**
		 * Sets the user display name.
		 * @param displayName the user display name
		 * @return an {@code Account} builder
		 */
		public Builder displayName(String displayName) {
			this.displayName = displayName;
			return this;
		}

		/**
		 * Indicates whether the account's credentials (password) has expired.
		 * @param expired {@code true} if the account is expired; {@code false} otherwise
		 * @return an {@code Account} builder
		 */		
		public Builder expired(boolean expired) {
			this.expired = expired;
			return this;
		}

		/**
		 * Indicates whether the user is enabled or disabled.
		 * @param enabled {@code true} if the account is enabled; {@code false} otherwise
		 * @return an {@code Account} builder
		 */
		public Builder enabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		/**
		 * Indicates whether the user is locked or unlocked.
		 * @param locked {@code true} if the account is locked; {@code false} otherwise
		 * @return an {@code Account} builder
		 */
		public Builder locked(boolean locked) {
			this.locked = locked;
			return this;
		}

		/**
		 * Adds roles to the {@code Account}.
		 * @param roles the roles to be added
		 * @return an {@code Account} builder
		 */
		public Builder roles(String... roles) {
			this.roles = Collections.unmodifiableList(
					Arrays.asList(roles));
			return this;
		}
		
		/**
		 * Builds an {@code Account} object using the values for this builder.
		 * @return the {@code Account} object
		 */
		public Account build() {
			return new Account(this);
		}
	}

	/**
	 * Returns the {@code Account} unique id.
	 * @return the unique id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Returns the {@code Account} email address
	 * @return the user email address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Returns the {@code Account} password.
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void init() {
		enabled = true;
		if (roles==null) roles = new ArrayList<String>();
		roles.add("ROLE_USER");		
	}
	
	/**
	 * Returns the {@code Account} display name.
	 * <p>
	 * The user's email address is not public, the displayed user name is taken from this field value. 
	 * It is a free text that contains either the full name or a nickname.
	 * </p>
	 *
	 * @return the user display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Returns the {@code Account} slug.
	 * <p>
	 * The slug for the {@code Account} is encoded from {@link Account#getDisplayName()} value.
	 * </p>
	 * 
	 * @return the slug
	 */
	public String getSlug() {
		if( slug==null ) slug = Slug.encode(displayName);
		return slug;
	}

	/**
	 * Indicates whether the {@code Account} is enabled or disabled.
	 * @return {@code true} if the account is enabled; {@code false} otherwise
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Indicates whether the {@code Account} is expired.
	 * @return {@code true} if the account is expired; {@code false} otherwise
	 */
	public boolean isExpired() {
		return expired;
	}

	/**
	 * Indicates whether the {@code Account} is locked or unlocked.
	 * @return {@code true} if the account is locked; {@code false} otherwise
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * Returns the roles granted to the {@code Account}.
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * Indicates whether some other {@code Account} is equal to this one.
	 * <p>
	 * Two accounts are equals if they have the same {@code email address}.
	 * </p>
	 * 
	 * @param obj the reference {@code Account} with which to compare
	 * @return {@code true} if this object is the same as the {@code obj} argument; {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if( obj==this ) return true;
		if( !(obj instanceof Account) ) return false;
		
		Account other = (Account) obj;
		return this.emailAddress.equals(other.emailAddress);
	}
	
	/**
	 * Returns a string representation of the {@code Account}.
     * <p>
     * This method returns a string equal to the value of:
	 * <blockquote>
     * <pre>
     * getEmailAddress() + '(' + getDisplayName() + ')'
     * </pre>
     * </blockquote>
	 * </p>
	 * @return a string representation of the {@code Account}
     */
	@Override
	public String toString() {
        return new StringBuilder()
			.append(getEmailAddress())
			.append(" (")
			.append(getDisplayName())
			.append(")")
			.toString();
    }
}
