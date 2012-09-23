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
package com.trenako.web.controllers.form;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.trenako.results.RangeRequest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ResultsOptionsFormTests {
	private Sort NAME_ORDER_ASC = new Sort(Direction.ASC, "name");
	private Sort NAME_ORDER_DESC = new Sort(Direction.DESC, "name");

	@Test
	public void shouldCheckWheterTwoFormsAreEquals() {
		ResultsOptionsForm x = ResultsOptionsForm.buildFor(
				new RangeRequest(NAME_ORDER_DESC, 10, null, null));
		ResultsOptionsForm y = ResultsOptionsForm.buildFor(
				new RangeRequest(NAME_ORDER_DESC, 10, null, null));
		assertTrue("Forms are different", x.equals(x));
		assertTrue("Forms are different", x.equals(y));
	}
	
	@Test
	public void shouldCheckWheterTwoFormsAreDifferent() {
		ResultsOptionsForm x = ResultsOptionsForm.buildFor(
				new RangeRequest(NAME_ORDER_DESC, 10, null, null));
		ResultsOptionsForm y = ResultsOptionsForm.buildFor(
				new RangeRequest(NAME_ORDER_ASC, 10, null, null));
		assertFalse("Forms are equals", x.equals(y));
	}
	
	@Test
	public void shouldBuildFormFromDescOrders() {
		RangeRequest range = new RangeRequest(NAME_ORDER_DESC, 10, null, null);
		
		ResultsOptionsForm form = ResultsOptionsForm.buildFor(range);
		
		assertEquals("name", form.getSort());
		assertEquals("desc", form.getDir());
		assertEquals(10, form.getSize());		
	}
	
	@Test
	public void shouldBuildFormFromAscOrders() {
		RangeRequest range = new RangeRequest(NAME_ORDER_ASC, 10, null, null);
		
		ResultsOptionsForm form = ResultsOptionsForm.buildFor(range);
		
		assertEquals("name", form.getSort());
		assertEquals("asc", form.getDir());
		assertEquals(10, form.getSize());		
	}
}
