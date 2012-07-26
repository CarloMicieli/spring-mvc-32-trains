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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.trenako.criteria.Criteria;
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
	public Iterable<LocalizedEnum<PowerMethod>> powerMethods() {
		return LocalizedEnum.list(PowerMethod.class, messageSource, null);
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
	public PaginatedResults<RollingStock> findByCriteria(SearchRequest sc, RangeRequest range) {
		SearchCriteria searchCriteria = loadSearchCriteria(sc); 
		return repo.findByCriteria(searchCriteria, range);
	}

	private SearchCriteria loadSearchCriteria(SearchRequest sc) {
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

	private <T> T resolveClass(SearchRequest sc, Class<T> criterionType) {
		String key = criterionValue(sc, criterionType);
		if (key == null) {
			return null;
		}
		
		return repo.findBySlug(key, criterionType);
	}
	
	private <T extends Enum<T>> LocalizedEnum<T> resolveEnum(SearchRequest sc, Class<T> criterionType) {
		String key = criterionValue(sc, criterionType);
		if (key == null) {
			return null;
		}
		
		// enum values are resolved without hitting the database 
		return parseSlugAsEnum(key, criterionType);
	}
	
	private <T extends Enum<T>> LocalizedEnum<T> parseSlugAsEnum(String slug, Class<T> criterionType) {
		T enumVal = LocalizedEnum.parseString(slug, criterionType);
		return new LocalizedEnum<T>(enumVal, messageSource, null);
	}

	private <T> String criterionValue(SearchRequest sc, Class<T> criterionType) {
		
		Criteria crit = Criteria.criterionForType(criterionType);
		if (!sc.has(crit)) {
			return null;	
		}

		String key = sc.get(crit);
		if (!StringUtils.hasText(key)) {
			return null;
		}
		
		return key;
	}
}
