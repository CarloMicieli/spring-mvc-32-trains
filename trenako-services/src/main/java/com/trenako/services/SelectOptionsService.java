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

/**
 * 
 * @author Carlo Micieli
 *
 */
public interface SelectOptionsService {

	/**
	 * Returns the list of {@code Brand}s.
	 * @return
	 */
	Iterable<Brand> brands();

	/**
	 * Returns the list of {@code Railway}s.
	 * @return
	 */
	Iterable<Railway> railways();

	/**
	 * Returns the list of {@code Scale}s.
	 * @return
	 */
	Iterable<Scale> scales();

	/**
	 * Returns the list of {@code Category}.
	 * @return
	 */
	Iterable<String> categories();
	
	/**
	 * Returns the list of {@code Era}s.
	 * @return
	 */
	Iterable<String> eras();
	
	/**
	 * Returns the list of {@code PowerMethod}.
	 * @return
	 */
	Iterable<String> powerMethods();
}
