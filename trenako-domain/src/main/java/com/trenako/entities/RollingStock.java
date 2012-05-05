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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import org.bson.types.ObjectId;

/**
 * It represents a rolling stock model.
 *
 * @author Carlo Micieli
 * 
 */
@Document(collection = "rolling_stocks")
public class RollingStock {

	@Id
	private ObjectId id;
	
	@DBRef
	@NotNull(message = "rs.brand.required")
	private Brand brand;
	
	@NotBlank(message = "rs.itemNumber.required")
	@Size(max = 10, message = "rs.itemNumber.size.notmet")
	private String itemNumber;
	
	@NotBlank(message = "rs.description.required")
	@Size(max = 250, message = "rs.description.size.notmet")
	private String description;

	@DBRef
	@NotNull(message = "rs.railway.required")
	private Railway railway;
	
	@DBRef
	@NotNull(message = "rs.scale.required")
	private Scale scale;
	
	private Era era;
	private PowerMethod powerMethod;
	
	/**
	 * Creates a new rolling stock
	 */
	public RollingStock() { //required
	}
	
	/**
	 * Creates a new rolling stock.
	 * @param brand the brand name.
	 * @param itemNumber the item number.
	 * @param description the rolling stock description.
	 * @param railway the railway name.
	 * @param scale the scale.
	 */
	public RollingStock(Brand brand, 
			String itemNumber, 
			String description, 
			Railway railway, 
			Scale scale) {
		
		this.brand = brand;
		this.itemNumber = itemNumber;
		this.description = description;
		this.railway = railway;
		this.scale = scale;
	}
	
	/**
	 * Creates a new rolling stock.
	 * @param id
	 */
	public RollingStock(ObjectId id) {
		this.id = id;
	}

	/**
	 * Return the unique id.
	 * @return the unique id.
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Set the unique id.
	 * @param id the unique id.
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Return the brand name.
	 * @return the brand name.
	 */
	public Brand getBrand() {
		return brand;
	}

	/**
	 * Set the brand name.
	 * @param brand the brand name.
	 */
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	/**
	 * Return the item number.
	 * @return the item number.
	 */
	public String getItemNumber() {
		return itemNumber;
	}

	/**
	 * Set the item number.
	 * @param itemNumber the item number.
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	/**
	 * Return the rolling stock description.
	 * @return the rolling stock description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the rolling stock description.
	 * @param description the rolling stock description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Return the railway name.
	 * @return the railway name.
	 */
	public Railway getRailway() {
		return railway;
	}

	/**
	 * Set the railway name.
	 * @param railway the railway name.
	 */
	public void setRailway(Railway railway) {
		this.railway = railway;
	}

	/**
	 * Return the scale.
	 * @return the scale.
	 */
	public Scale getScale() {
		return scale;
	}

	/**
	 * Set the scale.
	 * @param scale the scale.
	 */
	public void setScale(Scale scale) {
		this.scale = scale;
	}

	/**
	 * Returns the model era.
	 * @return the model era.
	 */
	public Era getEra() {
		return era;
	}

	/**
	 * Sets the model era.
	 * @param era the model era.
	 */
	public void setEra(Era era) {
		this.era = era;
	}
	
	/**
	 * Returns the model power method.
	 * @return the model power method.
	 */
	public PowerMethod getPowerMethod() {
		return powerMethod;
	}

	/**
	 * Sets the model power method.
	 * @param powerMethod the model power method.
	 */
	public void setPowerMethod(PowerMethod powerMethod) {
		this.powerMethod = powerMethod;
	}

	/**
	 * Return a string representation for the current rolling stock.
	 * @return the string representation.
	 */
	@Override
	public String toString() {
		return new StringBuffer()
			.append(getBrand().getName() + " ")
			.append(getItemNumber() + ": ")
			.append(getDescription())
			.toString();
	}
}
