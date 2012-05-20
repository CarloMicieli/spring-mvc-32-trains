package com.trenako.repositories;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
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
import com.trenako.entities.Railway;

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
public class RailwaysRepositoryIntegrationTests {
	private @Autowired MongoTemplate mongoOps;
	private @Autowired RailwaysRepository repo;
	
	private List<Railway> railways;
	
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
		mongoOps.insert(railways, Railway.class);
	}
	
	@After
	public void cleanUp() {
		mongoOps.remove(new Query(), Railway.class);
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
		
		assertNull(mongoOps.findById(railway.getId(), Railway.class));
	}
}
