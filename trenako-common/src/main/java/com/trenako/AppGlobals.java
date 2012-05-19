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

import java.util.Locale;

/**
 * Application constants.
 * 
 * @author Carlo Micieli
 *
 */
public class AppGlobals {
	public AppGlobals() {
		// avoid the constructor calls.
		throw new AssertionError();
	}
	
	/**
	 * The default locale for the application.
	 */
	public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
	
	/**
	 * The default language for the application.
	 */
	public static final String DEFAULT_LANGUAGE = Locale.ENGLISH.getLanguage();
	
}
