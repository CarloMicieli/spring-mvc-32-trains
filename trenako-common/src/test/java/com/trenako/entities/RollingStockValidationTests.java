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

import com.trenako.AbstractValidationTests;

import static org.junit.Assert.*;

public class RollingStockValidationTests 
	extends AbstractValidationTests<RollingStock> {

	@Before
	public void initValidator() {
		super.init(RollingStock.class);
	}
	
	@Test
	public void shouldValidateRollingStocks() {
		RollingStock rs = new RollingStock(
				new Brand(),
				"12345",
				"aaaa",
				new Railway(),
				new Scale()
				);
		Map<String, String> errors = validate(rs);
		assertEquals(0, errors.size());
	}	
	
	@Test
	public void shouldValidateInvalidRollingStocks() {
		RollingStock rs = new RollingStock();
		Map<String, String> errors = validate(rs);
		assertEquals(5, errors.size());
		assertEquals("rs.itemNumber.required", errors.get("itemNumber"));
		assertEquals("rs.brand.required", errors.get("brand"));
		assertEquals("rs.scale.required", errors.get("scale"));
		assertEquals("rs.railway.required", errors.get("railway"));
		assertEquals("rs.description.required", errors.get("description"));
	}
	
	@Test
	public void shouldValidateItemNumberSize() {
		RollingStock rs = new RollingStock(
				new Brand(),
				"12345678901", //max = 10
				"aaaa",
				new Railway(),
				new Scale()
				);
		Map<String, String> errors = validate(rs);
		assertEquals(1, errors.size());
		assertEquals("rs.itemNumber.size.notmet", errors.get("itemNumber"));
	}
	
	@Test
	public void shouldInitializeTheSlug() {
		RollingStock rs = new RollingStock(
				new Brand("ACME", null),
				"123456", //max = 10
				"aaaa",
				new Railway(),
				new Scale()
				);
		assertEquals("acme-123456", rs.getSlug());
	}
	
	
	
	
}
