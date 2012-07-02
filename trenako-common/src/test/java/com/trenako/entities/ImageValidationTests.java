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

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import com.trenako.AbstractValidationTests;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ImageValidationTests extends AbstractValidationTests<Image> {
	@Before
	public void initValidator() {
		super.init(Image.class);
	}
	
	@Test
	public void shouldValidateInvalidImages() {
		Image img = new Image();
		Map<String, String> errors = validate(img);
		assertEquals(2, errors.size());
		assertEquals("image.parentId.required", errors.get("parentId"));
		assertEquals("image.is.empty", errors.get("image"));
	}
	
	@Test
	public void shouldValidateCorrectImages() {
		Image img = new Image(new ObjectId(), new UploadFile());
		Map<String, String> errors = validate(img);
		assertEquals(0, errors.size());
	}
}