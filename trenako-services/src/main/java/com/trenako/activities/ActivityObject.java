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
package com.trenako.activities;

import com.trenako.entities.RollingStock;
import com.trenako.mapping.WeakDbRef;

/**
 * It represents an the object for a user activity.
 *
 * @author Carlo Micieli 
 *
 */
public class ActivityObject {

	private String url;
	private String objectType;
	private String displayName;

	/**
	 * Creates an empty {@code ActivityObject}.
	 */
	public ActivityObject() {
	}

	/**
	 * Creates a new {@code ActivityObject}.
	 * @param objectType the object type
	 * @param url the permalink url to this resource
	 * @param displayName the resource display name
	 */
	public ActivityObject(String objectType, String url, String displayName) {
		this.objectType = objectType;
		this.url = url;
		this.displayName = displayName;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof ActivityObject)) return false;
		
		ActivityObject other = (ActivityObject) obj;
		return this.objectType.equals(other.objectType) &&
				this.url.equals(other.url) &&
				this.displayName.equals(other.displayName);
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("object{type: ")
			.append(getObjectType())
			.append(", url: ")
			.append(getUrl())
			.append(", name: ")
			.append(getDisplayName())
			.append("}")
			.toString();
	}

	/**
	 * Creates a {@code ActivityObject} for the provided rolling stock.
	 * @param rs the rolling stock
	 * @return a new {@code ActivityObject}
	 */
	public static ActivityObject rsObject(WeakDbRef<RollingStock> rs) {
		return new ActivityObject("rollingStock", "/rollingstocks/" + rs.getSlug(), rs.getLabel());
	}
}
