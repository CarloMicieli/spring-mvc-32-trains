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

import static com.trenako.utility.Maps.map;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.trenako.mapping.DbReferenceable;

/**
 * It represents a web request for a file upload.
 *
 * @author Carlo Micieli
 */
public class UploadRequest {
    private String entity;
    private String slug;
    private MultipartFile file;

    /**
     * Creates an empty {@code UploadRequest}.
     */
    public UploadRequest() {
    }

    public static <E extends DbReferenceable> UploadRequest create(E obj, MultipartFile file) {
        return new UploadRequest(obj.getClass().getSimpleName().toLowerCase(),
                obj.getSlug(),
                file);
    }

    /**
     * Creates an empty {@code UploadRequest}.
     *
     * @param entity the entity name
     * @param slug   the slug
     * @param file   the uploaded file
     */
    public UploadRequest(String entity, String slug, MultipartFile file) {
        this.entity = entity;
        this.slug = slug;
        this.file = file;
    }

    public String getEntity() {
        return entity;
    }

    public String getSlug() {
        return slug;
    }

    public MultipartFile getFile() {
        return file;
    }

    public Map<String, String> asMetadata(boolean isThumb) {
        return map("slug", filename(isThumb));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof UploadRequest)) return false;

        UploadRequest other = (UploadRequest) obj;
        return this.entity.equals(other.entity) &&
                this.slug.equals(other.slug);
    }

    private String filename(boolean isThumb) {
        StringBuilder sb = new StringBuilder();

        if (isThumb) {
            sb.append("th_");
        }

        return sb.append(getEntity())
                .append("_")
                .append(getSlug())
                .toString();
    }
}