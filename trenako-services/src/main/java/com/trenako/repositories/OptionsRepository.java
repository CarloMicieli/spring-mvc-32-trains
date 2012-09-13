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

import com.trenako.entities.Option;

/**
 * The interface for the options repository.
 * @author Carlo Micieli
 *
 */
public interface OptionsRepository extends MongoRepository<Option, ObjectId> {

	/**
	 * Returns the option document by option name.
	 * @param name the option name.
	 * @return an option document; <em>null</em> otherwise.
	 */
	Option findByName(String name);
	
	/**
	 * Returns the option documents by option family.
	 * @param family the family.
	 * @return the option documents.
	 */
	Iterable<Option> findByFamily(String family);
}
