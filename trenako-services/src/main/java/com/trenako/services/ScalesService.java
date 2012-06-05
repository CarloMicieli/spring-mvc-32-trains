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

import com.trenako.entities.Scale;

/**
 * The interface for the service to manage the {@link Scale} entity class.
 * @author Carlo Micieli
 *
 */
public interface ScalesService {

	/**
	 * Returns the scale with the provided id.
	 * @param id the unique id
	 * @return a scale if found; <em>null</em> otherwise
	 */
	Scale findById(ObjectId id);

	/**
	 * Returns the scale with the provided name.
	 * @param name the scale name
	 * @return a scale if found; <em>null</em> otherwise
	 */
	Scale findByName(String name);
	
	/**
	 * Returns all the scale with the provided {@link Scale#isNarrow()} value.
	 * @param isNarrow <em>true</em> will returns all the scales with narrow gauges; 
	 * <em>false</em> will return the scales with standard gauges
	 * @return the list of scales
	 */
	//Iterable<Scale> findAll(boolean isNarrow);
	
	/**
	 * Returns all the scale with the provided {@link Scale#getRatio()} value.
	 * @param ratio the scale ratio
	 * @return the list of scales
	 */
	//Iterable<Scale> findByRatio(double ratio);

	/**
	 * Returns all the scale entities.
	 * @return the list of scales
	 */
	Iterable<Scale> findAll();

	/**
	 * Saves the changes for the scale.
	 * @param scale a scale
	 */
	void save(Scale scale);

	/**
	 * Deletes the scale.
	 * @param scale a scale
	 */
	void remove(Scale brand);

}
