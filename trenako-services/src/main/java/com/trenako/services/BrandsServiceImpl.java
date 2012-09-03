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
import org.springframework.stereotype.Service;

import com.trenako.entities.Brand;
import com.trenako.repositories.BrandsRepository;

/**
 * A concrete implementation for the brands service for mongodb.
 * @author Carlo Micieli
 *
 */
@Service("brandsService")
public class BrandsServiceImpl implements BrandsService {
	
	private BrandsRepository repo;
	
	@Autowired
	public BrandsServiceImpl(BrandsRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Brand findById(ObjectId id) {
		return repo.findById(id);
	}
	
	@Override
	public Brand findBySlug(String slug) {
		return repo.findBySlug(slug);
	}
	
	@Override
	public Brand findByName(String name) {
		return repo.findByName(name);
	}
	
	@Override
	public void save(Brand brand) {
		if (brand.getAddress() != null && brand.getAddress().isEmpty()) {
			brand.setAddress(null);
		}
		
		repo.save(brand);
	}
	
	@Override
	public void remove(Brand brand) {
		repo.delete(brand);
	}

	@Override
	public Iterable<Brand> findAll() {
		return repo.findAll();
	}
	
	@Override
	public Page<Brand> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}
}
