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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.MessageSource;

import com.trenako.CatFormatException;
import com.trenako.values.Category;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;

/**
 * It represent a value that includes both the power method and
 * the category.
 * 
 * @author Carlo Micieli
 *
 */
public class Cat {
	
	private final LocalizedEnum<PowerMethod> powerMethod;
	private final LocalizedEnum<Category> category;
	
	private Cat(LocalizedEnum<PowerMethod> powerMethod, LocalizedEnum<Category> category) {
		this.powerMethod = powerMethod;
		this.category = category;
	}
	
	private Cat(PowerMethod powerMethod, Category category) {
		this(new LocalizedEnum<PowerMethod>(powerMethod), new LocalizedEnum<Category>(category));
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
		if (s == null) {
			throw new IllegalArgumentException("Input string is null");
		}
		
		if (s.length() < 4) {
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
	 * Builds a new localized {@code Cat}.
	 * @param pm the power method value
	 * @param category the category value
	 * @return a {@code Cat}
	 */
	public static Cat buildCat(PowerMethod powerMethod, Category category) {
		return buildCat(powerMethod, category, null);
	}
	
	
	public static Cat buildCat(PowerMethod powerMethod, Category category, MessageSource messageSource) {
		return new Cat(
				new LocalizedEnum<PowerMethod>(powerMethod, messageSource, null),
				new LocalizedEnum<Category>(category, messageSource, null));
	}
	
	/**
	 * Builds a new localized {@code Cat}.
	 * @param pm the power method value
	 * @param category the category value
	 * @param messageSource the message source
	 * @return a {@code Cat}
	 */
	public static Cat buildCat(String pm, String category, MessageSource messageSource) {
		return new Cat(
				LocalizedEnum.parseString(pm, messageSource, PowerMethod.class),
				LocalizedEnum.parseString(category, messageSource, Category.class));
	}
	
	/**
	 * Returns the localized list of category labels by power method.
	 * @param pm the power method
	 * @param ms the message source
	 * @return the list of {@code Cat}
	 */
	public static Iterable<Cat> list(PowerMethod pm, MessageSource ms) {
		List<Cat> list = new ArrayList<Cat>(Category.values().length);
		for (Category c : Category.values()) {
			list.add(buildCat(pm, c, ms));
		}
				
		return Collections.unmodifiableList(list);
	}
	
	/**
	 * Returns the {@code Category} component.
	 * @return the {@code Category}
	 */
	public Category getCategory() {
		return category.getValue();
	}

	/**
	 * Returns the {@code Category} label.
	 * @return the {@code Category} label
	 */
	public String category() {
		return category.getLabel();
	}
	
	/**
	 * Returns the {@code PowerMethod} component.
	 * @return the {@code PowerMethod}
	 */
	public PowerMethod getPowerMethod() {
		return powerMethod.getValue();
	}
	
	/**
	 * Returns the {@code PowerMethod} label.
	 * @return the {@code PowerMethod} label
	 */
	public String powerMethod() {
		return powerMethod.getLabel();
	}
	
	/**
	 * The label for the current {@code Cat}
	 * @return the label
	 */
	public String label() {
		return new StringBuilder()
			.append(powerMethod())
			.append(" ")
			.append(category())
			.toString();
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
	 * powerMethod + '-' + category
	 * </pre>
	 * </blockquote>
	 * </p>
	 * 
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return new StringBuilder()
			.append(powerMethod.getKey())
			.append("-")
			.append(category.getKey())
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
