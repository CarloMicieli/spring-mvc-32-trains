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
package com.trenako.services.view;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.util.Assert;

import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class ItemView {
	private final String listSlug;
	private final String listName;
	private final String itemSlug;
	private final String itemName;
	private final Date addedAt;
	
	private ItemView(String listSlug, String listName, String itemSlug, String itemName, Date addedAt) {
		this.listSlug = listSlug;
		this.listName = listName;
		this.itemSlug = itemSlug;
		this.itemName = itemName;
		this.addedAt = addedAt;
	}
	
	/**
	 * Creates a new item view for a {@code Collection} item.
	 * @param collection the rolling stocks collection
	 * @param item the collection item
	 * @return a {@code ItemView}
	 */
	public static ItemView createView(Collection collection, CollectionItem item) {
		Assert.notNull(item.getRollingStock(), "Item rolling stock is required");
		
		return new ItemView(
				collection.getSlug(),
				"collection",
				item.getRollingStock().getSlug(), 
				item.getRollingStock().getLabel(), 
				item.getAddedAt());
	}
	
	/**
	 * Creates a new item view for a {@code WishList} item.
	 * @param collection the rolling stocks wish list
	 * @param item the wish list item
	 * @return a {@code ItemView}
	 */
	public static ItemView createView(WishList wishList, WishListItem item) {
		Assert.notNull(item.getRollingStock(), "Item rolling stock is required");
		
		return new ItemView(
				wishList.getSlug(),
				wishList.getName(),
				item.getRollingStock().getSlug(), 
				item.getRollingStock().getLabel(), 
				item.getAddedAt());
	}

	/**
	 * Returns the list slug.
	 * @return the list slug
	 */
	public String getListSlug() {
		return listSlug;
	}

	/**
	 * Returns the list name.
	 * @return the list name
	 */
	public String getListName() {
		return listName;
	}

	/**
	 * Returns the item slug.
	 * @return the item slug
	 */
	public String getItemSlug() {
		return itemSlug;
	}

	/**
	 * Returns the item name.
	 * @return the item name
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Returns the timestamp when the item was added.
	 * @return a timestamp
	 */
	public Date getAddedAt() {
		return addedAt;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof ItemView)) return false;
		
		ItemView other = (ItemView) obj;
		return new EqualsBuilder()
			.append(this.itemName, other.itemName)
			.append(this.itemSlug, other.itemSlug)
			.append(this.itemName, other.itemName)
			.append(this.itemSlug, other.itemSlug)
			.append(this.addedAt, other.addedAt)
			.isEquals();
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("itemView{")
			.append("listSlug: ").append(listSlug)
			.append(", listName: ").append(listName)
			.append(", itemSlug: ").append(itemSlug)
			.append(", itemName: ").append(itemName)
			.append("}")
			.toString();
	}
}
