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
package com.trenako.mapping;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import com.trenako.AppGlobals;

/**
 * It represents a container for different values for the same field, each
 * value is specific to a particular {@code Locale}.
 * 
 * @author Carlo Micieli
 *
 * @param <T>
 */
@SuppressWarnings("serial")
public class LocalizedField<T> extends LinkedHashMap<String, T> {

	/**
	 * Creates an empty {@code LocalizedField}.
	 */
	public LocalizedField() {
	}

	/**
	 * Creates a new {@code LocalizedField} with the provided
	 * default value.
	 * 
	 * @param defaultValue the default value
	 */
	public LocalizedField(T defaultValue) {
		put(AppGlobals.DEFAULT_LANGUAGE, defaultValue);
	}
	
	/**
	 * Builds a new {@code LocalizedField} with a value for the default {@code Locale}.
	 * @param value the value
	 * @return a {@code LocalizedField}
	 */
	public static <T> LocalizedField<T> localize(T value) {
		return new LocalizedField<T>(value);
	}
	
	/**
	 * Builds a new {@code LocalizedField} using the values from the provided map.
	 * @param values the map
	 * @return a {@code LocalizedField}
	 */
	public static <T> LocalizedField<T> localize(Map<String, T> values) {
		LocalizedField<T> field = new LocalizedField<T>();
		
		for (Map.Entry<String, T> entry : values.entrySet()) {
			field.put(new Locale(entry.getKey()), entry.getValue());
		}
		
		return field;
	}
	
	/**
	 * Returns the localized value for the provided {@code Locale}.
	 * @param lang the {@code Locale}
	 * @return the value to which the specified {@code Locale} is mapped; 
	 * 			or {@code null} if no value is mapped with the {@code Locale}
	 */
	public T get(Locale lang) {
		return get(keyFromLocale(lang));
	}

	/**
	 * Associates the provided value with the corresponding {@code Locale}.
	 * @param lang the {@code Locale}
	 * @param value the localized value
	 * @return the previous value associated with this {@code Locale}; {@code null} 
	 * 			if there was no mapping for {@code Locale}. 
 	 */
	public T put(Locale lang, T value) {
		return this.put(keyFromLocale(lang), value);
	}

	/**
	 * Checks whether the current field has default value.
	 * @return {@code true} if contains a default value; {@code false} otherwise
	 */
	public boolean containsDefault() {
		return containsKey(AppGlobals.DEFAULT_LANGUAGE);
	}

	/**
	 * Sets the value for the default {@code Locale}.
	 * @param defaultValue the new default value
	 * @return the previous value associated with the default {@code Locale}; {@code null} 
	 * 			if there was no mapping for {@code Locale}. 
	 */
	public T setDefault(T defaultValue) {
		return put(AppGlobals.DEFAULT_LANGUAGE, defaultValue);
	}
	
	/**
	 * Returns the value for the default {@code Locale}.
	 * @return the default value
	 */
	public T getDefault() {
		return get(AppGlobals.DEFAULT_LANGUAGE);
	}
	
	/**
	 * Returns the value for the provided {@code Locale}.
	 * @param lang the {@code Locale}
	 * @return the value for the {@code Locale} if exits; {@code null} otherwise
	 */
	public T getValue(Locale lang) {
		T val = get(lang);
		if (val == null) {
			val = getDefault();
		}

		return val;
	}
	
	/**
	 * Returns the {@code Locale} list to be managed.
	 * @param currentLocale the current user's {@code Locale}
	 * @return the {@code Locale} list
	 */
	public static Iterable<Locale> locales(Locale currentLocale) {
		if (currentLocale.equals(AppGlobals.DEFAULT_LOCALE)) {
			return Collections.unmodifiableList(Arrays.asList(AppGlobals.DEFAULT_LOCALE));
		}
		return Collections.unmodifiableList(Arrays.asList(AppGlobals.DEFAULT_LOCALE, currentLocale));
	}
	
	private String keyFromLocale(Locale locale) {
		return locale.getLanguage();
	}
}
