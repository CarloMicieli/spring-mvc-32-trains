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

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.repositories.BrandsRepository;
import com.trenako.repositories.RailwaysRepository;
import com.trenako.repositories.RollingStocksSearchRepository;
import com.trenako.repositories.ScalesRepository;
import com.trenako.results.PaginatedResults;
import com.trenako.results.RangeRequest;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Service("rollingStockSearchService")
public class RollingStocksSearchServiceImpl implements RollingStocksSearchService {

	private final RollingStocksSearchRepository repo;
	private final BrandsRepository brands;
	private final RailwaysRepository railways;
	private final ScalesRepository scales;
	
	/**
	 * Creates a new {@code RollingStockSearchServiceImpl}.
	 * @param repo
	 * @param scales 
	 * @param railways 
	 * @param brands 
	 */
	@Autowired
	public RollingStocksSearchServiceImpl(RollingStocksSearchRepository repo, 
			BrandsRepository brands, 
			RailwaysRepository railways, 
			ScalesRepository scales) {
		this.repo = repo;
		this.brands = brands;
		this.railways = railways;
		this.scales = scales;
	}
	
	@Override
	public PaginatedResults<RollingStock> findByCriteria(SearchCriteria sc, RangeRequest range) {
		return repo.findByCriteria(sc, range);
	}

	@Override
	public SearchCriteria loadSearchCriteria(SearchCriteria sc) {
		if (sc.isEmpty()) return SearchCriteria.immutableSearchCriteria(sc);
				
		return new SearchCriteria.Builder()
			.brand(loadBrand(sc))
			.railway(loadRailway(sc))
			.scale(loadScale(sc))
			.buildImmutable();
	}
	
	private Brand loadBrand(SearchCriteria sc) {
		if (!sc.hasBrand()) return null;
		return brands.findBySlug(sc.getBrand());
	}
	
	private Railway loadRailway(SearchCriteria sc) {
		if (!sc.hasRailway()) return null;
		return railways.findBySlug(sc.getRailway());
	}
	
	private Scale loadScale(SearchCriteria sc) {
		if (!sc.hasScale()) return null;
		return scales.findBySlug(sc.getScale());
	}
}
