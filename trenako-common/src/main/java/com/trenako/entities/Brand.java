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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trenako.utility.Slug;
import com.trenako.validation.constraints.ValidAddress;

/**
 * It represents a model railways rolling stock manufacturer.
 * <p>
 * Two distinct family of manufacturer exists:
 * <ul>
 * <li><strong>industrial</strong>: these manufactures produce models using the die casting method;</li>
 * <li><strong>brass models</strong>: these manufacturers produce models which are made of brass or
 * similar alloys. They are usually more expensive than the industrial series due to the limited
 * production quantities and the <em>"hand made"</em> nature of the production.</li>
 * </ul>
 * </p>
 * <p>
 * This class allows one company to have local branches by country, either a local 
 * office or a distribution company.
 * </p>
 * 
 * @author Carlo Micieli
 *
 */
@Document(collection = "brands")
public class Brand {

	@Id
	private ObjectId id;

	@NotBlank(message = "brand.name.required")
	@Size(max = 25, message = "brand.name.size.notmet")
	@Indexed(unique = true)
	private String name;

	@Indexed(unique = true)
	private String slug;
	
	@ValidAddress(message = "brand.address.invalid")
	@Valid
	private Address address;
	
	private Map<String, Address> branches;
	
	@URL(message = "brand.website.url.invalid")
	private String website;

	@Email(message = "brand.emailAddress.email.invalid")
	private String emailAddress;
	
	@Size(max = 250, message = "brand.description.size.notmet")
	private String description;
	
	private boolean industrial;
	
	private Date lastModified;
	
	// required by spring data
	public Brand() {
	}

	/**
	 * Creates a new {@code Brand}.
	 * @param id the unique id
	 */
	public Brand(ObjectId id) {
		this.id = id;
	}
	
	/**
	 * Creates a new {@code Brand}.
	 * @param name the company name
	 */
	public Brand(String name) {
		this.name = name;
	}

	private Brand(Builder b) {
		this.name = b.name;
		this.address = b.address;
		this.website = b.website;
		this.emailAddress = b.emailAddress;
		this.description = b.description;
		this.industrial = b.industrial;
		this.slug = b.slug;
		this.branches = b.branches;
	}
	
	/**
	 * It represents a {@code Brand} builder class.
	 * @author Carlo Micieli
	 *
	 */
	public static class Builder {
		// required fields
		private final String name;
		
		// optional fields
		private String website = null;
		private String emailAddress = null;
		private String description = null;
		private boolean industrial = false;
		private Address address = null;
		private String slug = null;
		private HashMap<String, Address> branches = null;
		
		/**
		 * Creates a new {@code Brand} builder.
		 * @param name the brand name
		 */
		public Builder(String name) {
			this.name = name;
		}
		
		/**
		 * The email address.
		 * @param emailAddress the email address
		 * @return a {@code Brand} builder
		 */
		public Builder emailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
			return this;
		}

		/**
		 * It indicates whether is using the die casting method. 
		 * @param industrial the industrial flag
		 * @return a {@code Brand} builder
		 */
		public Builder industrial(boolean industrial) {
			this.industrial = industrial;
			return this;
		}

		/**
		 * The description.
		 * @param desc the description
		 * @return a {@code Brand} builder
		 */
		public Builder description(String desc) {
			this.description = desc;
			return this;
		}

		/**
		 * The brand slug.
		 * @param slug the slug
		 * @return a {@code Brand} builder
		 */
		public Builder slug(String slug) {
			this.slug = slug;
			return this;
		}
		
		/**
		 * The address.
		 * @param addr the address
		 * @return a {@code Brand} builder
		 */
		public Builder address(Address addr) {
			address = addr;
			return this;
		}
		
		/**
		 * The local address.
		 * <p>
		 * The country code is one of the {@code ISO 3166-1 alpha-2} 
		 * standard codes.
		 * </p>
		 * 
		 * @param country the country code
		 * @param a the address
		 * @return a {@code Brand} builder
		 */
		public Builder address(String country, Address a) {
			if( branches==null ) branches = new HashMap<String, Address>();
			branches.put(country, a);
			return this;
		}
		
