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

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.types.ObjectId;

import com.trenako.mapping.DbReferenceable;
import com.trenako.mapping.LocalizedField;
import com.trenako.mapping.WeakDbRef;
import com.trenako.utility.Slug;
import com.trenako.validation.constraints.ContainsDefault;
import com.trenako.validation.constraints.ISOCountry;
import com.trenako.values.DeliveryDate;
import com.trenako.values.Era;
import com.trenako.values.OptionFamily;

/**
 * It represents a rolling stock.
 * <p>
 * The object represents a model corresponding to a given item number
 * from a {@link Brand} catalog.
 * </p>
 * <p>
 * This instance can include more items.
 *</p>
 *
 * @author Carlo Micieli
 * 
 */
@Document(collection = "rollingStocks")
public class RollingStock implements DbReferenceable {

	@Id
	private ObjectId id;
	
	@NotNull(message = "rs.brand.required")
	private WeakDbRef<Brand> brand;
	
	@NotNull(message = "rs.railway.required")
	private WeakDbRef<Railway> railway;
	
	@NotNull(message = "rs.scale.required")
	private WeakDbRef<Scale> scale;
	
	@Indexed(unique = true)
	private String slug;
	
	@NotBlank(message = "rs.itemNumber.required")
	@Size(max = 10, message = "rs.itemNumber.size.notmet")
	private String itemNumber;
	
	@NotNull(message = "rs.description.required")
	@ContainsDefault(message = "rs.description.default.required")
	private LocalizedField<String> description;

	private LocalizedField<String> details;
	
	@Indexed
	@NotBlank(message = "rs.era.required")
	private String era;
	
	@Indexed
	@NotBlank(message = "rs.category.required")
	private String category;
	
	@Indexed
	private String powerMethod;
	
	@Indexed
	private SortedSet<String> tags;
	
	@Range(min = 0, max = 1000, message = "rs.totalLength.range.notmet")
	private int totalLength;
	
	@Indexed
	@Size(max = 12, message = "rs.upcCode.size.notmet")
	private String upcCode;
	
	@ISOCountry(message = "rs.country.code.invalid")
	private String country;
	
	private Map<String, String> options;
	private DeliveryDate deliveryDate;
	private String modifiedBy;
	private Date lastModified;
	
	/**
	 * Creates an empty {@code RollingStock}.
	 */
	public RollingStock() {
	}
	
	/**
	 * Creates a new {@code RollingStock} with the provided id.
	 * @param id the {@code RollingStock} id
	 */
	public RollingStock(ObjectId id) {
		this.id = id;
	}
	
	private RollingStock(Builder b) {
		this.setBrand(b.brand);
		this.setRailway(b.railway);
		this.setScale(b.scale);
		this.itemNumber = b.itemNumber;
		
		this.description = b.description;
		this.details = b.details;
		
		this.id = b.id;
		this.tags = b.tags;
		this.era = b.era;
		this.category = b.category;
		this.powerMethod = b.powerMethod;
		this.options = b.options;
		this.deliveryDate = b.deliveryDate;
		this.totalLength = b.totalLength;
		this.country = b.country;
		this.upcCode = b.upcCode;
		
		this.modifiedBy = b.modifiedBy;
		this.lastModified = b.lastModified;
	}
	
	/**
	 * The rolling stock builder class.
	 * @author Carlo Micieli
	 *
	 */
	public static class Builder {
		// required fields
		private final Brand brand;
		private final String itemNumber;
		
		// optional fields
		private ObjectId id = null;
		private LocalizedField<String> description = null;
		private LocalizedField<String> details = null;
		
		private SortedSet<String> tags = null;
		private Railway railway = null; 
		private Scale scale = null;
		private String era = null;
		private String powerMethod = null;
		private String category = null;
		private String country = null;
		private DeliveryDate deliveryDate = null;
		private int totalLength = 0;
		private String upcCode = null;
		private String modifiedBy = null;
		private Date lastModified = null;
		
		private Map<String, String> options = null;
		
		public Builder(Brand brand, String itemNumber) {
			this.brand = brand;
			this.itemNumber = itemNumber;
		}
		
