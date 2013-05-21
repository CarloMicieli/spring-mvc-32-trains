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
package com.trenako.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for dates.
 *
 * @author Carlo Micieli
 *
 */
public class DateBuilder {

    private DateBuilder() {
        throw new UnsupportedOperationException();
    }

    public static Date now() {
        return new Date();
    }

    public static Date date(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return format.parse(date);
        }
        catch (ParseException pe) {
            return new Date();
        }
    }

    public static Date fulldate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        try {
            return format.parse(date);
        }
        catch (ParseException pe) {
            return new Date();
        }
    }

}
