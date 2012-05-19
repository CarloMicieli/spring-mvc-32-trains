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
	 * 
	 */
	public Option() {
	}
	
	/**
	 * Creates a new rolling stock option.
	 * @param name the option name.
	 * @param family the option family.
	 */
	public Option(String name, OptionFamily family) {
		this.name = name;
		this.family = family;
	}
	
	/**
	 * Gets the unique id for this option.	
	 * @return the id.
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Sets the unique id for this option.
	 * @param id the id.
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Returns the option name.
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the option name.
	 * @param name the name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the option family.
	 * @return the family.
	 */
	public OptionFamily getFamily() {
		return family;
	}

	/**
	 * Sets the option family.
	 * @param family the family.
	 */
	public void setFamily(OptionFamily family) {
		this.family = family;
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
		if( !(obj instanceof Option) ) return false;
		
		Option other = (Option)obj;
		return new EqualsBuilder()
			.append(name, other.name)
			.append(family, other.family)
			.isEquals();
	}
	
	/**
	 * 
	 * @return
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
	
	/**
	 * Creates an option instance for the DCC interface NEM-651.
	 * @return the option.
	 */
	public static Option Nem651() {
		final Option op = new Option("NEM-651", OptionFamily.DCC_INTERFACE);
		return op;
	}

	/**
	 * Creates an option instance for the DCC interface NEM-652.
	 * @return the option.
	 */	
	public static Option Nem652() {
		final Option op = new Option("NEM-652", OptionFamily.DCC_INTERFACE);
		return op;
	}
	
	/**
	 * Creates an option instance for white/red headlights.
	 * @return the option.
	 */
	public static Option WhiteRedHeadlights() {
		final Option op = new Option("White-Red", OptionFamily.HEADLIGHTS);
		return op;
	}
	
	/**
	 * Creates an option instance for white/red headlights changing according 
	 * to the route of march.
	 * 
	 * @return the option.
	 */
	public static Option WhiteRedAccordingMarchHeadlights() {
		final Option op = new Option("White-Red", OptionFamily.HEADLIGHTS);
		return op;
	}
	
}
