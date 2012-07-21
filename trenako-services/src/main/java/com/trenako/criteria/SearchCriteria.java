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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.utility.Cat;
import com.trenako.values.Category;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;

/**
 * It represents rolling stock search criteria.
 * <p>
 * The best way to create a {@code SearchCriteria} is using the
 * {@link SearchCriteria.Builder} class.
 * </p>
 * <p>
 * This {@code SearchCriteria} is independent from the data
 * store in use. The main purpose for the objects of this
 * class is to be containers for the search criteria. 
 * </p>
 * 
 * @author Carlo Micieli
 *
 */
public class SearchCriteria {
	
	private Map<String, Pair<String, String>> values = new HashMap<String, Pair<String, String>>();
	
	private final static String POWER_METHOD_KEY = "powermethod";
	private final static String BRAND_KEY = "brand";
	private final static String SCALE_KEY = "scale";
	private final static String CATEGORY_KEY = "category";
	private final static String CAT_KEY = "cat";
	private final static String ERA_KEY = "era";
	private final static String RAILWAY_KEY = "railway";
	
	/**
	 * The allowed search criteria names.
	 * <p>
	 * The keys are listed in importance order. Other application components
	 * depend on this particular order; any change can break something.
	 * </p>
	 */
	public final static List<String> KEYS = 
			Collections.unmodifiableList(
					Arrays.asList(BRAND_KEY, SCALE_KEY, CAT_KEY, RAILWAY_KEY, ERA_KEY, POWER_METHOD_KEY, CATEGORY_KEY));
	
	private SearchCriteria(Map<String, Pair<String, String>> values) {
		this.values = values;
	}
	
	/**
	 * Creates an empty {@code SearchCriteria}.
	 */
	public SearchCriteria() {
	}
	
	/**
	 * Returns an immutable copy of the provided {@code SearchCriteria}.
	 * <p>
	 * Only read operations are allowed; any attempt to modify the returned 
	 * {@code SearchCriteria} will result in an {@code UnsupportedOperationException}.
	 * </p>
	 * 
	 * @param sc
	 * @return
	 */
	public static SearchCriteria immutableSearchCriteria(SearchCriteria sc) {
		return new SearchCriteria(
				Collections.unmodifiableMap(sc.values));
	}

	/**
	 * It represents a {@code SearchCriteria} builder class.
	 * @author Carlo Micieli
	 *
	 */
	public static class Builder	{
		private Map<String, Pair<String, String>> values = null;
		
		/**
		 * Creates a new {@code SearchCriteria}.
		 * <p>
		 * No criteria is required, but it is not allowed
		 * to build {@code SearchCriteria} objects if at least
		 * one criteria is set.
		 * </p>
		 */
		public Builder() {
			values = new HashMap<String, Pair<String, String>>();
		}
		
		/**
		 * Sets the {@code power method} criteria.
		 * @param pm the power method
		 * @return a builder
		 */
		public Builder powerMethod(String pm) {
			add(POWER_METHOD_KEY, pm, pm);
			return this;
		}
		
		/**
		 * Sets the {@code power method} criteria.
		 * @param pm the power method
		 * @return a builder
		 */
		public Builder powerMethod(LocalizedEnum<PowerMethod> pm) {
			add(POWER_METHOD_KEY, pm.label(), pm.getMessage());
			return this;
		}
		
		/**
		 * Sets the {@code brand} criteria.
		 * @param brand the brand
		 * @return a builder
		 */
		public Builder brand(String brand) {
			add(BRAND_KEY, brand, brand);
			return this;
		}
		
		/**
		 * Sets the {@code brand} criteria.
		 * @param brand the brand
		 * @return a builder
		 */
		public Builder brand(Brand brand) {
			add(BRAND_KEY, brand.getSlug(), brand.label());
			return this;
		}
		
		/**
		 * Sets the {@code railway} criteria.
		 * @param railway the railway
		 * @return a builder
		 */
		public Builder railway(String railway) {
			add(RAILWAY_KEY, railway, railway);
			return this;
		}
		
		/**
		 * Sets the {@code railway} criteria.
		 * @param railway the railway
		 * @return a builder
		 */
		public Builder railway(Railway railway) {
			add(RAILWAY_KEY, railway.getSlug(), railway.label());
			return this;
		}
		
