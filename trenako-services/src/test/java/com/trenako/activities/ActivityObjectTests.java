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
package com.trenako.activities;

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;
import org.junit.Test;

import com.trenako.entities.RollingStock;
import com.trenako.mapping.WeakDbRef;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ActivityObjectTests {
	
	@Test
	public void shouldProduceStringRepresentationForActivityObjects() {
		ActivityObject obj = new ActivityObject("type", "/localhost", "name");
		assertEquals("object{type: type, url: /localhost, name: name}", obj.toString());
	}
	
	@Test
	public void shouldCheckWhetherTwoActivityObjectsAreEquals() {
		ActivityObject x = new ActivityObject("type", "/localhost", "name");
		ActivityObject y = new ActivityObject("type", "/localhost", "name");
		
		assertTrue("Activity objects are different", x.equals(x));
		assertTrue("Activity objects are different", x.equals(y));
	}
	
	@Test
	public void shouldCheckWhetherTwoActivityObjectsAreDifferent() {
		ActivityObject x = new ActivityObject("type", "/localhost", "name");
		ActivityObject y = new ActivityObject("type2", "/localhost", "name");
		assertFalse("Activity objects are equals", x.equals(y));
		
		ActivityObject z = new ActivityObject("type", "/localhost/2", "name");
		assertFalse("Activity objects are equals", x.equals(z));
		
		ActivityObject t = new ActivityObject("type", "/localhost", "name2");
		assertFalse("Activity objects are equals", x.equals(t));
	}
	
	@Test
	public void shouldCreateActivityObjectsFromRollingStocks() {
		ActivityObject obj = ActivityObject.rsObject(rsRef());
		
		assertEquals("rollingStock", obj.getObjectType());
		assertEquals("/rollingstocks/acme-123456", obj.getUrl());
		assertEquals("ACME 123456", obj.getDisplayName());
	}
	
	private WeakDbRef<RollingStock> rsRef() {
		return WeakDbRef.buildRef(rollingStock());
	}
	
	private RollingStock rollingStock() { 
		return new RollingStock.Builder(acme(), "123456")
			.scale(scaleH0())
			.railway(db())
			.build();
	}

}
