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
package com.trenako.results;

import static org.junit.Assert.*;
import java.util.Map;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class SearchRangeTests {
	
	static final ObjectId SINCE = new ObjectId("501171ab575ef9abd1a0c71e");
	static final ObjectId MAX = new ObjectId("501171ab575ef9abd1a0c71f");
	
	@Test
	public void shouldReturnParamsAsMaps() {
		SearchRange range = new SearchRange(10, new Sort(Direction.DESC, "name"), SINCE, MAX);
		
		Map<String, Object> params = range.asMap();
		
		assertNotNull(params);
		assertEquals(5, params.size());
		assertEquals("{dir=DESC, max=501171ab575ef9abd1a0c71f, since=501171ab575ef9abd1a0c71e, size=10, sort=name}", 
				params.toString());
	}
	
}
