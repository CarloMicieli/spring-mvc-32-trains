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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import com.trenako.entities.Scale;

/**
 * The interface for the model railway scales service.
 * <p>
 * The concrete classes that implements the {@code ScalesService} interface will provide
 * the following functionalities:
 * <ul>
 * <li>finds a {@code Scale} by id;</li>
 * <li>finds a {@code Scale} by name;</li>
 * <li>returns the {@code Scale} list;</li>
 * <li>returns the {@code Scale} list for narrow/standard gauges;</li>
 * <li>saves/removes a {@code Scale}.</li>
 * </ul>
 * </p>
 *
 * @author Carlo Micieli
 * @see com.trenako.entities.Scale
 */ 
public interface ScalesService {

	/**
	 * Finds the {@link Scale} with the provided id.
	 * @param id the unique id
	 * @return a {@code Scale} if found; {@code null} otherwise
	 */
	Scale findById(ObjectId id);

	/**
	 * Finds the {@link Scale} with the provided name.
	 * <p>
	 * Due to possible data store implementation the clients for this method must 
	 * think this search as case sensitive.
	 * </p>
	 *  
	 * @param name the {@code Scale} name
	 * @return a {@code Scale} if found; {@code null} otherwise
	 */
	Scale findByName(String name);
	
	/**
	 * Finds the {@link Scale} with the provided slug.
	 * <p>
	 * Due to possible data store implementation the clients for this method must 
	 * think this search as case sensitive.
	 * </p>
	 *  
	 * @param slug the {@code Scale} slug
	 * @return a {@code Scale} if found; {@code null} otherwise
	 */
	Scale findBySlug(String slug);
	
	/**
	 * Returns all {@link Scale} objects.
	 * <p>
	 * This methods return all the scale, sort by name.
	 * </p>
	 * @param pageable 
	 * 
	 * @return the list of scales
	 */
	Page<Scale> findAll(Pageable pageable);

	/**
	 * Persists the {@link Scale} changes in the data store.
	 * <p>
	 * This method performs a "upsert": if the {@code Scale} is not present in the data store
	 * a new {@code Scale} is created; otherwise the method will update the existing {@code Scale}. 
	 * </p>	 
	 * @param scale the {@code Scale} to be saved
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
	void save(Scale scale);

	/**
	 * Removes a {@link Scale} from the data store.
	 * @param scale the scale {@code Scale} to be removed
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
	void remove(Scale scale);

}
