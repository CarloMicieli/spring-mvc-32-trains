/*
 * Copyright 2012 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.repositories;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.trenako.TestConfiguration;
import com.trenako.entities.Brand;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,
	classes = {TestConfiguration.class})
@ActiveProfiles("test")
public class BrandsRepositoryIntegrationTests {

	private @Autowired MongoTemplate mongoOps;
	private @Autowired BrandsRepository repo;
	
	private List<Brand> brands;
	
	@Before
	public void setup() {
		brands = Arrays.asList(
			new Brand.Builder("ACME")
				.slug("acme")
				.description("Brand descritpion")
				.emailAddress("mail@acme.com")
				.industrial(false)
				.website("http://www.acme.com")
				.build(),
			new Brand.Builder("Roco")
				.slug("roco")
				.description("Brand descritpion")
				.emailAddress("mail@roco.cc")
				.industrial(false)
				.website("http://www.roco.cc")
				.build(),
			new Brand.Builder("LS Models")
				.slug("ls-models")
				.description("Brand descritpion")
				.emailAddress("mail@lsmodels.com")
				.industrial(false)
				.website("http://www.lsmodels.com")
				.build(),
			new Brand.Builder("Maerklin")
				.slug("maerklin")
				.description("Brand descritpion")
				.emailAddress("mail@maerklin.com")
				.industrial(false)
				.website("http://www.maerklin.de")
				.build());

		mongoOps.insert(brands, Brand.class);
	}
	
	@After
	public void cleanUp() {
		mongoOps.remove(new Query(), Brand.class);
	}
	
	@Test
	public void shouldReturnNullIfNoBrandIsNotFound() {
		Brand b = repo.findByName("AAAA");
		assertNull(b);
	}
	
	@Test
	public void shouldFindABrandByName() {
		Brand b = repo.findByName("ACME");
		assertEquals("ACME", b.getName());
	}

	@Test
	public void shouldFindABrandBySlug() {
		Brand b = repo.findBySlug("ls-models");
		assertEquals("LS Models", b.getName());
	}
	
	@Test
	public void shouldFindAllTheBrands() {
		List<Brand> bb = (List<Brand>) repo.findAll();
		assertEquals(brands.size(), bb.size());
	}
	
	@Test
	public void shouldCreateNewBrands() {
		Brand brand = new Brand.Builder("Brawa")
			.description("Brand descritpion")
			.emailAddress("mail@brawa.de")
			.industrial(false)
			.website("http://www.brawa.de")
			.build();
		repo.save(brand);
		
		assertNotNull(brand.getId());
	}
	
	@Test
	public void shouldFindBrandsById() {
		Brand b = repo.findById(brands.get(0).getId());
		assertNotNull(b);
	}
	
	@Test
	public void shouldRemoveBrand() {
		Brand b = brands.get(0);
		repo.remove(b);
		
		Brand bb = mongoOps.findById(b.getId(), Brand.class);
		assertNull(bb);
	}
}
