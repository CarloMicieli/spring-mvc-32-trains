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

import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class MapsTests {
	@Test
	public void shouldCreateMapsWithASingleEntry() {
		Map<String, Integer> map = Maps.map("key", 1);
		
		int val = map.get("key");
		assertEquals(1, val);
 	}
	
	@Test
	public void shouldCreateMapsWithTwoEntries() {
		Map<String, Integer> map = Maps.map("key1", 1, "key2", 2);
		
		int val1 = map.get("key1");
		int val2 = map.get("key2");
		assertEquals(1, val1);
		assertEquals(2, val2);
 	}
}
