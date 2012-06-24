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
package com.trenako.web.controllers;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trenako.entities.Image;
import com.trenako.services.ImagesService;
import com.trenako.web.images.ImageProcessingAdapter;

/**
 * It represents the controller to render images.
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/images")
public class ImagesController {

	private final ImagesService imagesService;
	private final ImageProcessingAdapter imgUtils;
	
	/**
	 * Creates a new {@code ImagesController} to render images.
	 * @param imagesService the service to load images from database
	 * @param imgUtils the image processing class
	 */
	@Autowired
	public ImagesController(ImagesService imagesService, ImageProcessingAdapter imgUtils) {
		this.imagesService = imagesService;
		this.imgUtils = imgUtils;
	}

	@RequestMapping(value = "/brand/{brandId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> renderBrandLogo(@PathVariable("brandId") ObjectId id) throws IOException {
		Image img = imagesService.getBrandImage(id);
		if( img==null ) {
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);	
		}
		
		final ResponseEntity<byte[]> resp = imgUtils.renderImage(img);
		return resp;
	}
}
