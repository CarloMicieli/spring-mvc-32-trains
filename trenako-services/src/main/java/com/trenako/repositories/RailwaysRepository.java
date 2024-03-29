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
import org.springframework.data.mongodb.repository.MongoRepository;

import com.trenako.entities.Railway;

/**
 * The interface for the {@code Railway}s repository.
 *
 * @author Carlo Micieli
 */
public interface RailwaysRepository extends MongoRepository<Railway, ObjectId> {
    /**
     * Returns the {@code Railway} with the provided name.
     *
     * @param name the {@code Railway} name
     * @return a {@code Railway} if found; {@code null} otherwise
     */
    Railway findByName(String name);

    /**
     * Returns the {@code Railway} with the provided slug.
     *
     * @param slug the {@code Railway} slug
     * @return a {@code Railway} if found; {@code null} otherwise
     */
    Railway findBySlug(String slug);

    /**
     * Returns the {@code Railway} list with the provided country code.
     *
     * @param country the country code
     * @return a list of {@code Railway}s
     */
    Iterable<Railway> findByCountryOrderByNameAsc(String country);
}
