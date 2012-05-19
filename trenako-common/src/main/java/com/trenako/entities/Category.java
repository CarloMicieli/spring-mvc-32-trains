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
	STEAM_LOCOMOTIVES,
	ELECTRIC_LOCOMOTIVES,
	DIESEL_LOCOMOTIVES,
	ELECTRIC_MULTIPLE_UNIT,
	TRAIN_SETS,
	RAILCARS,
	FREIGHT_CARS,
	PASSENGER_CARS,
	STARTER_SETS;
	
	/**
	 * Returns the category description to be stored in the database.
	 * @return the category name.
	 */
	public String getDescription() {
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