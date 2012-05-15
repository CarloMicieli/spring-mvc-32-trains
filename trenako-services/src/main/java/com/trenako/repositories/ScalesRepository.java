package com.trenako.repositories;

import org.bson.types.ObjectId;

import com.trenako.entities.Scale;

/**
 * The model railway scale repository.
 * @author Carlo Micieli
 *
 */
public interface ScalesRepository {
	/**
	 * Returns the scale document by the id.
	 * @param id the unique id.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	Scale findById(ObjectId id);
		
	/**
	 * Returns the brand from the name.
	 * @param name the brand name.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	Scale findByName(String name);
	
	/**
	 * Persist the brand instance to the data store.
	 * @param brand a brand.
	 */
	void save(Scale brand);
	
	/**
	 * Remove the brand from the data store.
	 * @param brand a brand.
	 */
	void remove(Scale brand);
}
