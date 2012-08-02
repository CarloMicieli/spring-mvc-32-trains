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
import java.util.Map;

import com.trenako.mapping.LocalizedField;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class LocalizedFieldPropertyEditor extends PropertyEditorSupport {
	@Override
	public void setValue(Object obj) {
		if (obj == null) {
			setValue(null);
		}
		
		if (obj.getClass().isAssignableFrom(Map.class)) {
			@SuppressWarnings("unchecked")
			Map<String, ?> values = (Map<String, ?>) obj;
			LocalizedField<?> field = LocalizedField.localize(values);
			setValue(field);
		}
	}
}
