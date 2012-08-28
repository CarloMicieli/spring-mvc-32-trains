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

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.trenako.entities.Account;
import com.trenako.entities.CategoriesCount;
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.CollectionsRepository;
import com.trenako.utility.Maps;
import com.trenako.values.Visibility;

/**
 * The concrete implementation of {@code CollectionsRepository} for Mongo db.
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

	// for testing
	private Date now = null;
	protected void setCurrentTimestamp(Date now) {
		this.now = now;
	}
	
	protected Date now() {
		if (now == null) {
			return new Date();
		}
		return now;
	}
	
	@Override
	public Collection findById(ObjectId id) {
		return mongo.findById(id, Collection.class);
	}

	@Override
	public Collection findBySlug(String slug) {
		Query query = query(where("slug").is(slug));
		return mongo.findOne(query, Collection.class);
	}

	@Override
	public Collection findByOwner(Account owner) {
		Query query = query(where("owner").is(owner.getSlug()));
		return mongo.findOne(query, Collection.class);
	}

	@Override
	public boolean containsRollingStock(Account owner, RollingStock rollingStock) {
		Query query = query(where("owner").is(owner.getSlug())
				.and("items.rollingStock.slug").is(rollingStock.getSlug()));
				
		return mongo.count(query, Collection.class) > 0;
	}

	@Override
	public void addItem(Account owner, CollectionItem item) {
		Collection collection = new Collection(owner);
				
		// force the item to calculate its id
		if (!StringUtils.hasText(item.getItemId())) {
			item.setItemId(item.getItemId());
		}
		
		Update upd = new Update()
			.set("owner", collection.getOwner())
			.set("lastModified", now())
			.push("items", item)
			.inc("categories." + CategoriesCount.getKey(item.getCategory()), 1);
		mongo.upsert(query(where("owner").is(owner.getSlug())), upd, Collection.class);
	}

	@Override
	public void updateItem(Account owner, CollectionItem item) {
		Collection collection = new Collection(owner);
		
		Query where = query(where("owner").is(collection.getOwner())
				.and("items.itemId").is(item.getItemId()));
		Update upd = new Update()
			.set("lastModified", now())
			.set("items.$", item);
		mongo.updateFirst(where, upd, Collection.class);
	}
	
	@Override
	public void removeItem(Account owner, CollectionItem item) {
		Collection collection = new Collection(owner);
		
		Update upd = new Update()
			.pull("items", Maps.map("itemId", item.getItemId()))
			.inc("categories." + CategoriesCount.getKey(item.getCategory()), -1)
			.set("lastModified", now());
		mongo.updateFirst(query(where("owner").is(collection.getOwner())), upd, Collection.class);
	}

	@Override
	public void changeVisibility(Account owner, Visibility visibility) {
		Update upd = new Update()
			.set("visibility", visibility.label())
			.set("lastModified", now());
		mongo.updateFirst(query(where("owner").is(owner.getSlug())), upd, Collection.class);	
	}

	@Override
	public void createNew(Collection collection) {
		mongo.save(collection);
	}

	@Override
	public void remove(Collection collection) {
		mongo.remove(collection);
	}
}
