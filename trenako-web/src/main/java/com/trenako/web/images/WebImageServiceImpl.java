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

import java.io.InputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.mongodb.gridfs.GridFSDBFile;

import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trenako.images.ImagesRepository;
import com.trenako.web.errors.NotFoundException;

/**
 * This is class provide a concrete implementation for the {@code WebImageService}
 * interface.
 * <p/>
 * <p>
 * <ul>
 * <li>{@link ImagesRepository}: to load/store images to the database;</li>
 * <li>{@link ImagesConverter} to convert images from and to {@link MultipartFile}.</li>
 * </ul>
 * </p>
 *
 * @author Carlo Micieli
 */
@Service("webImageService")
public class WebImageServiceImpl implements WebImageService {

    private final ImagesRepository repo;
    private final ImagesConverter converter;

    /**
     * Creates a new {@code WebImageServiceImpl}.
     *
     * @param repo      the images repository
     * @param converter the images converter
     */
    @Autowired
    public WebImageServiceImpl(ImagesRepository repo, ImagesConverter converter) {
        this.converter = converter;
        this.repo = repo;
    }

    @Override
    public void saveImage(UploadRequest req) {
        Assert.notNull(req, "Upload request must be not null");

        try {
            repo.store(converter.createImage(req.getFile(), req.asMetadata(false)));
        } catch (IOException ioEx) {
            throw new UploadSavingException("Error occurred uploading the file.", ioEx);
        }
    }

    @Override
    public void saveImageWithThumb(UploadRequest req, int size) {
        Assert.notNull(req, "Upload request must be not null");
        Assert.isTrue(size > 0, "Thumbnail size must be positive");

        try {
            repo.store(converter.createImage(req.getFile(), req.asMetadata(false)));
            repo.store(converter.createThumbnail(req.getFile(), req.asMetadata(true), size));
        } catch (IOException ioEx) {
            throw new UploadSavingException("Error occurred uploading the file.", ioEx);
        }
    }

    @Override
    public ResponseEntity<byte[]> renderImage(String imageSlug) {
        GridFSDBFile img = repo.findFileBySlug(imageSlug);
        if (img == null) {
            throw new NotFoundException();
        }

        InputStream in = img.getInputStream();
        MediaType mediaType = parse(img.getContentType());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        try {
            return new ResponseEntity<byte[]>(
                    IOUtils.toByteArray(in),
                    headers,
                    HttpStatus.CREATED);
        } catch (IOException ioEx) {
            throw new UploadRenderingException("Error occurred rendering the file.", ioEx);
        }
    }

    @Override
    public void deleteImage(ImageRequest req) {
        repo.delete(req.getFilename());
        repo.delete(req.getThumbFilename());
    }

    // helper methods

    private MediaType parse(String mediaType) {
        return MediaType.parseMediaType(mediaType);
    }
}
