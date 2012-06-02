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
import org.springframework.stereotype.Service;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.CollectionsRepository;

/**
 * A concrete implementation for the rolling stocks collection service.
 * @author Carlo Micieli
 *
 */
@Service("collectionsService")
public class CollectionsServiceImpl implements CollectionsService {

	private final CollectionsRepository repo;
	
	@Autowired
	public CollectionsServiceImpl(CollectionsRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Collection findById(ObjectId id) {
		return repo.findById(id);
	}

	@Override
	public Collection findByOwner(Account owner) {
		return repo.findByOwnerName(owner.getSlug());
	}

	@Override
	public Collection findByOwnerName(String ownerName) {
		return repo.findByOwnerName(ownerName);
	}

	@Override
	public boolean containsRollingStock(ObjectId collectionId,
			RollingStock rollingStock) {
		return repo.containsRollingStock(collectionId, rollingStock);
	}

	@Override
	public boolean containsRollingStock(String ownerName,
			RollingStock rollingStock) {
		return repo.containsRollingStock(ownerName, rollingStock);
	}

	@Override
	public void addItem(ObjectId collectionId, CollectionItem item) {
		repo.addItem(collectionId, item);
	}

	@Override
	public void addItem(String ownerName, CollectionItem item) {
		repo.addItem(ownerName, item);
	}

	@Override
	public void save(Collection collection) {
		repo.save(collection);
	}

	@Override
	public void remove(Collection collection) {
		repo.remove(collection);
	}
}
