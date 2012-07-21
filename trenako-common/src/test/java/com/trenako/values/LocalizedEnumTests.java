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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class LocalizedEnumTests {
	@Test
	public void shouldLocalizeCategories() {
		Category c = Category.ELECTRIC_LOCOMOTIVES;
		LocalizedEnum<Category> le = new LocalizedEnum<Category>(c, "Electric locomotives");
		assertEquals(c, le.getValue());
		assertEquals("Electric locomotives", le.getMessage());
	}
	
	@Test
	public void shouldLocalizePowerMethods() {
		PowerMethod pm = PowerMethod.AC;
		LocalizedEnum<PowerMethod> le = new LocalizedEnum<PowerMethod>(pm, "CC");
		assertEquals(pm, le.getValue());
		assertEquals("CC", le.getMessage());
	}	
	
	@Test
	public void shouldLocalizeEras() {
		Era e = Era.III;
		LocalizedEnum<Era> le = new LocalizedEnum<Era>(e, "III");
		assertEquals(e, le.getValue());
		assertEquals("III", le.getMessage());
	}
}
