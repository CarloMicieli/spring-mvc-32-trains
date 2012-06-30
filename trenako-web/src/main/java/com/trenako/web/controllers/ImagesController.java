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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trenako.web.images.WebImageService;

/**
 * It represents the controller to render images.
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/images")
public class ImagesController {

	private final WebImageService imagesService;

	/**
	 * Creates a new {@code ImagesController} to render images.
	 * @param imagesService the service to load images from database
	 * @param imgUtils the image processing class
	 */
	@Autowired
	public ImagesController(WebImageService imagesService) {
		this.imagesService = imagesService;
	}

	@RequestMapping(value = "/brand/{brandId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> renderBrandLogo(@PathVariable("brandId") ObjectId id) throws IOException {
		return imagesService.renderImageFor(id);
	}
}
