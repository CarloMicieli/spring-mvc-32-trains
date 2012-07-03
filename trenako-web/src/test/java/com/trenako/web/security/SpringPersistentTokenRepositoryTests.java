package com.trenako.web.security;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.trenako.entities.PersistentLogin;
import com.trenako.repositories.RememberMeRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SpringPersistentTokenRepositoryTests {

	@Mock RememberMeRepository mock;
	SpringPersistentTokenRepository repo;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		repo = new SpringPersistentTokenRepository(mock);
	}
	
	@Test
	public void testCreateNewToken() {
//		PersistentLogin token = new PersistentLogin("username", "series", new Date(), "tokenValue");
//		repo.createNewToken(token);
//		verify(mock, times(1)).save(eq(token));
	}

	@Test
	public void testGetTokenForSeries() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveUserTokens() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateToken() {
		fail("Not yet implemented");
	}

}
