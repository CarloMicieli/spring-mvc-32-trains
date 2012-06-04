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
	
	private Address address;
	
	private Map<String, Address> branches;
	
	@URL(message = "brand.website.url.invalid")
	private String website;

	@Email(message = "brand.emailAddress.email.invalid")
	private String emailAddress;
	
	@Size(max = 250, message = "brand.description.size.notmet")
	private String description;
	
	private boolean industrial;
	
	private byte[] logo;
	
	private Date lastModified;
	
	// required by spring data
	Brand() {
	}

	/**
	 * Create a new brand.
	 * @param id the unique id
	 */
	public Brand(ObjectId id) {
		this.id = id;
	}
	
	/**
	 * Create a new brand.
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
	 * It represents a brand builder class.
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
		 * Creates a new brands builder.
		 * @param name the brand name
		 */
		public Builder(String name) {
			this.name = name;
		}
		
		/**
		 * The email address.
		 * @param e the email address
		 * @return this brand builder
		 */
		public Builder emailAddress(String e) {
			emailAddress = e;
			return this;
		}

		/**
		 * It indicates whether is using the die casting method. 
		 * @param b the industrial flag
		 * @return this brand builder
		 */
		public Builder industrial(boolean b) {
			industrial = b;
			return this;
		}

		/**
		 * The description.
		 * @param d the description
		 * @return this brand builder
		 */
		public Builder description(String d) {
			description = d;
			return this;
		}

		/**
		 * The brand slug.
		 * @param s the slug
		 * @return this brand builder
		 */
		public Builder slug(String s) {
			slug = s;
			return this;
		}
		
		/**
		 * The address.
		 * @param a the address
		 * @return this brand builder
		 */
		public Builder address(Address a) {
			address = a;
			return this;
		}
		
		/**
		 * The local address.
		 * @param country the country
		 * @param a the address
		 * @return this brand builder
		 */
		public Builder address(String country, Address a) {
			if( branches==null ) branches = new HashMap<String, Address>();
			branches.put(country, a);
			return this;
		}
		
		/**
		 * The website url.
		 * @param w the website url
		 * @return this brand builder
		 */
		public Builder website(String w) {
			website = w;
			return this;
		}
		
		/**
		 * Builds the brand instance.
		 * @return the brand
		 */
		public Brand build() {
			return new Brand(this);
		}
	}
		
	/**
	 * Returns the unique id for the brand.
	 * @return the unique id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Sets the unique id for the brand.
	 * @param id the unique id
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Returns the company name.
	 * @return the company name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the company name.
	 * @param name the company name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the company name slug.
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
	 * Sets the company name slug.
	 * @param slug the company name slug
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}
	
	/**
	 * Returns a local branch address.
	 * <p>
	 * If no branch exists for the provided country name then this
	 * method will return <em>null</em>.
	 * </p>
	 * 
	 * @param country the country name
	 * @return the address
	 */
	public Address getAddress(String country) {
		if( branches==null ) return null;
		return branches.get(country);
	}
	
	/**
	 * Sets a local branch address.
	 * 
	 * @param country the country name
	 * @param a the address
	 */
	public void setAddress(String country, Address a) {
		if( branches==null ) branches = new HashMap<String, Address>();
		branches.put(country, a);
	}

	/**
	 * Returns the address.
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
	 * Sets the address.
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
	 * Returns the company website url.
	 * @return the company website url
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * Sets the company website url.
	 * @param website the company website url
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * Returns the brand company description.
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the brand company description.
	 * @param description the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the email address.
	 * @return the email address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the the email address.
	 * @param emailAddress the email address
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Returns the industrial flag.
	 * @return <em>true</em> if this is a industrial series producer; 
	 * <em>false</em> otherwise
	 */
	public boolean isIndustrial() {
		return industrial;
	}

	/**
	 * Sets the industrial flag.
	 * @param industrial <em>true</em> if this is a industrial series producer;
	 * <em>false</em> otherwise
	 */
	public void setIndustrial(boolean industrial) {
		this.industrial = industrial;
	}

	/**
	 * Returns the brand image.
	 * @return the image
	 */
	public byte[] getLogo() {
		return logo;
	}

	/**
	 * Sets the brand image.
	 * @param logo the image
	 */
	public void setLogo(byte[] logo) {
		this.logo = logo;
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
	 * Returns a string representation of this <em>Brand</em>.
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return getName();
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * @param obj the reference object with which to compare
	 * @return <em>true</em> if this object is the same as the obj argument; 
	 * <em>false</em> otherwise
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
