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

import com.trenako.entities.Scale;

/**
 * The interface for model railway scales repository.
 * @author Carlo Micieli
 *
 */
public interface ScalesRepository {
	/**
	 * Returns the scale document by the id.
	 * @param id the unique id.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	Scale findById(ObjectId id);
		
	/**
	 * Returns the brand from the name.
	 * @param name the brand name.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	Scale findByName(String name);

	/**
	 * Finds the {@code Scale} with the provided slug.
	 * @param slug the slug
	 * @return a {@code Brand}
	 */
	Scale findBySlug(String slug);
	
	/**
	 * Returns all scales in the collection.
	 * @return the scales list.
	 */
	Iterable<Scale> findAll();
	
	/**
	 * Saves the scale document in the collection.
	 * @param scale the scale document to be saved.
	 */
	void save(Scale scale);
	
	/**
	 * Remove the scale document from the collection.
	 * @param scale the scale document to be removed.
	 */
	void remove(Scale scale);
}
