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
package com.trenako.values;

import org.apache.commons.lang3.StringUtils;

/**
 * It represents the list of {@code Priority} values.
 *
 * @author Carlo Micieli
 */
public enum Priority {
    /**
     * Priority HIGH.
     */
    HIGH,

    /**
     * Priority NORMAL.
     */
    NORMAL,

    /**
     * Priority LOW.
     */
    LOW;

    /**
     * Gets the {@code Priority} label
     *
     * @return the label
     */
    public String label() {
        return LocalizedEnum.buildKey(this);
    }

    /**
     * Parses the string argument as a {@code Priority}.
     *
     * @param s the string to be parsed
     * @return a {@code Priority} value
     */
    public static Priority parse(String s) {
        return LocalizedEnum.parseString(s, Priority.class);
    }

    /**
     * Parses the string argument as a {@code Priority}.
     *
     * @param prio        the string to be parsed
     * @param defaultPrio the default {@code Priority} value
     * @return a {@code Priority} value
     */
    public static Priority parse(String prio, Priority defaultPrio) {
        if (StringUtils.isBlank(prio)) {
            return defaultPrio;
        }
        return LocalizedEnum.parseString(prio, Priority.class);
    }
}
