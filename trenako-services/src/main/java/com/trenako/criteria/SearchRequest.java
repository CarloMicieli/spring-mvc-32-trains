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
package com.trenako.criteria;

import java.util.EnumMap;
import java.util.Map;

/**
 * This class represent a container for rolling stocks
 * search criteria.
 * <p>
 * This class is mutable in order to give the clients the chance
 * to add and remove criterion freely. 
 * </p>
 * <p>
 * The object of this class are not synchronized.
 * </p>
 * 
 * @author Carlo Micieli
 *
 */
public class SearchRequest {
	private Map<Criteria, String> values;

	/**
	 * Creates an empty {@code SearchRequest}.
	 */
	public SearchRequest() {
		values = new EnumMap<Criteria, String>(Criteria.class);
	}
	
	/**
	 * Sets the {@code power method} search criteria.
	 * @param powerMethod the {@code power method} 
	 */
	public void setPowerMethod(String powerMethod) {
		values.put(Criteria.POWER_METHOD, powerMethod);
	}
	
	/**
	 * Sets the {@code Brand} search criteria.
	 * @param brand the {@code Brand} 
	 */
	public void setBrand(String brand) {
		values.put(Criteria.BRAND, brand);
	}
	
	/**
	 * Sets the {@code Scale} search criteria.
	 * @param scale the {@code Scale} 
	 */
	public void setScale(String scale) {
		values.put(Criteria.SCALE, scale);
	}
	
	/**
	 * Sets the {@code Category} search criteria.
	 * @param category the {@code Category} 
	 */
	public void setCategory(String category) {
		values.put(Criteria.CATEGORY, category);
	}

	/**
	 * Sets the {@code Cat} search criteria.
	 * @param cat the {@code Cat} 
	 */
	public void setCat(String cat) {
		values.put(Criteria.CAT, cat.toString());
	}
	
	/**
	 * Sets the {@code Era} search criteria.
	 * @param era the {@code Era} 
	 */
	public void setEra(String era) {
		values.put(Criteria.ERA, era);
	}
	
	/**
	 * Sets the {@code Railway} search criteria.
	 * @param railway the {@code Railway} 
	 */
	public void setRailway(String railway) {
		values.put(Criteria.RAILWAY, railway);
	}
	
	/**
	 * Indicates whether the current {@code SearchRequest} is empty.
	 * @return {@code true} if it is empty; {@code false} otherwise
	 */
	public boolean isEmpty() {
		return values.isEmpty();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof SearchRequest)) return false;
		
		SearchRequest other = (SearchRequest) obj;
		return this.values.equals(other.values);
	}
	
	@Override
	public int hashCode() {
		return values.hashCode();
	}

	@Override
	public String toString() {
		return values.toString();
	}
}