		/**
		 * Sets the {@code scale} criteria.
		 * @param scale the scale
		 * @return a builder
		 */
		public Builder scale(String scale) {
			add(SCALE_KEY, scale, scale);
			return this;
		}
		
		/**
		 * Sets the {@code scale} criteria.
		 * @param scale the scale
		 * @return a builder
		 */
		public Builder scale(Scale scale) {
			add(SCALE_KEY, scale.getSlug(), scale.label());
			return this;
		}
		
		/**
		 * Sets the {@code powerMethod} and {@code category} criteria.
		 * 
		 * @param cat the category
		 * @return a builder
		 */
		public Builder cat(String cat) {
			add(CAT_KEY, cat, cat);
			return this;
		}
		
		/**
		 * Sets the {@code category} criteria.
		 * @param category the category name
		 * @return a builder
		 */
		public Builder category(String category) {
			add(CATEGORY_KEY, category, category);
			return this;
		}
		
		/**
		 * Sets the {@code category} criteria.
		 * @param category the category name
		 * @return a builder
		 */
		public Builder category(LocalizedEnum<Category> category) {
			add(CATEGORY_KEY, category.label(), category.getMessage());
			return this;
		}
		
		/**
		 * Sets the {@code era} criteria.
		 * @param era the era
		 * @return a builder
		 */
		public Builder era(String era) {
			add(ERA_KEY, era, era);
			return this;
		}
		/**
		 * Sets the {@code era} criteria.
		 * @param era the era
		 * @return a builder
		 */
		public Builder era(LocalizedEnum<Era> era) {
			add(ERA_KEY, era.label(), era.getMessage());
			return this;
		}
		
		/**
		 * Builds a new {@code SearchCriteria} objects.
		 * @return a {@code SearchCriteria}
		 */
		public SearchCriteria build() {
			return new SearchCriteria(this.values);
		}
		
		/**
		 * Builds a new immutable {@code SearchCriteria} objects.
		 * @return a {@code SearchCriteria}
		 */
		public SearchCriteria buildImmutable() {
			return immutableSearchCriteria(new SearchCriteria(this.values));
		}
		
		private void add(String criterionName, String value, String label) {
			if (StringUtils.hasText(value)) {
				values.put(criterionName, new ImmutablePair<String, String>(value, label));
			}
		}
	}
	
	/**
	 * Sets the {@code power method} search criteria.
	 * @param powerMethod the {@code power method} 
	 */
	public void setPowerMethod(String powerMethod) {
		addValue(POWER_METHOD_KEY, powerMethod);
	}
	
	/**
	 * Returns the {@code power method} search criteria.
	 * <p>
	 * The appropriate way to check whether a power method is selected is using
	 * the {@link SearchCriteria#hasPowerMethod()} method.
	 * </p>
	 * 
	 * @return the criteria value
	 * @see com.trenako.values.PowerMethod
	 */
	public String getPowerMethod() {
		return getValue(POWER_METHOD_KEY);
	}

	/**
	 * Checks whether a {@code power method} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasPowerMethod() {
		return values.containsKey(POWER_METHOD_KEY);
	}

	/**
	 * Sets the {@code Brand} search criteria.
	 * @param brand the {@code Brand} 
	 */
	public void setBrand(String brand) {
		addValue(BRAND_KEY, brand);
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
		return getValue(BRAND_KEY);
	}
	
	/**
	 * Checks whether a {@code brand} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasBrand() {
		return values.containsKey(BRAND_KEY);
	}

	/**
	 * Sets the {@code Scale} search criteria.
	 * @param scale the {@code Scale} 
	 */
	public void setScale(String scale) {
		addValue(SCALE_KEY, scale);
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
		return getValue(SCALE_KEY);
	}

	/**
	 * Checks whether a {@code scale} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasScale() {
		return values.containsKey(SCALE_KEY);
	}

	/**
	 * Sets the {@code Category} search criteria.
	 * @param category the {@code Category} 
	 */
	public void setCategory(String category) {
		addValue(CATEGORY_KEY, category);
	}
	
