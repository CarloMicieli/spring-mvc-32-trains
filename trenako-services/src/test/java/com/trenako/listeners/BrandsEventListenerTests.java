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
package com.trenako.listeners;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.trenako.entities.Brand;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class BrandsEventListenerTests {

	private BrandsEventListener listener;
	
	@Test
	public void shouldFillTheValuesBeforeSave() {
		listener = new BrandsEventListener();
		
		Brand brand = new Brand.Builder("ACME").build();
		BasicDBObject dbo = new BasicDBObject();
		listener.onBeforeSave(brand, dbo);
		
		assertEquals("acme", dbo.get("slug"));
		assertNotNull(dbo.get("lastModified"));
	}
	
}
