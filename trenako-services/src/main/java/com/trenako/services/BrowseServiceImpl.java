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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.trenako.criteria.SearchCriteria;
import com.trenako.criteria.SearchRequest;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.repositories.BrowseRepository;
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
@Service("browseService")
public class BrowseServiceImpl implements BrowseService {

	private @Autowired MessageSource messageSource;
	
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
		return LocalizedEnum.list(Era.class, messageSource, null);
	}

	@Override
	public Iterable<LocalizedEnum<Category>> categories() {
		return LocalizedEnum.list(Category.class, messageSource, null);
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
	
	@Override
	public Brand findBrand(String slug) {
		return repo.findBySlug(slug, Brand.class);
	}

	@Override
	public Railway findRailway(String slug) {
		return repo.findBySlug(slug, Railway.class);
	}

	@Override
	public Scale findScale(String slug) {
		return repo.findBySlug(slug, Scale.class);
	}

	@Override
	public LocalizedEnum<Category> findCategory(String slug) {
		try {
			return parseSlugAsEnum(slug, Category.class);
		}
		catch (IllegalArgumentException ex) {
			// suppress the exception to have the same
			// behavior as the other service methods
			return null;
		}
	}

	@Override
	public LocalizedEnum<Era> findEra(String slug) {
		try {
			return parseSlugAsEnum(slug, Era.class);
		}
		catch (IllegalArgumentException ex) {
			// suppress the exception to have the same
			// behavior as the other service methods
			return null;
		}
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
		return parseSlugAsEnum(criterionPair.getKey(), criterionType);
	}
	
	private <T extends Enum<T>> LocalizedEnum<T> parseSlugAsEnum(String slug, Class<T> criterionType) {
		T enumVal = LocalizedEnum.parseString(slug, criterionType);
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

	@Override
	public PaginatedResults<RollingStock> findByCriteria(SearchRequest sc,
			RangeRequest range) {
		// TODO Auto-generated method stub
		return null;
	}
}
