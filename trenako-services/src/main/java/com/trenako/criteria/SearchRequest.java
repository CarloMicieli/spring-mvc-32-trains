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

import org.apache.commons.lang3.StringUtils;

/**
 * This class represent a container for rolling stocks
 * search criteria.
 * <p>
 * This class is mutable in order to give the clients the chance
 * to add and remove criterion freely. 
 * </p>
 * <p>
 * The objects of this class are not synchronized.
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
		initValues();
	}
	
	/**
	 * Creates a new {@code SearchRequest}.
	 * @param brand the brand criterion
	 * @param scale the scale criterion
	 * @param railway the railway criterion
	 * @param era the era criterion
	 * @param cat the power method and category criterion
	 * @param powerMethod the power method criterion
	 * @param category the category criterion
	 */
	public SearchRequest(String brand, String scale, String railway, 
			String era, String cat, String powerMethod, String category) {
		
		initValues();
		
		brand(brand);
		category(category);
		cat(cat);
		era(era);
		powerMethod(powerMethod);
		railway(railway);
		scale(scale);
	}

	/**
	 * Sets the {@code power method} search criteria.
	 * @param powerMethod the {@code power method} 
	 */
	public void setPowerMethod(String powerMethod) {
		powerMethod(powerMethod);
	}

	/**
	 * Sets the {@code Brand} search criteria.
	 * @param brand the {@code Brand} 
	 */
	public void setBrand(String brand) {
		brand(brand);
	}
		
	/**
	 * Sets the {@code Scale} search criteria.
	 * @param scale the {@code Scale} 
	 */
	public void setScale(String scale) {
		scale(scale);
	}
	
	/**
	 * Sets the {@code Category} search criteria.
	 * @param category the {@code Category} 
	 */
	public void setCategory(String category) {
		category(category);
	}

	/**
	 * Sets the {@code Cat} search criteria.
	 * @param cat the {@code Cat} 
	 */
	public void setCat(String cat) {
		cat(cat);
	}
	
	/**
	 * Sets the {@code Era} search criteria.
	 * @param era the {@code Era} 
	 */
	public void setEra(String era) {
		era(era);
	}
	
	/**
	 * Sets the {@code Railway} search criteria.
	 * @param railway the {@code Railway} 
	 */
	public void setRailway(String railway) {
		railway(railway);
	}
	
	/**
	 * Checks whether the current {@code SearchRequest} contains 
	 * the provided search criterion.
	 * 
	 * @param criteria the search criterion to be checked
	 * @return {@code true} if the criterion exists; {@code false} otherwise
	 */
	public boolean has(Criteria criteria) {
		return values.containsKey(criteria);
	}
	
	/**
	 * Returns the value for the provided search criterion.
	 * @param criteria the search criterion
	 * @return the criterion key if exists; {@code null} otherwise
	 */
	public String get(Criteria criteria) {
		return values.get(criteria);
	}
	
	/**
	 * Indicates whether the current {@code SearchRequest} is empty.
	 * @return {@code true} if it is empty; {@code false} otherwise
	 */
	public boolean isEmpty() {
		return values.isEmpty();
	}
	
	/**
	 * Removes all criteria from this {@code SearchRequest}.
	 */
	public void clear() {
		if (isEmpty()) {
			return;
		}
		values.clear();
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
	
	private void put(Criteria criteria, String value) {
		if (!StringUtils.isBlank(value)) {
			values.put(criteria, value);
		}
	}
	
	private void initValues() {
		values = new EnumMap<Criteria, String>(Criteria.class);
	}
	
	private void powerMethod(String powerMethod) {
		put(Criteria.POWER_METHOD, powerMethod);
	}
	
	private void brand(String brand) {
		put(Criteria.BRAND, brand);
	}
	
	private void scale(String scale) {
		put(Criteria.SCALE, scale);
	}
	
	private void railway(String railway) {
		put(Criteria.RAILWAY, railway);
	}

	private void era(String era) {
		put(Criteria.ERA, era);
	}

	private void cat(String cat) {
		put(Criteria.CAT, cat);
	}

	private void category(String category) {
		put(Criteria.CATEGORY, category);
	}
}
