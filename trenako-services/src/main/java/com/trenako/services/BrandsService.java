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

import com.trenako.entities.Brand;

/**
 * The interface for the brands service.
 * <p>
 * The concrete classes that implements the {@code BrandsService} interface will provide
 * the following functionalities:
 * <ul>
 * <li>finds a {@code Brand} by id;</li>
 * <li>finds a {@code Brand} by slug (unique, URL friendly value);</li>
 * <li>finds a {@code Brand} by name;</li>
 * <li>returns the {@code Brand} list;</li>
 * <li>saves/removes a {@code Brand}.</li>
 * </ul>
 * </p>
 *
 * @author Carlo Micieli
 * @see com.trenako.entities.Brand
 * @see com.trenako.repositories.BrandsRepository
 */
public interface BrandsService {

    /**
     * Finds the {@link Brand} with the provided id.
     *
     * @param id the unique id
     * @return a {@code Brand} if found; {@code null} otherwise
     */
    Brand findById(ObjectId id);

    /**
     * Finds the {@link Brand} with the provided slug value.
     *
     * @param slug the {@code Brand} slug
     * @return a {@code Brand} if found; {@code null} otherwise
     * @see com.trenako.entities.Brand#getSlug()
     */
    Brand findBySlug(String slug);

    /**
     * Finds the {@link Brand} with the provided name.
     * <p>
     * Due to possible data store implementation the clients for this method must
     * think this search as case sensitive.
     * </p>
     *
     * @param name the {@code Brand} name
     * @return a {@code Brand} if found; {@code null} otherwise
     */
    Brand findByName(String name);

    /**
     * Returns the list of all {@link Brand} objects.
     * <p>
     * This methods return all the brands, sort by name.
     * </p>
     *
     * @return a {@code Brand} list
     */
    Iterable<Brand> findAll();

    /**
     * Returns the list of paginated {@link Brand}s.
     *
     * @param pageable the pagination information
     * @return the paginated {@code Brand} results
     */
    Page<Brand> findAll(Pageable pageable);

    /**
     * Persists the {@link Brand} changes in the data store.
     * <p>
     * This method performs a "upsert": if the {@code Brand} is not present in the data store
     * a new {@code Brand} is created; otherwise the method will update the existing {@code Brand}.
     * </p>
     *
     * @param brand the {@code Brand} to be saved
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    void save(Brand brand);

    /**
     * Persists the {@link Brand} changes in the data store.
     * <p>
     * This method performs a "upsert": if the {@code Brand} is not present in the data store
     * a new {@code Brand} is created; otherwise the method will update the existing {@code Brand}.
     * </p>
     *
     * @param brand the {@code Brand} to be saved
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
    void remove(Brand brand);
}
