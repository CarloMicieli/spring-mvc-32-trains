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

import com.trenako.entities.Option;
import com.trenako.entities.OptionFamily;

/**
 * The interface for the options service.
 * @author Carlo Micieli
 *
 */
public interface OptionsService {

	/**
	 * Returns the option document by id.
	 * @param id the unique id.
	 * @return an option document; <em>null</em> otherwise.
	 */
	Option findById(ObjectId id);

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
	Iterable<Option> findByFamily(OptionFamily family);

	/**
	 * Returns all the option documents in the collection.
	 * @return the option documents.
	 */
	Iterable<Option> findAll();

	/**
	 * Saves the option document in the collection.
	 * @param option the option document to be saved.
	 */
	void save(Option option);

	/**
	 * Remove the option document from the collection.
	 * @param option the option document to be deleted.
	 */
	void remove(Option option);

}
