/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.utility;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * It converts a string to a "slug".
 * 
 * @see <a href="http://www.codecodex.com/wiki/Generate_a_url_slug#Java">Original implementation</a>
 */
public class Slug {
	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");  
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");  

	private Slug() {}
	
	/**
	 * Encodes this text to a <em>slug</em>.
	 * @param input the string to be encoded
	 * @return the slug
	 */
	public static String encode(String input) {
		if(input==null)
			throw new IllegalArgumentException();

		String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");  
		String normalized = Normalizer.normalize(nowhitespace, Form.NFD);  
		String slug = NONLATIN.matcher(normalized).replaceAll("");  
		return slug.toLowerCase(Locale.ENGLISH); 
	}
}