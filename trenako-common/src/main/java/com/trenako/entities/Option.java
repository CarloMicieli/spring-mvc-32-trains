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

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * It represents a rolling stock option
 * (DCC interface, head lights configuration, coupler...).
 * @author Carlo Micieli
 *
 */
@Document(collection = "options")
public class Option {
	@Id
	private ObjectId id;
	
	@NotBlank(message = "option.name.required")
	private String name;
	
	@NotNull(message = "option.family.required")
	private OptionFamily family;
	
	/**
	 * Creates an empty rolling stock {@code Option}.
	 */
	public Option() {
	}
	
	/**
	 * Creates a new rolling stock {@code Option}.
	 * @param name the option name
	 * @param family the option family
	 */
	public Option(String name, OptionFamily family) {
		this.name = name;
		this.family = family;
	}
	
	/**
	 * Gets the unique id for this option.	
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Sets the unique id for this option.	
	 * @param id the id
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	/**
	 * Returns the option name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the option name.
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the option family.
	 * @return the family
	 */
	public OptionFamily getFamily() {
		return family;
	}

	/**
	 * Sets the option family.
	 * @param family the family
	 */
	public void setFamily(OptionFamily family) {
		this.family = family;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * @param obj the reference object with which to compare
	 * @return {@code true} if this object is the same as the obj argument; 
	 * {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if( this==obj ) return true;
		if( !(obj instanceof Option) ) return false;
		
		Option other = (Option)obj;
		return new EqualsBuilder()
			.append(name, other.name)
			.append(family, other.family)
			.isEquals();
	}
	
	/**
	 * Converts the object to a string.
	 * @return a string representation of this class object.
	 */
	@Override
	public String toString() {
		return new StringBuilder()
			.append(name)
			.append(" (")
			.append(family)
			.append(")")
			.toString();
	}	
}
