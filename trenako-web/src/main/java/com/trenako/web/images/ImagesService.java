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

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * The service for the uploaded picture management.
 * 
 * @author Carlo Micieli
 *
 */
public interface ImagesService {
	/**
	 * Creates a thumbnail for a received {@link MultipartFile} from a web request.
	 * 
	 * @param file the received {@code MultipartFile}
	 * @param targetSize the target size in pixel
	 * @return the bytes array for the thumbnail
	 * @throws IOException if an I/O exception of some sort has occurred
	 */
	byte[] createThumbnail(MultipartFile file, int targetSize) throws IOException;
	
	/**
	 * Converts a received {@link MultipartFile} from a web request to a bytes array.
	 * 
	 * @param file the received {@code MultipartFile}
	 * @return the bytes array for the picture
	 * @throws IOException if an I/O exception of some sort has occurred
	 */
	byte[] convertToBytes(MultipartFile file) throws IOException;
	
	/**
	 * Renders the image to the HTTP response stream.
	 * 
	 * @param image the image to be rendered
	 * @param mediaType the image media type
	 * @return the output HTTP response
	 * @throws IOException if an I/O exception of some sort has occurred
	 */
	ResponseEntity<byte[]> renderImage(byte[] image, MediaType mediaType) throws IOException;
}
