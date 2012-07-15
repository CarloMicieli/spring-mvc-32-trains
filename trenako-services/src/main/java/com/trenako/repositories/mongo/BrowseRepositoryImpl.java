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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.repositories.BrowseRepository;

/**
 * The concrete implementation for the browse repository.
 * @author Carlo Micieli
 *
 */
@Repository("browseRepository")
public class BrowseRepositoryImpl implements BrowseRepository {
	
	private final MongoTemplate mongo;
	
	/**
	 * Creates a new {@code BrowseRepositoryImpl}.
	 * @param mongo the mongo template
	 */
	@Autowired
	public BrowseRepositoryImpl(MongoTemplate mongo) {
		this.mongo = mongo;
	}

	@Override
	public Iterable<Brand> getBrands() {
		return findAll(Brand.class);
	}

	@Override
	public Iterable<Scale> getScales() {
		return findAll(Scale.class);
	}

	@Override
	public Iterable<Railway> getRailways() {
		return findAll(Railway.class);
	}

	private <T> Iterable<T> findAll(Class<T> clazz) {
		Query query = new Query();
		query.sort().on("name", Order.ASCENDING);
		return mongo.find(query, clazz);
	}
}
