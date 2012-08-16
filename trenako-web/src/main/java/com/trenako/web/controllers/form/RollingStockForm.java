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

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.trenako.entities.Brand;
import com.trenako.entities.Railway;
import com.trenako.entities.RollingStock;
import com.trenako.entities.Scale;
import com.trenako.services.RollingStocksService;
import com.trenako.values.Category;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;
import com.trenako.web.images.validation.Image;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class RollingStockForm {
	
	@Valid
	private RollingStock rs;
	
	@Image(message = "rollingStock.image.notValid")
	private MultipartFile file;

	private Iterable<Brand> brands;
	private Iterable<Scale> scales;
	private Iterable<Railway> railways;
	private Iterable<LocalizedEnum<Category>> categories;
	private Iterable<LocalizedEnum<Era>> eras;
	private Iterable<LocalizedEnum<PowerMethod>> powerMethods;
	
	/**
	 * Creates a new empty {@code RollingStockForm}.
	 */
	public RollingStockForm() {
	}

	private RollingStockForm(RollingStock rs, 
			Iterable<Brand> brands,
			Iterable<Scale> scales,
			Iterable<Railway> railways,
			Iterable<LocalizedEnum<Category>> categories,
			Iterable<LocalizedEnum<Era>> eras,
			Iterable<LocalizedEnum<PowerMethod>> powerMethods) {
		this.rs = rs;
		this.brands = brands;
		this.scales = scales;
		this.railways = railways;
		this.categories = categories;
		this.eras = eras;
		this.powerMethods = powerMethods;
	}
	
	public static RollingStockForm newForm(RollingStock rs, RollingStocksService service) {
		return new RollingStockForm(rs,
				service.brands(),
				service.scales(),
				service.railways(),
				service.categories(),
				service.eras(),
				service.powerMethods());
	}
	
	public RollingStock getRs() {
		if (rs == null) {
			rs = new RollingStock();
		}
		return rs;
	}

	public void setRs(RollingStock rs) {
		this.rs = rs;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
		
	public Iterable<Brand> getBrandsList() {
		return brands;
	}
	
	public Iterable<Railway> getRailwaysList() {
		return railways;
	}
	
	public Iterable<Scale> getScalesList() {
		return scales;
	}
	
	public Iterable<LocalizedEnum<Category>> getCategoriesList() {
		return categories;
	}
	
	public Iterable<LocalizedEnum<Era>> getErasList() {
		return eras;
	}
	
	public Iterable<LocalizedEnum<PowerMethod>> getPowerMethodsList() {
		return powerMethods;
	}
}
