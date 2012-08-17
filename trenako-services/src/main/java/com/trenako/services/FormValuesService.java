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
package com.trenako.services;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.values.Category;
import com.trenako.values.DeliveryDate;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;

/**
 * It represents the public interface for the values required by
 * {@code RollingStock} creation / editing forms.
 * 
 * @author Carlo Micieli
 *
 */
public interface FormValuesService {

	/**
	 * Returns the list of {@code Brand}s.
	 * @return the {@code Brand} list
	 */
	Iterable<Brand> brands();

	/**
	 * Returns the list of {@code Railway}s.
	 * @return the {@code Railway} list
	 */
	Iterable<Railway> railways();

	/**
	 * Returns the list of {@code Scale}s.
	 * @return the {@code Scale} list
	 */
	Iterable<Scale> scales();

	/**
	 * Returns the list of categories.
	 * @return the {@code Category} list
	 */
	Iterable<LocalizedEnum<Category>> categories();
	
	/**
	 * Returns the list of eras.
	 * @return the {@code Era} list
	 */
	Iterable<LocalizedEnum<Era>> eras();
	
	/**
	 * Returns the list of power methods.
	 * @return the {@code power method} list
	 */
	Iterable<LocalizedEnum<PowerMethod>> powerMethods();
	
	/**
	 * Returns the list of delivery dates.
	 * @return the {@code delivery dates} list
	 */
	Iterable<DeliveryDate> deliveryDates();

	/**
	 * Returns the {@code Brand} with the provided slug.
	 * @param brandSlug the {@code Brand} slug
	 * @return a {@code Brand} if found; {@code null} otherwise
	 */
	Brand getBrand(String brandSlug);
	
	/**
	 * Returns the {@code Scale} with the provided slug.
	 * @param brandSlug the {@code Scale} slug
	 * @return a {@code Scale} if found; {@code null} otherwise
	 */
	Scale getScale(String scaleSlug);
	
	/**
	 * Returns the {@code Railway} with the provided slug.
	 * @param brandSlug the {@code Railway} slug
	 * @return a {@code Railway} if found; {@code null} otherwise
	 */
	Railway getRailway(String railwaySlug);
}
