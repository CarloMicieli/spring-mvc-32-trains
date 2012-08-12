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

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.apache.log4j.Logger;

import com.trenako.web.images.UploadRenderingException;
import com.trenako.web.images.WebImageService;

/**
 * It represents the controller used for images rendering.
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/images")
public class ImagesController {

	private static final Logger log = Logger.getLogger("com.trenako.web");
	private final WebImageService imgService;

	/**
	 * Creates a new {@code ImagesController}.
	 * @param imgService the image service
	 */
	@Autowired
	public ImagesController(WebImageService imgService) {
		this.imgService = imgService;
	}

	@RequestMapping(value = "/{imageSlug}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> renderImage(@PathVariable("imageSlug") String imageSlug) {
		return imgService.renderImage(imageSlug);
	}
	
	@ExceptionHandler(UploadRenderingException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public void handleIOException(UploadRenderingException ex, HttpServletResponse response) {
		log.error(ex.toString());
	}
}
