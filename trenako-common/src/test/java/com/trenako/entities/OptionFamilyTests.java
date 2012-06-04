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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class OptionFamilyTests {
	
	@Test
	public void shouldProduceKeyValues() {
		OptionFamily of = OptionFamily.DCC_INTERFACE;
		assertEquals("dcc-interface", of.keyValue());
	}
	
	@Test
	public void shouldParseValidOptionFamilyNames() {
		OptionFamily of = OptionFamily.parse("dcc-interface");
		assertEquals(OptionFamily.DCC_INTERFACE, of);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionWhenParsingInvalidFamilyNames() {
		OptionFamily.parse("wrong-family-name");
	}
}
