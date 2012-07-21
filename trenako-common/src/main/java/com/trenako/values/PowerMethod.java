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
package com.trenako.values;

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
	DC;
	
	/**
	 * Gets the {@code PowerMethod} label.
	 * @return the label
	 */
	public String label() {
		return LocalizedEnum.buildLabel(this);
	}

	/**
	 * Parses the string argument as a {@code PowerMethod}.
	 * @param s the string to be parsed
	 * @return a {@code PowerMethod} value
	 */
	public static PowerMethod parse(String s) {
		return LocalizedEnum.parseString(s, PowerMethod.class);
	}
	
	/**
	 * Returns the list of {@code PowerMethod} labels.
	 * @return the {@code PowerMethod} list
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
