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
package com.trenako.values;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.trenako.values.PowerMethod;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class PowerMethodTests {

	@Test
	public void shouldProduceDescription() {
		PowerMethod ac = PowerMethod.AC;
		assertEquals("ac", ac.label());
		
		PowerMethod ads = PowerMethod.DC;
		assertEquals("dc", ads.label());
	}
	
	@Test
	public void shouldParseAStringValue() {
		PowerMethod ads = PowerMethod.parse("ac");
		assertEquals(PowerMethod.AC, ads);
	}
	
	@Test
	public void shouldListAllPowerMethods() {
		List<String> l = PowerMethod.list();
		assertEquals("[ac, dc]", l.toString());
	}
}
