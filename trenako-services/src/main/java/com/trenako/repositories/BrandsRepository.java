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
import org.springframework.data.repository.PagingAndSortingRepository;

import com.trenako.entities.Brand;

/**
 * The repository for the model railway {@code Brand}s.
 * @author Carlo Micieli
 *
 */
public interface BrandsRepository extends PagingAndSortingRepository<Brand, ObjectId> {
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
}
