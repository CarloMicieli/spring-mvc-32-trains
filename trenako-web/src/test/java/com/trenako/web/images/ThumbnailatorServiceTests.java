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

import static org.junit.Assert.*;
import static com.trenako.utility.Maps.*;

import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.trenako.images.UploadFile;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( {Thumbnails.class} )
public class ThumbnailatorServiceTests {
	
	private ImagesConverter imgConverter = new ThumbnailatorService();

	@Test
	public void shouldCreateImages() throws Exception {
		final byte[] content = "file content".getBytes();
		MultipartFile file = mockFile(content, MediaType.IMAGE_JPEG);

		UploadFile img = imgConverter.createImage(file, metadata());

		assertNotNull(img.getContent());
		assertEquals(MediaType.IMAGE_JPEG_VALUE.toString(), img.getContentType());
	}
		
	private Map<String, String> metadata() {
		return map("slug", "img-slug");
	}
	
	private MultipartFile mockFile(byte[] content, MediaType type) {
		return new MockMultipartFile("file.jpg", "file.jpg", type.toString(), content);
	}
}
