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

import com.trenako.utility.Cat;

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
	private String powerMethod;
	private String brand;
	private String scale;
	private String category;
	private Cat cat;
	private String era;
	private String railway;
	
	private SearchCriteria(Builder b) {
		this.powerMethod = b.powerMethod;
		this.brand = b.brand;
		this.scale = b.scale;
		this.category = b.category;
		this.cat = b.cat;
		this.era = b.era;
		this.railway = b.railway;
	}
	
	/**
	 * Creates an empty {@code SearchCriteria}.
	 */
	public SearchCriteria() {
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
		private Cat cat = null;
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
		 * Sets the {@code powerMethod} and {@code category} criteria.
		 * 
		 * @param cat the category
		 * @return a builder
		 */
		public Builder cat(String cat) {
			if (cat!=null && !cat.isEmpty()) {
				this.cat = Cat.parseString(cat);
			}
			return this;
		}
		
		/**
		 * Sets the {@code category} criteria.
		 * @param category the category name
		 * @return a builder
		 */
		public Builder category(String category) {
			this.category = category;
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
	 * <p>
	 * The appropriate way to check whether a power method is selected is using
	 * the {@link SearchCriteria#hasPowerMethod()} method.
	 * </p>
	 * 
	 * @return the criteria value
	 * @see com.trenako.PowerMethod
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
	 * <p>
	 * The appropriate way to check whether a brand is selected is using
	 * the {@link SearchCriteria#hasBrand()} method.
	 * </p>
	 * 
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
	 * <p>
	 * The appropriate way to check whether a scale is selected is using
	 * the {@link SearchCriteria#hasScale()} method.
	 * </p>
	 * 
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
	 * <p>
	 * The appropriate way to check whether a category is selected is using
	 * the {@link SearchCriteria#hasCategory()} method.
	 * </p>
	 * 
	 * @return the criteria value
	 * @see com.trenako.Category
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
	 * Returns the {@code power method} and {@code category} search criteria.
	 * <p>
	 * The appropriate way to check whether a category is selected is using
	 * the {@link SearchCriteria#hasCat()} method.
	 * </p>
	 * 
	 * @return the criteria value
	 * @see com.trenako.Cat
	 */
	public Cat getCat() {
		return cat;
	}

	/**
	 * Checks whether a {@code category} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasCat() {
		return cat!=null && !cat.equals("");
	}
	
	/**
	 * Returns the {@code era} search criteria.
	 * <p>
	 * The appropriate way to check whether an era is selected is using
	 * the {@link SearchCriteria#hasEra()} method.
	 * </p>
	 * 
	 * @return the criteria value
	 * @see com.trenako.Era
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
	 * <p>
	 * The appropriate way to check whether a railway is selected is using
	 * the {@link SearchCriteria#hasRailway()} method.
	 * </p>
	 * 
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
	 * Sets the {@code power method} search criteria.
	 * @param powerMethod the {@code power method} 
	 */
	public void setPowerMethod(String powerMethod) {
		this.powerMethod = powerMethod;
	}

	/**
	 * Sets the {@code Brand} search criteria.
	 * @param brand the {@code Brand} 
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * Sets the {@code Scale} search criteria.
	 * @param scale the {@code Scale} 
	 */
	public void setScale(String scale) {
		this.scale = scale;
	}

	/**
	 * Sets the {@code Category} search criteria.
	 * @param category the {@code Category} 
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Sets the {@code Cat} search criteria.
	 * @param cat the {@code Cat} 
	 */
	public void setCat(Cat cat) {
		this.cat = cat;
	}

	/**
	 * Sets the {@code Era} search criteria.
	 * @param era the {@code Era} 
	 */
	public void setEra(String era) {
		this.era = era;
	}

	/**
	 * Sets the {@code Railway} search criteria.
	 * @param railway the {@code Railway} 
	 */
	public void setRailway(String railway) {
		this.railway = railway;
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
			.append(", cat=")
			.append(getCat())
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
			.append(cat, other.cat)
			.append(powerMethod, other.powerMethod)
			.isEquals();
	}
}
