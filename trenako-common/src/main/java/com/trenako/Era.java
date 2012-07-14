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
 * The model railways eras enumeration.
 * @author Carlo Micieli
 *
 */
public enum Era {
	/**
	 * Era I: country & private railways.
	 */
	I,
	/**
	 * Era II: the period after the formation of large state railways. 
	 */
	II,
	/**
	 * Era III: the new organization of European railroads.
	 */
	III,
	/**
	 * Era IV: standardized computer lettering on all rolling stock.
	 */
	IV,
	/**
	 * Era V: the modern era of railroading.
	 */
	V,
	/**
	 * Era VI
	 */
	VI;

	/**
	 * The list of labels for the {@code Era} enum.
	 * @return the labels list
	 */
	public static List<String> list() {
		int i = 0;
		String[] labels = new String[Era.values().length]; 
		for (Era e : Era.values()) {
			labels[i++] = e.name();
		}
			
		return Collections.unmodifiableList(Arrays.asList(labels));
	}
}
