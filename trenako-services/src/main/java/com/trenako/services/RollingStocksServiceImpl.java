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

import com.trenako.entities.RollingStock;
import com.trenako.repositories.RollingStocksRepository;

/**
 * A concrete implementation for the {@code RollingStocks} service.
 * @author Carlo Micieli
 *
 */
@Service("rollingStocksService")
public class RollingStocksServiceImpl implements RollingStocksService {
	
	private RollingStocksRepository repo;
	
	@Autowired
	public RollingStocksServiceImpl(RollingStocksRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public RollingStock findById(ObjectId id) {
		return repo.findOne(id);
	}
	
	@Override
	public RollingStock findBySlug(String slug) {
		return repo.findBySlug(slug);
	}
	
	@Override
	public void save(RollingStock rs) {
		repo.save(rs);
	}

	@Override
	public void remove(RollingStock rs) {
		repo.delete(rs);
	}
}
