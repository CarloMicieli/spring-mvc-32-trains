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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.repositories.BrowseRepository;
import com.trenako.values.Category;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Service("browseService")
public class BrowseServiceImpl implements BrowseService {

	private final BrowseRepository repo;
	
	/**
	 * Creates a new {@code BrowseServiceImpl}.
	 * @param repo the repository
	 */
	@Autowired
	public BrowseServiceImpl(BrowseRepository repo) {
		this.repo = repo;
	}

	@Override
	public Iterable<LocalizedEnum<Era>> eras() {
		return LocalizedEnum.list(Era.class);
	}

	@Override
	public Iterable<LocalizedEnum<Category>> categories() {
		return LocalizedEnum.list(Category.class);
	}
	
	@Override
	public Iterable<Scale> scales() {
		return repo.getScales();
	}

	@Override
	public Iterable<Railway> railways() {
		return repo.getRailways();
	}

	@Override
	public Iterable<Brand> brands() {
		return repo.getBrands();
	}
}
