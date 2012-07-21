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

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.trenako.format.annotations.IntegerFormat;
import com.trenako.format.annotations.IntegerFormat.Style;
import com.trenako.utility.Slug;
import com.trenako.values.Standard;

/**
 * It represents a {@code Scale} of model railway.
 * <p>
 * In order to avoid the {@code double} rounding problems this 
 * class is using integer values internally. 
 * </p>
 * <p>
 * The class provides methods with the correct values for both 
 * {@code ratio} and {@code gauge}.
 * </p>
 * 
 * @author Carlo Micieli
 *
 */
@Document(collection = "scales")
public class Scale {
	
	public final static BigDecimal GAUGE_FACTOR = BigDecimal.valueOf(100);
	public final static BigDecimal RATIO_FACTOR = BigDecimal.valueOf(10);

	@Id
	private ObjectId id;
	
	@NotBlank(message = "scale.name.required")
	@Size(max = 10, message = "scale.name.size.notmet")
	@Indexed(unique = true)
	private String name;
	
	@Indexed(unique = true)
	private String slug;
	
	@Range(min = 80, max = 2200, message = "scale.ratio.range.notmet")
	@IntegerFormat(style = Style.SCALE_RATIO)
	private int ratio;

	@Range(min = 0, max = 20000, message = "scale.gauge.range.notmet")
	@IntegerFormat(style = Style.SCALE_GAUGE)
	private int gauge;
	
	@Indexed
	private Set<String> standards;
	
	private boolean narrow;
	private Date lastModified;
	
	/**
	 * Creates a new empty {@code Scale}.
	 */
	public Scale() {
	}
	
	/**
	 * Creates a new {@code Scale}.
	 * @param name the name
	 */
	public Scale(String name) {
		this.name = name;
	}

	public Scale(ObjectId id) {
		this.id = id;
	}
	
	private Scale(Builder b) {
		this.name = b.name;
		this.ratio = b.ratio;
		this.gauge = b.gauge;
		this.narrow = b.narrow;
		this.slug = b.slug;
	}
	
	/**
	 * It represents a {@code Scale} builder class.
	 * @author Carlo Micieli
	 *
	 */
	public static class Builder {
		// required fields
		private final String name;
		
		// optional fields
		private String slug;
		private int ratio = 0;
		private int gauge = 0;
		private boolean narrow = false;
		
		/**
		 * Creates a new {@code Scale} builder.
		 * @param name the name
		 */
		public Builder(String name) {
			this.name = name;
		}
		
		/**
		 * Sets the {@code slug}.
		 * @param slug the slug
		 * @return a builder
		 */
		public Builder slug(String slug) {
			this.slug = slug;
			return this;
		}
		
		/**
		 * Sets the {@code scale} ratio.
		 * <p>
		 * The ratio is internally stored as {@code Integer}; the
		 * actual value must be multiplied by a {@code 10} factor.
		 * </p>
		 * <p>
		 * For example, a ratio of {@code 32.5} must be inserted as {@code 325}.
		 * </p>
		 * 
		 * @param ratio the ratio
		 * @return a builder
		 * @see Builder#ratio(BigDecimal)
		 */
		public Builder ratio(int ratio) {
			this.ratio = ratio;
			return this;
		}
		
		/**
		 * Sets the {@code scale} ratio in millimeters.
		 * <p>
		 * The clients must use this method if they provide
		 * the exact value.
		 * </p>
		 * 
		 * @param ratio the ratio
		 * @return a builder
		 */
		public Builder ratio(BigDecimal ratio) {
			this.ratio = ratio
					.multiply(RATIO_FACTOR).intValue();
			return this;
		}
		
		/**
		 * Sets the {@code Scale} gauge.
		 * <p>
		 * The gauge is internally stored as {@code Integer}; the
		 * actual value must be multiplied by a {@code 100} factor.
		 * </p>
		 * <p>
		 * For example, a gauge of {@code 16.5} must be inserted as {@code 1650}.
		 * </p>
		 *  
		 * @param gauge the gauge
		 * @return a builder
		 * @see Builder#gauge(BigDecimal)
		 */
		public Builder gauge(int gauge) {
			this.gauge = gauge;
			return this;
		}

