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
package com.trenako.entities;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockTests {
	
	@Test
	public void builderShouldInizializeRollingStocks() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.category("category")
			.deliveryDate(new DeliveryDate(2012, 1))
			.description("Description")
			.description("fr", "French description")
			.details("Details")
			.details("fr", "French details")
			.era("IV")
			.powerMethod("DC")
			.railway("DB")
			.scale("H0")
			.tags("tag1", "tag2")
			.totalLength(123)
			.build();
				
		assertEquals("ACME", rs.getBrand().getName());
		assertEquals("123456", rs.getItemNumber());
		assertEquals("category", rs.getCategory());
		assertEquals("2012/Q1", rs.getDeliveryDate().toString());
		assertEquals("Description", rs.getDescription());
		assertEquals("French description", rs.getDescription("fr"));
		assertEquals("Details", rs.getDetails());
		assertEquals("French details", rs.getDetails("fr"));
		assertEquals("IV", rs.getEra());
		assertEquals("DC", rs.getPowerMethod());
		assertEquals("DB", rs.getRailway().getName());
		assertEquals("H0", rs.getScale().getName());
		assertEquals(123, rs.getTotalLength());
		assertEquals(2, rs.getTags().size());
	}
	
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
	
	@Test
	public void equalsShouldReturnsTrueForEqualObjects() {
		RollingStock x = new RollingStock.Builder("ACME", "123456").build();
		RollingStock y = new RollingStock.Builder("ACME", "123456").build();
		assertTrue(x.equals(y));
	}
	
	@Test
	public void equalsShouldReturnsFalseForEqualObjects() {
		RollingStock x = new RollingStock.Builder("ACME", "123456").build();
		RollingStock y = new RollingStock.Builder("ACME", "123456")
			.description("aaaa")
			.build();
		assertFalse(x.equals(y));
	}
	
	@Test
	public void shouldTwoEqualObjectsHaveTheSameHashcode() {
		RollingStock x = new RollingStock.Builder("ACME", "123456").build();
		RollingStock y = new RollingStock.Builder("ACME", "123456").build();
		assertEquals(x.hashCode(), y.hashCode());
	}
		
	@Test
	public void equalsShouldBeReflexive() {
		RollingStock x = new RollingStock.Builder("ACME", "123456").build();
		assertTrue(x.equals(x));
	}
	
	@Test
	public void shouldAssignOptionsToRollingStocks() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.option(new Option("NEM-651", OptionFamily.DCC_INTERFACE))
			.build();
		assertEquals(1, rs.getOptions().size());
		assertEquals("{DCC_INTERFACE=NEM-651}", rs.getOptions().toString());
	}
	
	@Test
	public void shouldReturnNullIfTheOptionIsNotFound() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		assertEquals(null, rs.getOption(OptionFamily.DCC_INTERFACE));
	}
	
	@Test
	public void shouldAssignOnly1OptionForEachFamily() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		
		Option op1 = new Option("Op1", OptionFamily.HEADLIGHTS);
		
		rs.addOption(op1);
		assertTrue(rs.hasOption(op1));
		
		Option op2 = new Option("Op2", OptionFamily.DCC_INTERFACE);
		Option op3 = new Option("Op3", OptionFamily.HEADLIGHTS);
		
		rs.addOption(op2);
		rs.addOption(op3);
		
		assertEquals(op3, rs.getOption(OptionFamily.HEADLIGHTS));
		assertEquals(op2, rs.getOption(OptionFamily.DCC_INTERFACE));
	}
	
	@Test
	public void shouldFillTheBrandName() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456").build();
		assertEquals("ACME", rs.getBrandName());
	}
	
	@Test
	public void shouldFillTheRailwayName() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.railway("DB")
			.build();
		assertEquals("DB", rs.getRailwayName());
	}
	
	@Test
	public void shouldFillTheScaleName() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.scale("H0")
			.build();
		assertEquals("H0", rs.getScaleName());
	}
	
	@Test
	public void shouldFillTheCountryFromTheRailway() {
		Railway DB = new Railway.Builder("DB")
			.country("DEU")
			.build();
		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.railway(DB)
			.build();
		assertEquals("DEU", rs.getCountry());		
	}
}
