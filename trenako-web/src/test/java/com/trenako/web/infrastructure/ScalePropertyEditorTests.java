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

import com.trenako.entities.Scale;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ScalePropertyEditorTests {

	@Test
	public void shouldSetValidValues() {
		ObjectId id = new ObjectId();
		
		ScalePropertyEditor pe = new ScalePropertyEditor(true);
		pe.setAsText(id.toString());
		
		Scale scale = (Scale) pe.getValue();
		assertNotNull(scale);
		assertEquals(id, scale.getId());
	}

	@Test
	public void shouldSetNullForEmptyStrings() {
		ScalePropertyEditor pe = new ScalePropertyEditor(true);
		pe.setAsText("");
		Scale scale = (Scale) pe.getValue();
		assertNull(scale);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowsExceptionWithInvalidValues() {
		ScalePropertyEditor pe = new ScalePropertyEditor(true);
		pe.setAsText("123456");
	}
	
}
