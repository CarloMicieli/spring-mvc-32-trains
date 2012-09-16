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
package com.trenako.web.controllers.form;

import java.util.Collections;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.trenako.AppGlobals;
import com.trenako.entities.Brand;
import com.trenako.entities.Scale;
import com.trenako.services.FormValuesService;
import com.trenako.web.images.validation.Image;

/**
 * It represents the web form for brands. 
 * @author Carlo Micieli
 *
 */
public class BrandForm {

	private final static Map<String, String> COUNTRIES = AppGlobals.countries();
	
	private FormValuesService service;
	
	@Valid
	private Brand brand;
	
	@Image(message = "brand.logo.notValid")
	private MultipartFile file;

	/**
	 * Creates an empty {@code BrandForm}.
	 */
	public BrandForm() {
	}
	
	
	
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	/**
	 * Returns the countries list.
	 * @return the countries list
	 */
	public Map<String, String> getCountriesList() {
		return COUNTRIES;
	}

	/**
	 * Returns the scales list.
	 * @return the scale list
	 */
	public Iterable<Scale> getScalesList() {
		if (service == null) {
			return Collections.emptyList();
		}
		return service.scales();
	}
}
