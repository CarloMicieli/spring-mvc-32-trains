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

import java.util.GregorianCalendar;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class SlugTests {

	@Test
	public void shouldEncodeEmptyString() {
		String slug = Slug.encode("");
		assertEquals("", slug);
	}

	@Test
	public void shouldEncodeWhitespaces() {
		String slug = Slug.encode("Time is an illusion");
		assertEquals("time-is-an-illusion", slug);
	}

	@Test
	public void shouldEncodePunctuationSigns() {
		String slug = Slug.encode("Time; is an: illusion.");
		assertEquals("time-is-an-illusion", slug);
	}

	@Test
	public void shouldEncodeNonLatinCharacters() {
		String slug = Slug.encode("Timè is àn illusiòn.");
		assertEquals("time-is-an-illusion", slug);
	}

	@Test
	public void shouldEncodeDates() {
		String slug = Slug.encode(new GregorianCalendar(1976, 6, 4).getTime()); // month + 1
		assertEquals("1976-07-04", slug);
	}
	
}
