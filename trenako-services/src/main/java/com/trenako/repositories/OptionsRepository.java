package com.trenako.repositories;

import org.bson.types.ObjectId;

import com.trenako.entities.Option;
import com.trenako.entities.OptionFamily;

/**
 * The interface for the Options repository.
 * @author Carlo Micieli
 *
 */
public interface OptionsRepository {
	/**
	 * Returns the option document by id.
	 * @param id the unique id.
	 * @return an option document; <em>null</em> otherwise.
	 */
	Option findById(ObjectId id);
	
	/**
	 * Returns the option document by option name.
	 * @param name the option name.
	 * @return an option document; <em>null</em> otherwise.
	 */
	Option findByName(String name);
	
	/**
	 * Returns the option documents by option family.
	 * @param family the family.
	 * @return the option documents.
	 */
	Iterable<Option> findByFamily(OptionFamily family);
	
	/**
	 * Returns all the option documents in the collection.
	 * @return the option documents.
	 */
	Iterable<Option> findAll();
	
	/**
	 * Saves the option document in the collection.
	 * @param option the option document to be saved.
	 */
	void save(Option option);
	
	/**
	 * Remove the option document from the collection.
	 * @param option the option document to be deleted.
	 */
	void remove(Option option);
}
