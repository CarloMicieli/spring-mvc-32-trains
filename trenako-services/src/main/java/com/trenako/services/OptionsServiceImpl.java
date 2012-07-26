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

import com.trenako.entities.Option;
import com.trenako.repositories.OptionsRepository;
import com.trenako.values.OptionFamily;

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
	
	@Override
	public Option findById(ObjectId id) {
		return repo.findOne(id);
	}
	
	@Override
	public Option findByName(String name) {
		return repo.findByName(name);
	}
	
	@Override
	public Iterable<Option> findByFamily(OptionFamily family) {
		return repo.findByFamily(family);
	}
	
	@Override
	public Iterable<Option> findAll() {
		return repo.findAll();
	}
	
	@Override
	public void save(Option option) {
		repo.save(option);
	}
	
	@Override
	public void remove(Option option) {
		repo.delete(option);
	}
}
