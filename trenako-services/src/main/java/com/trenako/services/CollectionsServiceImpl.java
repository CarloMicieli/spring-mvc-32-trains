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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.CollectionsRepository;
import com.trenako.values.Condition;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.Visibility;

/**
 * A concrete implementation for the rolling stocks collection service.
 * @author Carlo Micieli
 *
 */
@Service("collectionsService")
public class CollectionsServiceImpl implements CollectionsService {

	private @Autowired(required = false) MessageSource messageSource;
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
	public Collection findBySlug(String slug) {
		return repo.findBySlug(slug);
	}

	@Override
	public Collection findByOwner(Account owner) {
		Collection coll = repo.findByOwner(owner);
		if (coll == null) {
			return Collection.defaultCollection();
		}
		return coll;
	}

	@Override
	public boolean containsRollingStock(Account owner, RollingStock rollingStock) {
		return repo.containsRollingStock(owner, rollingStock);
	}

	@Override
	public void addRollingStock(Account owner, CollectionItem item) {
		repo.addItem(owner, item);
	}


	@Override
	public void updateItem(Account owner, CollectionItem item) {
		repo.updateItem(owner, item);
	}
	
	@Override
	public void removeRollingStock(Account owner, CollectionItem item) {
		repo.removeItem(owner, item);
	}

	@Override
	public void changeVisibility(Account owner, Visibility visibility) {
		repo.changeVisibility(owner, visibility);
	}
	
	@Override
	public void createNew(Account owner) {
		Collection collection = new Collection(owner, Visibility.PUBLIC);
		repo.createNew(collection);
	}
	
	@Override
	public void saveChanges(Collection collection) {
		repo.saveChanges(collection);
	}
	
	@Override
	public void remove(Collection collection) {
		repo.remove(collection);
	}

	@Override
	public Iterable<LocalizedEnum<Condition>> conditionsList() {
		return LocalizedEnum.list(Condition.class, messageSource, null);
	}
}
