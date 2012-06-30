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

import static org.powermock.api.mockito.PowerMockito.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.eq;
import static org.imgscalr.Scalr.*;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.trenako.entities.UploadFile;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( {Scalr.class, ImageIO.class, IOUtils.class} )
public class ImgscalrServiceTests {

	private ImgscalrService imgService = new ImgscalrService();
	
	private MultipartFile mockFile(byte[] content, MediaType type) {
		return new MockMultipartFile("file.jpg", "file.jpg", type.toString(), content);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldValidateImageMediaTypesForCreateThumbnails() throws IOException {
		// not an image file type
		final byte[] content = "file content".getBytes();
		MultipartFile file = mockFile(content, MediaType.APPLICATION_XML);
		
		imgService.createThumbnail(file, 100);
	}
	
	@Test
	public void shouldCreateThumbnails() throws Exception {
		PowerMockito.mockStatic(ImageIO.class);
		PowerMockito.mockStatic(Scalr.class);
		
		// setup the mock methods
		final int targetSize = 100;
		final byte[] content = "file content".getBytes();
		
		final BufferedImage img = new BufferedImage(600, 600, BufferedImage.TYPE_INT_RGB);
		final BufferedImage resizedImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		final BufferedImage thumb = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
		final BufferedImageOp[] options = new BufferedImageOp[] { OP_ANTIALIAS, OP_BRIGHTER };

		when(ImageIO.read(isA(InputStream.class))).thenReturn(img);
		when(Scalr.resize(img, Method.SPEED, Mode.FIT_TO_HEIGHT, targetSize, options)).thenReturn(resizedImg);
		when(Scalr.pad(eq(resizedImg), eq(2))).thenReturn(thumb);

		MultipartFile file = mockFile(content, MediaType.IMAGE_JPEG);
		
		// call the method under test
		UploadFile image = imgService.createThumbnail(file, targetSize);
		
		// assert
		assertNotNull("Byte array is empty", image.getContent());
		assertEquals("image/jpeg", image.getMediaType());

		// (1) the image is resized
		PowerMockito.verifyStatic();
		Scalr.resize(img, Method.SPEED, Mode.FIT_TO_HEIGHT, targetSize, options);
		
		// (2) the image is padded
		PowerMockito.verifyStatic();
		Scalr.pad(eq(resizedImg), eq(2));

		// (3) the output stream is written
		PowerMockito.verifyStatic();
		ImageIO.write(eq(thumb), eq("jpg"), isA(ByteArrayOutputStream.class));
	}
	
	@Test
	public void shouldConvertImagesToBytes() throws Exception {
		final byte[] content = "file content".getBytes();
		MultipartFile file = mockFile(content, MediaType.IMAGE_JPEG);
		
		UploadFile img = imgService.createImage(file);
		
		assertEquals(content, img.getContent());
		assertEquals(MediaType.IMAGE_JPEG_VALUE, img.getMediaType());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldValidateImageMediaTypesForConvertToBytes() throws IOException {
		// not an image file type
		final byte[] content = "file content".getBytes();
		MultipartFile file = mockFile(content, MediaType.APPLICATION_XML);
		
		imgService.createImage(file);
	}
	
	@Test
	public void shouldRenderImagesToHttpOutputStream() throws IOException {
		PowerMockito.mockStatic(IOUtils.class);
		
		final byte[] out = "file content".getBytes();
		final byte[] content = "file content".getBytes();
		final UploadFile image = new UploadFile(content, MediaType.IMAGE_PNG_VALUE);
		
		when(IOUtils.toByteArray(isA(InputStream.class))).thenReturn(out);
				
		ResponseEntity<byte[]> resp = imgService.renderImage(image);
		
		assertNotNull("Response is null", resp);
		assertEquals(HttpStatus.CREATED, resp.getStatusCode());
		assertEquals(true, resp.hasBody());
		assertEquals(out, resp.getBody());
		assertEquals(MediaType.IMAGE_PNG, resp.getHeaders().getContentType());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRenderImagesThrowsAnExceptionIfInputIsNull() throws IOException {
		imgService.renderImage(null);
	}
}