		public RollingStock build() {
			return new RollingStock(this);
		}
		
		public Builder railway(Railway railway) { 
			this.railway = railway;
			return this;
		}
		
		public Builder scale(Scale scale) { 
			this.scale = scale;
			return this;
		}
		
		public Builder description(String desc) {
			if (this.description == null) {
				this.description = new LocalizedField<String>(desc);
			}
			else {
				this.description.setDefault(desc);
			}
			return this;
		}
		
		public Builder description(LocalizedField<String> desc) {
			this.description = desc;
			return this;
		}

		public Builder description(Locale lang, String desc) {
			if (this.description == null) {
				this.description = new LocalizedField<String>();
			}
			this.description.put(lang, desc);
			return this;
		}
		
		public Builder details(LocalizedField<String> details) {
			this.details = details;
			return this;
		}
		
		public Builder details(String det) {
			if (this.details == null) {
				this.details = new LocalizedField<String>(det);
			}
			else {
				this.details.setDefault(det);
			}
			return this;
		}

		public Builder details(Locale lang, String det) {
			if (this.details == null) {
				this.details = new LocalizedField<String>();
			}
			this.details.put(lang, det);
			return this;
		}

		public Builder deliveryDate(DeliveryDate d) { 
			deliveryDate = d;
			return this;
		}
		
		public Builder option(Option opt) {
			return option(opt.getFamily(), opt.getName());
		}
		
		public Builder option(String family, String opt) {
			if (options == null) {
				options = new HashMap<String, String>();
			}
			
			options.put(family, opt);
			return this;
		}

		public Builder powerMethod(String pm) { 
			powerMethod = pm;
			return this;
		}
		
		public Builder era(String e) { 
			era = e;
			return this;
		}
		
		public Builder country(String c) { 
			country = c;
			return this;
		}
		
		public Builder category(String c) { 
			category = c;
			return this;
		}
		
		public Builder upcCode(String c) { 
			upcCode = c;
			return this;
		}

		public Builder tags(SortedSet<String> tags) { 
			this.tags = tags;
			return this;
		}
		
		public Builder tags(String... t) { 
			tags = new TreeSet<String>(Arrays.asList(t));
			return this;
		}

		public Builder totalLength(int len) {
			totalLength = len;
			return this;
		}

		public Builder id(ObjectId id) {
			this.id = id;
			return this;
		}
		
		public Builder modifiedBy(Account modifiedBy) {
			this.modifiedBy = modifiedBy.getSlug();
			return this;
		}
		
