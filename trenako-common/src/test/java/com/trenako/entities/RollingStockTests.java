package com.trenako.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class RollingStockTests {
	@Test
	public void shouldGetLocalizedDescriptions() {

		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.description("English")
			.description("fr", "French")
			.description("de", "German")
			.build();

		assertEquals("French", rs.getDescription("fr"));
		assertEquals("German", rs.getDescription("de"));
	}
	
	@Test
	public void shouldGetDefaultDescriptionWhenTheLocalizedDontExist() {

		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.description("English")
			.description("fr", "French")
			.build();

		assertEquals("English", rs.getDescription("it"));
	}
	
	@Test
	public void shouldReplaceADescription() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.description("English")
			.description("fr", "French")
			.build();
		
		rs.setDescription("fr", "French2");
		assertEquals("French2", rs.getDescription("fr"));
	}
	
	@Test
	public void shouldGetLocalizedDetails() {

		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.details("English")
			.details("fr", "French")
			.details("de", "German")
			.build();

		assertEquals("French", rs.getDetails("fr"));
		assertEquals("German", rs.getDetails("de"));
	}
	
	@Test
	public void shouldGetDefaultDetailsWhenTheLocalizedDontExist() {

		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.details("English")
			.details("fr", "French")
			.build();

		assertEquals("English", rs.getDetails("it"));
	}
	
	@Test
	public void shouldReplaceTheDetails() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.details("English")
			.details("fr", "French")
			.build();
		
		rs.setDetails("fr", "French2");
		assertEquals("French2", rs.getDetails("fr"));
	}	
}
