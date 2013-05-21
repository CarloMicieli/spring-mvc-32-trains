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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.trenako.images.UploadFile;

/**
 * @author Carlo Micieli
 */
@Component
public class ThumbnailatorService implements ImagesConverter {

    @Override
    public UploadFile createThumbnail(MultipartFile file, Map<String, String> metadata, int targetSize) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Thumbnails.of(inputStream(file))
                .height(targetSize)
                .outputQuality(0.8d)
                .toOutputStream(baos);

        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        baos.close();
        return new UploadFile(is,
                file.getContentType(),
                file.getOriginalFilename(),
                metadata);
    }

    @Override
    public UploadFile createImage(MultipartFile file, Map<String, String> metadata) throws IOException {
        return new UploadFile(inputStream(file),
                file.getContentType(),
                file.getOriginalFilename(),
                metadata);
    }

    private InputStream inputStream(MultipartFile file) throws IOException {
        return new ByteArrayInputStream(file.getBytes());
    }
}
