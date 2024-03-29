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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * It represents an address.
 *
 * @author Carlo Micieli
 */
public class Address {

    private String streetAddress;
    private String postalCode;
    private String city;
    private String locality;
    private String country;

    /**
     * Creates a new empty {@code Address}.
     */
    public Address() {
    }

    private Address(Builder b) {
        this.streetAddress = b.streetAddress;
        this.postalCode = b.postalCode;
        this.city = b.city;
        this.country = b.country;
        this.locality = b.locality;
    }

    /**
     * The {@code Address} builder class.
     *
     * @author Carlo Micieli
     */
    public static class Builder {
        private String streetAddress = null;
        private String postalCode = null;
        private String city = null;
        private String country = null;
        private String locality = null;

        public Builder streetAddress(String sa) {
            streetAddress = sa;
            return this;
        }

        public Builder city(String c) {
            city = c;
            return this;
        }

        public Builder postalCode(String p) {
            postalCode = p;
            return this;
        }

        public Builder locality(String l) {
            locality = l;
            return this;
        }

        public Builder country(String c) {
            country = c;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }

    /**
     * Returns the street name.
     *
     * @return the street name
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * Sets the street name.
     *
     * @param streetAddress the street name
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /**
     * Returns the postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code.
     *
     * @param postalCode the postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Returns the city/town.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city/town.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the locality (apartment number, building).
     *
     * @return the locality
     */
    public String getLocality() {
        return locality;
    }

    /**
     * Sets the locality (apartment number, building).
     *
     * @param locality the locality
     */
    public void setLocality(String locality) {
        this.locality = locality;
    }

    /**
     * Returns the country name.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country name.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Indicates whether the current {@code Address} is empty.
     *
     * @return {@code true} if empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return StringUtils.isBlank(streetAddress) &&
                StringUtils.isBlank(postalCode) &&
                StringUtils.isBlank(city) &&
                StringUtils.isBlank(locality) &&
                StringUtils.isBlank(country);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Address)) return false;

        Address other = (Address) obj;
        return new EqualsBuilder()
                .append(this.streetAddress, other.streetAddress)
                .append(this.postalCode, other.postalCode)
                .append(this.city, other.city)
                .append(this.locality, other.locality)
                .append(this.country, other.country)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(15, 37)
                .append(streetAddress)
                .append(postalCode)
                .append(city)
                .append(locality)
                .append(country)
                .toHashCode();
    }

    /**
     * Returns a string representation of the object.
     * <p>
     * This method should returns an address representation like this:
     * <blockquote>
     * <pre>
     * {@code street name, postal code, city, (country)}
     * </pre>
     * </blockquote>
     * </p>
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        if (isEmpty()) return "";

        return new StringBuilder()
                .append(getStreetAddress())
                .append(", ")
                .append(getPostalCode())
                .append(" ")
                .append(getCity())
                .append(", (")
                .append(getCountry())
                .append(")")
                .toString();
    }
}