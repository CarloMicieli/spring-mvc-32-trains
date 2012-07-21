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

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

/**
 * It represents a wrapper for the enumerations localizations.
 * 
 * @author Carlo Micieli
 *
 * @param <T> the inner {@code enum} to be localized
 */
public class LocalizedEnum<T extends Enum<T>> implements MessageSourceAware {

	private MessageSource messageSource;

	private final T val;
	private final String key;
	private final String code;

	/**
	 * Creates a new {@code LocalizedEnum}.
	 * @param val the {@code enum} value
	 */
	public LocalizedEnum(T val) {
		this.val = val;
		this.key = buildLabel(val);
		this.code = buildCodeForValue(val);
	}
	
	/**
	 * Returns the label for the provided {@code enum} value.
	 * @return the label
	 */
	public static <T extends Enum<T>> String buildLabel(T val) {
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
	 * The label string for the current value.
	 * @return the label string
	 */
	public String label() {
		return key;
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
		if (messageSource == null) {
			return key;
		}
		return messageSource.getMessage(code, null, key, null);
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	private static <T extends Enum<T>> String buildCodeForValue(T val) {
		return new StringBuilder()
			.append(val.getClass().getSimpleName().toLowerCase())
			.append(".")
			.append(val.name().toLowerCase().replace('_', '.'))
			.append(".label")
			.toString();
	}
}
