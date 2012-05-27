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
	
	/**
	 * Returns the brand entity with the id.
	 * @param id the unique id.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	@Override
	public Brand findById(ObjectId id) {
		return repo.findById(id);
	}
	
	/**
	 * Returns the brand from the slug value.
	 * @param slug the brand slug.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	@Override
	public Brand findBySlug(String slug) {
		return repo.findBySlug(slug);
	}
	
	/**
	 * Returns the brand from the name.
	 * @param name the brand name.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	@Override
	public Brand findByName(String name) {
		return repo.findByName(name);
	}
	
	/**
	 * Persist the brand instance to the data store.
	 * @param brand a brand.
	 */
	@Override
	public void save(Brand brand) {
		repo.save(brand);
	}
	
	/**
	 * Remove the brand from the data store.
	 * @param brand a brand.
	 */
	@Override
	public void remove(Brand brand) {
		repo.remove(brand);
	}

	/**
	 * Finds all brand documents in the collection.
	 * @return the brand list.
	 */
	@Override
	public Iterable<Brand> findAll() {
		return repo.findAll();
	}
}
