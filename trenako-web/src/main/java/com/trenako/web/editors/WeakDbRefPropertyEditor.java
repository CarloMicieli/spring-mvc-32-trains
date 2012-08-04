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

import org.springframework.util.StringUtils;

import com.trenako.mapping.WeakDbRef;

/**
 * Custom {@code PropertyEditor} for fields with {@link WeakDbRef} type.
 * @author Carlo Micieli
 *
 */
public class WeakDbRefPropertyEditor extends PropertyEditorSupport {
	
	private final boolean allowEmpty;
	
	/**
	 * Creates a new {@code WeakDbRefPropertyEditor}.
	 * @param allowEmpty if empty strings should be allowed
	 */
	public WeakDbRefPropertyEditor(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
			return;
		}

		setValue(new WeakDbRef().setSlug(text));
	}	
}