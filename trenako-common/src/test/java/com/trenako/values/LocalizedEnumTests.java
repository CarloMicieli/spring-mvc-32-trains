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

import org.junit.Test;
import org.springframework.context.MessageSource;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class LocalizedEnumTests {
	
	MessageSource messageSource(String code, String defaultMessage, String message) {
		MessageSource ms = mock(MessageSource.class);
		when(ms.getMessage(eq(code), (Object[])eq(null), eq(defaultMessage), (Locale)eq(null)))
			.thenReturn(message);
		return ms;
	}
	
	void verifyMessageSource(MessageSource messageSource, String code, String defaultMessage) {
		verify(messageSource, times(1))
			.getMessage(eq(code), (Object[])eq(null), eq(defaultMessage), (Locale)eq(null));
	}
	
	@Test
	public void shouldLocalizeCategories() {
		final String code = "category.electric.locomotives.label";
		final String defaultMessage = "electric-locomotives";
		final String message = "Electric Locomotives";
		
		MessageSource ms = messageSource(code, defaultMessage, message);
		
		Category c = Category.ELECTRIC_LOCOMOTIVES;
		LocalizedEnum<Category> le = new LocalizedEnum<Category>(c);
		le.setMessageSource(ms);
		
		// this method will actually translate the label text
		String msg = le.getMessage();
		
		verifyMessageSource(ms, code, defaultMessage);
		assertEquals(c, le.getValue());
		assertEquals(message, msg);
	}
	
	@Test
	public void shouldLocalizePowerMethods() {
		final String code = "powermethod.ac.label";
		final String defaultMessage = "ac";
		final String message = "AC";
		
		MessageSource ms = messageSource(code, defaultMessage, message);
		
		PowerMethod pm = PowerMethod.AC;
		LocalizedEnum<PowerMethod> le = new LocalizedEnum<PowerMethod>(pm);
		le.setMessageSource(ms);
		
		// this method will actually translate the label text
		String msg = le.getMessage();
		
		verifyMessageSource(ms, code, defaultMessage);
		assertEquals(pm, le.getValue());
		assertEquals(message, msg);
	}	
	
	@Test
	public void shouldLocalizeEras() {
		final String code = "era.iii.label";
		final String defaultMessage = "iii";
		final String message = "III";
		
		MessageSource ms = messageSource(code, defaultMessage, message);
		
		Era e = Era.III;
		LocalizedEnum<Era> le = new LocalizedEnum<Era>(e);
		le.setMessageSource(ms);
		
		// this method will actually translate the label text
		String msg = le.getMessage();
		
		verifyMessageSource(ms, code, defaultMessage);
		assertEquals(e, le.getValue());
		assertEquals(message, msg);
	}
}
