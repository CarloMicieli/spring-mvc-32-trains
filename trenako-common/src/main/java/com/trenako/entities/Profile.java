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
package com.trenako.entities;

import java.util.Currency;

import org.apache.commons.lang3.StringUtils;

/**
 * It represents a user profile.
 *
 * @author Carlo Micieli
 */
public class Profile {

    private final static Profile DEFAULT_PROFILE = new Profile("EUR");

    private String currencyCode;

    /**
     * Creates a new empty {@code Profile}.
     */
    public Profile() {
    }

    /**
     * Creates a empty {@code Profile}.
     *
     * @param currencyCode the currency code
     */
    public Profile(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * Creates the default {@code Profile} for Euro.
     *
     * @return the default profile
     */
    public static Profile defaultProfile() {
        return DEFAULT_PROFILE;
    }

    /**
     * Returns the currency.
     *
     * @return the currency
     */
    public Currency getCurrency() {
        return Currency.getInstance(getCurrencyCode());
    }

    /**
     * Returns the currency code.
     * <p>
     * If the profile doesn't include a currency code, this method will return
     * the default value (in this case {@code EUR}).
     * </p>
     *
     * @return the currency code
     */
    public String getCurrencyCode() {
        if (StringUtils.isBlank(currencyCode)) {
            return defaultProfile().getCurrencyCode();
        }
        return currencyCode;
    }

    /**
     * Sets the currency code.
     *
     * @param currencyCode the currency code
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Profile)) return false;

        Profile other = (Profile) obj;
        return this.currencyCode.equals(other.currencyCode);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("profile{currency: ")
                .append(getCurrencyCode())
                .append("}")
                .toString();
    }
}