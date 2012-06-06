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
package com.trenako;

/**
 * It represents a rolling stock search criteria.
 * @author Carlo Micieli
 *
 */
public class SearchCriteria {
	private final String powerMethod;
	private final String brand;
	private final String scale;
	private final String category;
	private final String era;
	private final String railway;
	
	private SearchCriteria(Builder b) {
		this.powerMethod = b.powerMethod;
		this.brand = b.brand;
		this.scale = b.scale;
		this.category = b.category;
		this.era = b.era;
		this.railway = b.railway;
	}
	
	public static class Builder	{
		private String powerMethod = null;
		private String brand = null;
		private String scale = null;
		private String category = null;
		private String era = null;
		private String railway = null;
		
		public Builder() {
		}
		
		public Builder powerMethod(String pm) {
			powerMethod = pm;
			return this;
		}
		
		public Builder brand(String b) {
			brand = b;
			return this;
		}
		
		public Builder railway(String r) {
			railway = r;
			return this;
		}
		
		public Builder scale(String s) {
			scale = s;
			return this;
		}
		
		public Builder category(String c) {
			category = c;
			return this;
		}
		
		public Builder era(String e) {
			era = e;
			return this;
		}
		
		public SearchCriteria build() {
			return new SearchCriteria(this);
		}
	}

	public String getPowerMethod() {
		return powerMethod;
	}

	public String getBrand() {
		return brand;
	}

	public String getScale() {
		return scale;
	}

	public String getCategory() {
		return category;
	}

	public String getEra() {
		return era;
	}

	public String getRailway() {
		return railway;
	}
}