		public Builder lastModified(Date lastModified) {
			this.lastModified = lastModified;
			return this;
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
	 * Sets the {@code RollingStock} id
	 * @param id the id
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	@Override
	public String getLabel() {
		return getBrand().getLabel() + " " + getItemNumber();
	}
	
	/**
	 * Returns the slug for the rolling stock.
	 * <p>
	 * The slug is built encoding {@link RollingStock#getBrand()}
	 * and {@link RollingStock#getItemNumber()}.
	 * </p>
	 * <p>
	 * If the slug is not already set for the object, this method
	 * will return the appropriate value.
	 * </p>
	 * <p>
	 * The rolling stocks search must use this field as it is unique for
	 * the application and therefore indexed.
	 * </p>
	 * 
	 * @return the slug value
	 */
	@Override
	public String getSlug() {
		if (slug == null) {
			return Slug.encode(new StringBuilder()
				.append(brand.getSlug())
				.append(" ")
				.append(itemNumber)
				.toString());
		}
		return slug;
	}

	/**
	 * Sets the slug for the rolling stock.
	 * @param slug the slug
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}

	/**
	 * Returns the brand.
	 * @return the brand
	 */
	public WeakDbRef<Brand> getBrand() {
		return brand;
	}

	/**
	 * Sets the brand.
	 * @param brand the brand
	 */
	public void setBrand(Brand brand) {
		if (brand != null) {
			this.brand = WeakDbRef.buildRef(brand);
		}
	}

	/**
	 * Sets the brand.
	 * @param brand the brand
	 */
	public void setBrand(WeakDbRef<Brand> brand) {
		this.brand = brand;
	}
	
	/**
	 * Returns the item number.
	 * @return the item number
	 */
	public String getItemNumber() {
		return itemNumber;
	}

	/**
	 * Sets the item number.
	 * @param itemNumber the item number
	 */
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	/**
	 * Returns the rolling stock default description.
	 * @return the description
	 */
	public LocalizedField<String> getDescription() {
		return description;
	}
	
	/**
	 * Sets the rolling stock default description.
	 * @param description the description
	 */
	public void setDescription(LocalizedField<String> description) {
		this.description = description;
	}
	
	/**
	 * Returns the model detailed description.
	 * @return the details
	 */
	public LocalizedField<String> getDetails() {
		return details;
	}
	
	/**
	 * Sets the default model details.
	 * <p>
	 * This details text is for the default language (<em>English</em>).
	 * </p>
	 * 
	 * @param details the details
	 */
	public void setDetails(LocalizedField<String> details) {
		this.details = details;
	}

	/**
	 * Returns the railway.
	 * @return the railway
	 */
	public WeakDbRef<Railway> getRailway() {
		return railway;
	}

	/**
	 * Sets the railway.
	 * @param railway the railway
	 */
	public void setRailway(Railway railway) {
		if (railway != null) {
			this.railway = WeakDbRef.buildRef(railway);
			this.country = railway.getCountry();
		}
	}

	/**
	 * Sets the railway.
	 * @param railway the railway
	 */
	public void setRailway(WeakDbRef<Railway> railway) {
		this.railway = railway;
	}
	
	/**
	 * Returns the scale.
	 * @return the scale
	 */
	public WeakDbRef<Scale> getScale() {
		return scale;
	}

	/**
	 * Sets the scale.
	 * @param scale the scale
	 */
	public void setScale(Scale scale) {
		if (scale != null) {
			this.scale = WeakDbRef.buildRef(scale);
		}
	}

	/**
	 * Sets the scale.
	 * @param scale the scale
	 */
	public void setScale(WeakDbRef<Scale> scale) {
		this.scale = scale;
	}
	
	/**
	 * Returns the category.
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 * @param category the category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Returns the era for the current rolling stock.
	 * @return the era
	 */
	public String getEra() {
		return era;
	}

	/**
	 * Sets the era for the current rolling stock.
	 * <p>
	 * The value must be the String representation of a value
	 * from {@link Era} enumeration.
	 * </p>
	 * 
	 * @param era the era
	 */
	public void setEra(String era) {
		this.era = era;
	}
	
	/**
	 * Gets the total length over buffers in millimeters.	
	 * @return the total length
	 */
	public int getTotalLength() {
		return totalLength;
	}

	/**
	 * Sets the total length over buffers in millimeters.
	 * <p>
	 * If this rolling stock includes more items then
	 * this represents the overall length.
	 * </p>
	 * 
	 * @param totalLength the total length
	 */
	public void setTotalLength(int totalLength) {
		this.totalLength = totalLength;
	}

	/**
	 * Gets the universal product code.
	 * @return the universal product code
	 */
	public String getUpcCode() {
		return upcCode;
	}
	
	/**
	 * Sets the universal product code.
	 * @param upcCode the universal product code
	 */
	public void setUpcCode(String upcCode) {
		this.upcCode = upcCode;
	}

	/**
	 * Returns the delivery date.
	 * <p>
	 * The delivery date it usually includes the year and the 
	 * quarter.
	 * </p>
	 * 
	 * @return the delivery date
	 */
	public DeliveryDate getDeliveryDate() {
		return deliveryDate;
	}

	/**
	 * Sets the delivery date.
	 * 
	 * @param deliveryDate the delivery date
	 */
	public void setDeliveryDate(DeliveryDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	/**
	 * Gets the country code.
	 * <p>
	 * If country code is not set then the railway country code will be
	 * returned by this method.
	 * </p>
	 * <p>
	 * The country code value is following the ISO 3166-1 alpha-3 standard.
	 * </p>
	 * 
	 * @return the country code
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country code.
	 * @param country the country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Returns the model power method.
	 * <p>
	 * If the rolling stock is dummy (not motorized) then
	 * this method will return <em>null</em>.
	 * </p>
	 * 
	 * @return the model power method
	 */
	public String getPowerMethod() {
		return powerMethod;
	}

	/**
	 * Sets the model power method.
	 * @param powerMethod the model power method
	 */
	public void setPowerMethod(String powerMethod) {
		this.powerMethod = powerMethod;
	}
	
	/**
	 * Returns the list of tags for the rolling stock.	
	 * @return the list of tags
	 */
	public Set<String> getTags() {
		return tags;
	}

	/**
	 * Sets the list of tags for the rolling stock.
	 * @param tags the list of tags
	 */
	public void setTags(SortedSet<String> tags) {
		this.tags = tags;
	}
	
	/**
	 * Adds the specified tag for the rolling stock.
	 * @param tag the tag
	 */
	public void addTag(String tag) {
		if (tags == null) {
			tags = new TreeSet<String>();
		}
		tags.add(tag);
	}
	
	/**
	 * Adds an option to the rolling stock.
	 * <p>
	 * If an option for the same {@link OptionFamily} already exists,
	 * this method will replace the value.
	 * </p>
	 * 
	 * @param option the option value
	 */
	public void addOption(Option option) {
		if (options == null) {
			options = new HashMap<String, String>();
		}
		
		options.put(optionsKey(option), option.getName());
	}
	
	/**
	 * Checks if the rolling stock has the provided option.
	 * 
	 * @param option the option value
	 * @return {@code true} if the rolling stock contains the option; 
	 * {@code true} otherwise
	 */
	public boolean hasOption(Option option) {
		String key = optionsKey(option);
		if (!options.containsKey(key)) {
			return false;
		}
		
		return options.containsValue(option.getName());
	}
	
	/**
	 * Returns the option by its family.
	 * 
	 * @param family the option family
	 * @return the option value
	 */
	public Option getOption(OptionFamily family) {
		if (options == null) {
			return null;
		}
		
		String name = options.get(family.name());
		if (name == null) {
			return null;
		}
		
		return new Option(name, family);
	}
	
	/**
	 * Returns the options for the rolling stock.
	 * @return the options
	 */
	public Map<String, String> getOptions() {
		return options;
	}

	/**
	 * Sets the options map for the rolling stock.
	 * <p>
	 * The key for the map is one value from the {@link OptionFamily} 
	 * enumeration. It is possible to have only one value for each
	 * family.
	 * </p>
	 * 
	 * @param options the options
	 */
	public void setOptions(Map<String, String> options) {
		this.options = options;
	}
	
	/**
	 * Returns the the user who updated this {@code RollingStock}.
	 * @return the user
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * Sets the user who updated this {@code RollingStock}.
	 * @param modifiedBy the user
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * Returns the last modified timestamp.	
	 * @return the last modified timestamp
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * Sets the last modified timestamp.
	 * @param lastModified the timestamp
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {
		return new StringBuffer()
			.append("rollingStock{brand: ")
			.append(getBrand().getLabel())
			.append(", itemNumber: ")
			.append(getItemNumber())
			.append(", description: ")
			.append(getDescription().getDefault())
			.append(", scale: ")
			.append(getScale().getLabel())
			.append(", railway: ")
			.append(getRailway().getLabel())
			.append("}")
			.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof RollingStock)) return false;
		
		RollingStock other = (RollingStock) obj;
		return new EqualsBuilder()
			.append(brand, other.brand)
			.append(itemNumber, other.itemNumber)
			.append(description, other.description)
			.append(railway, other.railway)
			.append(scale, other.scale)
			.append(era, other.era)
			.append(category, other.category)
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(15, 37)
			.append(brand)
			.append(itemNumber)
			.append(description)
			.append(railway)
			.append(scale)
			.append(era)
			.append(category)
			.toHashCode();
	}
	
	private String optionsKey(Option opt) {
		return opt.getOptionFamily().name();
	}
}