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
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.stereotype.Repository;

import com.trenako.entities.Railway;
import com.trenako.repositories.RailwaysRepository;
import com.trenako.repositories.mongo.collections.RailwaysCollection;

/**
 * The concrete implementation for the railways repository for mongodb.
 * @author Carlo Micieli
 *
 */
@Repository("railwaysRepository")
public class RailwaysRepositoryImpl implements RailwaysRepository {

	final RailwaysCollection mongo;
	
	@Autowired
	public RailwaysRepositoryImpl(RailwaysCollection mongo) {
		this.mongo = mongo;
	}

	@Override
	public Iterable<Railway> findByCountry(String country) {
		return mongo.findOrderBy("country", country, "name", Order.ASCENDING);
	}
	
	@Override
	public Railway findByName(String name) {
		return mongo.findOne("name", name);
	}	
	
	@Override
	public Railway findBySlug(String slug) {
		return mongo.findOne("slug", slug);
	}

	@Override
	public Railway findById(ObjectId id) {
		return mongo.findById(id);
	}

	@Override
	public Iterable<Railway> findAll() {
		return mongo.findAllOrderBy("name", Order.ASCENDING);
	}

	@Override
	public void save(Railway railway) {
		mongo.save(railway);
	}

	@Override
	public void remove(Railway railway) {
		mongo.remove(railway);
	}	
}
