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
package com.trenako.repositories.mongo.collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.trenako.entities.Brand;

/**
 * It represents the helper class for the {@code Brand} mongo collection.
 * @author Carlo Micieli
 *
 */
@Component
public class BrandsCollection extends MongoCollection<Brand> {

	/**
	 * Creates a new {@code BrandsCollection} class.
	 * @param mongoOps the mongo template
	 */
	@Autowired
	public BrandsCollection(MongoTemplate mongoOps) {
		super(mongoOps, Brand.class);
	}
}