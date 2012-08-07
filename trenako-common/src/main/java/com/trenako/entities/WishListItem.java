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

import org.springframework.data.mongodb.core.index.Indexed;

import com.trenako.mapping.WeakDbRef;

/**
 * It represents a {@code WishList} item.
 * @author Carlo Micieli
 *
 */
public class WishListItem {

	@Indexed
	private String itemId;

	@NotNull(message = "wishList.rollingStock.required")
	private WeakDbRef<RollingStock> rollingStock;
	
	private String notes;
	private String priority;
	
	private Date addedAt;
	
	/**
	 * Creates an empty {@code WishListItem}.
	 */
	public WishListItem() {
	}
	
	/**
	 * Creates a new {@code WishListItem} for the provided rolling stock.
	 * @param rollingStock the rolling stock
	 */
	public WishListItem(RollingStock rollingStock) {
		this.setRollingStock(rollingStock);
		this.itemId = rollingStock.getSlug();
	}

	/**
	 * Creates a new {@code WishListItem}.
	 * @param rollingStock the rolling stock
	 * @param notes the item notes
	 * @param priority the item priority
	 * @param addedAt the timestamp
	 */
	public WishListItem(RollingStock rollingStock, String notes, String priority, Date addedAt) {
		this.setRollingStock(rollingStock);
		this.itemId = initItemId();
		this.notes = notes;
		this.priority = priority;
		this.addedAt = addedAt;
	}
	
	/**
	 * Returns the item id.
	 * @return the item id
	 */
	public String getItemId() {
		if (itemId == null) {
			itemId = initItemId();
		}
		return this.itemId;
	}
	
	/**
	 * Sets the item id.
	 * @param itemId the item id
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
	}

	/**
	 * Returns this item notes.
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets this item notes.
	 * @param notes the notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Returns the item priority in the wish list.
	 * @return the item priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * Sets the item priority in the wish list.
	 * @param priority the item priority
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	/**
	 * Returns the timestamp when this rolling stock was added to the list.
	 * @return the timestamp
	 */
	public Date getAddedAt() {
		return addedAt;
	}

	/**
	 * Sets the timestamp when this rolling stock was added to the list.
	 * @param addedAt the timestamp
	 */
	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}
	
	private String initItemId() {
		return rollingStock.getSlug();
	}
}