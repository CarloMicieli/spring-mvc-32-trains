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
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.trenako.Category;
import com.trenako.Era;
import com.trenako.PowerMethod;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.repositories.BrandsRepository;
import com.trenako.repositories.RailwaysRepository;
import com.trenako.repositories.RollingStocksRepository;
import com.trenako.repositories.ScalesRepository;

/**
 * A concrete implementation for the {@code RollingStocks} service.
 * @author Carlo Micieli
 *
 */
@Service("rollingStocksService")
public class RollingStocksServiceImpl implements RollingStocksService {
	
	private final RollingStocksRepository rollingStocks;
	private final BrandsRepository brands;
	private final ScalesRepository scales;
	private final RailwaysRepository railways;
	
	/**
	 * Creates a {@code RollingStocksServiceImpl}
	 * @param rollingStocks the {@code RollingStock} repository
	 * @param brands the {@code Brand} repository
	 * @param railways the {@code Railway} repository
	 * @param scales the {@code Scale} repository
	 */
	@Autowired
	public RollingStocksServiceImpl(RollingStocksRepository rollingStocks,
			BrandsRepository brands,
			RailwaysRepository railways,
			ScalesRepository scales) {
		this.rollingStocks = rollingStocks;
		this.brands = brands;
		this.scales = scales;
		this.railways = railways;
	}
	
	@Override
	public RollingStock findById(ObjectId id) {
		return rollingStocks.findOne(id);
	}
	
	@Override
	public RollingStock findBySlug(String slug) {
		return rollingStocks.findBySlug(slug);
	}
	
	@Override
	public void save(RollingStock rs) {
		rollingStocks.save(rs);
	}

	@Override
	public void remove(RollingStock rs) {
		rollingStocks.delete(rs);
	}
	
	@Override
	public Iterable<Brand> brands() {
		return brands.findAll(new Sort(Direction.ASC, "name"));
	}

	@Override
	public Iterable<Railway> railways() {
		return railways.findAll(new Sort(Direction.ASC, "name"));
	}

	@Override
	public Iterable<Scale> scales() {
		return scales.findAll(new Sort(Direction.DESC, "ratio"));
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

	@Override
	public Brand findBrand(ObjectId brandId) {
		return brands.findOne(brandId);
	}

	@Override
	public Scale findScale(ObjectId scaleId) {
		return scales.findOne(scaleId);
	}

	@Override
	public Railway findRailway(ObjectId railwayId) {
		return railways.findOne(railwayId);
	}
}
