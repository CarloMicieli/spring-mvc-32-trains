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

/**
 * The enumeration of option family for a rolling stock model.
 * @author Carlo Micieli
 *
 */
public enum OptionFamily {
	/**
	 * The digital control interface.
	 */
	DCC_INTERFACE,
	
	/**
	 * The head lights configuration.
	 */
	HEADLIGHTS,
	
	/**
	 * The transmission configuration.
	 */
	TRANSMISSION,
	
	/**
	 * The coupler plug.
	 */
	COUPLER;

	/**
	 * Returns the key value for the current option family.
	 * @return the key value
	 */
	public String label() {
		return LocalizedEnum.buildKey(this);
	}

	/**
	 * Parses the option family value from the database.
	 * @param family the family name.
	 * @return a value from OptionFamily enumeration.
	 */
	public static OptionFamily parse(String family) {
		return LocalizedEnum.parseString(family, OptionFamily.class);
	}	
}