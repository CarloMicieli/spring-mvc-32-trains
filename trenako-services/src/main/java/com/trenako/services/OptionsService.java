package com.trenako.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenako.entities.Option;
import com.trenako.entities.OptionFamily;
import com.trenako.repositories.OptionsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Service("optionsService")
public class OptionsService {

	private OptionsRepository repo;
	
	@Autowired
	public OptionsService(OptionsRepository repo) {
		this.repo = repo;
	}
	
	/**
	 * Returns the option document by id.
	 * @param id the unique id.
	 * @return an option document; <em>null</em> otherwise.
	 */
	public Option findById(ObjectId id) {
		return repo.findById(id);
	}
	
	/**
	 * Returns the option document by option name.
	 * @param name the option name.
	 * @return an option document; <em>null</em> otherwise.
	 */
	public Option findByName(String name) {
		return repo.findByName(name);
	}
	
	/**
	 * Returns the option documents by option family.
	 * @param family the family.
	 * @return the option documents.
	 */
	public Iterable<Option> findByFamily(OptionFamily family) {
		return repo.findByFamily(family);
	}
	
	/**
	 * Returns all the option documents in the collection.
	 * @return the option documents.
	 */
	public Iterable<Option> findAll() {
		return repo.findAll();
	}
	
	/**
	 * Saves the option document in the collection.
	 * @param option the option document to be saved.
	 */
	public void save(Option option) {
		repo.save(option);
	}
	
	/**
	 * Remove the option document from the collection.
	 * @param option the option document to be deleted.
	 */
	public void remove(Option option) {
		repo.remove(option);
	}
}
