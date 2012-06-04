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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
	private ObjectId id;
	
	@NotBlank(message = "scale.name.required")
	@Size(max = 10, message = "scale.name.size.notmet")
	@Indexed(unique = true)
	private String name;
	
	@Range(min = 2, max = 220, message = "scale.ratio.range.notmet")
	private double ratio;

	@Range(min = 0, max = 1000, message = "scale.gauge.range.notmet")
	private double gauge;
	
	private boolean narrow;
	
	// required
	Scale() {
	}
	
	/**
	 * Creates a new scale.
	 * @param name the scale name.
	 */
	public Scale(String name) {
		this.name = name;
	}

	private Scale(Builder b) {
		this.name = b.name;
		this.ratio = b.ratio;
		this.gauge = b.gauge;
		this.narrow = b.narrow;
	}
	
	public static class Builder {
		// required fields
		private final String name;
		
		// optional fields
		private double ratio = 0;
		private double gauge = 0;
		private boolean narrow = false;
		
		public Builder(String name) {
			this.name = name;
		}
		
		public Builder ratio(double r) {
			ratio = r;
			return this;
		}
		
		public Builder gauge(double g) {
			gauge = g;
			return this;
		}
		
		public Builder narrow(boolean n) {
			narrow = n;
			return this;
		}
		
		public Scale build() {
			return new Scale(this);
		}
	}
	
	/**
	 * Returns the unique id.
	 * @return the unique id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Returns the scale name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the scale name.
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the ratio of a linear dimension of the 
	 * model to the same dimension of the original.
	 * @return the scale ratio
	 */
	public double getRatio() {
		return ratio;
	}

	/**
	 * Sets the ratio of a linear dimension of the 
	 * model to the same dimension of the original.
	 * @param ratio the scale ratio
	 */
	public void setRatio(double ratio) {
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
	 * @param gauge the gauge
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
	 * @return the narrow flag value
	 */
	public boolean isNarrow() {
		return narrow;
	}

	/**
	 * Indicates whether has track gauge narrower than the standard 
	 * gauge railways or not.
	 * 
	 * @param isNarrow the narrow flag value
	 */
	public void setNarrow(boolean isNarrow) {
		this.narrow = isNarrow;
	}
	
	/**
	 * Returns the string representation for this scale ratio.
	 * @return the scale ratio as string.
	 */
	public String getRatioText() {
		int r = (int)ratio;
		if( ratio - r!=0 ) {
			return String.format("1:%.1f", ratio);
		}
		
		return String.format("1:%d", (int)ratio);
	}
	
	/**
	 * Returns a string representation of this object.
	 * @return a string representation of the object.
	 */	
	@Override
	public String toString() {
		return new StringBuffer()
			.append(getName())
			.append(" (")
			.append(getRatioText())
			.append(")")
			.toString();
	}
	
	/**
	 * Indicates whether some other object is "equal to" this one.
	 * @param obj the reference object with which to compare.
	 * @return <em>true</em> if this object is the same as the obj argument; 
	 * <em>false</em> otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if( this==obj ) return true;
		if( !(obj instanceof Scale) ) return false;
		
		Scale other = (Scale)obj;
		return new EqualsBuilder()
			.append(name, other.name)
			.append(ratio, other.ratio)
			.append(narrow, other.narrow)
			.append(gauge, other.gauge)
			.isEquals();
	}
	
	/**
	 * Returns a hash code value for the object.
	 * @return a hash code value for this object. 
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(15, 37)
			.append(name)
			.append(ratio)
			.append(narrow)
			.append(gauge)
			.hashCode();
	}
}
