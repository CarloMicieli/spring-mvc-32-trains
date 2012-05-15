package com.trenako.repositories;

import org.bson.types.ObjectId;

import com.trenako.entities.Brand;

/**
 * The model railway brands repository.
 * @author Carlo Micieli
 *
 */
public interface BrandsRepository {
	/**
	 * Returns the brand entity with the id.
	 * @param id the unique id.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	Brand findById(ObjectId id);
	
	/**
	 * Returns the brand from the slug value.
	 * @param slug the brand slug.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	Brand findBySlug(String slug);
	
	/**
	 * Returns the brand from the name.
	 * @param name the brand name.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	Brand findByName(String name);
	
	/**
	 * Persist the brand instance to the data store.
	 * @param brand a brand.
	 */
	void save(Brand brand);
	
	/**
	 * Remove the brand from the data store.
	 * @param brand a brand.
	 */
	void remove(Brand brand);
}
