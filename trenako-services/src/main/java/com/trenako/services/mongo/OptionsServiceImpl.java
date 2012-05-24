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
package com.trenako.services.mongo;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenako.entities.Option;
import com.trenako.entities.OptionFamily;
import com.trenako.repositories.OptionsRepository;
import com.trenako.services.OptionsService;

/**
 * A concrete implementation for the options service for mongodb.
 * @author Carlo Micieli
 *
 */
@Service("optionsService")
public class OptionsServiceImpl implements OptionsService {

	private OptionsRepository repo;
	
	@Autowired
	public OptionsServiceImpl(OptionsRepository repo) {
		this.repo = repo;
	}
	
	/**
	 * Returns the option document by id.
	 * @param id the unique id.
	 * @return an option document; <em>null</em> otherwise.
	 */
	@Override
	public Option findById(ObjectId id) {
		return repo.findById(id);
	}
	
	/**
	 * Returns the option document by option name.
	 * @param name the option name.
	 * @return an option document; <em>null</em> otherwise.
	 */
	@Override
	public Option findByName(String name) {
		return repo.findByName(name);
	}
	
	/**
	 * Returns the option documents by option family.
	 * @param family the family.
	 * @return the option documents.
	 */
	@Override
	public Iterable<Option> findByFamily(OptionFamily family) {
		return repo.findByFamily(family);
	}
	
	/**
	 * Returns all the option documents in the collection.
	 * @return the option documents.
	 */
	@Override
	public Iterable<Option> findAll() {
		return repo.findAll();
	}
	
	/**
	 * Saves the option document in the collection.
	 * @param option the option document to be saved.
	 */
	@Override
	public void save(Option option) {
		repo.save(option);
	}
	
	/**
	 * Remove the option document from the collection.
	 * @param option the option document to be deleted.
	 */
	@Override
	public void remove(Option option) {
		repo.remove(option);
	}
}
