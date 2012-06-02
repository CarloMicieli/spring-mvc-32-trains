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
import static org.springframework.data.mongodb.core.query.Criteria.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.CollectionsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Repository("collectionsRepository")
public class CollectionsRepositoryImpl implements CollectionsRepository {

	private final MongoTemplate mongo;
	
	@Autowired
	public CollectionsRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongo = mongoTemplate;
	}

	@Override
	public Collection findById(ObjectId id) {
		return mongo.findById(id, Collection.class);
	}

	@Override
	public Collection findByOwnerName(String ownerName) {
		Query query = query(where("ownerName").is(ownerName));
		return mongo.findOne(query, Collection.class);
	}

	@Override
	public boolean containsRollingStock(ObjectId collectionId,
			RollingStock rollingStock) {
		Query query = query(
				where("id").is(collectionId)
				.and("items.rsSlug").is(rollingStock.getSlug()));
				
		return mongo.count(query, Collection.class) > 0;
	}

	@Override
	public boolean containsRollingStock(String ownerName,
			RollingStock rollingStock) {
		Query query = query(
				where("ownerName").is(ownerName)
				.and("items.rsSlug").is(rollingStock.getSlug()));
				
		return mongo.count(query, Collection.class) > 0;
	}

	@Override
	public void addItem(ObjectId collectionId, CollectionItem item) {
		mongo.updateFirst(query(where("id").is(collectionId)),
				new Update().addToSet("items", item), 
				Collection.class);
	}

	@Override
	public void addItem(String ownerName, CollectionItem item) {
		mongo.updateFirst(query(where("ownerName").is(ownerName)),
				new Update().addToSet("items", item), 
				Collection.class);
	}

	@Override
	public void save(Collection collection) {
		mongo.save(collection);
	}

	@Override
	public void remove(Collection collection) {
		mongo.remove(collection);
	}

}
