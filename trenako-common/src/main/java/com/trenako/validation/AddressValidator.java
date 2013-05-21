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
package com.trenako.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.trenako.entities.Address;
import com.trenako.validation.constraints.ValidAddress;

/**
 * It represents an {@code Address} validator class.
 * <p>
 * Address values are valid if they are:
 * <ul>
 * <li>{@code empty};</li>
 * <li>have valid values for {@code street address}, {@code postal code}, {@code city} and {@code country}.</li>
 * </ul>
 * </p>
 *
 * @author Carlo Micieli
 */
public class AddressValidator
        implements ConstraintValidator<ValidAddress, Address> {

    @Override
    public void initialize(ValidAddress constraintAnnotation) {
    }

    @Override
    public boolean isValid(Address a, ConstraintValidatorContext context) {
        if (a == null || a.isEmpty()) {
            return true;
        }

        if (invalidStreetAddress(a)) return false;
        if (invalidPostalCode(a)) return false;
        if (invalidCity(a)) return false;
        if (invalidCountry(a)) return false;

        return true;
    }

    private static boolean invalidStreetAddress(Address a) {
        return StringUtils.isBlank(a.getStreetAddress());
    }

    private static boolean invalidPostalCode(Address a) {
        return StringUtils.isBlank(a.getPostalCode());
    }

    private static boolean invalidCity(Address a) {
        return StringUtils.isBlank(a.getCity());
    }

    private static boolean invalidCountry(Address a) {
        return StringUtils.isBlank(a.getCountry());
    }
}