package com.trenako.repositories.mongo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

/**
 * A Mongo database repository.
 * @author Carlo Micieli
 *
 */
public class MongoRepository<T> {
	
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