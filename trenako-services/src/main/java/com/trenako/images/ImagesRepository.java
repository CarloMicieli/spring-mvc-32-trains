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
package com.trenako.images;

import com.trenako.images.UploadFile;
import com.mongodb.gridfs.GridFSDBFile;

/**
 * The interface for Mongo GridFs repository.
 * @author Carlo Micieli
 *
 */
public interface ImagesRepository {
	/**
	 * Returns the file with the provided slug.
	 * @param slug the slug
	 * @return a {@code GridFSDBFile} if found; {@code null} otherwise
	 */
	GridFSDBFile findFileBySlug(String slug);

	/**
	 * Stores the uploaded file.
	 * @param file the uploaded file
	 */
	void store(UploadFile file);

	/**
	 * Deletes the uploaded file.
	 * @param slug the slug
	 */
	void delete(String slug);
}