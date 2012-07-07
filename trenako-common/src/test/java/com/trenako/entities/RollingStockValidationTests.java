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
		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.description("AAAA")
			.era("IV")
			.category("loco")
			.scale("H0")
			.railway("DB")
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
	public void shouldValidateItemNumberSize() {
		RollingStock rs = new RollingStock.Builder("ACME", "12345678901")
			.description("AAAA")
			.scale("H0")
			.era("IV")
			.category("loco")
			.railway("DB")
			.build();
		Map<String, String> errors = validate(rs);
		assertEquals(1, errors.size());
		assertEquals("rs.itemNumber.size.notmet", errors.get("itemNumber"));
	}
	
	@Test
	public void shouldInitializeTheSlug() {
		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.build();
		assertEquals("acme-123456", rs.getSlug());
	}
}
