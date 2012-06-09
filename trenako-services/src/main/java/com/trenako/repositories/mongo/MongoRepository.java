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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

/**
 * A mongodb generic database repository.
 * @param T the entity type.
 * 
 * @author Carlo Micieli
 *
 */
public class MongoRepository<T> {
	
	private final MongoTemplate mongoOps;
	private final Class<T> clazz;
	
	/**
	 * Creates a new mongodb repository.
	 * @param mongoOps the mongodb template
	 * @param clazz the target type
	 */
	public MongoRepository(MongoTemplate mongoOps, Class<T> clazz) {
		this.clazz = clazz;
		this.mongoOps = mongoOps;
	}
	
	/**
	 * Finds all the documents in the collection.
	 * @return the iterator for all the documents in the collection
	 */
	public Iterable<T> findAll() {
		return mongoOps.findAll(clazz);
	}

	/**
	 * Finds the document with the given id.
	 * @param id the document id
	 * @return the document with the given id if found; <em>null</em> otherwise
	 */
	public T findById(ObjectId id) {
		return mongoOps.findById(id, clazz);
	}
	
	/**
	 * Save the object to the collection for the entity 
	 * type of the object to save
	 * @param object the object to save
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
	 * Finds the first document in the collection according to the provided criteria.
	 * <p>
	 * Both key name and value are case sensitive.
	 * </p>
	 * 
	 * @param key the key name for the search criteria
	 * @param value the key value for the search criteria
	 * @return a document if found; <em>null</em> otherwise
	 */
	public T findOne(String key, Object value) {
		return mongoOps.findOne(query(where(key).is(value)), clazz);
	}
	
	/**
	 * Returns all the document in the collection by a given key value.
	 * 
	 * @param key the key name
	 * @param value the key value
	 * @return the list of documents
	 */
	public Iterable<T> findAll(String key, Object value) {
		return mongoOps.find(query(where(key).is(value)), clazz);
	}
	
	/**
	 * Returns all the sorted document in the collection by a given key value.
	 * 
	 * @param key the key name
	 * @param value the key value
	 * @param sortCriteria the key used to sort the documents
	 * @param order the order
	 * @return the list of documents
	 */
	public Iterable<T> findAll(String key, Object value, String sortCriteria, Order order) {
		final Query q = query(where(key).is(value));
		q.sort().on(sortCriteria, order);
		return mongoOps.find(q, clazz);
	}
	
	/**
	 * Returns all the document in the collection by two given key values.
	 * @param key1 the first key name
	 * @param value1 the first key value
	 * @param key2 the second key name
	 * @param value2 the second key value
	 * @return the list of documents
	 */
	public Iterable<T> findAll(String key1, Object value1, String key2, Object value2) {
		return mongoOps.find(query(where(key1).is(value1).and(key2).is(value2)), clazz);
	}
	
	
	public Iterable<T> findAll(MongoSearchCriteria sc) {
		Criteria critera = new Criteria();
		critera.andOperator(sc.criterias());
		Query query = query(critera);		
		return mongoOps.find(query, clazz);
	}
	
	
	/**
	 * Remove the document from the collection.
	 * @param object the document to delete
	 */
	public void remove(T object) {
		mongoOps.remove(object);
	}
	
	/**
	 * Remove the document from the collection.
	 * @param id the id for the document to delete
	 */
	public void removeById(ObjectId id) {
		mongoOps.remove(query(where("id").is(id)), clazz);
	}
}
