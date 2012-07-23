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
package com.trenako.criteria;

import static org.junit.Assert.*;

import org.junit.Test;

import com.trenako.entities.Brand;
import com.trenako.values.Era;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CriteriaTests {

	@Test
	public void shouldFillListWithCriteriaNames() {
		
		Iterable<String> keys = Criteria.keys();
		
		String expected = "[brand, scale, cat, railway, era, powermethod, category]";
		assertEquals(expected, keys.toString());
	}

	@Test
	public void shouldGetTheTypeNameForCriterion() {
		Criteria c1 = Criteria.criterionForType(Brand.class);
		assertEquals(Criteria.BRAND, c1);
		
		Criteria c2 = Criteria.criterionForType(Era.class);
		assertEquals(Criteria.ERA, c2);
	}
}
