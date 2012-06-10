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
package com.trenako.repositories.mongo;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.stereotype.Repository;

import com.trenako.entities.Brand;
import com.trenako.repositories.BrandsRepository;

/**
 * The concrete implementation for the mongodb brands repository.
 * @author Carlo Micieli
 *
 */
@Repository("brandsRepository")
public class BrandsRepositoryImpl implements BrandsRepository {
	
	private final MongoRepository<Brand> mongo;
	
	/**
	 * Creates a new mongodb brands repository.
	 * @param mongo the mongodb template
	 */
	@Autowired
	public BrandsRepositoryImpl(MongoTemplate mongo) {
		this.mongo = new MongoRepository<Brand>(mongo, Brand.class);
	}
	
	// constructor: for testing
	BrandsRepositoryImpl(MongoRepository<Brand> mongo) {
		this.mongo = mongo;
	}
	
	@Override
	public Brand findBySlug(String slug) {
		return mongo.findOne("slug", slug);
	}

	@Override
	public Brand findByName(String name) {
		return mongo.findOne("name", name);
	}

	@Override
	public Brand findById(ObjectId id) {
		return mongo.findById(id);
	}

	@Override
	public Iterable<Brand> findAll() {
		return mongo.findAllOrderBy("name", Order.ASCENDING);
	}

	@Override
	public void save(Brand brand) {
		mongo.save(brand);		
	}

	@Override
	public void remove(Brand brand) {
		mongo.remove(brand);
	}
}
