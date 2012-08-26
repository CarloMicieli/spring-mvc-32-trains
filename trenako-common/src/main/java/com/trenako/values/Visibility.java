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

import org.apache.commons.lang3.StringUtils;

/**
 * The content visibility enum.
 * 
 * @author Carlo Micieli
 *
 */
public enum Visibility {
	PUBLIC,
	PRIVATE;
	
	/**
	 * Gets the {@code Visibility} label
	 * @return the label
	 */
	public String label() {
		return LocalizedEnum.buildKey(this);
	}

	/**
	 * Parses the string argument as a {@code Visibility}.
	 * @param vis the string to be parsed
	 * @return a {@code Visibility} value
	 */
	public static Visibility parse(String vis) {
		return LocalizedEnum.parseString(vis, Visibility.class);
	}
	
	/**
	 * Parses the string argument as a {@code Visibility}.
	 * @param vis the string to be parsed
	 * @param defaultVis the default {@code Visibility} value 
	 * @return a {@code Visibility} value
	 */
	public static Visibility parse(String vis, Visibility defaultVis) {
		if (StringUtils.isBlank(vis)) {
			return defaultVis;
		}
		return LocalizedEnum.parseString(vis, Visibility.class);
	}
}