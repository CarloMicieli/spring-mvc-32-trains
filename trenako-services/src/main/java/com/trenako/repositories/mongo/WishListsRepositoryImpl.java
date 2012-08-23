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

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.trenako.entities.Account;
import com.trenako.entities.Money;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;
import com.trenako.repositories.WishListsRepository;
import com.trenako.values.Visibility;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Repository("wishListsRepository")
public class WishListsRepositoryImpl implements WishListsRepository {

	private final MongoTemplate mongoTemplate;
	
	// for testing
	private Date now;
	protected void setTimestamp(Date now) {
		this.now = now;
	}
	
	protected Date now() {
		if (now == null) {
			return new Date();
		}
		return now;
	}
	
	/**
	 * Creates a new {@code WishListsRepositoryImpl}.
	 * @param mongoTemplate the mongo template
	 */
	@Autowired
	public WishListsRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public Iterable<WishList> findByOwner(Account owner) {
		Query query = query(where("owner").is(owner.getSlug()));
		query.fields().exclude("items");
		query.sort().on("name", Order.ASCENDING);
		return mongoTemplate.find(query, WishList.class);
	}

	@Override
	public Iterable<WishList> findAllByOwner(Account owner, int maxNumberOfItems) {
		Query query = query(where("owner").is(owner.getSlug()));
		query.fields().slice("items", -1 * maxNumberOfItems);
		query.sort().on("name", Order.ASCENDING);
		return mongoTemplate.find(query, WishList.class);
	}
	
	@Override
	public WishList findBySlug(String slug) {
		return mongoTemplate.findOne(query(where("slug").is(slug)), WishList.class);
	}

	@Override
	public boolean containsRollingStock(WishList wishList, RollingStock rs) {
		WishListItem item = new WishListItem(rs);
		Query query = query(where("slug").is(wishList.getSlug())
				.and("items.itemId").is(item.getItemId()));
		return mongoTemplate.count(query, WishList.class) > 0;
	}

	@Override
	public void addItem(WishList wishList, WishListItem newItem) {
		Update upd = new Update()
			.set("lastModified", now())
			.inc("numberOfItems", 1)
			.push("items", newItem);
		mongoTemplate.updateFirst(query(where("slug").is(wishList.getSlug())), upd, WishList.class);
	}

	@Override
	public void updateItem(WishList wishList, WishListItem item) {
		Query where = query(where("slug").is(wishList.getSlug())
				.and("items.itemId").is(item.getItemId()));
		Update upd = new Update()
			.set("lastModified", now())
			.set("items.$", item);
		mongoTemplate.updateFirst(where, upd, WishList.class);
	}

	@Override
	public void removeItem(WishList wishList, WishListItem item) {
		Update upd = new Update()
			.set("lastModified", now())
			.inc("numberOfItems", -1)
			.pull("items", item);
		mongoTemplate.updateFirst(query(where("slug").is(wishList.getSlug())), upd, WishList.class);
	}

	@Override
	public void changeName(WishList wishList, String newName) {
		Update upd = new Update()
			.set("lastModified", now())
			.set("name", newName)
			.set("slug", wishList.slug(newName));
		mongoTemplate.updateFirst(query(where("slug").is(wishList.getSlug())), upd, WishList.class);
	}
	
	@Override
	public void changeVisibility(WishList wishList, Visibility visibility) {
		Update upd = new Update()
			.set("lastModified", now())
			.set("visibility", visibility.label());
		mongoTemplate.updateFirst(query(where("slug").is(wishList.getSlug())), upd, WishList.class);
	}

	@Override
	public void changeBudget(WishList wishList, Money newBudget) {
		Update upd = new Update()
			.set("lastModified", now())
			.set("budget", newBudget);
		mongoTemplate.updateFirst(query(where("slug").is(wishList.getSlug())), upd, WishList.class);
	}

	@Override
	public void save(WishList wishList) {
		mongoTemplate.save(wishList);		
	}

	@Override
	public void saveChanges(WishList wishList) {
		Update upd = new Update()
			.set("name", wishList.getName())
			.set("slug", wishList.slug())
			.set("budget", wishList.getBudget())
			.set("visibility", wishList.getVisibility())
			.set("lastModified", now());
		mongoTemplate.updateFirst(query(where("slug").is(wishList.getSlug())), upd, WishList.class);	
	}
	
	@Override
	public void remove(WishList wishList) {
		mongoTemplate.remove(wishList);
	}
}
