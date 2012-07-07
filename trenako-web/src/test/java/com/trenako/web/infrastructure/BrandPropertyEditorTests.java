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

import org.bson.types.ObjectId;
import org.junit.Test;

import com.trenako.entities.Brand;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class BrandPropertyEditorTests {

	@Test
	public void shouldSetValidValues() {
		ObjectId id = new ObjectId();
		
		BrandPropertyEditor pe = new BrandPropertyEditor(true);
		pe.setAsText(id.toString());
		
		Brand brand = (Brand) pe.getValue();
		assertNotNull(brand);
		assertEquals(id, brand.getId());
	}

	@Test
	public void shouldSetNullForEmptyStrings() {
		BrandPropertyEditor pe = new BrandPropertyEditor(true);
		pe.setAsText("");
		Brand brand = (Brand) pe.getValue();
		assertNull(brand);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowsExceptionWithInvalidValues() {
		BrandPropertyEditor pe = new BrandPropertyEditor(true);
		pe.setAsText("123456");
	}
	
}
