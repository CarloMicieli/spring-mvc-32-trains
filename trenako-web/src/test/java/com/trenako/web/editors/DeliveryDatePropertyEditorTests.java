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
package com.trenako.web.editors;

import static org.junit.Assert.*;

import org.junit.Test;

import com.trenako.entities.DeliveryDate;
import com.trenako.web.editors.DeliveryDatePropertyEditor;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class DeliveryDatePropertyEditorTests {

	@Test
	public void shouldSetValidValues() {
		DeliveryDatePropertyEditor pe = new DeliveryDatePropertyEditor(true);
		pe.setAsText("2012/Q1");
		
		DeliveryDate dd = (DeliveryDate) pe.getValue();
		assertNotNull(dd);
		assertEquals("2012/Q1", dd.toString());
	}

	@Test
	public void shouldSetNullForEmptyStrings() {
		DeliveryDatePropertyEditor pe = new DeliveryDatePropertyEditor(true);
		pe.setAsText("");
		DeliveryDate dd = (DeliveryDate) pe.getValue();
		assertNull(dd);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowsExceptionWithInvalidValues() {
		DeliveryDatePropertyEditor pe = new DeliveryDatePropertyEditor(true);
		pe.setAsText("123456");
	}
}
