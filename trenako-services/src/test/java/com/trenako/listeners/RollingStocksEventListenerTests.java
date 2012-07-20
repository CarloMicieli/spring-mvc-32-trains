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
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStocksEventListenerTests {
	@Test
	public void shouldFillTheValuesBeforeSave() {
		RollingStocksEventListener listener = new RollingStocksEventListener();
		
		Railway railway = new Railway.Builder("S.N.C.F.")
			.slug("sncf")
			.country("de").build();
		
		Scale scale = new Scale.Builder("H0")
			.slug("h0").build();
		
		Brand brand = new Brand.Builder("ACME")
			.slug("acme").build();
		
		RollingStock rs = new RollingStock.Builder(brand, "123456")
			.scale(scale)
			.railway(railway)
			.build();
		BasicDBObject dbo = new BasicDBObject();
		listener.onBeforeSave(rs, dbo);
		
		assertNotNull(dbo.get("lastModified"));
		assertEquals("h0", dbo.get("scaleName"));
		assertEquals("sncf", dbo.get("railwayName"));
		assertEquals("acme", dbo.get("brandName"));
		assertEquals("de", dbo.get("country"));
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
