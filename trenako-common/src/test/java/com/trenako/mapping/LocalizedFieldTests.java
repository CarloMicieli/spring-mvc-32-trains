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
package com.trenako.mapping;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class LocalizedFieldTests {

	@Test
	public void shouldCreateALocalizedFieldWithDefaultValues() {
		LocalizedField<String> field = new LocalizedField<String>("default message");
		assertEquals("default message", field.getDefault());
		assertTrue(field.containsDefault());
		
		LocalizedField<String> field2 = new LocalizedField<String>();
		field2.put(Locale.GERMAN, "German");
		assertFalse(field2.containsDefault());
	}
	
	@Test
	public void shouldGetLocalizedDescriptions() {
		LocalizedField<String> field = new LocalizedField<String>();
		field.put(Locale.FRENCH, "French");
		field.put(Locale.GERMAN, "German");
		
		assertEquals("French", field.getValue(Locale.FRENCH));
		assertEquals("German", field.getValue(Locale.GERMAN));
	}
	
	@Test
	public void shouldCheckWhetherTwoLocalizedFieldsAreEquals() {
		LocalizedField<String> x = new LocalizedField<String>("hello world");
		LocalizedField<String> y = new LocalizedField<String>("hello world");
		assertTrue(x.equals(x));
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldGetDefaultDescriptionWhenTheLocalizedDontExist() {
		LocalizedField<String> field = new LocalizedField<String>("We few, we happy few, we band of brothers.");
		field.put(Locale.FRENCH, "French");

		assertEquals("We few, we happy few, we band of brothers.", field.getValue(Locale.ITALIAN));
	}
}
