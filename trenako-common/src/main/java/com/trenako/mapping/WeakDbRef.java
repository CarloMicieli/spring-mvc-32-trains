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

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class WeakDbRef implements Serializable {
	private static final long serialVersionUID = 1265205378294217474L;
	private Map<String, String> values;

	private static final String SLUG_KEY = "slug";
	private static final String LABEL_KEY = "label";
	
	private WeakDbRef(Map<String, String> values) {
		this.values = values;
	}
	
	/**
	 * Creates an empty {@code WeakDbRef}.
	 */
	public WeakDbRef() {
		values = new HashMap<String, String>();
	}
	
	public static <E extends DbReferenceable> WeakDbRef buildRef(E entity) {
		Map<String, String> values = new HashMap<String, String>();
		values.put("slug", entity.getSlug());
		values.put("label", entity.getLabel());
		return new WeakDbRef(Collections.unmodifiableMap(values));
	}

	public String getLabel() {
		return values.get(LABEL_KEY);
	}

	public String getSlug() {
		return values.get(SLUG_KEY);
	}
	
	@Override
	public String toString() {
		return values.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof WeakDbRef)) return false;
		
		WeakDbRef other = (WeakDbRef) obj;
		return this.values.equals(other.values);
	}
}
