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
 * It represents a wrapper for the enumerations localizations.
 * 
 * @author Carlo Micieli
 *
 * @param <T> the inner {@code enum} to be localized
 */
public class LocalizedEnum<T extends Enum<T>> {

	private final T val;
	private final String message;
	
	/**
	 * Creates a new {@code LocalizedEnum}.
	 * @param val the {@code enum} value
	 * @param label the localized label string
	 */
	public LocalizedEnum(T val, String message) {
		this.val = val;
		this.message = message;
	}
	
	/**
	 * 
	 * @return
	 */
	public String label() {
		return val.name().toLowerCase().replace('_', '-');
	}
	
	/**
	 * Returns the current {@code enum} value.
	 * @return the value
	 */
	public T getValue() {
		return val;
	}

	/**
	 * Returns the localized message for this {@code enum} value.
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
}
