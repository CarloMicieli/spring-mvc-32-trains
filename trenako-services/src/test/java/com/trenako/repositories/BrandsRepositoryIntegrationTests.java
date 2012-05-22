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
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.AbstractMongoIntegrationTests;
import com.trenako.entities.Brand;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public abstract class BrandsRepositoryIntegrationTests extends AbstractMongoIntegrationTests<Brand> {

	private @Autowired BrandsRepository repo;
	private List<Brand> brands;

	public BrandsRepositoryIntegrationTests() {
		super(Brand.class);
	}
	
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

		super.init(brands);
	}
	
	@After
	public void cleanUp() {
		super.cleanUp();
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
		
		assertNull(repo.findById(b.getId()));
	}
}
