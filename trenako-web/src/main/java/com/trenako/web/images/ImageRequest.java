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
package com.trenako.web.images;

import java.util.Map;

import com.trenako.mapping.DbReferenceable;

import static com.trenako.utility.Maps.*;

/**
 * It represents a container class used to select images for web requests.
 *
 * @author Carlo Micieli
 */
public class ImageRequest {
    private String entityName;
    private String slug;

    /**
     * Creates an empty {@code ImageRequest}.
     */
    public ImageRequest() {
    }

    /**
     * Creates a new {@code ImageRequest}.
     *
     * @param entityName the entity name
     * @param slug       the entity slug
     */
    public ImageRequest(String entityName, String slug) {
        this.entityName = entityName;
        this.slug = slug;
    }

    /**
     * Creates a new {@code ImageRequest} for the provided {@code obj}.
     *
     * @param obj the object
     * @return a new {@code ImageRequest}
     */
    public static <E extends DbReferenceable> ImageRequest create(E obj) {
        return new ImageRequest(obj.getClass().getSimpleName().toLowerCase(), obj.getSlug());
    }

    /**
     * Sets the entity name.
     *
     * @param entityName the entity name
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     * Returns the entity name.
     *
     * @return the entity name
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * Sets the entity slug.
     *
     * @param slug the entity slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * Returns the entity slug.
     *
     * @return the entity slug
     */
    public String getSlug() {
        return slug;
    }

    /**
     * Builds the image filename for the current {@code ImageRequest}.
     *
     * @return the filename
     */
    public String getFilename() {
        return filename(false);
    }

    /**
     * Builds the thumbnail filename for the current {@code ImageRequest}.
     *
     * @return the thumbnail filename
     */
    public String getThumbFilename() {
        return filename(true);
    }

    /**
     * Returns the metadata for the current {@code ImageRequest}.
     *
     * @param isThumb {@code true} for thumbnail images; {@code false} otherwise
     * @return the file metadata
     */
    public Map<String, String> asMetadata(boolean isThumb) {
        return map("slug", filename(isThumb));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ImageRequest)) return false;

        ImageRequest other = (ImageRequest) obj;
        return this.entityName.equals(other.entityName) &&
                this.slug.equals(other.slug);
    }

    private String filename(boolean isThumb) {
        StringBuilder sb = new StringBuilder();

        if (isThumb) {
            sb.append("th_");
        }

        return sb.append(getEntityName())
                .append("_")
                .append(getSlug())
                .toString();
    }
}
