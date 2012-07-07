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

import com.trenako.Category;
import com.trenako.Era;
import com.trenako.PowerMethod;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.repositories.SelectOptionsRepository;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Service("selectOptionsService")
public class SelectOptionsServiceImpl implements SelectOptionsService {

	private final SelectOptionsRepository repo;
	
	@Autowired
	public SelectOptionsServiceImpl(SelectOptionsRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public Iterable<Brand> brands() {
		return repo.getBrands();
	}

	@Override
	public Iterable<Railway> railways() {
		return repo.getRailways();
	}

	@Override
	public Iterable<Scale> scales() {
		return repo.getScales();
	}

	@Override
	public Iterable<String> categories() {
		return Category.list();
	}

	@Override
	public Iterable<String> eras() {
		return Era.list();
	}

	@Override
	public Iterable<String> powerMethods() {
		return PowerMethod.list();
	}

}
