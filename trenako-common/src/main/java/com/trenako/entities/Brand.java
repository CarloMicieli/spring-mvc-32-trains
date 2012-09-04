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

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

import com.trenako.mapping.DbReferenceable;
import com.trenako.mapping.LocalizedField;
import com.trenako.utility.Slug;
import com.trenako.validation.constraints.ContainsDefault;
import com.trenako.validation.constraints.ValidAddress;

/**
 * It represents a model railways rolling stock manufacturer.
 * <p>
 * Two distinct family of manufacturer exists:
 * <ul>
 * <li>{@code industrial}: these manufactures produce models using the die casting method;</li>
 * <li>{@code brass models}: these manufacturers produce models which are made of brass or
 * similar alloys. They are usually more expensive than the industrial series due to the limited
 * production quantities and the {@code "hand made"} nature of the production.</li>
 * </ul>
 * </p>
 * <p>
 * This class allows one company to have local branches for a given country, either a local 
 * office or a distribution company.
 * </p>
 * 
 * @author Carlo Micieli
 *
 */
@Document(collection = "brands")
public class Brand implements DbReferenceable {

	@Id
	private ObjectId id;

	@NotBlank(message = "brand.name.required")
	@Size(max = 25, message = "brand.name.size.notmet")
	@Indexed(unique = true)
	private String name;
	
	private String companyName;

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
	
	@NotNull(message = "brand.description.required")
	@ContainsDefault(message = "brand.description.default.required")
	private LocalizedField<String> description;
	
	private boolean industrial;
	
	private Set<String> scales;
	
	private Date lastModified;
	
	/**
	 * Creates an empty {@code Brand}.
	 */
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
		
		this.id = b.id;
		this.companyName = b.companyName;
		this.address = b.address;
		this.website = b.website;
		this.emailAddress = b.emailAddress;
		this.description = b.description;
		this.industrial = b.industrial;
		this.slug = b.slug;
		this.branches = b.branches;
		this.scales = b.scales;
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
		private ObjectId id = null;
		private String companyName = null;
		private String website = null;
		private String emailAddress = null;
		private LocalizedField<String> description = null;
		private boolean industrial = false;
		private Address address = null;
		private String slug = null;
		private Set<String> scales = null;
		private HashMap<String, Address> branches = null;
		
		public Builder(String name) {
			this.name = name;
		}
		
		public Builder id(ObjectId id) {
			this.id = id;
			return this;
		}
		
		public Builder companyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
		
		public Builder emailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
			return this;
		}

		public Builder industrial(boolean industrial) {
			this.industrial = industrial;
			return this;
		}

		public Builder description(String desc) {
			if (this.description == null) {
				this.description = new LocalizedField<String>(desc);
			}
			else {
				this.description.setDefault(desc);
			}
			return this;
		}

		public Builder description(Locale lang, String desc) {
			if (this.description == null) {
				this.description = new LocalizedField<String>();
			}
			this.description.put(lang, desc);
			return this;
		}

		public Builder slug(String slug) {
			this.slug = slug;
			return this;
		}
		
		public Builder address(Address addr) {
			address = addr;
			return this;
		}
		
		public Builder address(String country, Address a) {
			if (branches == null) {
				branches = new HashMap<String, Address>();
			}
			branches.put(country, a);
			return this;
		}
		
		public Builder website(String url) {
			website = url;
			return this;
		}
		
		public Builder scales(String... scales) {
			this.scales = new HashSet<String>(Arrays.asList(scales));
			return this;
		}
		
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
	 * Returns the {@code Brand} name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the {@code Brand} name.
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the {@code Brand} company name.
	 * @return the company name
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the {@code Brand} company name.
	 * @param companyName the company name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	/**
	 * Returns a label for the current {@code Brand}.
	 * <p>
	 * The application will show this value as string representation
	 * for the current {@code Brand} instead of the usual {@code Brand#toString()}.
	 * </p>
	 * @return the {@code Brand} label string
	 */
	@Override
	public String getLabel() {
		return name;
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
	@Override
	public String getSlug() {
		if (slug == null) {
			slug = Slug.encode(name);
		}
		return slug;
	}
	
	/**
	 * Sets the {@code Brand} slug.
	 * @param slug the slug
	 */
	public void setSlug(String slug) {
		this.slug = slug;
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
		if (branches == null) {
			return null;
		}
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
		if (branches == null) {
			branches = new HashMap<String, Address>();
		}
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
	public LocalizedField<String> getDescription() {
		return description;
	}

	/**
	 * Sets the {@code Brand} company description.
	 * @param description the description
	 */
	public void setDescription(LocalizedField<String> description) {
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
	 * Returns the scales list.
	 * @return the scales list
	 */
	public Set<String> getScales() {
		return scales;
	}

	/**
	 * Sets the scales list.
	 * @param scales the scales list
	 */
	public void setScales(Set<String> scales) {
		this.scales = scales;
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
		if (this == obj) return true;
		if (!(obj instanceof Brand)) return false;
		
		Brand other = (Brand) obj;
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