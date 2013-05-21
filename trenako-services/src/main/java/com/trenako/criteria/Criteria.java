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
package com.trenako.criteria;

import java.util.ArrayList;
import java.util.List;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.utility.Cat;
import com.trenako.values.Category;
import com.trenako.values.Era;
import com.trenako.values.PowerMethod;

/**
 * The enum values for rolling stocks criteria.
 *
 * @author Carlo Micieli
 */
public enum Criteria {
    /**
     * The criterion by rolling stocks {@code Brand}.
     */
    BRAND(Brand.class),

    /**
     * The criterion by rolling stocks {@code Scale}.
     */
    SCALE(Scale.class),

    /**
     * The criterion by rolling stocks {@code Cat}.
     */
    CAT(Cat.class),

    /**
     * The criterion by rolling stocks {@code Railway}.
     */
    RAILWAY(Railway.class),

    /**
     * The criterion by rolling stocks {@code Era}.
     */
    ERA(Era.class),

    /**
     * The criterion by rolling stocks {@code PowerMethod}.
     */
    POWER_METHOD(PowerMethod.class),

    /**
     * The criterion by rolling stocks {@code Category}.
     */
    CATEGORY(Category.class);

    private final Class<?> criterionType;

    Criteria(Class<?> type) {
        criterionType = type;
    }

    public Class<?> getCriterionType() {
        return criterionType;
    }

    public static <T> Criteria criterionForType(Class<T> providedType) {
        for (Criteria crit : Criteria.values()) {
            if (crit.criterionType.equals(providedType)) {
                return crit;
            }
        }

        return null;
    }

    public String criterionName() {
        return name().replace("_", "").toLowerCase();
    }

    public static Iterable<String> keys() {
        List<String> keys = new ArrayList<String>();
        for (Criteria crit : Criteria.values()) {
            keys.add(crit.name().replace("_", "").toLowerCase());
        }
        return keys;
    }
}
