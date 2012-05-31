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

import com.trenako.entities.Account;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.ReviewsRepository;

import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;

/**
 * A concrete implementation for the user reviews repository for mongodb.
 * @author Carlo Micieli
 *
 */
public class ReviewsRepositoryImpl implements ReviewsRepository {

	private final MongoTemplate mongo;
	
	/**
	 * Creates a new reviews repository for mongodb.
	 * @param mongo
	 */
	@Autowired
	public ReviewsRepositoryImpl(MongoTemplate mongo) {
		this.mongo = mongo;
	}
	
	@Override
	public Review findById(ObjectId id) {
		return mongo.findById(id, Review.class);
	}

	@Override
	public Iterable<Review> findByAuthor(Account author) {
		return findByAuthor(author.getSlug());
	}

	@Override
	public Iterable<Review> findByAuthor(String authorName) {
		return mongo.find(query(where("authorName").is(authorName)), Review.class);
	}

	@Override
	public Iterable<Review> findByRollingStock(RollingStock rollingStock) {
		return findByRollingStock(rollingStock.getSlug());
	}

	@Override
	public Iterable<Review> findByRollingStock(String rsSlug) {
		return mongo.find(query(where("rsSlug").is(rsSlug)), Review.class);
	}

	@Override
	public void save(Review review) {
		mongo.save(review);
	}

	@Override
	public void remove(Review review) {
		mongo.remove(review);
	}

}
