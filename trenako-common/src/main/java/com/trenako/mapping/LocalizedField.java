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

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.trenako.AppGlobals;

/**
 * It represents a container for different values for the same field, each
 * value is specific to a particular {@code Locale}.
 * 
 * @author Carlo Micieli
 *
 * @param <T>
 */
public class LocalizedField<T> extends AbstractMap<Locale, T> {

	private Map<Locale, T> values;
	
	/**
	 * Creates an empty {@code LocalizedField}.
	 */
	public LocalizedField() {
		values = new HashMap<Locale, T>();
	}

	/**
	 * Creates a new {@code LocalizedField} with the provided
	 * default value.
	 * 
	 * @param defaultValue the default value
	 */
	public LocalizedField(T defaultValue) {
		this();
		putDefault(defaultValue);
	}
	
	@Override
	public T get(Object lang) {
		return values.get(lang);
	}
	
	@Override
	public Set<Map.Entry<Locale, T>> entrySet() {
		return values.entrySet();
	}
	
	@Override
	public T put(Locale lang, T msg) {
		return values.put(lang, msg);
	}

	@Override
	public String toString() {
		return values.toString();
	}
	
	/**
	 * Checks whether the current field has default value.
	 * @return {@code true} if contains a default value; {@code false} otherwise
	 */
	public boolean containsDefault() {
		return values.containsKey(AppGlobals.DEFAULT_LOCALE);
	}
	
	/**
	 * Returns the value for the default {@code Locale}.
	 * @return the default value
	 */
	public T getDefault() {
		return get(AppGlobals.DEFAULT_LOCALE);
	}
	
	/**
	 * Returns the value for the provided {@code Locale}.
	 * @param lang the {@code Locale}
	 * @return the value for the {@code Locale} if exits; {@code null} otherwise
	 */
	public T getValue(Locale lang) {
		T msg = get(lang);
		if (msg == null) {
			msg = get(AppGlobals.DEFAULT_LOCALE);
		}

		return msg;
	}

	public final void putDefault(T desc) {
		values.put(AppGlobals.DEFAULT_LOCALE, desc);	
	}
}