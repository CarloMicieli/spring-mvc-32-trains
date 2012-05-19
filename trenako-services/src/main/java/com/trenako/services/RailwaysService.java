package com.trenako.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenako.entities.Railway;
import com.trenako.repositories.RailwaysRepository;

@Service("railwaysService")
public class RailwaysService {
	
	private RailwaysRepository repo;
	
	@Autowired
	public RailwaysService(RailwaysRepository repo) {
		this.repo = repo;
	}
	
	/**
	 * Finds the railway document in the collection by id.
	 * @param id the unique id.
	 * @return a railway document. <em>null</em> otherwise.
	 */
	public Railway findById(ObjectId id) {
		return repo.findById(id);
	}
	
	/**
	 * Finds the railway document in the collection by name.
	 * @param name the railway name.
	 * @return a railway document. <em>null</em> otherwise.
	 */
	public Railway findByName(String name) {
		return repo.findByName(name);
	}
	
	/**
	 * Finds all the railway document in the collection by country.
	 * @param country the country.
	 * @return the documents.
	 */
	public Iterable<Railway> findByCountry(String country) {
		return repo.findByCountry(country);
	}
	
	/**
	 * Saves the railway document in the collection.
	 * @param railway the railway document to be saved.
	 */
	public void save(Railway railway) {
		repo.save(railway);
	}
	
	/**
	 * Remove the railway document from the collection.
	 * @param railway the railway document to be deleted.
	 */
	public void remove(Railway railway) {
		repo.remove(railway);
	}
	
}
