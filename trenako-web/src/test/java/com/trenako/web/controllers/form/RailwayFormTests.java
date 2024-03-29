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

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RailwayFormTests {

	@Test 
	public void shouldCheckWhetherTwoRailwayFormsAreEquals() {
		RailwayForm x = RailwayForm.newForm(fs());
		RailwayForm y = RailwayForm.newForm(fs());
		assertTrue("Railway forms are different", x.equals(x));
		assertTrue("Railway forms are different", x.equals(y));
	}

	@Test 
	public void shouldCheckWhetherTwoRailwayFormsAreDifferent() {
		RailwayForm x = RailwayForm.newForm(fs());
		RailwayForm y = RailwayForm.newForm(db());
		assertFalse("Railway forms are equals", x.equals(y));
	}

}