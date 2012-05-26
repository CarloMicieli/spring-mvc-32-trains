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

	private boolean enabled;
	private List<String> roles;
	
	/**
	 * Creates a new user account.
	 */
	public Account() {
	}
	
	/**
	 * Creates a new user account.
	 * @param emailAddress the user email address.
	 * @param password the password.
	 * @param displayName the user display name.
	 */
	public Account(String emailAddress, String password, String displayName) {
		this.emailAddress = emailAddress;
		this.password = password;
		this.displayName = displayName;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Returns the user email address.
	 * @return the user email address.
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the user email address.
	 * @param emailAddress the user email address.
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Returns the password.
	 * @return the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 * @param password the password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the user display name.
	 * @return the user display name.
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the user display name.
	 * @param displayName the user display name.
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.enabled = isEnabled;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}	
}
