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
package com.trenako.web.infrastructure;

import java.beans.PropertyEditorSupport;

import org.bson.types.ObjectId;
import org.springframework.util.StringUtils;

import com.trenako.entities.Railway;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RailwayPropertyEditor extends PropertyEditorSupport {
	private final boolean allowEmpty;
	
	/**
	 * Creates a new {@code RailwayPropertyEditor}.
	 * @param allowEmpty if empty strings should be allowed
	 */
	public RailwayPropertyEditor(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
			return;
		}
		
		if (!ObjectId.isValid(text)) {
			throw new IllegalArgumentException(text + " is not a valid ObjecId");
		}
		
		ObjectId id = new ObjectId(text);
		setValue(new Railway(id));
	}
}