		/**
		 * Sets the {@code Scale} gauge in millimeters.
		 * <p>
		 * The clients must use this method if they provide
		 * the exact value.
		 * </p>
		 * 
		 * @param gauge the gauge
		 * @return a builder
		 */
		public Builder gauge(BigDecimal gauge) {
			this.gauge = gauge
					.multiply(GAUGE_FACTOR).intValue();
			return this;
		}
		
		/**
		 * Indicates whether a {@code Scale} is narrow or not.
		 * @param n {@code true} for narrow scales; {@code false} otherwise
		 * @return a builder
		 */
		public Builder narrow(boolean n) {
			narrow = n;
			return this;
		}
		
		/**
		 * Builds a new {@code Scale} object.
		 * @return a {@code Scale}
		 */
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
	 * Sets the {@code Brand} id.
	 * @param id the unique id
	 */
	public void setId(ObjectId id) {
		this.id = id;
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
	 * Returns the {@code Scale} slug.
	 * @return the slug
	 */
	public String getSlug() {
		if( slug==null ) slug = Slug.encode(name);
		return slug;
	}
		
	/**
	 * Returns the ratio of a linear dimension of the 
	 * model to the same dimension of the original.
	 * @return the scale ratio
	 */
	public int getRatio() {
		return ratio;
	}

	/**
	 * Returns the {@code Scale} ratio in millimeters,
	 * @return the ratio
	 */
	public BigDecimal ratio() {
		return (new BigDecimal(getRatio()))
				.divide(RATIO_FACTOR);
	}
	
	/**
	 * Sets the ratio of a linear dimension of the 
	 * model to the same dimension of the original.
	 * @param ratio the scale ratio
	 */
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	
	/**
	 * Sets the {@code Scale} ratio from a {@link java.math.BigDecimal}
	 * value.
	 * 
	 * @param ratio the {@code Scale} ratio
	 */
	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio
				.multiply(RATIO_FACTOR).intValue();
	}
	
	/**
	 * Returns the the distance between the two rails forming a railroad track.	
	 * @return the gauge.
	 */
	public int getGauge() {
		return gauge;
	}

	/**
	 * Returns the {@code Scale} gauge in millimeters.
	 * @return the gauge
	 */
	public BigDecimal gauge() {
		return (new BigDecimal(getGauge()))
				.divide(GAUGE_FACTOR);
	}
	
	/**
	 * Sets the distance between the two rails forming a railroad track
	 * @param gauge the gauge
	 */
	public void setGauge(int gauge) {
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
	 * Sets the list of {@link Standard} that include this 
	 * {@code Scale}.	
	 * @param standards the list of standards
	 */
	public void setStandards(Set<String> standards) {
		this.standards = standards;
	}
	
	/**
	 * Returns the list of {@link Standard} that include this 
	 * {@code Scale}.	
	 * @return the list of standards
	 */
	public Set<String> getStandards() {
		return standards;
	}

	/**
	 * Adds a new standard to this scale.
	 * @param standard a standard
	 */
	public void addStandard(Standard standard) {
		if( standards==null ) standards = new HashSet<String>();
		standards.add(standard.toString());
	}

	/**
	 * Returns a string with the standards list.
	 * @return
	 */
	public String getStandardsList() {
		if (standards==null || standards.size()==0 ) return "";
		return standards.toString();
	}
	
	/**
	 * Returns the string representation for this scale ratio.
	 * @return the scale ratio as string.
	 */
	public String getRatioText() {
		return new StringBuilder()
			.append("1:")
			.append(this.ratio().toString())
			.toString();
	}
	
	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	/**
	 * Returns a label for the current {@code Scale}.
	 * <p>
	 * The application will show this value as string representation
	 * for the current {@code Scale} instead of the usual {@code Scale#toString()}.
	 * </p>
	 * @return
	 */
	public String label() {
		return new StringBuffer()
			.append(getName())
			.append(" (")
			.append(getRatioText())
			.append(")")
			.toString();
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
