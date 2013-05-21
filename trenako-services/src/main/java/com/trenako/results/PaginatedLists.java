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
package com.trenako.results;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

/**
 * It represents an utility class to produce paginated partition of {@code List}.
 *
 * @author Carlo Micieli
 */
public class PaginatedLists {
    /**
     * Returns a values page according the provided {@code Pageable}.
     *
     * @param values   the values to be paginated
     * @param paging the paging information
     * @return a {@code Page}
     */
    public static <T> Page<T> paginate(List<T> values, Pageable paging) {
        Assert.notNull(values, "Values list must be not null");

        int total = values.size();
        int offset = paging.getOffset();

        if (offset > total) {
            return failbackPage();
        }

        int sIndex = start(total, offset);
        int eIndex = end(total, offset, paging.getPageSize());

        List<T> content = values.subList(sIndex, eIndex);
        return new PageImpl<>(content, paging, total);
    }

    private static <T> Page<T> failbackPage() {
        List<T> empty = Collections.emptyList();
        return new PageImpl<>(empty);
    }

    private static int start(int total, int offset) {
        return (offset > total) ? total : offset;
    }

    private static int end(int total, int offset, int pageSize) {
        return (offset + pageSize > total) ? total : offset + pageSize;
    }
}
