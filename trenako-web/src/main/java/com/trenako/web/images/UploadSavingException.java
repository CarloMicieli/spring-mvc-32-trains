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

import org.springframework.core.NestedRuntimeException;

/**
 * It represents a not checked exception during file upload saving operations.
 *
 * @author Carlo Micieli
 */
@SuppressWarnings("serial")
public class UploadSavingException extends NestedRuntimeException {

    /**
     * Creates a new {@code UploadSavingException}.
     *
     * @param msg the exception message
     */
    public UploadSavingException(String msg) {
        super(msg);
    }

    /**
     * Creates a new {@code UploadSavingException}.
     *
     * @param msg   the exception message
     * @param cause the nested exception
     */
    public UploadSavingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
