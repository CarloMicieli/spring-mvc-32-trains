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
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.trenako.test.AbstractValidationTests;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockValidationTests 
	extends AbstractValidationTests<RollingStock> {

	@Before
	public void initValidator() {
		super.init(RollingStock.class);
	}
	
	@Test
	public void shouldValidateRollingStocks() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.description("AAAA")
			.era("IV")
			.category("loco")
			.scale(scaleH0())
			.railway(db())
			.build();
		Map<String, String> errors = validate(rs);
		assertEquals(0, errors.size());
	}	
	
	@Test
	public void shouldValidateInvalidRollingStocks() {
		RollingStock rs = new RollingStock();
		Map<String, String> errors = validate(rs);
		assertEquals(7, errors.size());
		assertEquals("rs.itemNumber.required", errors.get("itemNumber"));
		assertEquals("rs.brand.required", errors.get("brand"));
		assertEquals("rs.scale.required", errors.get("scale"));
		assertEquals("rs.railway.required", errors.get("railway"));
		assertEquals("rs.description.required", errors.get("description"));
		assertEquals("rs.era.required", errors.get("era"));
		assertEquals("rs.category.required", errors.get("category"));
	}
	
	@Test
	public void shouldValidateCountryCode() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.description("AAAA")
			.era("IV")
			.category("loco")
			.scale(scaleH0())
			.railway(db())
			.country("rr")
			.build();
		Map<String, String> errors = validate(rs);
		assertEquals(1, errors.size());
		assertEquals("rs.country.code.invalid", errors.get("country"));
	}
	
	@Test
	public void shouldValidateDefaultDescriptions() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.description(Locale.FRENCH, "AAAA")
			.era("IV")
			.category("loco")
			.scale(scaleH0())
			.railway(db())
			.country("fr")
			.build();
		Map<String, String> errors = validate(rs);
		assertEquals(1, errors.size());
		assertEquals("rs.description.default.required", errors.get("description"));
	}
	
	@Test
	public void shouldValidateItemNumberSize() {
		RollingStock rs = new RollingStock.Builder(acme(), "12345678901")
			.description("AAAA")
			.railway(db())
			.scale(scaleH0())
			.era("IV")
			.category("loco")
			.build();
		Map<String, String> errors = validate(rs);
		assertEquals(1, errors.size());
		assertEquals("rs.itemNumber.size.notmet", errors.get("itemNumber"));
	}

	@Test
	public void shouldValidateTheProductCode() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.description("AAAA")
			.railway(db())
			.scale(scaleH0())
			.era("IV")
			.category("loco")
			.upcCode("1234567890123") // max 12
			.build();
		Map<String, String> errors = validate(rs);
		assertEquals(1, errors.size());
		assertEquals("rs.upcCode.size.notmet", errors.get("upcCode"));
	}
	
	@Test
	public void shouldValidateTotalLength() {
		Map<String, String> errors = null;
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.description("AAAA")
			.railway(db())
			.scale(scaleH0())
			.era("IV")
			.category("loco")
			.build();
		
		rs.setTotalLength(-1000);
		errors = validate(rs);
		assertEquals(1, errors.size());
		assertEquals("rs.totalLength.range.notmet", errors.get("totalLength"));
		
		rs.setTotalLength(1001);
		errors = validate(rs);
		assertEquals(1, errors.size());
		assertEquals("rs.totalLength.range.notmet", errors.get("totalLength"));
	}
}
