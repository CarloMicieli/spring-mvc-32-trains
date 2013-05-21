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

/**
 * It represents the rolling stock condition.
 *
 * @author Carlo Micieli
 */
public enum Condition {
    /**
     * New condition.
     */
    NEW,

    /**
     * Pre owned.
     */
    PRE_OWNED;

    /**
     * Gets the {@code Condition} label
     *
     * @return the label
     */
    public String label() {
        return LocalizedEnum.buildKey(this);
    }

    /**
     * Parses the string argument as a {@code Condition}.
     *
     * @param s the string to be parsed
     * @return a {@code Condition} value
     */
    public static Condition parse(String s) {
        return LocalizedEnum.parseString(s, Condition.class);
    }
}
