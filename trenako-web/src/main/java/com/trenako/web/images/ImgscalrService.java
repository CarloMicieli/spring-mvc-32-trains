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
import java.util.Map;

import org.imgscalr.Scalr.Mode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.trenako.images.UploadFile;

/**
 * A concrete implementation for the {@link ImageConverter} interface.
 * <p>
 * This class is using the functionality provided by the {@code imgscalr} image processing library.
 * </p>
 *
 * @author Carlo Micieli
 *
 */
@Component
public class ImgscalrService implements ImagesConverter {

	@Override
	public UploadFile createThumbnail(MultipartFile file, Map<String, String> metadata, int targetSize)
			throws IOException {
		
		final BufferedImage image = convertToImage(file);
		final BufferedImage thumb = 
				resize(image, Method.ULTRA_QUALITY, Mode.FIT_TO_HEIGHT, targetSize, OP_ANTIALIAS, OP_BRIGHTER);

		return new UploadFile(
				inputStream(thumb, file.getContentType()), 
				file.getContentType(), 
				file.getOriginalFilename(),
				metadata);
	}

	@Override
	public UploadFile createImage(MultipartFile file, Map<String, String> metadata) throws IOException {
		return new UploadFile(inputStream(file), 
				file.getContentType(), 
				file.getOriginalFilename(),
				metadata);
	}

	// helper methods
		
	private BufferedImage convertToImage(MultipartFile file) throws IOException {		
		InputStream in = new ByteArrayInputStream(file.getBytes());
		return ImageIO.read(in);
	}

	private InputStream inputStream(MultipartFile file) throws IOException {
		return new ByteArrayInputStream(file.getBytes());
	}
	
	private InputStream inputStream(BufferedImage image, String contentType) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, getTypeName(contentType), baos);
		baos.flush();
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		baos.close();
		return is;
	}
	
	private String getTypeName(String contentType) {
		String typeName = "jpg";
		if (contentType.equals(MediaType.IMAGE_PNG)) {
			typeName = "png";
		}
		else if(contentType.equals(MediaType.IMAGE_GIF)) {
			typeName = "gif";
		}
		return typeName;
	}
}
