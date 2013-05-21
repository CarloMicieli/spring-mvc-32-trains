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
package com.trenako.repositories;

import com.trenako.criteria.SearchCriteria;
import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.results.PaginatedResults;
import com.trenako.results.RangeRequest;

/**
 * It represents the repository for the rolling stocks browsing.
 *
 * @author Carlo Micieli
 */
public interface BrowseRepository {

    /**
     * Returns the list of {@code Brand}s.
     *
     * @return a list of {@code Brand}s
     */
    Iterable<Brand> getBrands();

    /**
     * Returns the list of {@code Scale}s.
     *
     * @return a list of {@code Scale}s
     */
    Iterable<Scale> getScales();

    /**
     * Returns the list of {@code Railway}s.
     *
     * @return a list of {@code Railway}s
     */
    Iterable<Railway> getRailways();

    /**
     * Returns the {@code RollingStock} list according the provided search criteria.
     *
     * @param sc    the search criteria
     * @param range the {@code RangeRequest} information
     * @return a {@code RollingStock} list
     */
    PaginatedResults<RollingStock> findByCriteria(SearchCriteria sc, RangeRequest range);

    /**
     * Returns the {@code RollingStock} list with the provided tag.
     *
     * @param tag   the tag value
     * @param range the {@code RangeRequest} information
     * @return a {@code RollingStock} list
     */
    PaginatedResults<RollingStock> findByTag(String tag, RangeRequest range);

    /**
     * Returns the entity with the provided {@code slug}.
     *
     * @param slug         the entity slug
     * @param providedType the entity type
     * @return the entity if found; {@code null} otherwise
     */
    <T> T findBySlug(String slug, Class<T> providedType);
}
