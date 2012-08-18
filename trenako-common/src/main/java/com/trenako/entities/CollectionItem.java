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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.mongodb.core.index.Indexed;

import com.trenako.format.annotations.IntegerFormat;
import com.trenako.format.annotations.IntegerFormat.Style;
import com.trenako.mapping.WeakDbRef;
import com.trenako.utility.Slug;
import com.trenako.values.Condition;

/**
 * It represents a rolling stocks collection item.
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionItem {

	@Indexed
	private String itemId;

	@NotNull(message = "item.rollingStock.required")
	private WeakDbRef<RollingStock> rollingStock;

	@Range(min = 0, max = 999900, message = "item.price.range.notmet")
	@IntegerFormat(style = Style.CURRENCY)
	private int price;

	private String condition;

	private String notes;

	private String category;

	@Range(min = 1, max = 99, message = "item.quantity.range.notmet")
	private int quantity = 1;

	@NotNull(message = "item.addedAt.required")
	@Past(message = "item.addedAt.past.notmet")
	private Date addedAt;

	/**
	 * Creates an empty {@code CollectionItem}.
	 */
	public CollectionItem() {
	}

	private CollectionItem(Builder b) {
		setRollingStock(b.rs);
		
		this.addedAt = b.addedAt;
		this.price = b.price;
		this.condition = b.condition;
		this.notes = b.notes;
		this.quantity = b.quantity;
		this.category = b.category;
	}
    
	public static class Builder {
		// required fields
		private final RollingStock rs;
		
		// optional fields
		private String category = null;
		private Date addedAt = null;
		private int price = 0;
		private String condition = null;
		private String notes = null;
		private int quantity = 1;
		
		public Builder(RollingStock rs) {
			this.rs = rs;
		}

		public Builder addedAt(Date date) {
			addedAt = date;
			return this;
		}

		public Builder condition(Condition cond) {
			condition = cond.label();
			return this;
		}
		
		public Builder category(String category) {
			this.category = category;
			return this;
		}

		public Builder notes(String n) {
			notes = n;
			return this;
		}

		public Builder price(int p) {
			price = p;
			return this;
		}

		public Builder quantity(int q) {
			quantity = q;
			return this;
		}
		
		public CollectionItem build() {
			return new CollectionItem(this);
		}
	}
    
	/**
	 * Returns the {@code CollectionItem} id.    
	 * @return the id
	 */
	public String getItemId() {
		if (itemId == null) {
			itemId = new StringBuilder()
				.append(getRollingStock().getSlug())
				.append("-")
				.append(Slug.encode(getAddedAt()))
				.toString();
		}
		return itemId;
	}

	/**
	 * Sets the {@code CollectionItem} id.    
	 * @param id the id
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 * Returns the rolling stock.
	 * @return the rolling stock
	 */
	public WeakDbRef<RollingStock> getRollingStock() {
		return rollingStock;
	}

	/**
	 * Sets the rolling stock.
	 * @param rollingStock the rolling stock
	 */
	public void setRollingStock(WeakDbRef<RollingStock> rollingStock) {
		this.rollingStock = rollingStock;
	}

	/**
	 * Sets the rolling stock.
	 * @param rollingStock the rolling stock
	 */
	public void setRollingStock(RollingStock rollingStock) {
		this.rollingStock = WeakDbRef.buildRef(rollingStock);
		this.category = rollingStock.getCategory();
	}

	/**
	 * Returns the rolling stock category.
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the rolling stock category.
	 * @param category the category
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Returns the date in which this rolling stock was purchased.
	 * @return the purchasing date
	 */
	public Date getAddedAt() {
		if (addedAt == null) {
			addedAt = new Date();
		}
		return addedAt;
	}

	/**
	 * Sets the date in which this rolling stock was purchased.
	 * @param addedAt the purchasing date
	 */
	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}

	/**
	 * Returns the price as currency value.
	 * @return the price
	 */
	public BigDecimal price() {
		return (new BigDecimal(getPrice()))
				.divide(new BigDecimal(100));
	}

	/**
	 * Returns the price.
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Sets the rolling stock price.
	 * @param price the price
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Returns the rolling stock model quantity.
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets the rolling stock model quantity.
	 * @param quantity the quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Returns the rolling stock condition.
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * Sets the rolling stock condition.
	 * @param condition the condition
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * Returns the entry notes.
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the entry notes.
	 * @param notes the notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof CollectionItem)) return false;

		CollectionItem other = (CollectionItem) obj;
		return this.getItemId().equals(other.getItemId());
	}
}