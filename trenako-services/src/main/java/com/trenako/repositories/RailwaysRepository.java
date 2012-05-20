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

import com.trenako.entities.Railway;

public interface RailwaysRepository {
	/**
	 * Finds the railway document in the collection by id.
	 * @param id the unique id.
	 * @return a railway document. <em>null</em> otherwise.
	 */
	Railway findById(ObjectId id);
	
	/**
	 * Finds the railway document in the collection by name.
	 * @param name the railway name.
	 * @return a railway document. <em>null</em> otherwise.
	 */
	Railway findByName(String name);

	/**
	 * Finds the railway document in the collection by name slug.
	 * @param slug the railway name slug.
	 * @return a railway document. <em>null</em> otherwise.
	 */
	Railway findBySlug(String slug);
	
	/**
	 * Finds all the railway document in the collection by country.
	 * @param country the country.
	 * @return the documents.
	 */
	Iterable<Railway> findByCountry(String country);
	
	/**
	 * Finds all the railway document in the collection.
	 * @return the documents.
	 */
	Iterable<Railway> findAll();
	
	/**
	 * Saves the railway document in the collection.
	 * @param railway the railway document to be saved.
	 */
	void save(Railway railway);
	
	/**
	 * Remove the railway document from the collection.
	 * @param railway the railway document to be deleted.
	 */
	void remove(Railway railway);
}
