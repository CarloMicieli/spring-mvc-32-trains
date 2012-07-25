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

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import com.trenako.values.Standard;

import static java.math.BigDecimal.*;
import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ScaleTests {
	
	@Test
	public void shouldReturnScaleLabels() {
		Scale s = new Scale.Builder("H0")
		.ratio(870) // stored as integer
		.build();
	assertEquals("H0 (1:87)", s.getLabel());	
	}
	
	@Test
	public void shouldReturnTheRatioText() {
		Scale s = new Scale.Builder("H0")
			.ratio(435) // stored as integer
			.build();
		assertEquals("1:43.5", s.getRatioText());		
	}
	
	@Test
	public void shouldReturnTheGaugeInMillimeters() {
		Scale s = new Scale.Builder("H0")
			.gauge(1650) // stored as integer
			.build();
		assertEquals(valueOf(16.5), s.gauge());
	}
	
	@Test
	public void shouldreturnTheRatioInMillimeters() {
		Scale s = new Scale.Builder("H0")
			.ratio(435) // stored as integer
			.build();
		assertEquals(valueOf(43.5), s.ratio());		
	}
	
	@Test
	public void shouldPrintTheScaleRatio() {
		Scale s1 = new Scale.Builder("H0")
			.ratio(valueOf(87.0))
			.build();
		assertEquals("1:87", s1.getRatioText());
	
		Scale s2 = new Scale.Builder("0")
			.ratio(valueOf(43.5))
			.build();
		assertEquals("1:43.5", s2.getRatioText());
	}
	
	@Test
	public void equalsShouldFalseForDifferentScales() {
		Scale x = new Scale.Builder("H0")
			.ratio(valueOf(87))
			.build();
		Scale y = new Scale.Builder("N")
			.ratio(valueOf(160))
			.build();
		assertFalse(x.equals(y));
	}
	
	@Test
	public void equalsShouldTrueForEqualScales() {
		Scale x = new Scale.Builder("H0")
			.ratio(valueOf(87))
			.build();
		Scale y = new Scale.Builder("H0")
			.ratio(valueOf(87))
			.build();
		assertTrue(x.equals(y));
	}

	@Test
	public void hashCodeShouldProduceTheSameHashForEqualScales() {
		Scale x = new Scale.Builder("H0")
			.ratio(valueOf(87))
			.build();
		Scale y = new Scale.Builder("H0")
			.ratio(valueOf(87))
			.build();
		assertEquals(x.hashCode(), y.hashCode());
	}
	
	@Test
	public void shouldFillTheSlugValue() {
		Scale x = new Scale.Builder("#1n3").build();
		assertEquals("1n3", x.getSlug());
	}
	
	@Test
	public void shouldBuildNewScales() {	
		Scale s = new Scale.Builder("H0")
			.description("Most famous model railway scale")
			.ratio(valueOf(87))
			.gauge(1650)
			.powerMethods("ac", "dc")
			.narrow(true)
			.build();
		assertEquals("H0", s.getName());
		assertEquals("Most famous model railway scale", s.getDescription());
		assertEquals(870, s.getRatio());
		assertEquals(1650, s.getGauge());
		assertEquals(true, s.isNarrow());
		assertEquals("[dc, ac]", s.getPowerMethods().toString());
	}
	
	@Test
	public void shouldReturnStandardList() {
		Scale x = new Scale.Builder("H0").build();
		x.setStandards(new HashSet<String>(Arrays.asList(Standard.NEM.toString(), Standard.NMRA.toString())));
		assertEquals(2, x.getStandards().size());
		assertEquals("[NMRA, NEM]", x.getStandardsList());
	}
	
	@Test
	public void shouldAddNewStandards() {
		Scale s = new Scale.Builder("H0").build();
		s.addStandard(Standard.NMRA);
		assertEquals(1, s.getStandards().size());
	}
}
