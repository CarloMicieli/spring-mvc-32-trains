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
package com.trenako.format;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.trenako.mapping.DbReferenceable;
import com.trenako.mapping.WeakDbRef;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class WeakDbRefFormatter implements Formatter<WeakDbRef<?>> {

	@Override
	public String print(WeakDbRef<?> ref, Locale locale) {
		return ref.getSlug();
	}

	@Override
	public WeakDbRef<DbReferenceable> parse(String text, Locale locale)
			throws ParseException {
		return WeakDbRef.buildFromSlug(text, DbReferenceable.class);
	}

}
