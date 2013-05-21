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
package com.trenako.services;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.repositories.BrandsRepository;
import com.trenako.repositories.RailwaysRepository;
import com.trenako.repositories.ScalesRepository;
import com.trenako.values.Category;
import com.trenako.values.DeliveryDate;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;

/**
 * @author Carlo Micieli
 */
@Service("formValuesService")
public class FormValuesServiceImpl implements FormValuesService {

    private
    @Autowired(required = false)
    MessageSource messageSource;

    private final BrandsRepository brands;
    private final RailwaysRepository railways;
    private final ScalesRepository scales;

    private final static Sort BY_NAME_SORT_ORDER = new Sort(Direction.ASC, "name");
    private final static Sort SCALES_SORT_ORDER = new Sort(new Sort.Order(Direction.ASC, "ratio"), new Sort.Order(Direction.DESC, "gauge"));

    /**
     * Creates a new {@code FormValuesServiceImpl}
     *
     * @param brands   the {@code Brand} repository
     * @param railways the {@code Railway} repository
     * @param scales   the {@code Scale} repository
     */
    @Autowired
    public FormValuesServiceImpl(
            BrandsRepository brands,
            RailwaysRepository railways,
            ScalesRepository scales) {
        this.brands = brands;
        this.railways = railways;
        this.scales = scales;
    }


    @Override
    public Iterable<Brand> brands() {
        return brands.findAll(BY_NAME_SORT_ORDER);
    }

    @Override
    public Iterable<Railway> railways() {
        return railways.findAll(BY_NAME_SORT_ORDER);
    }

    @Override
    public Iterable<Scale> scales() {
        return scales.findAll(SCALES_SORT_ORDER);
    }

    @Override
    public Iterable<LocalizedEnum<Category>> categories() {
        return LocalizedEnum.list(Category.class, messageSource, null);
    }

    @Override
    public Iterable<LocalizedEnum<Era>> eras() {
        return LocalizedEnum.list(Era.class, messageSource, null);
    }

    @Override
    public Iterable<LocalizedEnum<PowerMethod>> powerMethods() {
        return LocalizedEnum.list(PowerMethod.class, messageSource, null);
    }

    @Override
    public Iterable<DeliveryDate> deliveryDates() {
        Calendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        return DeliveryDate.list(2000, year - 1, year, year + 1);
    }

    @Override
    public Brand getBrand(String brandSlug) {
        return brands.findBySlug(brandSlug);
    }

    @Override
    public Scale getScale(String scaleSlug) {
        return scales.findBySlug(scaleSlug);
    }

    @Override
    public Railway getRailway(String railwaySlug) {
        return railways.findBySlug(railwaySlug);
    }
}
