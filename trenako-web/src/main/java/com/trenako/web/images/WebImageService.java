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

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * The interface for the upload images service for web requests.
 * @author Carlo Micieli
 *
 */
public interface WebImageService {

	/**
	 * Saves the uploaded image to the database.
	 * <p>
	 * To create also the thumbnail the clients have to call 
	 * the {@link WebImageService#saveImageWithThumb(ObjectId, MultipartFile, int)} method
	 * instead. 
	 * </p>
	 * 
	 * @param parentId
	 * @param file
	 */
	void saveImage(ObjectId parentId, MultipartFile file);

	/**
	 * 
	 * @param parentId
	 * @param file
	 * @param size
	 */
	void saveImageWithThumb(ObjectId parentId, MultipartFile file, int size);
	
	/**
	 * Renders the original image for a web request.
	 * 
	 * @param parentId
	 * @return
	 */
	ResponseEntity<byte[]> renderImageFor(ObjectId parentId);
	
	/**
	 * 
	 * @param parentId
	 * @return
	 */
	ResponseEntity<byte[]> renderThumbnailFor(ObjectId parentId);
}
