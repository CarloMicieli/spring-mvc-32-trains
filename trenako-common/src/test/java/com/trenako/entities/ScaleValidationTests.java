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
package com.trenako.entities;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.trenako.AbstractValidationTests;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ScaleValidationTests extends AbstractValidationTests<Scale> {

	@Before
	public void initValidator() {
		super.init(Scale.class);
	}
	
	@Test
	public void shouldValidateScales() {
		Scale scale = new Scale("H0", 87);
		Map<String, String> errors = validate(scale);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidScale() {
		Scale scale = new Scale();
		Map<String, String> errors = validate(scale);
		assertEquals(2, errors.size());
		assertEquals("scale.name.required", errors.get("name"));
		assertEquals("scale.ratio.range.notmet", errors.get("ratio"));
	}
	
	@Test
	public void shouldValidateScaleNameSize() {
		Scale scale = new Scale();
		scale.setName("12345678901"); //max = 10
		scale.setRatio(87);
		Map<String, String> errors = validate(scale);
		assertEquals(1, errors.size());
		assertEquals("scale.name.size.notmet", errors.get("name"));
	}
	
	@Test
	public void shouldValidateScaleRatio() {
		Map<String, String> errors;
		
		Scale s1 = new Scale("H0", -1, true);
		errors = validate(s1);
		assertEquals(1, errors.size());
		assertEquals("scale.ratio.range.notmet", errors.get("ratio"));

		Scale s2 = new Scale("H0", 221, true);
		errors = validate(s2);
		assertEquals(1, errors.size());
		assertEquals("scale.ratio.range.notmet", errors.get("ratio"));
	}
}
