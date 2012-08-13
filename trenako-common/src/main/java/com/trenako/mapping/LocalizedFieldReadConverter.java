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

import java.util.Set;

import org.springframework.core.convert.converter.Converter;

import com.mongodb.DBObject;

/**
 * Converter from {@code DBObject} to {@code LocalizedField} during reading operations.
 * @author Carlo Micieli
 *
 */
public class LocalizedFieldReadConverter<T> implements Converter<DBObject, LocalizedField<T>> {

	@SuppressWarnings("unchecked")
	@Override
	public LocalizedField<T> convert(DBObject dbo) {

		LocalizedField<T> field = new LocalizedField<T>();
		Set<String> fieldNames = dbo.keySet();
		for (String lang : fieldNames) {
			field.put(lang, (T) dbo.get(lang));
		}
				
		return field;
	}
}
