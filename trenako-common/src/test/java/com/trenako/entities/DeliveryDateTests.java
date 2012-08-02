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

import static org.junit.Assert.*;
import org.junit.Test;

import com.trenako.DeliveryDateFormatException;
import com.trenako.values.DeliveryDate;


/**
 * 
 * @author Carlo Micieli
 *
 */
public class DeliveryDateTests {

	@Test
	public void shouldCreateDeliveryDatesList() {
		Iterable<DeliveryDate> dates =
				DeliveryDate.list(1978, 1980, 1981, 1982);
		
		String expected = "[1982/Q4, 1982/Q3, 1982/Q2, 1982/Q1, "+
				"1981/Q4, 1981/Q3, 1981/Q2, 1981/Q1, " +
				"1980, 1979, 1978]";
		assertEquals(expected, dates.toString());

	
		Iterable<DeliveryDate> dates2 =
				DeliveryDate.list(1978, 1978, 1979, 1979);
		
		String expected2 = "[1979/Q4, 1979/Q3, 1979/Q2, 1979/Q1, 1978]";
		assertEquals(expected2, dates2.toString());
	}
		
	@Test
	public void shouldCreateDeliveryDateWithQuarter() {
		DeliveryDate dd = new DeliveryDate(2012, 1);
		assertEquals("2012/Q1", dd.toString());
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void shouldThrowAnExceptionIfQuarterIsNotValid() {
		new DeliveryDate(2012, 5);
	}
	
	@Test
	public void shouldCreateDeliveryDateWithoutQuarter() {
		DeliveryDate dd = new DeliveryDate(2012);
		assertEquals("2012", dd.toString());
	}
	
	@Test
	public void shouldReturnTrueIfTwoDeliveryDatesAreEquals() {
		DeliveryDate x = new DeliveryDate(2012, 1);
		DeliveryDate y = new DeliveryDate(2012, 1);
		assertTrue(x.equals(y));
	}
	
	@Test
	public void shouldReturnFalseIfTwoDeliveryDatesAreEquals() {
		DeliveryDate x = new DeliveryDate(2012, 1);
		DeliveryDate y = new DeliveryDate(2011, 2);
		assertFalse(x.equals(y));
		
		DeliveryDate z = new DeliveryDate(2012);
		assertFalse(x.equals(z));
	}
	
	@Test(expected = DeliveryDateFormatException.class)
	public void shouldThrowsExceptionParsingInvalidValues() {
		DeliveryDate.parseString("aaaa");
	}
	
	@Test(expected = DeliveryDateFormatException.class)
	public void shouldThrowsExceptionParsingInvalidYearValues() {
		DeliveryDate.parseString("123456");
	}

	@Test(expected = DeliveryDateFormatException.class)
	public void shouldThrowsExceptionParsingInvalidYears() {
		DeliveryDate.parseString("aaaa/Q1");
	}
	
	@Test(expected = DeliveryDateFormatException.class)
	public void shouldThrowsExceptionParsingInvalidQuarters() {
		DeliveryDate.parseString("2012/Q5");
	}
	
	@Test(expected = DeliveryDateFormatException.class)
	public void shouldThrowsExceptionParsingInvalidQuarterPrefix() {
		DeliveryDate.parseString("2012/T5");
	}
	
	@Test
	public void shouldParseDeliveryDates() {
		DeliveryDate dd = DeliveryDate.parseString("2012/Q3");
		assertEquals("2012/Q3", dd.toString());
	}
	
	@Test
	public void shouldParseDeliveryDatesWithYearOnly() {
		DeliveryDate dd = DeliveryDate.parseString("2012");
		assertEquals("2012", dd.toString());
	}
}
