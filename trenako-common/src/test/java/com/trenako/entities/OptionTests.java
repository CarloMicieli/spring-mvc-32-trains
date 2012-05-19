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

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class OptionTests {

	@Test
	public void equalsShouldReturnTrueForTwoEqualsOptions() {
		Option x = new Option("AAA", OptionFamily.COUPLER);
		Option y = new Option("AAA", OptionFamily.COUPLER);
		
		assertTrue(x.equals(y));
	}
	
	@Test
	public void equalsShouldReturnFalseForTwoEqualsOptions() {
		Option x = new Option("AAA", OptionFamily.COUPLER);
		Option y = new Option("BBB", OptionFamily.COUPLER);
		
		assertFalse(x.equals(y));
	}
}
