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
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Application constants.
 * 
 * @author Carlo Micieli
 *
 */
public class AppGlobals {
	private AppGlobals() {
		// avoid the constructor calls.
		throw new AssertionError();
	}
	
	/**
	 * The maximum size for the result set returned by the data store.
	 * <p>
	 * The default size is {@code 100}.
	 * </p>
	 */
	public static final int MAX_RESULT_SET_SIZE = 100;
	
	/**
	 * Returns the list of the locale supported by the application.
	 * @return the list of locale
	 */
	public static Iterable<Locale> languages() {
		return Collections.unmodifiableList(Arrays.asList(
			Locale.ENGLISH, Locale.ITALIAN));
	}
	
	/**
	 * Returns the list of the countries supported by the application.
	 * <p>
	 * This method returns a reduced list of all the countries. The map is sorted by the English
	 * country name. The map key is the two-letter codes as defined by ISO-3166.
	 * </p>
	 *
	 * @return the list of countries
	 */
	@SuppressWarnings("serial")
	public static SortedMap<String, String> countries() {
		return Collections.unmodifiableSortedMap(new TreeMap<String, String>() {{ 
			put("at", "Austria");
			put("be", "Belgium");
			put("ca", "Canada");
			put("cn", "China");
			put("dk", "Denmark");
			put("fi", "Finland");
			put("fr", "France");
			put("de", "Germany");
			put("it", "Italy");
			put("jp", "Japan");
			put("mx", "Mexico");
			put("nl", "Netherlands");
			put("no", "Norway");
			put("ro", "Romania");
			put("ru", "Russian Federation");
			put("es", "Spain");
			put("se", "Sweden");
			put("ch", "Switzerland");
			put("tr", "Turkey");
			put("uk", "United Kingdom");
			put("us", "United States");
		}});
	}
	
	/**
	 * The default locale for the application.
	 */
	public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
	
	/**
	 * The default language for the application.
	 */
	public static final String DEFAULT_LANGUAGE = Locale.ENGLISH.getLanguage();
	
	/**
	 * The maximum allowed size for uploaded files.
	 */
	public static final int MAX_UPLOAD_SIZE = 512 * 1024;
	
	/**
	 * The allowed media types for uploaded files.
	 */
	public static final List<String> ALLOWED_MEDIA_TYPES =
			Collections.unmodifiableList(Arrays.asList("images/png", "images/jpg"));
	
}
