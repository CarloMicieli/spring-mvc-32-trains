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

import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.trenako.test.AbstractValidationTests;

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
		Scale scale = new Scale.Builder("H0")
			.ratio(870)
			.description("Scale description")
			.build();
		Map<String, String> errors = validate(scale);
		assertEquals(0, errors.size());
	}
	
	@Test
	public void shouldValidateInvalidScale() {
		Scale scale = new Scale();
		Map<String, String> errors = validate(scale);
		assertEquals(3, errors.size());
		assertEquals("scale.name.required", errors.get("name"));
		assertEquals("scale.ratio.range.notmet", errors.get("ratio"));
		assertEquals("scale.description.required", errors.get("description"));
	}
	
	@Test
	public void shouldValidateScaleNameSize() {
		Scale scale = new Scale.Builder("12345678901") //max = 10
			.ratio(870)
			.description("Scale description")
			.build();
		Map<String, String> errors = validate(scale);
		assertEquals(1, errors.size());
		assertEquals("scale.name.size.notmet", errors.get("name"));
	}
	
	@Test
	public void shouldValidateScaleRatio() {
		Map<String, String> errors;
		
		Scale s1 = new Scale.Builder("H0")
			.narrow(true)
			.description("Scale description")
			.build();
		errors = validate(s1);
		assertEquals(1, errors.size());
		assertEquals("scale.ratio.range.notmet", errors.get("ratio"));

		Scale s2 = new Scale.Builder("H0")
			.ratio(2210)
			.narrow(true)
			.description("Scale description")
			.build();
		errors = validate(s2);
		assertEquals(1, errors.size());
		assertEquals("scale.ratio.range.notmet", errors.get("ratio"));
	}
	
	@Test
	public void shouldValidateDescriptionForDefaultLanguage() {
		Map<String, String> errors = null;
		Scale s1 = new Scale.Builder("H0")
			.ratio(870)
			.description("Scale description")
			.build();
		errors = validate(s1);
		assertEquals(0, errors.size());
		
		Scale s2 = new Scale.Builder("H0")
			.ratio(870)
			.description(Locale.FRENCH, "Scale description")
			.build();
		errors = validate(s2);
		assertEquals(1, errors.size());
		assertEquals("scale.description.default.required", errors.get("description"));
	}
}
