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

import com.trenako.entities.Scale;
import com.trenako.repositories.ScalesRepository;

/**
 * A concrete implementation for the scales service for mongodb.
 * @author Carlo Micieli
 *
 */
@Service("scalesService")
public class ScalesServiceImpl implements ScalesService {
	
	private ScalesRepository repo;
	
	@Autowired
	public ScalesServiceImpl(ScalesRepository repo) {
		this.repo = repo;
	}
	
	/**
	 * Returns the scale document by the id.
	 * @param id the unique id.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	@Override
	public Scale findById(ObjectId id) {
		return repo.findById(id);
	}
		
	/**
	 * Returns the brand from the name.
	 * @param name the brand name.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	@Override
	public Scale findByName(String name) {
		return repo.findByName(name);
	}
	
	@Override
	public Iterable<Scale> findAll() {
		return repo.findAll();
	}
	
	/**
	 * Persist the brand instance to the data store.
	 * @param brand a brand.
	 */
	@Override
	public void save(Scale brand) {
		repo.save(brand);
	}
	
	/**
	 * Remove the brand from the data store.
	 * @param brand a brand.
	 */
	@Override
	public void remove(Scale brand) {
		repo.remove(brand);
	}
}