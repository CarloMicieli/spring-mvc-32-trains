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

import static com.trenako.utility.Maps.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.trenako.images.ImagesRepository;
import com.trenako.images.UploadFile;

/**
 * @author Carlo Micieli
 *
 */
public class WebImageServiceTests {

	private @Mock ImagesConverter converter;
	private @Mock ImagesRepository repo;
	private WebImageService service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new WebImageServiceImpl(repo, converter);
	}
		
	@Test
	public void shouldRenderImages() {
		GridFSDBFile mockFile = mock(GridFSDBFile.class);
		when(mockFile.getContentType()).thenReturn(MediaType.IMAGE_JPEG.toString());
		when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{}));
		
		when(repo.findFileBySlug(eq("img-slug"))).thenReturn(mockFile);

		ResponseEntity<byte[]> resp = service.renderImage("img-slug");

		assertNotNull(resp);
		assertTrue(resp.hasBody());
		assertEquals(HttpStatus.CREATED, resp.getStatusCode());
		assertEquals(MediaType.IMAGE_JPEG, resp.getHeaders().getContentType());
	}
	
	@Test
	public void shouldSaveImageFiles() throws IOException {
		MultipartFile file = multipartFile();
		UploadRequest req = new UploadRequest("brand", "ls-model", file);
		UploadFile value = uploadFile("brand_ls-models");

		when(converter.createImage(eq(file), eq(req.asMetadata(false))))
			.thenReturn(value);

		service.saveImage(req);

		verify(converter, times(1)).createImage(eq(file), eq(req.asMetadata(false)));
		verify(repo, times(1)).store(eq(value));
	}

	@Test
	public void shouldSaveImageFilesWithThumbnail() throws IOException {
		MultipartFile file = multipartFile();
		UploadRequest req = new UploadRequest("brand", "ls-model", file);
		UploadFile img = uploadFile("brand_ls-models");
		UploadFile thumb = uploadFile("th_brand_ls-models");

		when(converter.createImage(eq(file), eq(req.asMetadata(false))))
			.thenReturn(img);
		when(converter.createThumbnail(eq(file), eq(req.asMetadata(true)), eq(100)))
			.thenReturn(thumb);

		service.saveImageWithThumb(req, 100);

		verify(converter, times(1)).createImage(eq(file), eq(req.asMetadata(false)));
		verify(repo, times(1)).store(eq(img));

		verify(converter, times(1)).createThumbnail(eq(file), eq(req.asMetadata(true)), eq(100));
		verify(repo, times(1)).store(eq(thumb));
	}

	private UploadFile uploadFile(String imgSlug) {
		return new UploadFile(new ByteArrayInputStream(new byte[]{}), 
				MediaType.IMAGE_JPEG.toString(),
				"image.jpg", 
				map("slug", imgSlug));
	}

	private MultipartFile multipartFile() {
		MultipartFile mf = new MockMultipartFile("upload", 
			"upload.jpg",
			MediaType.IMAGE_JPEG.toString(),
			new byte[]{});
		return mf;
	}
}