		/**
		 * The website url.
		 * @param url the website url
		 * @return a {@code Brand} builder
		 */
		public Builder website(String url) {
			website = url;
			return this;
		}
		
		/**
		 * Builds a {@code Brand} object using the values for this builder.
		 * @return a {@code Brand} builder
		 */
		public Brand build() {
			return new Brand(this);
		}
	}
		
	/**
	 * Returns the {@code Brand} id.
	 * @return the unique id
	 */
	public ObjectId getId() {
		return id;
	}
	
	/**
	 * Sets the {@code Brand} id.
	 * @param id the unique id
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	/**
	 * Returns the {@code Brand} company name.
	 * @return the company name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the {@code Brand} company name.
	 * @param name the company name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the {@code Brand} slug.
	 * <p>
	 * If this property is not set then this method will return
	 * the encoded value for {@link Brand#getName()}.
	 * </p>
	 * 
	 * @return the slug
	 */
	public String getSlug() {
		if( slug==null ) slug = Slug.encode(name);
		return slug;
	}
	
	/**
	 * Returns a {@code Brand} local branch address.
	 * <p>
	 * If no branch exists for the provided country code then this
	 * method will return {@code null}.
	 * </p>
	 * <p>
	 * The country code is one of the {@code ISO 3166-1 alpha-2} 
	 * standard codes.
	 * </p>
	 *  
	 * @param country the country code
	 * @return the address
	 */
	public Address getAddress(String country) {
		if( branches==null ) return null;
		return branches.get(country);
	}
	
	/**
	 * Sets a {@code Brand} local branch address.
	 * <p>
	 * The country code is one of the {@code ISO 3166-1 alpha-2} 
	 * standard codes.
	 * </p>
	 * 
	 * @param country the country code
	 * @param a the address
	 */
	public void setAddress(String country, Address a) {
		if( branches==null ) branches = new HashMap<String, Address>();
		branches.put(country, a);
	}

	/**
	 * Returns the {@code Brand} address.
	 * <p>
	 * The company can have more local branch, this method will
	 * return the main address.
	 * </p>
	 * 
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Sets the {@code Brand} address.
	 * <p>
	 * This method will set the main address for the company.
	 * </p>
	 * 
	 * @param address the address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Returns the {@code Brand} website url.
	 * @return the website url
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * Sets the {@code Brand} website url.
	 * @param website the website url
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * Returns the {@code Brand} company description.
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the {@code Brand} company description.
	 * @param description the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the {@code Brand} email address.
	 * @return the email address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the {@code Brand} email address.
	 * @param emailAddress the email address
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Returns the industrial flag.
	 * @return {@code true} if this is a industrial series producer; 
	 * {@code false} otherwise
	 */
	public boolean isIndustrial() {
		return industrial;
	}

	/**
	 * Sets the industrial flag.
	 * @param industrial {@code true} if this is a industrial series 
	 * producer; {@code false} otherwise
	 */
	public void setIndustrial(boolean industrial) {
		this.industrial = industrial;
	}

	/**
	 * Returns the last modified timestamp.
	 * @return the timestamp
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * Sets the last modified timestamp.
	 * @param lastModified the timestamp
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * Returns a string representation of this {@code Brand}.
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return getName();
	}
	
	/**
	 * Indicates whether some other {@code Brand} is equal to this one.
	 * @param obj the reference object with which to compare
	 * @return {@code true} if this object is the same as the obj argument; 
	 * {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if( this==obj ) return true;
		if( !(obj instanceof Brand) ) return false;
		
		Brand other = (Brand)obj;
		return new EqualsBuilder()
			.append(name, other.name)
			.append(website, other.website)
			.append(emailAddress, other.emailAddress)
			.append(description, other.description)
			.append(industrial, other.industrial)
			.isEquals();
	}
	
	/**
	 * Returns a hash code value for this object.
	 * @return a hash code value for this object
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(15, 37)
			.append(getName())
			.append(getWebsite())
			.append(getEmailAddress())
			.append(getDescription())
			.append(isIndustrial())
			.toHashCode();
	}
}
