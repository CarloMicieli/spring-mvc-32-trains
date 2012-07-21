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

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.repositories.RollingStocksSearchRepository;
import com.trenako.results.PaginatedResults;
import com.trenako.results.RangeRequest;
import com.trenako.values.Category;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Service("rollingStockSearchService")
public class RollingStocksSearchServiceImpl implements RollingStocksSearchService {

	private final RollingStocksSearchRepository repo;
	
	/**
	 * Creates a new {@code RollingStockSearchServiceImpl}.
	 * @param repo
	 */
	@Autowired
	public RollingStocksSearchServiceImpl(RollingStocksSearchRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public PaginatedResults<RollingStock> findByCriteria(SearchCriteria sc, RangeRequest range) {
		return repo.findByCriteria(sc, range);
	}

	@Override
	public SearchCriteria loadSearchCriteria(SearchCriteria sc) {
		if (sc.isEmpty()) return SearchCriteria.EMPTY;
				
		return new SearchCriteria.Builder()
			.brand(resolveClass(sc, Brand.class))
			.railway(resolveClass(sc, Railway.class))
			.scale(resolveClass(sc, Scale.class))
			.era(resolveEnum(sc, Era.class))
			.category(resolveEnum(sc, Category.class))
			.powerMethod(resolveEnum(sc, PowerMethod.class))
			.buildImmutable();
	}

	private <T> T resolveClass(SearchCriteria sc, Class<T> criterionType) {
		Pair<String, String> criterionPair = criterionValue(sc, criterionType);
		if (criterionPair == null) {
			return null;
		}
		
		return repo.findBySlug(criterionPair.getKey(), criterionType);
	}
	
	private <T extends Enum<T>> LocalizedEnum<T> resolveEnum(SearchCriteria sc, Class<T> criterionType) {
		Pair<String, String> criterionPair = criterionValue(sc, criterionType);
		if (criterionPair == null) {
			return null;
		}
		
		// enum values are resolved without accessing the db 
		T enumVal = LocalizedEnum.parseString(criterionPair.getKey(), criterionType);
		return new LocalizedEnum<T>(enumVal);
	}

	private <T> Pair<String, String> criterionValue(SearchCriteria sc, Class<T> criterionType) {
		if (!sc.has(criterionType)) {
			return null;	
		}

		Pair<String, String> criterionPair = sc.get(criterionType);
		if (criterionPair == null || !StringUtils.hasText(criterionPair.getKey())) {
			return null;
		}
		
		return criterionPair;
	}
}
