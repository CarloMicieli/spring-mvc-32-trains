/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.web.infrastructure;

import static com.trenako.web.infrastructure.SearchRequestUrlParser.*;
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class SearchRequestUrlParserTests {
	
	@Test
	public void shouldParsePathsWithoutValues() {
		Map<String, String> values = parseUrl("/rs/some/random/text");
		assertEquals(0, values.size());
	}
	
	@Test
	public void shouldParsePathsWithASingleValue() {
		Map<String, String> values = parseUrl("/rs/brand/ACME");
		assertValue(values, "brand", "ACME");
	}
	
	@Test
	public void shouldParsePathsWithMoreValues() {
		Map<String, String> values = parseUrl("/rs/brand/ACME/railway/DB/scale/H0/powermethod/AC/category/electric-locomotives");
		assertValue(values, "brand", "ACME");
		assertValue(values, "railway", "DB");
		assertValue(values, "scale", "H0");
		assertValue(values, "powermethod", "AC");
		assertValue(values, "category", "electric-locomotives");
	}

	@Test
	public void shouldParsePathsWithWrongSequence() {
		Map<String, String> values = parseUrl("/rs/brand/ACME/DB/scale/H0");
		assertValue(values, "brand", "ACME");
		assertValue(values, "scale", "H0");
	}
	
	void assertValue(Map<String, String> values, String key, String value) {
		assertEquals(true, values.containsKey(key));
		assertEquals(value, values.get(key));
	}
}
