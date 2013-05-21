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
package com.trenako.values;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.trenako.DeliveryDateFormatException;
import com.trenako.utility.Utils;

/**
 * It represents a delivery date for a rolling stock.
 * <p/>
 * The delivery date has two parts: the {@code year} (required) and
 * the {@code quarter} (optional).
 *
 * @author Carlo Micieli
 */
public class DeliveryDate {

    private static final String QUARTER_PREFIX = "Q";
    private static final List<Integer> QUARTERS =
            Collections.unmodifiableList(Arrays.asList(1, 2, 3, 4));

    private int year;
    private int quarter;

    /**
     * Creates an empty {@code DeliveryDate}.
     */
    public DeliveryDate() {
    }

    /**
     * Creates a new {@code DeliveryDate} without the quarter.
     *
     * @param year the year
     */
    public DeliveryDate(int year) {
        this.year = year;
    }

    /**
     * Creates a new {@code DeliveryDate} with year and quarter.
     *
     * @param year    the year
     * @param quarter quarter (1-4)
     * @throws IllegalArgumentException if the quarter is invalid.
     */
    public DeliveryDate(int year, int quarter) {
        if (!QUARTERS.contains(quarter)) {
            throw new IllegalArgumentException("Delivery quarter must be >=1 and <=4");
        }

        this.year = year;
        this.quarter = quarter;
    }

    /**
     * Sets the delivery date year.
     *
     * @param year the year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns the delivery date year.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the delivery date quarter.
     *
     * @param quarter the quarter
     */
    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    /**
     * Returns the delivery date quarter.
     *
     * @return the quarter
     */
    public int getQuarter() {
        return quarter;
    }

    /**
     * Returns the list of {@code DeliveryDate}.
     * <p>
     * This methods provides two different years ranges:
     * <ul>
     * <li>since {@code endYearWithQuarters} back to {@code startYearWithQuarters}
     * the years are listed with quarter information;
     * <li>since {@code endYearWithoutQuarters} back to {@code startYearWithoutQuarters}
     * the years are listed without quarter information.
     * </ul>
     * </p>
     *
     * @param startYearWithoutQuarters the first year without quarters
     * @param endYearWithoutQuarters   the last year without quarters
     * @param startYearWithQuarters    the first year with quarters
     * @param endYearWithQuarters      the last year with quarters
     * @return the {@code DeliveryDate} list
     */
    public static Iterable<DeliveryDate> list(int startYearWithoutQuarters,
                                              int endYearWithoutQuarters,
                                              int startYearWithQuarters,
                                              int endYearWithQuarters) {

        Assert.isTrue(startYearWithoutQuarters <= endYearWithoutQuarters);
        Assert.isTrue(startYearWithQuarters <= endYearWithQuarters);

        List<DeliveryDate> list = new ArrayList<DeliveryDate>();

        for (int year : inverseRange(startYearWithQuarters, endYearWithQuarters)) {
            for (int q : Utils.reverseIterable(QUARTERS)) {
                list.add(new DeliveryDate(year, q));
            }
        }

        for (int year : inverseRange(startYearWithoutQuarters, endYearWithoutQuarters)) {
            list.add(new DeliveryDate(year));
        }

        return Collections.unmodifiableList(list);
    }

    /**
     * Parses the string argument as a {@code DeliveryDate}.
     * <p>
     * A valid instance of {@code DeliveryDate} can have two formats:
     * <ul>
     * <li>{@code YYYY}, where {@code YYYY} is a valid year (ie {@code year>=1900 && year<2999});</li>
     * <li>{@code YYYY + '/Q' + N}, where {@code YYYY} is a valid year (ie {@code year>=1900 && year<2999})
     * and {@code N} is the quarter number (ie {@code quarter>=1 && quarter<4}).
     * </li>
     * </ul>
     * </p>
     *
     * @param s the string to be parsed
     * @return a {@code DeliveryDate} represented by the string argument
     * @throws IllegalArgumentException    if {@code s} is empty or {@code null}
     * @throws DeliveryDateFormatException if {@code s} doesn't represent a valid {@code DeliveryDate}
     */
    public static DeliveryDate parseString(String s) {
        if (StringUtils.isBlank(s)) {
            throw new IllegalArgumentException("Empty string is not valid");
        }

        int year = 0;
        int quarter = 0;

        String[] tokens = s.split("/");

        String sYear = tokens[0];
        if (!StringUtils.isNumeric(sYear)) {
            throw new DeliveryDateFormatException("'" + sYear + "' is not a valid year");
        }

        year = Integer.parseInt(sYear);
        if (year < 1900 || year > 2999) {
            throw new DeliveryDateFormatException("'" + sYear + "' is not a valid year");
        }

        if (tokens.length == 2) {
            String sQuarter = tokens[1];
            if (sQuarter.length() != 2 && !sQuarter.startsWith(QUARTER_PREFIX)) {
                throw new DeliveryDateFormatException("'" + sQuarter + "' is not a valid quarter");
            }

            quarter = Integer.parseInt(sQuarter.substring(1));
            if (!QUARTERS.contains(quarter)) {
                throw new DeliveryDateFormatException("'" + sQuarter + "' is not a valid quarter");
            }
        }

        if (quarter == 0)
            return new DeliveryDate(year);
        else
            return new DeliveryDate(year, quarter);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DeliveryDate)) return false;

        DeliveryDate other = (DeliveryDate) obj;
        return this.quarter == other.quarter &&
                this.year == other.year;
    }

    @Override
    public String toString() {
        if (QUARTERS.contains(quarter)) {
            return String.format("%d/%s%d", year, QUARTER_PREFIX, quarter);
        }

        return String.format("%d", year);
    }

    private static Iterable<Integer> inverseRange(final int start, final int end) {
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    private int current = end;

                    @Override
                    public boolean hasNext() {
                        return current >= start;
                    }

                    @Override
                    public Integer next() {
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }
                        int n = current;
                        current--;
                        return n;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
}
