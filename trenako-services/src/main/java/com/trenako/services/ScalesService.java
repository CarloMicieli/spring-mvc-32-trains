package com.trenako.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenako.entities.Scale;
import com.trenako.repositories.ScalesRepository;

@Service("scalesService")
public class ScalesService {
	
	private ScalesRepository repo;
	
	@Autowired
	public ScalesService(ScalesRepository repo) {
		this.repo = repo;
	}
	
	/**
	 * Returns the scale document by the id.
	 * @param id the unique id.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	public Scale findById(ObjectId id) {
		return repo.findById(id);
	}
		
	/**
	 * Returns the brand from the name.
	 * @param name the brand name.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	public Scale findByName(String name) {
		return repo.findByName(name);
	}
	
	public Iterable<Scale> findAll() {
		return repo.findAll();
	}
	
	/**
	 * Persist the brand instance to the data store.
	 * @param brand a brand.
	 */
	public void save(Scale brand) {
		repo.save(brand);
	}
	
	/**
	 * Remove the brand from the data store.
	 * @param brand a brand.
	 */
	public void remove(Scale brand) {
		repo.remove(brand);
	}
}
