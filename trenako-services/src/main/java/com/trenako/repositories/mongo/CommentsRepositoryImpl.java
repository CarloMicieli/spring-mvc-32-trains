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
import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.repositories.CommentsRepository;

import static org.springframework.data.mongodb.core.query.Query.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;

/**
 * The concrete implementation for the comments mongodb repository.
 * @author Carlo Micieli
 *
 */
public class CommentsRepositoryImpl implements CommentsRepository {

	private final MongoTemplate mongo;
	
	/**
	 * Creates a new mongodb repository for comments.
	 * @param mongo the mongodb template
	 */
	@Autowired
	public CommentsRepositoryImpl(MongoTemplate mongo) {
		this.mongo = mongo;
	}
	
	@Override
	public Comment findById(ObjectId id) {
		return mongo.findById(id, Comment.class);
	}

	@Override
	public Iterable<Comment> findByAuthor(Account author) {
		return findByAuthor(author.getSlug());
	}

	@Override
	public Iterable<Comment> findByAuthor(String authorName) {
		return mongo.find(query(where("authorName").is(authorName)), Comment.class);
	}

	@Override
	public Iterable<Comment> findByRollingStock(RollingStock rollingStock) {
		return findByRollingStock(rollingStock.getSlug());
	}

	@Override
	public Iterable<Comment> findByRollingStock(String rsSlug) {
		return mongo.find(query(where("rsSlug").is(rsSlug)), Comment.class);
	}

	@Override
	public void save(Comment comment) {
		mongo.save(comment);
	}

	@Override
	public void remove(Comment comment) {
		mongo.remove(comment);
	}

}