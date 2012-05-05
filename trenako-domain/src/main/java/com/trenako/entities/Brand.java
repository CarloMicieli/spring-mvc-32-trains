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

import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * It represents a model railways company.
 * @author Carlo Micieli
 *
 */
@Document(collection = "brands")
public class Brand {

	@Id
	ObjectId id;

	@NotBlank(message = "brand.name.required")
	@Size(max = 25, message = "brand.name.size.notmet")
	@Indexed(unique = true)
	String name;

	@URL(message = "brand.website.url.invalid")
	String website;

	@Email(message = "brand.emailAddress.email.invalid")
	String emailAddress;
	
	@Size(max = 250, message = "brand.description.size.notmet")
	String description;
	
	/**
	 * Create a new brand with <em>null</em> value.
	 */
	public Brand() {
	}

	/**
	 * Create a new brand.
	 * @param id the unique id.
	 */
	public Brand(ObjectId id) {
		this.id = id;
	}
	
	/**
	 * Create a new brand.
	 * @param name the company name.
	 * @param website the company website url.
	 */
	public Brand(String name, String website) {
		this.name = name;
		this.website = website;
	}

	/**
	 * Returns the unique id for the <em>Brand</em>.
	 * @return the unique id.
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Sets the unique id for the <em>Brand</em>.
	 * @param id the unique id.
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Returns the company name.
	 * @return the company name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the company name.
	 * @param name the company name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the company website url.
	 * @return the company website url.
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * Sets the company website url.
	 * @param website the company website url.
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * Returns the short description for the <em>Brand</em>.
	 * @return the short description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the short description for the <em>Brand</em>.
	 * @param description the short description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the email address.
	 * @return the email address.
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the the email address.
	 * @param emailAddress the email address.
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Returns a string representation of this <em>Brand</em>.
	 * @return a string representation of the object.
	 */
	@Override
	public String toString() {
		return new StringBuffer()
			.append(getId() + ": ")
			.append(getName())
			.toString();
	}
}
