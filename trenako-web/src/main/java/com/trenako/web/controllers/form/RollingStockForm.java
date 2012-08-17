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
import com.trenako.services.FormValuesService;
import com.trenako.values.Category;
import com.trenako.values.DeliveryDate;
import com.trenako.values.Era;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.PowerMethod;
import com.trenako.web.images.validation.Image;

/**
 * It represent a creation/editing form for {@code RollingStock}.
 * @author Carlo Micieli
 *
 */
public class RollingStockForm {
	
	@Valid
	private RollingStock rs;
	
	@Image(message = "rollingStock.image.notValid")
	private MultipartFile file;
	
	private FormValuesService valuesService;
	
	/**
	 * Creates a new empty {@code RollingStockForm}.
	 */
	public RollingStockForm() {
	}

	/**
	 * Creates a new form for the provided {@code RollingStock}. 
	 * @param rs the {@code RollingStock}
	 */
	public RollingStockForm(RollingStock rs) {
		this(rs, null, null);
	}
	
	/**
	 * Creates a new multipart form for the provided {@code RollingStock}. 
	 * @param rs the {@code RollingStock}
	 * @param valuesService the service to fill the dropdown lists
	 * @param file the {@code RollingStock} image file
	 */
	public RollingStockForm(RollingStock rs, 
			FormValuesService valuesService, 
			MultipartFile file) {
		this.rs = rs;
		this.valuesService = valuesService;
		this.file = file;
	}
	
	/**
	 * Creates a new form for the provided {@code RollingStock}. 
	 * @param rs the {@code RollingStock}
	 * @param valuesService the service to fill the dropdown lists
	 */
	public static RollingStockForm newForm(RollingStock rs, 
			FormValuesService valuesService) {
		return new RollingStockForm(rs, valuesService, null);
	}	
	
	public FormValuesService getValuesService() {
		return valuesService;
	}

	/**
	 * Returns the form {@code RollingStock}.
	 * @return the {@code RollingStock}
	 */
	public RollingStock getRs() {
		if (rs == null) {
			rs = new RollingStock();
		}
		
		return rs;
	}

	public RollingStock getRsLoadingRefs(FormValuesService valuesService) {
		getRs().setBrand(valuesService.getBrand(getRs().getBrand().getSlug()));
		getRs().setScale(valuesService.getScale(getRs().getScale().getSlug()));
		getRs().setRailway(valuesService.getRailway(getRs().getRailway().getSlug()));
		return getRs();
	}
	
	/**
	 * Sets the form {@code RollingStock}.
	 * @param rs the {@code RollingStock}
	 */
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
		return valuesService.brands();
	}
	
	public Iterable<Railway> getRailwaysList() {
		return valuesService.railways();
	}
	
	public Iterable<Scale> getScalesList() {
		return valuesService.scales();
	}
	
	public Iterable<LocalizedEnum<Category>> getCategoriesList() {
		return valuesService.categories();
	}
	
	public Iterable<LocalizedEnum<Era>> getErasList() {
		return valuesService.eras();
	}
	
	public Iterable<LocalizedEnum<PowerMethod>> getPowerMethodsList() {
		return valuesService.powerMethods();
	}
	
	public Iterable<DeliveryDate> getDeliveryDates() {
		return valuesService.deliveryDates();
	}
}
