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
package com.trenako.web.editors;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;

import com.trenako.DeliveryDateFormatException;
import com.trenako.values.DeliveryDate;

/**
 * It represents a {@code DeliveryDate} property editor.
 * @author Carlo Micieli
 *
 */
public class DeliveryDatePropertyEditor extends PropertyEditorSupport {
	
	private final boolean allowEmpty;
	
	/**
	 * Creates a new {@code DeliveryDatePropertyEditor}.
	 * @param allowEmpty if empty strings should be allowed
	 */
	public DeliveryDatePropertyEditor(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (allowEmpty && StringUtils.isBlank(text)) {
			setValue(null);
			return;
		}
	
		try {
			DeliveryDate dd = DeliveryDate.parseString(text);
			setValue(dd);
		}
		catch (DeliveryDateFormatException e) {
			throw new IllegalArgumentException("'" + text + "' is not a valid delivery date");
		}
	}
	
}
