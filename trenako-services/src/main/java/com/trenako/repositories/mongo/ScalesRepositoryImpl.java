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

import com.trenako.entities.Scale;
import com.trenako.repositories.ScalesRepository;
import com.trenako.repositories.mongo.collections.ScalesCollection;

/**
 * The concrete implementation for scales repository for mongodb.
 * @author Carlo Micieli
 *
 */
@Repository("scalesRepository")
public class ScalesRepositoryImpl implements ScalesRepository {

	private final ScalesCollection scales;
	
	@Autowired
	public ScalesRepositoryImpl(ScalesCollection scales) {
		this.scales = scales;
	}

	@Override
	public Scale findByName(String name) {
		return scales.findOne("name", name);
	}

	@Override
	public Scale findById(ObjectId id) {
		return scales.findById(id);
	}

	@Override
	public Scale findBySlug(String slug) {
		return scales.findOne("slug", slug);
	}
	
	@Override
	public Iterable<Scale> findAll() {
		return scales.findAllOrderBy("name", Order.ASCENDING);
	}

	@Override
	public void save(Scale scale) {
		scales.save(scale);
	}

	@Override
	public void remove(Scale scale) {
		scales.remove(scale);
	}

}
