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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

/**
 * A Mongo database repository
 * @author Carlo Micieli
 *
 */
public abstract class MongoRepository<T> {
	
	protected MongoTemplate mongoOps;
	protected Class<T> clazz;
	
	/**
	 * Creates a new Mongo repository.
	 * @param mongoOps the Mongo template.
	 * @param clazz the target type.
	 */
	public MongoRepository(MongoTemplate mongoOps, Class<T> clazz) {
		this.clazz = clazz;
		this.mongoOps = mongoOps;
	}
	
	/**
	 * Finds all the documents in the collection.
	 * @return the iterator for all the documents in the collection.
	 */
	public Iterable<T> findAll() {
		return mongoOps.findAll(clazz);
	}

	/**
	 * Returns the document with the given id.
	 * @param id the document id.
	 * @return the document with the given id.
	 */
	public T findById(ObjectId id) {
		return mongoOps.findById(id, clazz);
	}
	
	/**
	 * Save the object to the collection for the entity 
	 * type of the object to save. 
	 * @param object the object to save.
	 */
	public void save(T object) {
		mongoOps.save(object);
	}
	
	/**
	 * Remove all documents in the collection.
	 */
	public void removeAll() {
		mongoOps.remove(new Query(), clazz);
	}
	
	/**
	 * Finds a document in the collection by a given key value.
	 * @param key the key name.
	 * @param value the key value.
	 * @return a document.
	 */
	public T findOne(String key, Object value) {
		return mongoOps.findOne(query(where(key).is(value)), clazz);
	}
	
	/**
	 * Finds all the document in the collection by a given key value.
	 * @param key the key name.
	 * @param value the key value.
	 * @return the documents.
	 */
	public Iterable<T> findAll(String key, Object value) {
		return mongoOps.find(query(where(key).is(value)), clazz);
	}
	
	public Iterable<T> findAll(String key, Object value, String sortCriteria, Order order) {
		final Query q = query(where(key).is(value));
		q.sort().on(sortCriteria, order);
		return mongoOps.find(q, clazz);
	}
		
	public Iterable<T> findAll(String key1, Object value1, String key2, Object value2) {
		return mongoOps.find(query(where(key1).is(value1).and(key2).is(value2)), clazz);
	}
	
	/**
	 * Remove the document from the collection.
	 * @param object the document to delete.
	 */
	public void remove(T object) {
		mongoOps.remove(object);
	}
	
	/**
	 * Remove the document from the collection.
	 * @param id the id for the document to delete.
	 */
	public void removeById(ObjectId id) {
		mongoOps.remove(query(where("id").is(id)), clazz);
	}
}
