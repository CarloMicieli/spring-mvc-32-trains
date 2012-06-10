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
package com.trenako.repositories;

import org.bson.types.ObjectId;

import com.trenako.entities.Brand;

/**
 * The interface for the model railway brands repository.
 * @author Carlo Micieli
 *
 */
public interface BrandsRepository {
	/**
	 * Finds the {@code Brand} with the provided id.
	 * @param id the unique id.
	 * @return a {@code Brand} if found; {@code null} otherwise
	 */
	Brand findById(ObjectId id);
	
	/**
	 * Finds the {@code Brand} with the provided name.
	 * @param name the name
	 * @return a {@code Brand} if found; {@code null} otherwise
	 */
	Brand findByName(String name);
	
	/**
	 * Finds the {@code Brand} with the provided slug.
	 * @param slug the slug
	 * @return a {@code Brand} if found; {@code null} otherwise
	 */
	Brand findBySlug(String slug);
	
	/**
	 * Returns the {@code Brand} list.
	 * @return the brands.
	 */
	Iterable<Brand> findAll();
	
	/**
	 * Persists the brand instance to the data store.
	 * @param brand a brand.
	 */
	void save(Brand brand);
	
	/**
	 * Removes the brand from the data store.
	 * @param brand a brand.
	 */
	void remove(Brand brand);
}
