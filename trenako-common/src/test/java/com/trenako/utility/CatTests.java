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
package com.trenako.utility;

import static org.junit.Assert.*;

import org.junit.Test;

import com.trenako.CatFormatException;
import com.trenako.Category;
import com.trenako.PowerMethod;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CatTests {

	@Test
	public void shouldParseValues() {
		Cat cat = Cat.parseString("ac-electric-locomotives");

		assertEquals(Category.ELECTRIC_LOCOMOTIVES, cat.getCategory());
		assertEquals(PowerMethod.AC, cat.getPowerMethod());
	}
	
	
	@Test
	public void shouldReturnStringsForCriteria() {
		Cat cat = new Cat(PowerMethod.DC, Category.ELECTRIC_LOCOMOTIVES);
		assertEquals("dc", cat.powerMethod());
		assertEquals("electric-locomotives", cat.category());
	}
	
	@Test
	public void shouldReturnStringRepresentation() {
		Cat cat = Cat.parseString("ac-electric-locomotives");
		assertEquals("ac-electric-locomotives", cat.toString());
	}

	@Test(expected = CatFormatException.class)
	public void shouldThrowExceptionForIllegalString() {
		Cat.parseString("aa");
	}
	
	@Test(expected = CatFormatException.class)
	public void shouldThrowExceptionForIllegalPowerMethod() {
		Cat.parseString("aa-electric-locomotives");
	}

	@Test(expected = CatFormatException.class)
	public void shouldThrowExceptionForIllegalCategory() {
		Cat.parseString("ac-solar-locomotives");
	}
}