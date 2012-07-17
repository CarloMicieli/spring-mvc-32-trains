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
package com.trenako.web.infrastructure;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.PropertyValues;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ServletRequestPathVariablesPropertyValuesTests {

	@Test
	public void shouldFillPropertyValues() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServletPath("/rs/brand/ACME");
		
		PropertyValues pvs =
				new ServletRequestPathVariablesPropertyValues(request);
		
		assertNotNull(pvs);
		assertEquals(1, pvs.getPropertyValues().length);
		assertEquals("ACME", pvs.getPropertyValue("brand").getValue());
	}

}
