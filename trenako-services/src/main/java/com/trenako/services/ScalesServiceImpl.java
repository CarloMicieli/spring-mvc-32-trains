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

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	@Override
	public Scale findById(ObjectId id) {
		return repo.findById(id);
	}
		
	@Override
	public Scale findByName(String name) {
		return repo.findByName(name);
	}
	
	@Override
	public Iterable<Scale> findAll() {
		return repo.findAll();
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void save(Scale brand) {
		repo.save(brand);
	}
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void remove(Scale brand) {
		repo.remove(brand);
	}

	@Override
	public Iterable<Scale> findAll(boolean isNarrow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Scale> findByRatio(BigDecimal ratio) {
		// TODO Auto-generated method stub
		return null;
	}
}
