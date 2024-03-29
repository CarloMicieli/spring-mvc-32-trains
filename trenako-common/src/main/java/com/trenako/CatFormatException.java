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
package com.trenako;

/**
 * Thrown to indicate that a method has been passed an illegal string argument
 * to be parsed as a {@code Cat}.
 *
 * @author Carlo Micieli
 */
@SuppressWarnings("serial")
public class CatFormatException extends IllegalArgumentException {

    /**
     * Constructs an {@code CatFormatException} with no detail message.
     */
    public CatFormatException() {
        super();
    }

    /**
     * Constructs an {@code CatFormatException} with the specified detail message.
     *
     * @param details the detail message
     */
    public CatFormatException(String details) {
        super(details);
    }
}