	/**
	 * Returns the {@code category} search criteria.
	 * <p>
	 * The appropriate way to check whether a category is selected is using
	 * the {@link SearchCriteria#hasCategory()} method.
	 * </p>
	 * 
	 * @return the criteria value
	 * @see com.trenako.values.Category
	 */
	public String getCategory() {
		return getValue(CATEGORY_KEY);
	}

	/**
	 * Checks whether a {@code category} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasCategory() {
		return values.containsKey(CATEGORY_KEY);
	}
	
	/**
	 * Sets the {@code Cat} search criteria.
	 * @param cat the {@code Cat} 
	 */
	public void setCat(Cat cat) {
		if (cat!=null) {
			addValue(CAT_KEY, cat.toString());
		}
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
		String c = getValue(CAT_KEY);
		if (c!=null) return Cat.parseString(c);
		return null;
	}

	/**
	 * Checks whether a {@code category} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasCat() {
		return values.containsKey(CAT_KEY);
	}
	
	/**
	 * Sets the {@code Era} search criteria.
	 * @param era the {@code Era} 
	 */
	public void setEra(String era) {
		addValue(ERA_KEY, era);
	}
	
	/**
	 * Returns the {@code era} search criteria.
	 * <p>
	 * The appropriate way to check whether an era is selected is using
	 * the {@link SearchCriteria#hasEra()} method.
	 * </p>
	 * 
	 * @return the criteria value
	 * @see com.trenako.values.Era
	 */
	public String getEra() {
		return getValue(ERA_KEY);
	}

	/**
	 * Checks whether a {@code era} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasEra() {
		return values.containsKey(ERA_KEY);
	}
	
	/**
	 * Sets the {@code Railway} search criteria.
	 * <p>
	 * This method will use {@link Railway#getSlug()} as {@code key} and
	 * {@link Railway#getSlug()} as value for the railway criteria.
	 * </p>
	 * 
	 * @param railway the {@code Railway} 
	 */
	public void setRailway(String railway) {
		addValue(RAILWAY_KEY, railway);
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
		return getValue(RAILWAY_KEY);
	}

	/**
	 * Checks whether a {@code railway} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasRailway() {
		return values.containsKey(RAILWAY_KEY);
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
			.append(getEra(), other.getEra())
			.append(getScale(), other.getScale())
			.append(getRailway(), other.getRailway())
			.append(getBrand(), other.getBrand())
			.append(getCategory(), other.getCategory())
			.append(getCat(), other.getCat())
			.append(getPowerMethod(), other.getPowerMethod())
			.isEquals();
	}
	
	/**
	 * Indicates whether the current object has a criteria
	 * for the provided key.
	 * @param key the criteria name
	 * @return {@code true} if the criteria exists; {@code false} otherwise
	 */
	public boolean has(String key) {
		validateKey(key);
		return values.containsKey(key);
	}
	
	/**
	 * Returns the {@code key} and {@code label} for the search criteria
	 * for the provided value (if any).
	 * @param key the criteria key name
	 * @return a pair if found; {@code null} otherwise
	 */
	public Pair<String, String> get(String key) {
		validateKey(key);

		if (values.containsKey(key)) {
			return new ImmutablePair<String, String>(getValue(key), getValue(key));
		}
		
		return null;
	}
	
	/**
	 * Indicates whether the current {@code SearchCriteria} is empty.
	 * @return {@code true} if it is empty; {@code false} otherwise
	 */
	public boolean isEmpty() {
		return values.isEmpty();
	}
	
	/**
	 * Returns the list of keys with a criterion set.
	 * @return the list of keys
	 */
	public Iterable<String> criteria() {
		return this.values.keySet();
	}
	
	private void addValue(String key, String value) {
		validateKey(key);
		
		if (value == null || value.isEmpty()) {
			values.remove(key);
		}
		else {
			values.put(key, new ImmutablePair<String, String>(value, value));	
		}
	}
	
	private String getValue(String key) {
		validateKey(key);
		
		if (values.get(key) == null) {
			return null;
		}
		return values.get(key).getKey();
	}
	
	private void validateKey(String key) {
		Assert.notNull("Key must be not null", key);
		if (!KEYS.contains(key)) {
			throw new IllegalArgumentException(key + " is not a valid key name");
		}
	}
}
