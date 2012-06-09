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

import com.trenako.entities.Railway;

/**
 * The interface for the railways service.
 * <p>
 * The concrete classes that implements the {@code RailwaysService} interface will provide
 * the following functionalities:
 * <ul>
 * <li>finds a {@code Railway} by id;</li>
 * <li>finds a {@code Railway} by slug (unique, URL friendly value);</li>
 * <li>finds a {@code Railway} by name;</li>
 * <li>returns the list of {@code Railway} by ISO country code;</li>
 * <li>returns the {@code Railway} list;</li>
 * <li>saves/removes a {@code Railway}.</li>
 * </ul>
 * </p>
 *
 * @author Carlo Micieli
 * @see com.trenako.entities.Railway
 */
public interface RailwaysService {

	/**
	 * Finds the {@link Railway} with the provided id.
	 * @param id the unique id
	 * @return a {@code Railway} if found; {@code null} otherwise
	 */
	Railway findById(ObjectId id);

	/**
	 * Finds the {@link Railway} with the provided name.
	 * <p>
	 * Due to possible data store implementation the clients for this method must 
	 * think this search as case sensitive.
	 * </p>
	 *
	 * @param name the {@code Railway} name
	 * @return a {@code Railway} if found; {@code null} otherwise
	 * @see com.trenako.entities.Railway#getName()
	 */
	Railway findByName(String name);

	/**
	 * Finds the {@link Railway} with the provided slug value.
	 * @param slug the {@code Railway} slug
	 * @return a {@code Railway} if found; {@code null} otherwise
	 * @see com.trenako.entities.Railway#getSlug()
	 */
	Railway findBySlug(String slug);
	
	/**
	 * Returns all {@link Railway} objects for the provided country from the data store.
	 * <p>
	 * This methods return all the railways, sort by name.
	 * </p>
	 * <p>
	 * The country code is one value from the {@code ISO 3166-1 alpha-2} code standard.
	 * </p>
	 * 
	 * @param country the country code
	 * @return a {@code Railway} list
	 */	
	Iterable<Railway> findByCountry(String country);
	
	/**
	 * Returns all {@link Railway} objects from the data store.
	 * <p>
	 * This methods return all the railways, sort by name.
	 * </p>
	 * @return a {@code Railway} list
	 */
	Iterable<Railway> findAll();

	/**
	 * Persists the {@link Railway} changes in the data store.
	 * <p>
	 * This method performs a "upsert": if the {@code Railway} is not present in the data store
	 * a new {@code Railway} is created; otherwise the method will update the existing {@code Railway}. 
	 * </p>	 
	 * @param railway the {@code Railway} to be saved
	 */
	void save(Railway railway);

	/**
	 * Removes a {@link Railway} from the data store.
	 * @param railway the {@code Railway} to be removed
	 */
	void remove(Railway railway);

}
