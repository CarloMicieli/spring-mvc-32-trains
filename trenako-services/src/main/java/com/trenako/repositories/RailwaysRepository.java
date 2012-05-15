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
	 * Returns the railway entity with the id.
	 * @param id the unique id.
	 * @return a railway instance. <em>null</em> if no brands are found.
	 */
	Railway findById(ObjectId id);
	
	/**
	 * Returns the railway from the name.
	 * @param name the railway name.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	Railway findByName(String name);
	
	/**
	 * Persist the railway instance to the data store.
	 * @param railway a railway.
	 */
	void save(Railway railway);
	
	/**
	 * Remove the railway from the data store.
	 * @param railway a railway.
	 */
	void remove(Railway railway);
}
