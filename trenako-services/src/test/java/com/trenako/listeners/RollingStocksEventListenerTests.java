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

import static org.junit.Assert.*;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStocksEventListenerTests {
	@Test
	public void shouldFillTheValuesBeforeSave() {
		RollingStocksEventListener listener = new RollingStocksEventListener();
		
		Railway DB = new Railway.Builder("DB")
			.country("DEU").build();
		
		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.scale("H0")
			.railway(DB)
			.build();
		BasicDBObject dbo = new BasicDBObject();
		listener.onBeforeSave(rs, dbo);
		
		assertNotNull(dbo.get("lastModified"));
		assertEquals("H0", dbo.get("scaleName"));
		assertEquals("DB", dbo.get("railwayName"));
		assertEquals("ACME", dbo.get("brandName"));
		assertEquals("DEU", dbo.get("country"));
	}
	
	@Test
	public void shouldLeaveCountryNullIfTheRailwayHasNone() {
		RollingStocksEventListener listener = new RollingStocksEventListener();
	
		RollingStock rs = new RollingStock.Builder("ACME", "123456")
			.scale("H0")
			.railway("DB")
			.build();
		BasicDBObject dbo = new BasicDBObject();
		listener.onBeforeSave(rs, dbo);
		
		assertNull(dbo.get("country"));
	}
}