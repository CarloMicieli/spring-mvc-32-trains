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

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.AbstractMongoIntegrationTests;
import com.trenako.entities.Railway;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RailwaysRepositoryIntegrationTests extends AbstractMongoIntegrationTests<Railway> {
	private @Autowired RailwaysRepository repo;
	
	private List<Railway> railways;

	public RailwaysRepositoryIntegrationTests() {
		super(Railway.class);
	}
	
	@Before
	public void setup() {
		railways = Arrays.asList(
			new Railway.Builder("Die Bahn")
				.companyName("Deutsche Bahn AG")
				.country("DEU")
				.operatingSince(1994)
				.build(),
			new Railway.Builder("DB")
				.companyName("Deutsche Bundesbahn")
				.country("DEU")
				.operatingSince(1949)
				.operatingUntil(1994)
				.build(),
			new Railway.Builder("Sncf")
				.companyName("Société Nationale des Chemins de fer Français")
				.country("FRA")
				.operatingSince(1938)
				.build()
			);
		super.init(railways);
	}
	
	@After
	public void cleanUp() {
		super.cleanUp();
	}
	
	@Test
	public void shouldFindAllRailways() {
		List<Railway> rr = (List<Railway>) repo.findAll();
		assertEquals(railways.size(), rr.size());
	}
	
	@Test
	public void shouldFindRailwaysById() {
		ObjectId id = railways.get(0).getId();
		
		Railway r = repo.findById(id);
		
		assertNotNull(r);
		assertEquals("Die Bahn", r.getName());
	}
	
	@Test
	public void shouldFindRailwaysByCountry() {
		
		List<Railway> rr = (List<Railway>) repo.findByCountry("DEU");
		assertNotNull(rr);
		assertEquals(2, rr.size());
		assertEquals("DB", rr.get(0).getName());
		assertEquals("Die Bahn", rr.get(1).getName());
	}
	
	@Test
	public void shouldFindRailwaysBySlug() {
		
		Railway r = repo.findBySlug("die-bahn");
		
		assertNotNull(r);
		assertEquals("Die Bahn", r.getName());		
	}
	
	@Test
	public void shouldFindRailwaysByName() {
		
		Railway r = repo.findByName("Sncf");
		
		assertNotNull(r);
		assertEquals("Sncf", r.getName());		
	}
	
	@Test
	public void shouldCreateNewRailways() {
		
		Railway railway = new Railway.Builder("FS")
			.companyName("Ferrovie dello stato")
			.country("ITA")
			.operatingSince(1903)
			.build();
		repo.save(railway);
		assertNotNull(railway.getId());		
	}
	
	@Test
	public void shouldRemoveRailways() {
		
		Railway railway = railways.get(0);
		
		repo.remove(railway);
		
		assertNull(repo.findById(railway.getId()));
	}
}
