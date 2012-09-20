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
package com.trenako.repositories;

import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.NoRepositoryBean;

import com.trenako.entities.Account;

/**
 * This class implements custom methods for the {@code BrandsRepository}.
 * @author Carlo Micieli
 *
 */
@NoRepositoryBean
public class AccountsRepositoryImpl implements AccountsCustomRepository {

	private final MongoTemplate mongoTemplate;
	
	@Autowired
	public AccountsRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public void updateChanges(Account account) {
		Update upd = new Update()
			.set("roles", account.getRoles())
			.set("locked", account.isLocked())
			.set("enabled", account.isEnabled());
		
		mongoTemplate.updateFirst(query(where("slug").is(account.getSlug())), upd, Account.class);
	}
}
