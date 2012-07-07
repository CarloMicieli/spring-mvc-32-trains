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
package com.trenako;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
	 * The diesel locomotives category.
	 */
	DIESEL_LOCOMOTIVES,
	
	/**
	 * The electric locomotives category.
	 */
	ELECTRIC_LOCOMOTIVES,
	
	/**
	 * The railcar category.
	 */
	RAILCARS,
	
	/**
	 * The electric multiple unit category.
	 */
	ELECTRIC_MULTIPLE_UNIT,
	
	/**
	 * The freight cars category.
	 */
	FREIGHT_CARS,
	
	/**
	 * The passenger cars category.
	 */
	PASSENGER_CARS,
	
	/**
	 * The train set category.
	 */
	TRAIN_SETS,
	
	/**
	 * The starter sets (usually includes the tracks) category.
	 */
	STARTER_SETS;
	
	/**
	 * Returns the category description to be stored in the database.
	 * @return the category value
	 */
	public String label() {
		return name().toLowerCase().replace('_', '-');
	}

	/**
	 * Parses the category value from the database.
	 * @param category the category value
	 * @return a value from Category enumeration
	 */
	public static Category parse(String category) {
		String c = category.toUpperCase().replace('-', '_');
		return Category.valueOf(c);
	}

	/**
	 * Returns the list of category labels by power method.
	 * @param isDc the power method
	 * @return the list of categories
	 */
	public static Iterable<String> list(boolean isDc) {
		String pm = isDc ? "dc" : "ac";
		
		int i = 0;
		String[] labels = new String[Category.values().length];
		for (Category cat : Category.values()) {
			labels[i++] = String.format("category-%s-%s", pm, cat.label());
		}

		return Collections.unmodifiableList(
				Arrays.asList(labels));
	}

	public static List<String> list() {
		int i = 0;
		String[] labels = new String[Category.values().length];
		for (Category cat : Category.values()) {
			labels[i++] = cat.label();
		}

		return Collections.unmodifiableList(
				Arrays.asList(labels));
	}
}
