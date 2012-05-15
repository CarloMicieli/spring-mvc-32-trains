package com.trenako.repositories;

import org.bson.types.ObjectId;

import com.trenako.entities.Railway;

public interface RailwaysRepository {
	/**
	 * Returns the railway entity with the id.
	 * @param id the unique id.
	 * @return a railway instance. <em>null</em> if no brands are found.
	 */
	Railway findById(ObjectId id);
	
	/**
	 * Returns the railway from the name.
	 * @param name the railway name.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	Railway findByName(String name);
	
	/**
	 * Persist the railway instance to the data store.
	 * @param railway a railway.
	 */
	void save(Railway railway);
	
	/**
	 * Remove the railway from the data store.
	 * @param railway a railway.
	 */
	void remove(Railway railway);
}
