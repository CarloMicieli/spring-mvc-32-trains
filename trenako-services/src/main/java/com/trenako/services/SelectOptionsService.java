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

import org.bson.types.ObjectId;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;

/**
 * 
 * @author Carlo Micieli
 *
 */
public interface SelectOptionsService {

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
	Iterable<String> categories();
	
	/**
	 * Returns the list of eras.
	 * @return the {@code Era} list
	 */
	Iterable<String> eras();
	
	/**
	 * Returns the list of power methods.
	 * @return the {@code power method} list
	 */
	Iterable<String> powerMethods();

	/**
	 * Returns the {@code Brand} with the provided id.
	 * @param brandId the {@code Brand} id
	 * @return a {@code Brand} if found; {@code null} otherwise
	 */
	Brand findBrand(ObjectId brandId);
	
	/**
	 * Returns the {@code Scale} with the provided id.
	 * @param scaleId the {@code Scale} id
	 * @return a {@code Scale} if found; {@code null} otherwise
	 */
	Scale findScale(ObjectId scaleId);
	
	/**
	 * Returns the {@code Railway} with the provided id.
	 * @param railwayId the {@code Railway} id
	 * @return a {@code Railway} if found; {@code null} otherwise
	 */
	Railway findRailway(ObjectId railwayId);
}
