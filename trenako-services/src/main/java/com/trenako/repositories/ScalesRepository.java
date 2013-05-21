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

import com.trenako.entities.Scale;

/**
 * The interface for model railway {@code Scale}s repository.
 *
 * @author Carlo Micieli
 */
public interface ScalesRepository extends MongoRepository<Scale, ObjectId> {
    /**
     * Returns the {@code Scale} with the provided name.
     *
     * @param name the {@code Scale} name.
     * @return a {@code Scale} if found; {@code null} otherwise
     */
    Scale findByName(String name);

    /**
     * Returns the {@code Scale} with the provided name.
     *
     * @param slug the {@code Scale} slug.
     * @return a {@code Scale} if found; {@code null} otherwise
     */
    Scale findBySlug(String slug);
}
