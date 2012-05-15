/**
 * 
 */
package com.trenako.repositories.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.trenako.entities.Brand;
import com.trenako.repositories.BrandsRepository;

/**
 * @author Carlo Micieli
 *
 */
@Repository("brandsRepository")
public class BrandsRepositoryImpl extends MongoRepository<Brand> implements BrandsRepository {
	
	@Autowired
	public BrandsRepositoryImpl(MongoTemplate mongoOps) {
		super(mongoOps, Brand.class);
	}
	
	@Override
	public Brand findBySlug(String slug) {
		return findOne("slug", slug);
	}

	@Override
	public Brand findByName(String name) {
		return findOne("name", name);
	}
}
