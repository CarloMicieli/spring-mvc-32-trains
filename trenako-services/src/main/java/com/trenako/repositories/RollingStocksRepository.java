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

import com.trenako.entities.RollingStock;

/**
 * The interface for the model rolling stocks repository.
 * @author Carlo Micieli
 *
 */
public interface RollingStocksRepository extends MongoRepository<RollingStock, ObjectId> {
	
	/**
	 Finds the rolling stock document in the collection by slug.
	 * @param slug the rolling stock slug.
	 * @return the rolling stock document if found; <em>null</em> otherwise.
	 */
	RollingStock findBySlug(String slug);
}
