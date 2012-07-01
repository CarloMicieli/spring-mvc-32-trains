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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.trenako.entities.Railway;
import com.trenako.repositories.RailwaysRepository;

/**
 * A concrete implementation for the railways service for mongodb.
 * @author Carlo Micieli
 *
 */
@Service("railwaysService")
public class RailwaysServiceImpl implements RailwaysService {
	
	private final RailwaysRepository repo;
	
	@Autowired
	public RailwaysServiceImpl(RailwaysRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Railway findById(ObjectId id) {
		return repo.findOne(id);
	}
	
	@Override
	public Railway findByName(String name) {
		return repo.findByName(name);
	}
	
	@Override
	public Iterable<Railway> findByCountry(String country) {
		return repo.findByCountryOrderByNameAsc(country);
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void save(Railway railway) {
		repo.save(railway);
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void remove(Railway railway) {
		repo.delete(railway);
	}

	@Override
	public Page<Railway> findAll(Pageable paging) {
		return repo.findAll(paging);
	}

	@Override
	public Railway findBySlug(String slug) {
		return repo.findBySlug(slug);
	}	
}
