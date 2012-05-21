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

/**
 * The enumeration of the model categories.
 * @author Carlo Micieli
 *
 */
public enum Category {
	/**
	 * The steam locomotives category.
	 */
	STEAM_LOCOMOTIVES,
	
	/**
	 * The electric locomotives category.
	 */
	ELECTRIC_LOCOMOTIVES,
	
	/**
	 * The diesel locomotives category.
	 */
	DIESEL_LOCOMOTIVES,
	
	/**
	 * The electric multiple unit category.
	 */
	ELECTRIC_MULTIPLE_UNIT,
	
	/**
	 * The train set category.
	 */
	TRAIN_SETS,
	
	/**
	 * The railcar category.
	 */
	RAILCARS,
	
	/**
	 * The freight cars category.
	 */
	FREIGHT_CARS,
	
	/**
	 * The passenger cars category.
	 */
	PASSENGER_CARS,
	
	/**
	 * The starter sets (usually includes the tracks) category.
	 */
	STARTER_SETS;
	
	/**
	 * Returns the category description to be stored in the database.
	 * @return the category name.
	 */
	public String keyValue() {
		return name().toLowerCase().replace('_', '-');
	}

	/**
	 * Parses the category value from the database.
	 * @param category the category name.
	 * @return a value from Category enumeration.
	 */
	public static Category parse(String category) {
		String c = category.toUpperCase().replace('-', '_');
		return Category.valueOf(c);
	}
}
