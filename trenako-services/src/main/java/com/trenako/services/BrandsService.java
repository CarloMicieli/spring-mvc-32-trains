package com.trenako.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenako.entities.Brand;
import com.trenako.repositories.BrandsRepository;

/**
 * The brands service.
 * @author Carlo Micieli
 *
 */
@Service("brandsService")
public class BrandsService {
	private BrandsRepository repo;
	
	@Autowired
	public BrandsService(BrandsRepository repo) {
		this.repo = repo;
	}
	
	/**
	 * Returns the brand entity with the id.
	 * @param id the unique id.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	public Brand findById(ObjectId id) {
		return repo.findById(id);
	}
	
	/**
	 * Returns the brand from the slug value.
	 * @param slug the brand slug.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	public Brand findBySlug(String slug) {
		return repo.findBySlug(slug);
	}
	
	/**
	 * Returns the brand from the name.
	 * @param name the brand name.
	 * @return a brand instance. <em>null</em> if no brands are found.
	 */
	public Brand findByName(String name) {
		return repo.findByName(name);
	}
	
	/**
	 * Persist the brand instance to the data store.
	 * @param brand a brand.
	 */
	public void save(Brand brand) {
		repo.save(brand);
	}
	
	/**
	 * Remove the brand from the data store.
	 * @param brand a brand.
	 */
	public void remove(Brand brand) {
		repo.remove(brand);
	}
}
