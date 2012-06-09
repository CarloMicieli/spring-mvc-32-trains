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

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * It represents an immutable rolling stock search criteria.
 * <p>
 * The best way to create a {@code SearchCriteria} is using the
 * {@link SearchCriteria.Builder} class.
 * </p>
 * <p>
 * This {@code SearchCriteria} is independent from the data
 * store in use. The main purpose for the objects of this
 * class is to be containers for the search criteria values. 
 * </p>
 * 
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
	
	/**
	 * It represents a {@code SearchCriteria} builder class.
	 * @author Carlo Micieli
	 *
	 */
	public static class Builder	{
		private String powerMethod = null;
		private String brand = null;
		private String scale = null;
		private String category = null;
		private String era = null;
		private String railway = null;
		
		/**
		 * Creates a new {@code SearchCriteria}.
		 * <p>
		 * No criteria is required, but it is not allowed
		 * to build {@code SearchCriteria} objects if at least
		 * one criteria is set.
		 * </p>
		 */
		public Builder() {
		}
		
		/**
		 * Sets the [@code power method} criteria.
		 * @param pm the power method
		 * @return a builder
		 */
		public Builder powerMethod(String pm) {
			powerMethod = pm;
			return this;
		}
		
		/**
		 * Sets the {@code brand} criteria.
		 * @param brand the brand
		 * @return a builder
		 */
		public Builder brand(String brand) {
			this.brand = brand;
			return this;
		}
		
		/**
		 * Sets the {@code railway} criteria.
		 * @param railway the railway
		 * @return a builder
		 */
		public Builder railway(String railway) {
			this.railway = railway;
			return this;
		}
		
		/**
		 * Sets the {@code scale} criteria.
		 * @param scale the scale
		 * @return a builder
		 */
		public Builder scale(String scale) {
			this.scale = scale;
			return this;
		}
		
		/**
		 * Sets the {@code category} criteria.
		 * @param cat the category
		 * @return a builder
		 */
		public Builder category(String cat) {
			category = cat;
			return this;
		}
		
		/**
		 * Sets the {@code era} criteria.
		 * @param era the era
		 * @return a builder
		 */
		public Builder era(String era) {
			this.era = era;
			return this;
		}
		
		/**
		 * Builds a new {@code SearchCriteria} objects.
		 * @return a {@code SearchCriteria}
		 */
		public SearchCriteria build() {
			return new SearchCriteria(this);
		}
	}

	/**
	 * Returns the {@code power method} search criteria.
	 * @return the criteria value
	 * @see com.trenako.entities.PowerMethod
	 */
	public String getPowerMethod() {
		return powerMethod;
	}

	/**
	 * Checks whether a {@code power method} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasPowerMethod() {
		return powerMethod!=null && !powerMethod.equals("");
	}
	
	/**
	 * Returns the {@code brand} search criteria.
	 * @return the criteria value
	 * @see com.trenako.entities.Brand
	 */
	public String getBrand() {
		return brand;
	}
	
	/**
	 * Checks whether a {@code brand} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasBrand() {
		return brand!=null && !brand.equals("");
	}

	/**
	 * Returns the {@code scale} search criteria.
	 * @return the criteria value
	 * @see com.trenako.entities.Scale
	 */
	public String getScale() {
		return scale;
	}

	/**
	 * Checks whether a {@code scale} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasScale() {
		return scale!=null && !scale.equals("");
	}
	
	/**
	 * Returns the {@code category} search criteria.
	 * @return the criteria value
	 * @see com.trenako.entities.Category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Checks whether a {@code category} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasCategory() {
		return category!=null && !category.equals("");
	}
	
	/**
	 * Returns the {@code era} search criteria.
	 * @return the criteria value
	 * @see com.trenako.entities.Era
	 */
	public String getEra() {
		return era;
	}

	/**
	 * Checks whether a {@code era} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasEra() {
		return era!=null && !era.equals("");
	}
	
	/**
	 * Returns the {@code railway} search criteria.
	 * @return the criteria value
	 * @see com.trenako.entities.Railway
	 */
	public String getRailway() {
		return railway;
	}

	/**
	 * Checks whether a {@code railway} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasRailway() {
		return railway!=null && !railway.equals("");
	}
	
	/**
	 * Returns a string representation of the {@code SearchCriteria}.
	 * @return a string representation of the {@code SearchCriteria}
     */
	@Override
	public String toString() {
		return new StringBuilder()
			.append("{brand=")
			.append(getBrand())
			.append(", category=")
			.append(getCategory())
			.append(", era=")
			.append(getEra())
			.append(", powerMethod=")
			.append(getPowerMethod())
			.append(", railway=")
			.append(getRailway())
			.append(", scale=")
			.append(getScale())
			.append("}")
			.toString();			
	}
	
	/**
	 * Indicates whether some other {@code SearchCriteria} is equal to this one.
	 * 
	 * @param obj the reference {@code SearchCriteria} with which to compare
	 * @return {@code true} if this object is the same as the {@code obj} 
	 * argument; {@code false} otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if( obj==this ) return true;
		if( !(obj instanceof SearchCriteria) ) return false;
		
		SearchCriteria other = (SearchCriteria) obj;
		return new EqualsBuilder()
			.append(era, other.era)
			.append(scale, other.scale)
			.append(railway, other.railway)
			.append(brand, other.brand)
			.append(category, other.category)
			.append(powerMethod, other.powerMethod)
			.isEquals();
	}
}
