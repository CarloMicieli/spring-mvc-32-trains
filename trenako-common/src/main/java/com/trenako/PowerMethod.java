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
 * The power methods for the model.
 * @author Carlo Micieli
 *
 */
public enum PowerMethod {
	/**
	 * Alternating current (Maerklin).
	 */
	AC,
	/**
	 * Direct current.
	 */
	DC,
	/**
	 * Alternating current with digital interface (Maerklin).
	 */
	AC_DCC,
	/**
	 * Direct current with digital interface.
	 */
	DC_DCC,
	/**
	 * Alternating current with digital interface and sound (Maerklin).
	 */
	AC_DCC_SOUND,
	/**
	 * Direct current with digital interface and sound.
	 */
	DC_DCC_SOUND;
	
	/**
	 * Returns the category description to be stored in the database.
	 * @return the category name.
	 */
	public String label() {
		return name().toLowerCase().replace('_', '-');
	}

	/**
	 * Parses the category value from the database.
	 * @param category the category name.
	 * @return a value from Category enumeration.
	 */
	public static PowerMethod parse(String category) {
		String c = category.toUpperCase().replace('-', '_');
		return PowerMethod.valueOf(c);
	}
	
	/**
	 * 
	 * @return
	 */
	public static List<String> list() {
		int i = 0;
		String[] labels = new String[PowerMethod.values().length];
		for (PowerMethod pm : PowerMethod.values()) {
			labels[i++] = pm.label();
		}

		return Collections.unmodifiableList(
				Arrays.asList(labels));
	}
}
