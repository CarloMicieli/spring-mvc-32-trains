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

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.Scale;
import com.trenako.values.Category;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
/**
 * It represents a service for the rolling stocks browsing.
 * @author Carlo Micieli
 *
 */
public interface BrowseService {

	/**
	 * Returns the list of model railway {@code Era}.
	 * @return the eras list
	 */
	Iterable<LocalizedEnum<Era>> eras();
	
	/**
	 * Returns the list of model railway {@code Category}.
	 * @return the brands list
	 */
	Iterable<LocalizedEnum<Category>> categories();
	
	/**
	 * Returns the list of model railway {@code Scale}.
	 * @return the scales list
	 */
	Iterable<Scale> scales();
	
	/**
	 * Returns the list of {@code Railway}.
	 * @return the scales list
	 */
	Iterable<Railway> railways();

	/**
	 * Returns the list of model railway {@code Brand}.
	 * @return the brands list
	 */
	Iterable<Brand> brands();
}
