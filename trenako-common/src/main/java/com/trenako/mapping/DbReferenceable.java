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
package com.trenako.mapping;

/**
 * The interface implemented by entity classes that takes part in
 * weak database references.
 * <p>
 * Usually the weak references contains two values:
 * <blockquote>
 * <pre>
 * {"slug": "slug-value", "label": "label value"}
 * </pre>
 * </blockquote>
 * </p>
 *
 * @author Carlo Micieli
 */
public interface DbReferenceable {

    /**
     * Returns the {@code slug} for the current entity object.
     *
     * @return the entity {@code slug} value
     */
    String getSlug();

    /**
     * Returns the {@code label} for the current entity object.
     *
     * @return the entity {@code label} value
     */
    String getLabel();
}
