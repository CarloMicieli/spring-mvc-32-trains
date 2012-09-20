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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.trenako.entities.Account;
import com.trenako.repositories.AccountsRepository;

/**
 * The concrete implementation for the accounts service.
 * @author Carlo Micieli
 *
 */
@Service("accountsService")
public class AccountsServiceImpl implements AccountsService {

	private final AccountsRepository repo;
	
	@Autowired
	public AccountsServiceImpl(AccountsRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Account findById(ObjectId id) {
		return repo.findOne(id);
	}

	@Override
	public Account findByEmailAddress(String emailAddress) {
		return repo.findByEmailAddress(emailAddress);
	}

	@Override
	public Account findBySlug(String slug) {
		return repo.findBySlug(slug);
	}

	@Override
	public void save(Account account) {
		repo.save(account);
	}

	@Override
	public void remove(Account account) {
		repo.delete(account);
	}

	@Override
	public void updateChanges(Account account) {
		repo.updateChanges(account);
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {
		return repo.findAll(pageable);
	}

}
