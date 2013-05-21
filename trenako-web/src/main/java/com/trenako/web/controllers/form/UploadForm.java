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
package com.trenako.web.controllers.form;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.web.multipart.MultipartFile;

import com.trenako.web.images.ImageRequest;
import com.trenako.web.images.UploadRequest;
import com.trenako.web.images.validation.Image;

/**
 * It represents a web form for files upload.
 *
 * @author Carlo Micieli
 */
public class UploadForm {
    @NotNull
    private String entity;

    @NotNull
    private String slug;

    @Image(message = "upload.file.notValid")
    private MultipartFile file;

    /**
     * Creates a new empty {@code UploadForm}.
     */
    public UploadForm() {
    }

    /**
     * Creates a new {@code UploadForm}.
     *
     * @param entity the upload entity name
     * @param slug   the upload slug
     * @param file   the uploaded file
     */
    public UploadForm(String entity, String slug, MultipartFile file) {
        this.entity = entity;
        this.slug = slug;
        this.file = file;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    /**
     * Checks whether the current upload is empty.
     *
     * @return {@code true} if empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        if (file == null) return true;
        return file.isEmpty();
    }

    /**
     * Builds a new {@code ImageRequest} from the current form values.
     *
     * @return a new {@code ImageRequest}
     */
    public ImageRequest buildImageRequest() {
        return new ImageRequest(getEntity(), getSlug());
    }

    /**
     * Builds a new {@code UploadRequest} from the current form values.
     *
     * @return a new {@code UploadRequest}
     */
    public UploadRequest buildUploadRequest() {
        return new UploadRequest(getEntity(), getSlug(), getFile());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof UploadForm)) return false;

        UploadForm other = (UploadForm) obj;
        return new EqualsBuilder()
                .append(this.entity, other.entity)
                .append(this.slug, other.slug)
                .append(this.file, other.file)
                .isEquals();
    }
}