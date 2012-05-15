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

import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * It represents a scale of model railway.
 * @author Carlo Micieli
 *
 */
@Document(collection = "scales")
public class Scale {
	
	@Id
	ObjectId id;
	
	@NotBlank(message = "scale.name.required")
	@Size(max = 10, message = "scale.name.size.notmet")
	@Indexed(unique = true)
	String name;
	
	@Range(min = 2, max = 220, message = "scale.ratio.range.notmet")
	int ratio;

	@Range(min = 0, max = 1000, message = "scale.gauge.range.notmet")
	double gauge;
	
	boolean isNarrow;
	
	/**
	 * Creates a new scale.
	 */
	public Scale() {
	}
	
	/**
	 * Creates a new scale.
	 * @param id the unique scale id.
	 */
	public Scale(ObjectId id) {
		this.id = id;
	}

	/**
	 * Creates a new standard gauge scale.
	 * @param name the scale name.
	 * @param ratio the scale ratio.
	 * narrower than the standard gauge railways or not.
	 */
	public Scale(String name, int ratio) {
		this(name, ratio, false);
	}
	
	/**
	 * Creates a new scale.
	 * @param name the scale name.
	 * @param ratio the scale ratio.
	 * @param isNarrow whether has track gauge 
	 * narrower than the standard gauge railways or not.
	 */
	public Scale(String name, int ratio, boolean isNarrow) {
		this.name = name;
		this.ratio = ratio;
		this.isNarrow = isNarrow;
	}

	/**
	 * Returns the unique id for the <em>Scale</em>.
	 * @return the unique id.
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Sets the unique id for the <em>Scale</em>.
	 * @param id the unique id.
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Returns the scale name.
	 * @return the scale name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the scale name.
	 * @param name the scale name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the ratio of a linear dimension of the 
	 * model to the same dimension of the original.
	 * @return the scale ratio.
	 */
	public int getRatio() {
		return ratio;
	}

	/**
	 * Sets the ratio of a linear dimension of the 
	 * model to the same dimension of the original.
	 * @param ratio the scale ratio.
	 */
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	
	/**
	 * Returns the the distance between the two rails forming a railroad track.	
	 * @return the gauge.
	 */
	public double getGauge() {
		return gauge;
	}

	/**
	 * Sets the distance between the two rails forming a railroad track
	 * @param gauge the gauge.
	 */
	public void setGauge(double gauge) {
		this.gauge = gauge;
	}

	/**
	 * Returns whether has track gauge 
	 * narrower than the standard gauge railways or not.
	 * <p>
	 * <em>true</em> if the scale is narrow, 
	 * <em>false</em> otherwise.
	 * </p>
	 * 
	 * @return the narrow flag value. 
	 */
	public boolean isNarrow() {
		return isNarrow;
	}

	/**
	 * Sets whether has track gauge 
	 * narrower than the standard gauge railways or not.
	 * @param isNarrow the narrow flag value.
	 */
	public void setNarrow(boolean isNarrow) {
		this.isNarrow = isNarrow;
	}
	
	/**
	 * Returns a string representation of this <em>Scale</em>.
	 * @return a string representation of the object.
	 */	
	@Override
	public String toString() {
		return new StringBuffer()
			.append(getName() + " (1:")
			.append(getRatio() + ")")
			.toString();
	}
}
