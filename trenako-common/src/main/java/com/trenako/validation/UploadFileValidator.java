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
package com.trenako.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.trenako.AppGlobals;
import com.trenako.entities.UploadFile;
import com.trenako.validation.constraints.SupportedImage;

/**
 * The {@code UploadFile} validator class.
 * @author Carlo Micieli
 *
 */
public class UploadFileValidator implements ConstraintValidator<SupportedImage, UploadFile> {

	/**
	 * Creates a new {@code ImageValidator}.
	 */
	public UploadFileValidator() {
	}

	@Override
	public void initialize(SupportedImage constraintAnnotation) {
	}

	@Override
	public boolean isValid(UploadFile value, ConstraintValidatorContext context) {
		if (value==null) {
			return true;
		}
		
		final String mediaType = value.getMediaType();
		if (mediaType!=null && !AppGlobals.ALLOWED_MEDIA_TYPES.contains(mediaType)) {
			return false;
		}
		
		final byte[] content = value.getContent();
		if (content!=null && content.length > AppGlobals.MAX_UPLOAD_SIZE) {
			return false;
		}
		
		return true;
	}
}
