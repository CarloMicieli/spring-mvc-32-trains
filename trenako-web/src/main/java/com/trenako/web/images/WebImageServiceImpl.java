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

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trenako.entities.Image;
import com.trenako.entities.UploadFile;
import com.trenako.services.ImagesService;
import com.trenako.web.errors.NotFoundException;

/**
 * This is class provide a concrete implementation for the {@code WebImageService}
 * interface.
 * 
 * <p>
 * <ul>
 * <li>{@link ImagesService}: to persist images to the database;</li>
 * <li>{@link ImageConverter} to convert images from and to {@link MultipartFile}.</li>
 * </ul> 
 * </p>
 * 
 * @author Carlo Micieli
 *
 */
@Service("webImageService")
public class WebImageServiceImpl implements WebImageService {
	
	private final ImagesService db;
	private final ImageConverter converter;
	
	@Autowired
	public WebImageServiceImpl(ImagesService db, ImageConverter converter) {
		this.converter = converter;
		this.db = db;
	}
	
	@Override
	public void saveImage(ObjectId parentId, MultipartFile file) {
		try {
			final UploadFile uploadFile = converter.createImage(file);
			final Image image = new Image.Builder(parentId, uploadFile)
				.filename(file.getOriginalFilename())
				.build();
			db.saveImage(parentId, image);
		} catch (IOException ex) {
			throw new ImageConversionException("Upload exception");
		}
	}

	@Override
	public void saveImageWithThumb(ObjectId parentId, MultipartFile file, int size) {
		try {
			final UploadFile uploadFile = converter.createImage(file);
			final UploadFile thumbnail = converter.createThumbnail(file, size);
			final Image image = new Image.Builder(parentId, uploadFile)
				.thumbnail(thumbnail)
				.filename(file.getOriginalFilename())
				.build();
			db.saveImage(parentId, image);
		} catch (IOException ex) {
			throw new ImageConversionException("Upload exception");
		}	
	}
	
	@Override
	public ResponseEntity<byte[]> renderImageFor(ObjectId parentId) {
		Image img = db.getImage(parentId);
		if (img==null)
			throw new NotFoundException();
		
		try {
			return converter.renderImage(img.getImage());
		} catch (IOException ex) {
			throw new ImageConversionException("Upload exception");
		}
	}

	@Override
	public ResponseEntity<byte[]> renderThumbnailFor(ObjectId parentId) {
		Image img = db.getImage(parentId);
		if (img==null)
			throw new NotFoundException();
		
		try {
			return converter.renderImage(img.getThumbnail());
		} catch (IOException ex) {
			throw new ImageConversionException("Upload exception");
		}
	}
}
