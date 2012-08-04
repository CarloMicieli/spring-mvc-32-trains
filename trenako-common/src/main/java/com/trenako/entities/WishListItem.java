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

import com.trenako.mapping.WeakDbRef;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class WishListItem {
	@NotNull(message = "wishList.rollingStock.required")
	private WeakDbRef<RollingStock> rollingStock;
	
	private String notes;
	private String priority;
	
	/**
	 * Creates an empty {@code WishListItem}.
	 */
	public WishListItem() {
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
	 * Returns the notes.
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the notes.
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
}
