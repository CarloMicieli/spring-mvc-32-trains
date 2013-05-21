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

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.trenako.activities.Activity;

/**
 * @author Carlo Micieli
 */
public interface ActivityRepository extends MongoRepository<Activity, ObjectId> {

    /**
     * Returns the {@code Activity} list for the provided actor
     *
     * @param actorSlug the actor slug
     * @param pageable  the paging information
     * @return the {@code Activity} list
     */
    List<Activity> findByActor(String actorSlug, Pageable pageable);
}
