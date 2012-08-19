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
import java.util.EnumMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
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
 * It represents immutable rolling stock search criteria as used
 * to perform queries against the database.
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
	
	private final Map<Criteria, Pair<String, String>> values;
	
	// Init search criteria with the builder values
	private SearchCriteria(Map<Criteria, Pair<String, String>> values) {
		this.values = values;
	}
	
	public static final Iterable<Criteria> KEYS = Arrays.asList(Criteria.values());
	
	/**
	 * Returns an empty immutable {@code SearchCriteria}.
	 * @return an empty {@code SearchCriteria}
	 */
	public static final SearchCriteria EMPTY = new SearchCriteria(
			Collections.<Criteria, Pair<String, String>> emptyMap());
	
	/**
	 * Creates an empty {@code SearchCriteria}.
	 */
	public SearchCriteria() {
		values = new EnumMap<Criteria, Pair<String, String>>(Criteria.class);
	}
	
	/**
	 * Returns an immutable copy of the provided {@code SearchCriteria}.
	 * <p>
	 * Only read operations are allowed; any attempt to modify the returned 
	 * {@code SearchCriteria} will result in an {@code UnsupportedOperationException}.
	 * </p>
	 * 
	 * @param sc the original {@code SearchCriteria}
	 * @return an immutable object
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
		private Map<Criteria, Pair<String, String>> values = null;
		
		/**
		 * Creates a new {@code SearchCriteria}.
		 * <p>
		 * No criteria is required, but it is not allowed
		 * to build {@code SearchCriteria} objects if at least
		 * one criteria is set.
		 * </p>
		 */
		public Builder() {
			values = new EnumMap<Criteria, Pair<String, String>>(Criteria.class);
		}
		
		/**
		 * Sets the {@code power method} criteria.
		 * <p>
		 * If the provided parameter is {@code null} then the value
		 * is safely ignored by the builder.
		 * </p>
		 * 
		 * @param pm the power method
		 * @return a builder
		 */
		public Builder powerMethod(LocalizedEnum<PowerMethod> pm) {
			if (pm != null) {
				add(Criteria.POWER_METHOD, pm.getKey(), pm.getLabel());
			}
			return this;
		}
		
		/**
		 * Sets the {@code brand} criteria.
		 * <p>
		 * If the provided parameter is {@code null} then the value
		 * is safely ignored by the builder.
		 * </p>
		 * 
		 * @param brand the brand
		 * @return a builder
		 */
		public Builder brand(Brand brand) {
			if (brand != null) {
				add(Criteria.BRAND, brand.getSlug(), brand.getLabel());
			}
			return this;
		}
		
		/**
		 * Sets the {@code railway} criteria.
		 * <p>
		 * If the provided parameter is {@code null} then the value
		 * is safely ignored by the builder.
		 * </p>
		 * 
		 * @param railway the railway
		 * @return a builder
		 */
		public Builder railway(Railway railway) {
			if (railway != null) {
				add(Criteria.RAILWAY, railway.getSlug(), railway.getLabel());
			}
			return this;
		}
		
		/**
		 * Sets the {@code scale} criteria.
		 * <p>
		 * If the provided parameter is {@code null} then the value
		 * is safely ignored by the builder.
		 * </p>
		 * 
		 * @param scale the scale
		 * @return a builder
		 */
		public Builder scale(Scale scale) {
			if (scale != null) {
				add(Criteria.SCALE, scale.getSlug(), scale.getLabel());
			}
			return this;
		}
		
		/**
		 * Sets the {@code powerMethod} and {@code category} criteria.
		 * <p>
		 * If the provided parameter is {@code null} then the value
		 * is safely ignored by the builder.
		 * </p>
		 * 
		 * @param cat the category
		 * @return a builder
		 */
		public Builder cat(Cat cat) {
			if (cat != null) { 
				add(Criteria.CAT, cat.toString(), cat.toString());
			}
			return this;
		}
		
		/**
		 * Sets the {@code category} criteria.
		 * <p>
		 * If the provided parameter is {@code null} then the value
		 * is safely ignored by the builder.
		 * </p>
		 * 
		 * @param category the category name
		 * @return a builder
		 */
		public Builder category(LocalizedEnum<Category> category) {
			if (category != null) {
				add(Criteria.CATEGORY, category.getKey(), category.getLabel());
			}
			return this;
		}
		
		/**
		 * Sets the {@code era} criteria.
		 * <p>
		 * If the provided parameter is {@code null} then the value
		 * is safely ignored by the builder.
		 * </p>
		 * 
		 * @param era the era
		 * @return a builder
		 */
		public Builder era(LocalizedEnum<Era> era) {
			if (era != null) {
				add(Criteria.ERA, era.getKey(), era.getLabel());
			}
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
		
		private void add(Criteria criteria, String key, String label) {
			if (StringUtils.hasText(key)) {
				values.put(criteria, new ImmutablePair<String, String>(key, label));
			}
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
	 * @see com.trenako.values.PowerMethod
	 */
	public String getPowerMethod() {
		return getValue(Criteria.POWER_METHOD);
	}

	/**
	 * Checks whether a {@code power method} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasPowerMethod() {
		return values.containsKey(Criteria.POWER_METHOD);
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
		return getValue(Criteria.BRAND);
	}
	
	/**
	 * Checks whether a {@code brand} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasBrand() {
		return values.containsKey(Criteria.BRAND);
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
		return getValue(Criteria.SCALE);
	}

	/**
	 * Checks whether a {@code scale} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasScale() {
		return values.containsKey(Criteria.SCALE);
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
		return getValue(Criteria.CATEGORY);
	}

	/**
	 * Checks whether a {@code category} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasCategory() {
		return values.containsKey(Criteria.CATEGORY);
	}
	
	/**
	 * Returns the {@code power method} and {@code category} search criteria.
	 * <p>
	 * The appropriate way to check whether a category is selected is using
	 * the {@link SearchCriteria#hasCat()} method.
	 * </p>
	 * 
	 * @return the criteria value
	 */
	public String getCat() {
		return getValue(Criteria.CAT);
	}

	/**
	 * Checks whether a {@code category} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasCat() {
		return values.containsKey(Criteria.CAT);
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
		return getValue(Criteria.ERA);
	}

	/**
	 * Checks whether a {@code era} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasEra() {
		return values.containsKey(Criteria.ERA);
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
		return getValue(Criteria.RAILWAY);
	}

	/**
	 * Checks whether a {@code railway} criteria is set.
	 * @return {@code true} if a criteria exists; {@code false} otherwise
	 */
	public boolean hasRailway() {
		return values.containsKey(Criteria.RAILWAY);
	}

	/**
	 * Indicates whether the current object has a criteria
	 * for the provided key.
	 * @param key the criteria name
	 * @return {@code true} if the criterion exists; {@code false} otherwise
	 */
	public boolean has(Criteria key) {
		return values.containsKey(key);
	}

	/**
	 * Indicates whether the current object has a criteria
	 * for the provided type.
	 * 
	 * @param criterionType the criterion type
	 * @return {@code true} if the criterion exists; {@code false} otherwise
	 */
	public boolean has(Class<?> criterionType) {
		Criteria key = Criteria.criterionForType(criterionType);
		return values.containsKey(key);
	}
	
	/**
	 * Returns the {@code key} and {@code label} for the search criteria
	 * for the provided value (if any).
	 * @param key the criteria key name
	 * @return a pair if found; {@code null} otherwise
	 */
	public Pair<String, String> get(Criteria key) {
		return values.get(key);
	}
	
	/**
	 * Returns the {@code key} and {@code label} for the search criteria
	 * for the provided value (if any).
	 * @param key the criteria key name
	 * @return a pair if found; {@code null} otherwise
	 */
	public Pair<String, String> get(Class<?> criterionType) {
		Criteria key = Criteria.criterionForType(criterionType);
		return get(key);
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
	public Iterable<Criteria> criteria() {
		return this.values.keySet();
	}
	
	@Override
	public String toString() {
		return this.values.toString();		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof SearchCriteria)) return false;
		
		SearchCriteria other = (SearchCriteria) obj;
		return this.values.equals(other.values);
	}
	
	private String getValue(Criteria key) {
		if (values.get(key) == null) {
			return null;
		}
		return values.get(key).getKey();
	}
}
