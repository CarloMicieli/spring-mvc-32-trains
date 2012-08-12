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
package com.trenako.web.images;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.trenako.AppGlobals;

/**
 * A custom validator for {@code MultipartFile}. 
 * <p>
 * Validation is checking the {@code size} and {@code contentType}
 * for the uploaded file. Validation for empty files will always succeeds.
 * </p>
 *
 * @author Carlo Micieli
 *
 */
@Component
public class MultipartFileValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return MultipartFile.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// skip validation for empty files.
		if (target == null) return;
		
		MultipartFile file = (MultipartFile) target;
		if (file.isEmpty()) return;

		// validate file size
		if (file.getSize() > AppGlobals.MAX_UPLOAD_SIZE) {
			errors.reject("uploadFile.size.range.notmet", "Invalid media size. Max size is 512 Kb");
		}
		
		// validate content type
		MediaType contentType = MediaType.parseMediaType(file.getContentType());
		if (!AppGlobals.ALLOWED_MEDIA_TYPES.contains(contentType)) {
			errors.reject("uploadFile.contentType.notvalid", "Invalid media type");
		}
	}
 }
