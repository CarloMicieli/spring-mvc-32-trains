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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import org.bson.types.ObjectId;

import com.trenako.utility.Slug;

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
	
	@Indexed(unique = true)
	private String slug;
	
	@NotBlank(message = "rs.itemNumber.required")
	@Size(max = 10, message = "rs.itemNumber.size.notmet")
	private String itemNumber;
	
	@NotBlank(message = "rs.description.required")
	@Size(max = 150, message = "rs.description.size.notmet")
	private String description;

	@Size(max = 500, message = "rs.modelDescription.size.notmet")
	private String modelDescription;
	
	@Size(max = 500, message = "rs.prototypeDescription.size.notmet")
	private String prototypeDescription;
	
	@DBRef
	@NotNull(message = "rs.railway.required")
	private Railway railway;
	@Indexed
	private String railwayName;
	
	@DBRef
	@NotNull(message = "rs.scale.required")
	private Scale scale;
	@Indexed
	private String scaleName;
	
	@Indexed
	private String era;
	@Indexed
	private String powerMethod;
	@Indexed
	private String category;
	
	@Indexed
	private Set<String> tags;
	
	@Range(min = 0, max = 1000, message = "rs.totalLength.range.notmet")
	private int totalLength;
	
	@Indexed
	@Size(max = 12, message = "rs.upcCode.size.notmet")
	private String upcCode;
	
	@Size(max = 2, message = "rs.country.size.notmet")
	private String country;
	
	private Date lastModified;
	
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
	 * Returns the slug for the rolling stock.
	 * @return the slug value.
	 */
	public String getSlug() {
		if (slug==null && brand!=null) {
			String s = Slug.encode(
					String.format("%s %s", brand.getName(), itemNumber));
			setSlug(s);
		}
		return slug;
	}

	/**
	 * Sets the slug for the rolling stock.
	 * @param slug the slug.
	 */
	public void setSlug(String slug) {
		this.slug = slug;
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
	 * Returns the model detailed description.
	 * @return the model description.
	 */
	public String getModelDescription() {
		return modelDescription;
	}

	/**
	 * Sets the model description.
	 * @param modelDescription the model description.
	 */
	public void setModelDescription(String modelDescription) {
		this.modelDescription = modelDescription;
	}

	/**
	 * Returns the prototype description.
	 * @return the prototype description.
	 */
	public String getPrototypeDescription() {
		return prototypeDescription;
	}

	/**
	 * Sets the prototype description.
	 * @param prototypeDescription the prototype description.
	 */
	public void setPrototypeDescription(String prototypeDescription) {
		this.prototypeDescription = prototypeDescription;
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
	 * Returns the railway name.
	 * @return the railway name.
	 */

	public String getRailwayName() {
		return railwayName;
	}

	/**
	 * Set the railway name.
	 * @param railway the railway name.
	 */
	public void setRailwayName(String railwayName) {
		this.railwayName = railwayName;
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
	 * Return the scale.
	 * @return the scale.
	 */
	public String getScaleName() {
		return scaleName;
	}

	/**
	 * Set the scale.
	 * @param scale the scale.
	 */
	public void setScaleName(String scaleName) {
		this.scaleName = scaleName;
	}
	
	/**
	 * Returns the category.
	 * @return the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 * @param category the category.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Returns the model era.
	 * @return the model era.
	 */
	public String getEra() {
		return era;
	}

	/**
	 * Sets the model era.
	 * @param era the model era.
	 */
	public void setEra(String era) {
		this.era = era;
	}
	
	/**
	 * Sets the model era.
	 * @param era the model era.
	 */
	public void setEra(Era era) {
		setEra(era.name());
	}
	
	/**
	 * Gets the total length in millimeters.	
	 * @return the total length.
	 */
	public int getTotalLength() {
		return totalLength;
	}

	/**
	 * Sets the total length in millimeters.
	 * @param totalLength the total length.
	 */
	public void setTotalLength(int totalLength) {
		this.totalLength = totalLength;
	}

	/**
	 * Gets the universal product code.
	 * @return the universal product code.
	 */
	public String getUpcCode() {
		return upcCode;
	}
	
	/**
	 * Sets the universal product code.
	 * @param upcCode the universal product code.
	 */
	public void setUpcCode(String upcCode) {
		this.upcCode = upcCode;
	}

	/**
	 * Gets the country code.
	 * @return the country code.
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country code.
	 * @param country the country code.
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Returns the model power method.
	 * @return the model power method.
	 */
	public String getPowerMethod() {
		return powerMethod;
	}

	/**
	 * Sets the model power method.
	 * @param powerMethod the model power method.
	 */
	public void setPowerMethod(String powerMethod) {
		this.powerMethod = powerMethod;
	}
	
	/**
	 * Sets the model power method.
	 * @param powerMethod the model power method.
	 */
	public void setPowerMethod(PowerMethod powerMethod) {
		setPowerMethod(powerMethod.getDescription());
	}

	/**
	 * Returns the list of tags for the rolling stock.	
	 * @return the list of tags.
	 */
	public Set<String> getTags() {
		return tags;
	}

	/**
	 * Sets the list of tags for the rolling stock.
	 * @param tags the list of tags.
	 */
	public void setTags(Set<String> tags) {
		this.tags = tags;
	}
	
	/**
	 * Adds the specified tag for the rolling stock.
	 * @param tag the tag.
	 */
	public void addTag(String tag) {
		if (tags==null) {
			tags = new HashSet<String>();
		}
		tags.add(tag);
	}

	/**
	 * Returns the last modified timestamp.	
	 * @return the last modified timestamp.
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * Sets the last modified timestamp.
	 * @param lastModified the last modified timestamp.
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
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
