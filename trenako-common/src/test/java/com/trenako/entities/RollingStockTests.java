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

import static com.trenako.test.TestDataBuilder.*;

import java.util.Locale;

import org.junit.Test;

import com.trenako.mapping.DbReferenceable;
import com.trenako.mapping.LocalizedField;
import com.trenako.values.OptionFamily;


import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockTests {
	
	@Test
	public void shouldCreateNewRollingStocksUsingTheBuilder() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.category("category")
			.deliveryDate(new DeliveryDate(2012, 1))
			.description("Description")
			.description(Locale.FRENCH, "French description")
			.details("Details")
			.details(Locale.FRENCH, "French details")
			.era("IV")
			.powerMethod("DC")
			.railway(db())
			.scale(scaleH0())
			.tags("tag1", "tag2")
			.totalLength(123)
			.upcCode("ABCD")
			.country("de")
			.build();
				
		assertEquals("acme", rs.getBrand().getSlug());
		assertEquals("123456", rs.getItemNumber());
		assertEquals("category", rs.getCategory());
		assertEquals("2012/Q1", rs.getDeliveryDate().toString());
		assertEquals("Description", rs.getDescription().getDefault());
		assertEquals("French description", rs.getDescription().getValue(Locale.FRENCH));
		assertEquals("Details", rs.getDetails().getDefault());
		assertEquals("French details", rs.getDetails().getValue(Locale.FRENCH));
		assertEquals("IV", rs.getEra());
		assertEquals("DC", rs.getPowerMethod());
		assertEquals("db", rs.getRailway().getSlug());
		assertEquals("h0", rs.getScale().getSlug());
		assertEquals(123, rs.getTotalLength());
		assertEquals(2, rs.getTags().size());
		assertEquals("de", rs.getCountry());
		assertEquals("ABCD", rs.getUpcCode());
	}
	
	@Test
	public void shouldProduceLocalizedRollingStocksDescriptions() {
		RollingStock x = new RollingStock();
		LocalizedField<String> desc = new LocalizedField<String>("English description");
		desc.put(Locale.ITALIAN, "Descrizione");
		x.setDescription(desc);
		
		assertEquals("English description", x.getDescription().getDefault());
		assertEquals("English description", x.getDescription().getValue(Locale.CHINESE));
		assertEquals("Descrizione", x.getDescription().getValue(Locale.ITALIAN));
	}
		
	@Test
	public void shouldProduceLocalizedRollingStocksDetails() {
		RollingStock x = new RollingStock();
		LocalizedField<String> det = new LocalizedField<String>("English details");
		det.put(Locale.ITALIAN, "Dettagli");
		x.setDetails(det);
		
		assertEquals("English details", x.getDetails().getDefault());
		assertEquals("English details", x.getDetails().getValue(Locale.CHINESE));
		assertEquals("Dettagli", x.getDetails().getValue(Locale.ITALIAN));
	}
	
	@Test
	public void shouldChecksWhetherTwoRollingStocksAreEquals() {
		RollingStock z = new RollingStock.Builder(acme(), "123456")
			.railway(db())
			.scale(scaleH0())
			.build();
		assertTrue(z.equals(z));
		
		RollingStock x = new RollingStock.Builder(acme(), "123456")
			.railway(db())
			.description("rolling stock desc")
			.scale(scaleH0())
			.build();
		RollingStock y = new RollingStock.Builder(acme(), "123456")
			.railway(db())
			.description("rolling stock desc")
			.scale(scaleH0())
			.build();
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldChecksWhetherTwoRollingStocksAreDifferent() {
		RollingStock x = new RollingStock.Builder(acme(), "123456")
			.railway(db())
			.scale(scaleH0())
			.build();
		RollingStock y = new RollingStock.Builder(acme(), "123456")
			.railway(db())
			.scale(scaleH0())
			.description("aaaa")
			.build();
		assertFalse(x.equals(y));
		
		assertFalse(x.equals(new Brand()));
	}
	
	@Test
	public void shouldProduceTheSameHashCodeForTwoEqualsRollingStocks() {
		RollingStock x = new RollingStock.Builder(acme(), "123456")
			.railway(db())
			.scale(scaleH0())
			.build();
		RollingStock y = new RollingStock.Builder(acme(), "123456")
			.railway(db())
			.scale(scaleH0())
			.build();
		assertTrue(x.equals(y));
		assertEquals(x.hashCode(), y.hashCode());
	}

	@Test
	public void shouldAssignOptionsToRollingStocks() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.railway(db())
			.scale(scaleH0())	
			.option(new Option("NEM-651", OptionFamily.DCC_INTERFACE))
			.build();
		assertEquals(1, rs.getOptions().size());
		assertEquals("{DCC_INTERFACE=NEM-651}", rs.getOptions().toString());
	}
	
	@Test
	public void shouldReturnNullIfTheOptionIsNotFound() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.railway(db())
			.scale(scaleH0())
			.build();
		assertEquals(null, rs.getOption(OptionFamily.DCC_INTERFACE));
	}
	
	@Test
	public void shouldAssignOnlyOneOptionForEachFamily() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.railway(db())
			.scale(scaleH0())
			.build();
		
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
	public void shouldInitTheBrandDbReferences() {
		RollingStock rs = new RollingStock();
		rs.setBrand(acme());
		assertEquals(acme().getSlug(), rs.getBrand().getSlug());
		assertEquals(acme().getLabel(), rs.getBrand().getLabel());
	}
	
	@Test
	public void shouldInitTheRailwayDbReferences() {
		RollingStock rs = new RollingStock();
		rs.setRailway(db());
		assertEquals(db().getSlug(), rs.getRailway().getSlug());
		assertEquals(db().getLabel(), rs.getRailway().getLabel());
	}
	
	@Test
	public void shouldInitTheScaleDbReferences() {
		RollingStock rs = new RollingStock();
		rs.setScale(scaleH0());
		assertEquals(scaleH0().getSlug(), rs.getScale().getSlug());
		assertEquals(scaleH0().getLabel(), rs.getScale().getLabel());
	}
	
	@Test
	public void shouldFillTheCountryCodeUsingTheRailwayCountry() {
		RollingStock rs = new RollingStock();
		rs.setRailway(db());
		assertEquals("de", rs.getCountry());
		
		RollingStock rs2 = new RollingStock();
		rs2.setCountry("fr");
		rs2.setRailway(db());
		assertEquals("de", rs2.getCountry());	
	}
	
	@Test
	public void shouldImplementDbReferenceable() {
		DbReferenceable ref = new RollingStock.Builder(acme(), "123456")
			.railway(db())
			.scale(scaleH0())
			.build();
		assertEquals("acme-123456", ref.getSlug());
		assertEquals("ACME 123456", ref.getLabel());
	}
	
	@Test
	public void shouldInitializeTheSlug() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.railway(db())
			.scale(scaleH0())
			.build();
		assertEquals("acme-123456", rs.getSlug());
	}
	
	@Test
	public void shouldProduceStringRepresentationsOfRollingStocks() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.railway(db())
			.scale(scaleH0())		
			.description("rolling stock description")
			.build();

		assertEquals("ACME 123456: rolling stock description", rs.toString());
	}
	
	@Test
	public void shouldAssignTagsToRollingStocks() {
		RollingStock rs = new RollingStock();
		rs.addTag("tag2");
		rs.addTag("tag1");
		rs.addTag("tag3");
		rs.addTag("tag1");
		assertEquals(3, rs.getTags().size());
		assertEquals("[tag1, tag2, tag3]", rs.getTags().toString());
	}
}
