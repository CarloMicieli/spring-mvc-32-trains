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

import org.springframework.http.ResponseEntity;

/**
 * The interface for the upload images service for web requests.
 * @author Carlo Micieli
 *
 */
public interface WebImageService {

	/**
	 * Saves the uploaded image to the database.
	 * <p>
	 * To create also the thumbnail the clients have to call the 
	 * {@link WebImageService#saveImageWithThumb(UploadRequest)} 
	 * method instead. 
	 * </p>
	 * 
	 * @param req the file upload request
	 */
	void saveImage(UploadRequest req);

	/**
	 * Saves the uploaded image to the database creating the thumbnail too.
	 *
	 * @param req the file upload request
	 * @param size the thumbnail size in pixels
	 */
	void saveImageWithThumb(UploadRequest req, int size);
	
	/**
	 * Renders the image with the provided {@code ImageRequest} 
	 * in the web response.
	 * 
	 * @param imageSlug the image slug
	 * @return a web response entity
	 */
	ResponseEntity<byte[]> renderImage(String imageSlug);

	/**
	 * Deletes a file from the database.
	 * 
	 * @param imageSlug the image slug
	 */
	void deleteImage(ImageRequest req);
}
