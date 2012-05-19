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

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
	
	@NotBlank(message = "railway.country.required")
	@Size(max = 3, message = "railway.country.size.notmet")
	private String country;
	
	@Size(max = 100, message = "railway.companyName.size.notmet")
	private String companyName;
	
	private byte[] image;
	private Date lastModified;
	
	/**
	 * Create a new railway.
	 */
	public Railway() {
	}
	
	/**
	 * Create a new railway.
	 * @param name the railway name.
	 */
	public Railway(String name) {
		this.name = name;
	}

	/**
	 * Create a new railway.
	 * @param id the unique railway id.
	 */
	public Railway(ObjectId id) {
		this.id = id;
	}

	/**
	 * Returns the unique id for the <em>Railway</em>.
	 * @return the unique id.
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Sets the unique id for the <em>Railway</em>.
	 * @param id the unique id.
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Returns the railway name.
	 * @return the railway name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the railway name
	 * @param name the railway name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the full company name.
	 * @return the full company name.
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the full company name.
	 * @param companyName the full company name.
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	/**
	 * Returns the country ISO 3166-1 alpha-3 code.
	 * @return the country code.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country ISO 3166-1 alpha-3 code.
	 * @param country the country code.
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Returns the railway image.
	 * @return the image.
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * Sets the railway image.
	 * @param image the image.
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * Returns a string representation of this <em>Railway</em>.
	 * @return a string representation of the object.
	 */
	@Override
	public String toString() {
		return new StringBuffer()
			.append(getId() + ": ")
			.append(getName())
			.toString();
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * @param obj the reference object with which to compare.
	 * @return <em>true</em> if this object is the same as the obj argument; 
	 * <em>false</em> otherwise.
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
	 * Returns a hash code value for the <strong>Railway</strong>.
	 * @return a hash code value for this object. 
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