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
package com.trenako.utility;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Carlo Micieli
 */
public class Maps {
    private Maps() {
    }

    /**
     * Builds an immutable {@code Map} with the provided single pair of key/value.
     *
     * @param key   the key
     * @param value the value
     * @return a map
     */
    public static <K, V> Map<K, V> map(K key, V value) {
        Map<K, V> m = new HashMap<>(1);
        m.put(key, value);
        return Collections.unmodifiableMap(m);
    }

    /**
     * Builds an immutable {@code Map} with two pairs of key/value.
     *
     * @param key1   the first key
     * @param value1 the first value
     * @param key2   the second key
     * @param value2 the second value
     * @return a map
     */
    public static <K, V> Map<K, V> map(K key1, V value1, K key2, V value2) {
        Map<K, V> m = new HashMap<>(2);
        m.put(key1, value1);
        m.put(key2, value2);
        return Collections.unmodifiableMap(m);
    }
}
