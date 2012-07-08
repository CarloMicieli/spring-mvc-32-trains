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

import static org.springframework.data.mongodb.core.query.Query.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.trenako.entities.PersistentLogin;
import com.trenako.repositories.RememberMeRepository;
import static  org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Repository("rememberMeRepository")
public class RememberMeRepositoryImpl implements RememberMeRepository {

	private MongoTemplate mongoTemplate;
	
	@Autowired
	public RememberMeRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public PersistentLogin findBySeries(String seriesId) {
		return mongoTemplate.findOne(query(where("series").is(seriesId)), PersistentLogin.class);
	}

	@Override
	public void createNew(PersistentLogin token) {
		mongoTemplate.insert(token);
	}

	@Override
	public void save(PersistentLogin token) {
		mongoTemplate.save(token);
	}

	@Override
	public void deleteByUsername(String username) {
		mongoTemplate.remove(query(where("username").is(username)), PersistentLogin.class);
	}
}
