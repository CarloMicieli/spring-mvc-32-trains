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

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trenako.utility.Slug;

/**
 * It represents a operator of the rail transport.
 * @author Carlo Micieli
 *
 */
@Document(collection = "railways")
public class Railway {
	
	@Id
	private ObjectId id;
	
	@NotBlank(message = "railway.name.required")
	@Size(max = 10, message = "railway.name.size.notmet")
	@Indexed(unique = true)
	private String name;
	
	@Indexed(unique = true)
	private String slug; 
	
	@Size(max = 100, message = "railway.companyName.size.notmet")
	private String companyName;
	
	@NotBlank(message = "railway.country.required")
	@Size(max = 2, message = "railway.country.size.notmet")
	private String country;
	
	@Past(message = "railway.operatingSince.past.notmet")
	private Date operatingSince;
	@Past(message = "railway.operatingUntil.past.notmet")
	private Date operatingUntil;

	private Date lastModified;
	
	/**
	 * Creates a new empty {@code Railway}.
	 */
	public Railway() {
	}
	
	/**
	 * Creates a new {@code Railway} with the provided name.
	 * @param name the railway name.
	 */
	public Railway(String name) {
		this.name = name;
	}
	
	public Railway(ObjectId id) {
		this.id = id;
	}
	
	private Railway(Builder b) {
		this.name = b.name;
		this.companyName = b.companyName;
		this.country = b.country;
		this.operatingSince = b.operatingSince;
		this.operatingUntil = b.operatingUntil;
		this.slug = b.slug;
	}
	
	/**
	 * The railway builder class.
	 * @author Carlo Micieli
	 *
	 */
	public static class Builder {
		// required fields
		private final String name;
		
		// optional fields
		private String companyName = "";
		private String country = "";
		private String slug = "";
		
		private Date operatingSince = null;
		private Date operatingUntil = null;
		
		public Builder(String name) {
			this.name = name;
		}
		
		public Builder companyName(String cm) {
			this.companyName = cm;
			return this;
		}
		
		public Builder country(String c) {
			this.country = c;
			return this;
		}
		
		public Builder slug(String s) {
			this.slug = s;
			return this;
		}
		
		public Builder operatingSince(Date start) {
			this.operatingSince = start;
			return this;
		}
		
		public Builder operatingUntil(Date end) {
			this.operatingUntil = end;
			return this;
		}
		
		public Railway build() {
			return new Railway(this);
		}
	}
	
	/**
	 * Returns the unique id for the {@code Railway}.
	 * @return the unique id
	 */
	public ObjectId getId() {
		return id;
	}
	
	/**
	 * Sets the {@code Railway} id.
	 * @param id the unique id
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Returns the railway name.
	 * <p>
	 * This field contains the acronym from the company name.
	 * </p>
	 * 
	 * @return the railway name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the railway name.
	 * <p>
	 * This field contains the acronym from the company name.
	 * </p>
	 * 
	 * @param name the railway name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the full company name.
	 * @return the full company name
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the full company name.
	 * @param companyName the full company name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	/**
	 * Returns the country code.
	 * <p>
	 * The country is following ISO 3166-1 alpha-3 code standard.
	 * </p>
	 * 
	 * @return the country code
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country code.
	 * <p>
	 * The country is following ISO 3166-1 alpha-3 code standard.
	 * </p>
	 * 
	 * @param country the country code
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Returns the starting year of operations.
	 * @return the year
	 */
	public Date getOperatingSince() {
		return operatingSince;
	}

	/**
	 * Sets the starting year of operations.
	 * @param operatingSince the year
	 */
	public void setOperatingSince(Date operatingSince) {
		this.operatingSince = operatingSince;
	}

	/**
	 * Returns the finishing year of operations.
	 * @return the year
	 */
	public Date getOperatingUntil() {
		return operatingUntil;
	}

	/**
	 * Sets the finishing year of operations.
	 * @param operatingUntil the year
	 */
	public void setOperatingUntil(Date operatingUntil) {
		this.operatingUntil = operatingUntil;
	}

	/**
	 * Returns the slug for the railway.
	 * <p>
	 * If the slug is not set this method will return
	 * the encoded value for {@link Railway#getName()}.
	 * </p>
	 * 
	 * @return the slug
	 */
	public String getSlug() {
		if( slug==null ) slug = Slug.encode(getName());
		return slug;
	}

	/**
	 * Sets the slug for the railway.
	 * @param slug the slug
	 */
	public void setSlug(String slug) {
		this.slug = slug;
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
	 * Returns a label for the current {@code Railway}.
	 * <p>
	 * The application will show this value as string representation
	 * for the current {@code Railway} instead of the usual {@code Railway#toString()}.
	 * </p>
	 * @return
	 */
	public String label() {
		return new StringBuilder()
			.append(getName())
			.append(" (")
			.append(getCompanyName())
			.append(")").toString();
	}
	
	/**
	 * Returns a string representation of the object.
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return new StringBuilder()
			.append(getId() + ": ")
			.append(getName())
			.toString();
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
		if( !(obj instanceof Railway) ) return false;
		
		Railway other = (Railway)obj;
		return new EqualsBuilder()
			.append(name, other.name)
			.append(companyName, other.companyName)
			.append(country, other.country)
			.isEquals();
	}
	
	/**
	 * Returns a hash code value for the object.
	 * @return a hash code value for this object
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(15, 37)
			.append(name)
			.append(companyName)
			.append(country)
			.hashCode();
	}
}
