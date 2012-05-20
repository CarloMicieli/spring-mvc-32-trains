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
public class ScaleTests {
	
	@Test
	public void shouldPrintTheScaleRatio() {
		Scale s1 = new Scale.Builder("H0").ratio(87).build();
		assertEquals("1:87", s1.getRatioText());
	
		Scale s2 = new Scale.Builder("0").ratio(43.5).build();
		assertEquals("1:43.5", s2.getRatioText());
	}
	
	@Test
	public void equalsShouldFalseForDifferentScales() {
		Scale x = new Scale.Builder("H0").ratio(87).build();
		Scale y = new Scale.Builder("N").ratio(160).build();
		assertFalse(x.equals(y));
	}
	
	@Test
	public void equalsShouldTrueForEqualScales() {
		Scale x = new Scale.Builder("H0").ratio(87).build();
		Scale y = new Scale.Builder("H0").ratio(87).build();
		assertTrue(x.equals(y));
	}

	@Test
	public void hashCodeShouldProduceTheSameHashForEqualScales() {
		Scale x = new Scale.Builder("H0").ratio(87).build();
		Scale y = new Scale.Builder("H0").ratio(87).build();
		assertEquals(x.hashCode(), y.hashCode());
	}
}
