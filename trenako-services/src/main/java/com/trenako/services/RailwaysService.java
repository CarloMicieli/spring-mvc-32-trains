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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenako.entities.Railway;
import com.trenako.repositories.RailwaysRepository;

@Service("railwaysService")
public class RailwaysService {
	
	private RailwaysRepository repo;
	
	@Autowired
	public RailwaysService(RailwaysRepository repo) {
		this.repo = repo;
	}
	
	/**
	 * Finds the railway document in the collection by id.
	 * @param id the unique id.
	 * @return a railway document. <em>null</em> otherwise.
	 */
	public Railway findById(ObjectId id) {
		return repo.findById(id);
	}
	
	/**
	 * Finds the railway document in the collection by name.
	 * @param name the railway name.
	 * @return a railway document. <em>null</em> otherwise.
	 */
	public Railway findByName(String name) {
		return repo.findByName(name);
	}
	
	/**
	 * Finds all the railway document in the collection by country.
	 * @param country the country.
	 * @return the documents.
	 */
	public Iterable<Railway> findByCountry(String country) {
		return repo.findByCountry(country);
	}
	
	/**
	 * Saves the railway document in the collection.
	 * @param railway the railway document to be saved.
	 */
	public void save(Railway railway) {
		repo.save(railway);
	}
	
	/**
	 * Remove the railway document from the collection.
	 * @param railway the railway document to be deleted.
	 */
	public void remove(Railway railway) {
		repo.remove(railway);
	}
	
}
