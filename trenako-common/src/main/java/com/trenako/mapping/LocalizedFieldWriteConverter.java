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

import java.util.Locale;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Converter from {@code LocalizedField} to {@code DBObject} during writing operations.
 * @author Carlo Micieli
 *
 */
public class LocalizedFieldWriteConverter implements Converter<LocalizedField<?>, DBObject> {

	@Override
	public DBObject convert(LocalizedField<?> source) {
		BasicDBObject dbo = new BasicDBObject();
		for (Map.Entry<Locale, ?> entry : source.entrySet()) {
			dbo.append(entry.getKey().getLanguage(), entry.getValue());
		}
		
		return dbo;
	}

}
