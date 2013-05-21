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
package com.trenako.activities;

import com.trenako.entities.Collection;
import com.trenako.entities.WishList;

/**
 * @author Carlo Micieli
 */
public class ActivityContext {

    public static final String COLLECTION = "collection";
    public static final String WISH_LIST = "wishList";

    private String contextType;
    private String name;
    private String description;

    /**
     * Creates an empty {@code ActivityContext}.
     */
    public ActivityContext() {
    }

    /**
     * Creates a new {@code ActivityContext}.
     *
     * @param contextType the context type
     * @param name        the name
     * @param description the description
     */
    public ActivityContext(String contextType, String name, String description) {
        this.contextType = contextType;
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the {@code ActivityContext} type.
     *
     * @return the type
     */
    public String getContextType() {
        return contextType;
    }

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ActivityContext)) return false;

        ActivityContext other = (ActivityContext) obj;
        return this.contextType.equals(other.contextType) &&
                this.name.equals(other.name) &&
                this.description.equals(other.description);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("activityContext{type: ")
                .append(getContextType())
                .append(", name: ")
                .append(getName())
                .append(", description: ")
                .append(getDescription())
                .append("}")
                .toString();
    }


    /**
     * Creates a new {@code ActivityContext} for the provided {@code Collection}.
     *
     * @return a new {@code ActivityContext}
     */
    public static ActivityContext collectionContext(Collection c) {
        return new ActivityContext(COLLECTION, c.getSlug(), "");
    }

    /**
     * Creates a new {@code ActivityContext} for the provided {@code WishList}.
     *
     * @return a new {@code ActivityContext}
     */
    public static ActivityContext wishListContext(WishList w) {
        return new ActivityContext(WISH_LIST, w.getSlug(), w.getName());
    }

}
