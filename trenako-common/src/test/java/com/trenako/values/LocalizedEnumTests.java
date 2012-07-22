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

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.trenako.values.LocalizedEnum.MessageFailback;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LocalizedEnumTests {
	
	@Mock MessageSource ms;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	void mockMessage(String code, String defaultMessage, String message) {
		when(ms.getMessage(eq(code), (Object[])eq(null), eq(defaultMessage), (Locale)eq(null)))
			.thenReturn(message);
	}
	
	void verifyMessageSource(MessageSource messageSource, String code, String defaultMessage) {
		verify(messageSource, times(1))
			.getMessage(eq(code), (Object[])eq(null), eq(defaultMessage), (Locale)eq(null));
	}
	
	@Test
	public void shouldLocalizeCategories() {
		final String labelCode = "category.electric.locomotives.label";
		final String descCode = "category.electric.locomotives.description";
		final String defaultMessage = "electric-locomotives";
		final String label = "Electric Locomotives";
		final String description = "Electric Locomotives description";
		
		mockMessage(labelCode, defaultMessage, label);
		mockMessage(descCode, defaultMessage, description);
		
		Category c = Category.ELECTRIC_LOCOMOTIVES;
		LocalizedEnum<Category> le = new LocalizedEnum<Category>(c, ms, null);
		
		String msgLabel = le.getLabel();
		String msgDesc = le.getDescription();
		
		verifyMessageSource(ms, labelCode, defaultMessage);
		verifyMessageSource(ms, descCode, defaultMessage);
		assertEquals(c, le.getValue());
		assertEquals(label, msgLabel);
		assertEquals(description, msgDesc);
	}
	
	@Test
	public void shouldLocalizePowerMethods() {
		final String labelCode = "powermethod.ac.label";
		final String descCode = "powermethod.ac.description";
		final String defaultMessage = "ac";
		final String label = "AC";
		final String description = "Alternate Current";
		
		mockMessage(labelCode, defaultMessage, label);
		mockMessage(descCode, defaultMessage, description);
		
		PowerMethod pm = PowerMethod.AC;
		LocalizedEnum<PowerMethod> le = new LocalizedEnum<PowerMethod>(pm, ms, null);
		
		String msgLabel = le.getLabel();
		String msgDesc = le.getDescription();
		
		verifyMessageSource(ms, labelCode, defaultMessage);
		verifyMessageSource(ms, descCode, defaultMessage);
		assertEquals(pm, le.getValue());
		assertEquals(label, msgLabel);
		assertEquals(description, msgDesc);
	}	
	
	@Test
	public void shouldLocalizeEras() {
		final String labelCode = "era.iii.label";
		final String descCode = "era.iii.description";
		final String defaultMessage = "iii";
		final String label = "III";
		final String description = "III";
		
		mockMessage(labelCode, defaultMessage, label);
		mockMessage(descCode, defaultMessage, description);
		
		Era e = Era.III;
		LocalizedEnum<Era> le = new LocalizedEnum<Era>(e, ms, null);
		
		// this method will actually translate the label text
		String msgLabel = le.getLabel();
		String msgDesc = le.getDescription();
		
		verifyMessageSource(ms, labelCode, defaultMessage);
		verifyMessageSource(ms, descCode, defaultMessage);
		assertEquals(e, le.getValue());
		assertEquals(label, msgLabel);
		assertEquals(description, msgDesc);
	}
		
	@Test
	public void shouldReturnStringRepresentations() {
		LocalizedEnum<Era> x = new LocalizedEnum<Era>(Era.III);
		assertEquals("(iii)", x.toString());
	}
	
	@Test
	public void shouldTrueForTwoEqualsValues() {
		Era e = Era.III;
		LocalizedEnum<Era> x = new LocalizedEnum<Era>(e);
		LocalizedEnum<Era> y = new LocalizedEnum<Era>(e);
		assertTrue("Values are different", x.equals(y));
	}
	
	@Test
	public void shouldFalseForTwoDifferentValues() {
		LocalizedEnum<Era> x = new LocalizedEnum<Era>(Era.III);
		LocalizedEnum<Era> y = new LocalizedEnum<Era>(Era.IV);
		assertFalse("Values are equals", x.equals(y));
	}
	
	@Test
	public void shouldFalseForTwoDifferentEnums() {
		LocalizedEnum<Era> x = new LocalizedEnum<Era>(Era.III);
		LocalizedEnum<Category> y = new LocalizedEnum<Category>(Category.ELECTRIC_LOCOMOTIVES);
		assertFalse("Values are equals", x.equals(y));
	}
	
	@Test
	public void shouldCreateErasList() {
		Iterable<LocalizedEnum<Era>> eras = LocalizedEnum.list(Era.class);
		assertNotNull(eras);
		assertEquals("[(i), (ii), (iii), (iv), (v), (vi)]", eras.toString());
	}
	
	@Test
	public void shouldCreateCategoriesList() {
		Iterable<LocalizedEnum<Category>> categories = LocalizedEnum.list(Category.class);
		
		assertNotNull(categories);
		String expected = "[(steam-locomotives), (diesel-locomotives), " +
				"(electric-locomotives), (railcars), " +
				"(electric-multiple-unit), (freight-cars), " +
				"(passenger-cars), (train-sets), (starter-sets)]";
		
		assertEquals(expected, categories.toString());
	}
	
	@Test
	public void shouldProduceFailbackMessagesForEras() {
		MessageFailback<Era> failback = new LocalizedEnum.EraMessageFailback();

		LocalizedEnum<Era> le = new LocalizedEnum<Era>(Era.IV, null, failback);
		
		assertEquals("IV", le.getLabel());
		assertEquals("IV", le.getDescription());
	}
	
	@Test
	public void shouldProduceFailbackMessagesForCategories() {
		MessageFailback<Category> failback = new LocalizedEnum.CategoryMessageFailback();

		LocalizedEnum<Category> le = new LocalizedEnum<Category>(Category.ELECTRIC_LOCOMOTIVES, null, failback);
		
		assertEquals("Electric locomotives", le.getLabel());
		assertEquals("Electric locomotives", le.getDescription());
	}
}
