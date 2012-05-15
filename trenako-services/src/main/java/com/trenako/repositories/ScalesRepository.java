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

import com.trenako.entities.Scale;

/**
 * The model railway scale repository.
 * @author Carlo Micieli
 *
 */
public interface ScalesRepository {
	/**
	 * Returns the scale document by the id.
	 * @param id the unique id.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	Scale findById(ObjectId id);
		
	/**
	 * Returns the brand from the name.
	 * @param name the brand name.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	Scale findByName(String name);
	
	/**
	 * Persist the brand instance to the data store.
	 * @param brand a brand.
	 */
	void save(Scale brand);
	
	/**
	 * Remove the brand from the data store.
	 * @param brand a brand.
	 */
	void remove(Scale brand);
}
