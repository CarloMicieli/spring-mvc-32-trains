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

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.springframework.context.MessageSource;

import com.trenako.CatFormatException;
import com.trenako.values.Category;
import com.trenako.values.PowerMethod;

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
		Cat cat = Cat.buildCat(PowerMethod.DC, Category.ELECTRIC_LOCOMOTIVES);
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
	
	@Test
	public void shouldReturnLocalizedLabels() {
		messageSource = mock(MessageSource.class);
		mockMessage("powermethod.ac.label", "ac", "AC");
		mockMessage("category.electric.locomotives.label", "electric-locomotives", "Electric locomotives");
		
		Cat cat = Cat.buildCat("ac", "electric-locomotives", messageSource);
		
		verifyMessageSource("powermethod.ac.label", "ac");
		verifyMessageSource("category.electric.locomotives.label", "electric-locomotives");
		assertNotNull(cat);
		assertEquals("ac-electric-locomotives", cat.toString());
		assertEquals("AC Electric locomotives", cat.label());
	}
	
	@Test
	public void shouldListAllTheCategoryLabelsByPowerMethod() {
		messageSource = mock(MessageSource.class);
		
		Iterable<Cat> cat = Cat.list(PowerMethod.AC, messageSource);
		List<Cat> l = (List<Cat>) cat;
		
		String expected = "[ac-steam-locomotives, ac-diesel-locomotives, " +
				"ac-electric-locomotives, ac-railcars, ac-electric-multiple-unit, " +
				"ac-freight-cars, ac-passenger-cars, ac-train-sets, ac-starter-sets]";
		assertEquals(expected, l.toString());
	}
	
	MessageSource messageSource;
	void mockMessage(String code, String defaultMessage, String message) {
		when(messageSource.getMessage(eq(code), (Object[])eq(null), eq(defaultMessage), (Locale)eq(null)))
			.thenReturn(message);
	}
	
	void verifyMessageSource(String code, String defaultMessage) {
		verify(messageSource, times(1))
			.getMessage(eq(code), (Object[])eq(null), eq(defaultMessage), (Locale)eq(null));
	}
}
