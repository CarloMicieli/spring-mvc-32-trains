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
package com.trenako.repositories.mongo;

import static org.springframework.data.mongodb.core.query.Query.*;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.trenako.results.RangeRequest;

/**
 * It represents a {@code Query} builder for range requests.
 *
 * @author Carlo Micieli
 */
public class RollingStockQueryBuilder {

    /**
     * Builds a new query applying the range request to the
     * provided selection criteria.
     *
     * @param criteria the selection {@code Criteria}
     * @param range    the range information
     * @return a {@code Query}
     */
    public static Query buildQuery(Criteria criteria, RangeRequest range) {
        String prop = range.getSortProperty();

        if (range.getSince() != null && range.getMax() != null) {
            criteria.and(prop).lt(range.getSince()).gt(range.getMax());
        } else if (range.getSince() != null) {
            criteria.and(prop).lt(range.getSince());
        } else if (range.getMax() != null) {
            criteria.and(prop).gt(range.getMax());
        }

        final Query q = query(criteria);
        q.limit(range.getSize() + 1);
        q.with(range.getSort());

        return q;
    }
}
