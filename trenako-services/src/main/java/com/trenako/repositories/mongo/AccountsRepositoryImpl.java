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
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;

import com.trenako.entities.Account;
import com.trenako.repositories.AccountsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Repository("accountsRepository")
public class AccountsRepositoryImpl implements AccountsRepository {

	private final MongoTemplate mongo;
	
	@Autowired
	public AccountsRepositoryImpl(MongoTemplate mongo) {
		this.mongo = mongo;
	}
	
	@Override
	public Account findById(ObjectId id) {
		return mongo.findById(id, Account.class);
	}

	@Override
	public Account findByEmailAddress(String emailAddress) {
		Query query = query(where("emailAddress").is(emailAddress));
		return mongo.findOne(query, Account.class);
	}

	@Override
	public Account findBySlug(String slug) {
		Query query = query(where("slug").is(slug));
		return mongo.findOne(query, Account.class);
	}

	@Override
	public void save(Account account) {
		mongo.save(account);
	}

	@Override
	public void remove(Account account) {
		mongo.remove(account);
	}
}
