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

import static org.imgscalr.Scalr.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr.Mode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * A concrete implementation for the {@link ImagesService} interface.
 * <p>
 * This class is using the functionality provided by the {@code imgscalr} image processing library.
 * </p>
 * @author Carlo Micieli
 *
 */
public class ImgscalrService implements ImagesService {

	/**
	 * The list of {@link MediaType}s allowed by the application for uploads operations.
	 */
	public final static List<MediaType> SUPPORTED_MEDIA_TYPES =
			Collections.unmodifiableList(Arrays.asList(MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG));
	
	@Override
	public byte[] createThumbnail(MultipartFile file, int targetSize)
			throws IOException {
		
		validateFile(file);
		
		final BufferedImage image = convertToImage(file);
		final BufferedImage thumb = pad(
				resize(image, Method.SPEED, Mode.FIT_TO_HEIGHT, targetSize, OP_ANTIALIAS, OP_BRIGHTER), 2);

		return convertToArray(thumb, file.getContentType());
	}

	@Override
	public byte[] convertToBytes(MultipartFile file) throws IOException {
		validateFile(file);
		return file.getBytes();
	}

	@Override
	public ResponseEntity<byte[]> renderImage(byte[] image, MediaType mediaType) throws IOException {
		if( image==null )
			throw new IllegalArgumentException("Input stream is null");
		
		InputStream in = new ByteArrayInputStream(image);

	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(mediaType);
	    
	    return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
	}
	
	// helper methods
	
	private void validateFile(MultipartFile file) throws IOException {
		MediaType contentType = MediaType.parseMediaType(file.getContentType());
		
		if (!SUPPORTED_MEDIA_TYPES.contains(contentType))
			throw new IllegalArgumentException("Invalid media type");
	}

	private BufferedImage convertToImage(MultipartFile file) throws IOException {		
		InputStream in = new ByteArrayInputStream(file.getBytes());
		return ImageIO.read(in);
	}

	private byte[] convertToArray(BufferedImage image, String contentType) throws IOException {
		byte[] imageInByte;

		String typeName = "jpg";
		if (contentType.equals(MediaType.IMAGE_PNG))
			typeName = "png";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, typeName, baos);
		baos.flush();
		imageInByte = baos.toByteArray();
		baos.close();

		return imageInByte;
	}
}
