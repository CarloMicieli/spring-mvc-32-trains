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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.MessageSource;

/**
 * It represents a wrapper for the {@code enum} values.
 * <p>
 * This class provided additional functionalities to {@code enum} values. 
 * <ul>
 * <li>{@code key}: the value stored in database;</li>
 * <li>{@code label}: the value label, localized by {@code MessageSource};</li>
 * <li>{@code description}: the value details, localized by {@code MessageSource}.</li>
 * </ul>
 * </p>
 * 
 * @author Carlo Micieli
 *
 * @param <T> the inner {@code enum} to be localized
 */
public class LocalizedEnum<T extends Enum<T>> {

	private final static MessageFailback<Era> eraMessageFailback = new EraMessageFailback();
	private final static MessageFailback<Category> categoryMessageFailback = new CategoryMessageFailback();
	
	private final T val;
	private final String key;
	private final String label;
	private final String description;

	/**
	 * Creates a new {@code LocalizedEnum}.
	 * @param val the {@code enum} value
	 */
	public LocalizedEnum(T val) {
		this(val, null, null);
	}
	
	/**
	 * Creates a new {@code LocalizedEnum}.
	 * @param val the {@code enum} value
	 * @param messageSource the {@code MessageSource} to localize the text strings
	 * @param failback the failback interface to produce default messages
	 */
	public LocalizedEnum(T val, MessageSource messageSource, MessageFailback<T> failback) {		
		this.val = val;
		this.key = buildKey(val);
		this.label = getMessage(buildCodeForLabel(val), messageSource, failback);
		this.description = getMessage(buildCodeForDesc(val), messageSource, failback);
	}
	
	/**
	 * Returns the label for the provided {@code enum} value.
	 * @return the label
	 */
	public static <T extends Enum<T>> String buildKey(T val) {
		return val.name().toLowerCase().replace('_', '-');
	}
	
	/**
	 * Returns the label for the provided {@code enum} value.
	 * @param str the string to be parsed
	 * @param enumType the {@code enum} type
	 * @return a value if {@code str} is valid constant name
	 */
	public static <T extends Enum<T>> T parseString(String str, Class<T> enumType) {
		return T.valueOf(enumType, str.toUpperCase().replace('-', '_'));
	}
	
	/**
	 * Builds the list with the provided {@code enum} values.
	 * @param enumType the {@code enum} type
	 * @return the values list
	 */
	public static <T extends Enum<T>> Iterable<LocalizedEnum<T>> list(Class<T> enumType) {
		return list(enumType, null, null);
	}
	
	/**
	 * Builds the list with the provided {@code enum} values.
	 * @param enumType the {@code enum} type
	 * @param messageSource the {@code MessageSource} to localize the text strings
	 * @param failback the failback interface to produce default messages
	 * @return the values list
	 */
	public static <T extends Enum<T>> Iterable<LocalizedEnum<T>> list(Class<T> enumType, 
			MessageSource messageSource, 
			MessageFailback<T> failback) {
		
		if (!enumType.isEnum()) {
			throw new IllegalArgumentException("The provided type is not an enum");
		}
		
		T[] consts = enumType.getEnumConstants();
		List<LocalizedEnum<T>> items = new ArrayList<LocalizedEnum<T>>(consts.length);
		
		for (T val : consts) {
			items.add(new LocalizedEnum<T>(val, messageSource, failback));
		}
		
		return Collections.unmodifiableList(items);
	}
	
	/**
	 * Returns the current {@code enum} value.
	 * @return the value
	 */
	public T getValue() {
		return val;
	}

	/**
	 * Returns the key for this {@code enum} value.
	 * @return the label string
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * Returns the localized label for this {@code enum} value.
	 * @return the message
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Returns the localized description for this {@code enum} value.
	 * @return the message
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Returns the default {@code MessageFailback} to produce default message.
	 * @param enumType the {@code enum} type name
	 * @return a {@code MessageFailback}
	 */
	public static <T extends Enum<T>> MessageFailback<?> messageFailbackFor(Class<?> enumType) {
		if (enumType.equals(Era.class)) {
			return eraMessageFailback;
		}
		else if (enumType.equals(Category.class)) {
			return categoryMessageFailback;
		}
		
		return null;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("(").append(key.toString()).append(")")
			.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof LocalizedEnum<?>)) return false;
		
		LocalizedEnum<?> other = (LocalizedEnum<?>) obj;
		return this.getValue().equals(other.getValue());
	}
	
	private String getMessage(String code, MessageSource messageSource, MessageFailback<T> failback) {
		if (messageSource == null) {
			if (failback != null) {
				return failback.failbackMessage(val);
			}
			return key;
		}
		return messageSource.getMessage(code, null, key, null);
	}
	
	private static <T extends Enum<T>> String buildCodeForLabel(T val) {
		return new StringBuilder()
			.append(val.getClass().getSimpleName().toLowerCase())
			.append(".")
			.append(val.name().toLowerCase().replace('_', '.'))
			.append(".label")
			.toString();
	}
	
	private static <T extends Enum<T>> String buildCodeForDesc(T val) {
		return new StringBuilder()
			.append(val.getClass().getSimpleName().toLowerCase())
			.append(".")
			.append(val.name().toLowerCase().replace('_', '.'))
			.append(".description")
			.toString();
	}
	
	/**
	 * Clients can implement this interface to provide failback messages.
	 * @author Carlo Micieli
	 *
	 * @param <T>
	 */
	public static interface MessageFailback<T extends Enum<T>> {
		String failbackMessage(T val);
	}

	private static class EraMessageFailback implements MessageFailback<Era> {
		@Override
		public String failbackMessage(Era val) {
			return val.name();
		}
	}
	
	private static class CategoryMessageFailback implements MessageFailback<Category> {
		@Override
		public String failbackMessage(Category val) {
			return val.name();
		}
	}
}
