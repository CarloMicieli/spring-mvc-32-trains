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
package com.trenako.images;

import java.io.InputStream;
import java.util.Map;

/**
 * This class represents an immutable container for uploaded files.
 *
 * @author Carlo Micieli
 */
public class UploadFile {
    private final InputStream content;
    private final String contentType;
    private final String filename;
    private final Map<String, String> metadata;

    /**
     * Creates a new {@code UploadFile}.
     *
     * @param content     the file content stream
     * @param contentType the file content type
     * @param filename    the filename
     * @param metadata    the file metadata
     */
    public UploadFile(InputStream content,
                      String contentType,
                      String filename,
                      Map<String, String> metadata) {
        this.content = content;
        this.contentType = contentType;
        this.filename = filename;
        this.metadata = metadata;
    }

    /**
     * Returns the {@code UploadFile} content.
     *
     * @return the content
     */
    public InputStream getContent() {
        return content;
    }

    /**
     * Returns the {@code UploadFile} content type.
     *
     * @return the content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Returns the {@code UploadFile} filename.
     *
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Returns the {@code UploadFile} metadata.
     *
     * @return the metadata
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof UploadFile)) return false;

        UploadFile other = (UploadFile) obj;
        return this.content.equals(other.content) &&
                this.filename.equals(other.filename) &&
                this.metadata.equals(other.metadata);
    }
}
