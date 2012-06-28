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
import org.springframework.stereotype.Repository;

import com.trenako.entities.Brand;
import com.trenako.entities.Image;
import com.trenako.repositories.ImagesRepository;

/**
 * The concrete implementation for {@code ImagesRepository} interface.
 * @author Carlo Micieli
 *
 */
@Repository("imagesRepository")
public class ImagesRepositoryImpl implements ImagesRepository {

	private final MongoTemplate mongoTemplate;
	
	/**
	 * Creates a new {@code ImagesRepositoryImpl} class.
	 * @param mongoTemplate the mongo db template
	 */
	@Autowired
	public ImagesRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public Image findBrandImage(ObjectId brandId) {
		final Brand b = mongoTemplate.findById(brandId, Brand.class);
		if( b==null ) return null;
		return b.getLogo();
	}

}
