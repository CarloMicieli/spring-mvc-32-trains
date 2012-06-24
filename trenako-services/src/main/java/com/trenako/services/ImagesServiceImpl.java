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
package com.trenako.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenako.entities.Image;
import com.trenako.repositories.ImagesRepository;

/**
 * The concrete implementation for the {@code ImagesService}.
 * @author Carlo Micieli
 *
 */
@Service("imagesService")
public class ImagesServiceImpl implements ImagesService {

	private final ImagesRepository repo;
	
	@Autowired
	public ImagesServiceImpl(ImagesRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Image getBrandImage(ObjectId brandId) {
		return repo.findBrandImage(brandId);
	}

}