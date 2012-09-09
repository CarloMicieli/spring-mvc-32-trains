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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.data.mongodb.core.index.Indexed;

import com.trenako.mapping.WeakDbRef;
import com.trenako.utility.Slug;
import com.trenako.validation.constraints.ValidMoney;
import com.trenako.values.Condition;

/**
 * It represents an item of rolling stocks collections.
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionItem {

	@NotNull(message = "item.itemId.required")
	@Indexed
	private String itemId;

	@NotNull(message = "item.rollingStock.required")
	private WeakDbRef<RollingStock> rollingStock;

	@ValidMoney(message = "item.price.money.notmet")
	private Money price;

	private String condition;

	private String notes;

	private String category;

	@NotNull(message = "item.addedAt.required")
	@Past(message = "item.addedAt.past.notmet")
	private Date addedAt;

	/**
	 * Creates an empty {@code CollectionItem}.
	 */
	public CollectionItem() {
	}

	/**
	 * Creates a new {@code CollectionItem} for the provided rolling stock
	 * @param rs the rolling stock
	 * @param addedAt the date
	 */
	public CollectionItem(RollingStock rs) {
		this.rollingStock = rollingStock(rs);
		this.category = rs.getCategory();
	}
	
	public CollectionItem(RollingStock rs, Date addedAt) {
		this(rollingStock(rs), rs.getCategory(), addedAt, null, null, null);
	}
	
	/**
	 * Creates a new {@code CollectionItem} for the provided rolling stock
	 * @param rs the rolling stock
	 * @param addedAt the date
	 * @param notes the notes
	 * @param price the purchasing price
	 * @param condition the item conditions
	 */
	public CollectionItem(RollingStock rs, Date addedAt, String notes, Money price, Condition condition) {
		this(rollingStock(rs), rs.getCategory(), addedAt, notes, price, condition);
	}
	
	/**
	 * Creates a new {@code CollectionItem} for the provided rolling stock
	 * @param rs the rolling stock
	 * @param category the rolling stock category
	 * @param addedAt the date
	 * @param notes the notes
	 * @param price the purchasing price
	 * @param condition the item conditions
	 */
	public CollectionItem(WeakDbRef<RollingStock> rs, String category, Date addedAt, String notes, Money price, Condition condition) {
		this.rollingStock = rs;
		this.category = category;
		this.addedAt = addedAt;
		this.notes = notes;
		this.price = price;
		this.condition = condition(condition);
		
		this.itemId = itemId(this.rollingStock, this.addedAt);
	}
	
	/**
	 * Returns the {@code CollectionItem} id.    
	 * @return the id
	 */
	public String getItemId() {
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
	 * Returns the price.
	 * @return the price
	 */
	public Money getPrice() {
		if (price == null) {
			return Money.nullMoney();
		}
		return price;
	}

	/**
	 * Sets the rolling stock price.
	 * @param price the price
	 */
	public void setPrice(Money price) {
		this.price = price;
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
		return new EqualsBuilder()
			.append(this.itemId, other.itemId)
			.append(this.rollingStock, other.rollingStock)
			.append(this.addedAt, other.addedAt)
			.isEquals();
	}
	
	private static String itemId(WeakDbRef<RollingStock> rs, Date addedAt) {
		return new StringBuilder()
			.append(Slug.encode(addedAt))
			.append("_")
			.append(rs.getSlug())
			.toString();
	}
	
	private static WeakDbRef<RollingStock> rollingStock(RollingStock rs) {
		return WeakDbRef.buildRef(rs);
	}
	
	private static String condition(Condition cond) {
		if (cond == null) {
			return null;
		}
		
		return cond.label();
	}
}