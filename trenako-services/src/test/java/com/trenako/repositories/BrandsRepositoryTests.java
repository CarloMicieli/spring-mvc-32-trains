package com.trenako.repositories;

import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.trenako.AbstractRepositoryTests;
import com.trenako.entities.Brand;
import com.trenako.repositories.mongo.BrandsRepositoryImpl;

import static org.junit.Assert.*;

public class BrandsRepositoryTests extends AbstractRepositoryTests<Brand> {

	@InjectMocks private BrandsRepositoryImpl repo;
		
	@Before
	public void setUp() {
		super.init(Brand.class);
	}
	
	@Test 
	public void shouldFindBrandsById() {
		final ObjectId id = new ObjectId();
		mockFindById(id, new Brand());
		
		Brand brand = repo.findById(id);
		
		verifyFindById(id);
		assertNotNull("Brand not found.", brand);
	}

	@Test
	public void shouldFindBrandsByName() {
		
		mockFindOneByQuery(new Brand());
		
		Brand brand = repo.findByName("AAA");		
		
		Query query = null;
		Map<String, Criteria> criterias = verifyFindOneByQuery(query);
		
		assertNotNull("Criteria for 'name' not found.", criterias.get("name"));
		assertNotNull("Brand not found.", brand);
	}
}
