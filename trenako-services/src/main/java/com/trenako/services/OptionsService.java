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

import org.bson.types.ObjectId;

import com.trenako.entities.Option;
import com.trenako.values.OptionFamily;

/**
 * The interface for the rolling stock options service.
 * <p>
 * The concrete classes that implements the {@code OptionsService} interface will provide
 * the following functionalities:
 * <ul>
 * <li>finds an {@link Option} by id;</li>
 * <li>returns the list of {@link Option} by {@link OptionFamily};</li>
 * <li>returns the {@link Option} list;</li>
 * <li>saves/removes a {@link Option}.</li>
 * </ul>
 * </p>
 *
 * @author Carlo Micieli
 * @see com.trenako.entities.Option
 * @see com.trenako.values.OptionFamily
 */
public interface OptionsService {

    /**
     * Finds the {@link Option} with the provided id.
     *
     * @param id the unique id
     * @return a {@code Option} if found; {@code null} otherwise
     */
    Option findById(ObjectId id);

    /**
     * Finds the {@link Option} with the provided name.
     *
     * @param name the option name
     * @return a {@code Option} if found; {@code null} otherwise
     */
    Option findByName(String name);

    /**
     * Returns the list of {@link Option} with the same {@link OptionFamily}.
     * <p>
     * This method returns all {@code Option} objects, sort by name.
     * </p>
     *
     * @param family the {@code OptionFamily}
     * @return a {@code Option} list
     * @see com.trenako.values.OptionFamily
     */
    Iterable<Option> findByFamily(OptionFamily family);

    /**
     * Returns the list of all {@link Option} objects sorted by name.
     *
     * @return a {@code Option} list
     */
    Iterable<Option> findAll();

    /**
     * Persists the {@link Option} changes in the data store.
     * <p>
     * This method performs a "upsert": if the {@code Option} is not present in the data store
     * a new {@code Option} is created; otherwise the method will update the existing {@code Option}.
     * </p>
     *
     * @param option the {@code Option} to be saved
     */
    void save(Option option);

    /**
     * Removes a {@link Option} from the data store.
     *
     * @param option the {@code Option} to be removed
     */
    void remove(Option option);

}
