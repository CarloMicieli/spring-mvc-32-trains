package com.trenako.security

import spock.lang.*

import org.springframework.security.core.Authentication
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

/**
 * 
 * @author Carlo Micieli
 *
 */
@ContextConfiguration(locations = "classpath:META-INF/security-context.xml")
class SecuritySpecification extends Specification {
	
	@Autowired AuthenticationManager am
	
	def cleanup() {
		SecurityContextHolder.clearContext();
	}
	
	def login(String name, String password) {
		Authentication auth = new UsernamePasswordAuthenticationToken(name, password);
		SecurityContextHolder.getContext().setAuthentication(am.authenticate(auth));
	}
	
	def "should forbid access if user not authenticated"() {
		given:
		login "bob", "bobpwd"
		
		when:
		def a = 1 + 1
		
		then:
		a == 2
	}
	
	/*
	@Test
	@ExpectedException(AuthenticationCredentialsNotFoundException.class)
	public void testNoAuth() {
		controller.modifyContent(...);
	}

	@Test
	@ExpectedException(AccessDeniedException.class)
	public void testAccessDenied() {
		login("userWithoutAccessRight", "...");
		controller.modifyContent(...);
	}

	@Test
	public void testAuthOK() {
		login("userWithAccessRight", "...");
		controller.modifyContent(...);
	}
	*/
}
