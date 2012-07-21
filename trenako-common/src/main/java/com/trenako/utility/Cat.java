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
package com.trenako.utility;

import com.trenako.CatFormatException;
import com.trenako.values.Category;
import com.trenako.values.PowerMethod;

/**
 * It represent a value that includes both the power method and
 * the category.
 * 
 * @author Carlo Micieli
 *
 */
public class Cat {
	
	private final PowerMethod powerMethod;
	private final Category category;
	
	/**
	 * Creates a new {@code Cat}.
	 * @param powerMethod the {@code PowerMethod} name
	 * @param category the {@code Category} name
	 */
	public Cat(PowerMethod powerMethod, Category category) {
		this.powerMethod = powerMethod;
		this.category = category;
	}
	
	/**
	 * Parses the string argument as a {@code Cat}.
	 * <p>
	 * The allowed format is {@code PowerMethod + '-' + Category}.
	 * </p>
	 * 
	 * @param s the string containing the {@code Cat} representation
	 * @return a {@code Cat}
	 */
	public static Cat parseString(String s) {
		if (s==null) {
			throw new IllegalArgumentException("Input string is null");
		}
		
		if (s.length()<4) {
			throw new CatFormatException("'" + s + "' is too short");
		}
		
		String pm = s.substring(0, 2);
		String c = s.substring(3); 
		validate(pm, c);
				
		Category category = Category.parse(c);
		PowerMethod powerMethod = PowerMethod.parse(pm);
		
		return new Cat(powerMethod, category);
	}
	
	/**
	 * Returns the {@code Category} component.
	 * @return the {@code Category}
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * Returns the {@code Category} label.
	 * @return the {@code Category} label
	 */
	public String category() {
		return getCategory().label();
	}
	
	/**
	 * Returns the {@code PowerMethod} component.
	 * @return the {@code PowerMethod}
	 */
	public PowerMethod getPowerMethod() {
		return powerMethod;
	}
	
	/**
	 * Returns the {@code PowerMethod} label.
	 * @return the {@code PowerMethod} label
	 */
	public String powerMethod() {
		return getPowerMethod().label();
	}
	
	/**
	 * Indicates whether some other {@code Cat} is equal to this one.
	 * @param obj the reference object with which to compare
	 * @return {@code true} if this object is the same as the obj argument; 
	 * {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj==this) return true;
		if( !(obj instanceof Cat) ) return false;
			
		Cat other = (Cat) obj;
		return this.powerMethod.equals(other.powerMethod) &&
				this.category.equals(other.category);
	}

	/**
	 * Returns a string representation of the object.
	 * <p>
	 * This method returns a string representation like:
	 * <blockquote>
	 * <pre>
	 * 'powerMethod=' + PowerMethod + ', category=' + Category
	 * </pre>
	 * </blockquote>
	 * </p>
	 * 
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return new StringBuilder()
			.append(powerMethod.label())
			.append("-")
			.append(category.label())
			.toString();
	}	

	private static void validate(String pm, String c) {
		if (!Category.list().contains(c)) {
			throw new CatFormatException("'" + c + "' illegal value for category");
		}
		
		if (!PowerMethod.list().contains(pm)) {
			throw new CatFormatException("'" + pm + "' illegal value for power method");
		}		
	}
}
