/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.trenako.utility;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * It converts a string to a "slug".
 *
 * @see <a href="http://www.codecodex.com/wiki/Generate_a_url_slug#Java">Original implementation</a>
 */
public class Slug {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    private Slug() {
    }

    /**
     * Encodes the provided String as {@code slug}.
     *
     * @param input the String to be encoded
     * @return the slug
     */
    public static String encode(String input) {
        Assert.notNull(input, "The input string must be not null");

        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    /**
     * Encodes the provided Date as {@code slug}.
     *
     * @param date the date to be encoded
     * @return the slug
     */
    public static String encode(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new StringBuilder(sdf.format(date)).toString();
    }

    public static String encodeFull(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
        return new StringBuilder(sdf.format(date)).toString();
    }
}