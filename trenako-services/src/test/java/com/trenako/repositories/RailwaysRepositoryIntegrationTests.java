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
			new Railway("DB"),
			new Railway("Scnf"));
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
	
}
