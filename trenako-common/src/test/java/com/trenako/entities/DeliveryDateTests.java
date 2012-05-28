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

/**
 * 
 * @author Carlo Micieli
 *
 */
public class DeliveryDateTests {

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
}
