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
package com.trenako.web.controllers.form;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

import com.trenako.entities.Money;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishListItem;
import com.trenako.mapping.WeakDbRef;
import com.trenako.values.Priority;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class WishListItemForm {
	@NotNull
	private String slug;
	@NotNull
	private String rsSlug;
	private String rsLabel;
	
	private String itemId;
	private String notes;
	private String priority;
	private Date addedAt;

	public WishListItemForm() {
	}

	public WishListItemForm(String slug, String rsSlug) {
		this.slug = slug;
		this.rsSlug = rsSlug;
	}

	public WishListItem newItem() {
		return new WishListItem(
			getItemId(),	
			new WeakDbRef<RollingStock>(getRsSlug(), getRsLabel()), 
			getNotes(), 
			Priority.parse(getPriority()), 
			getAddedAt());
	}
	
	public WishListItem deletedItem() {
		return new WishListItem(getItemId());
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getRsSlug() {
		return rsSlug;
	}

	public void setRsSlug(String rsSlug) {
		this.rsSlug = rsSlug;
	}
	
	public String getRsLabel() {
		return rsLabel;
	}

	public void setRsLabel(String rsLabel) {
		this.rsLabel = rsLabel;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getPriority() {
		if (StringUtils.isBlank(priority)) {
			priority = "normal";
		}
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof WishListItemForm)) return false;
		
		WishListItemForm other = (WishListItemForm) obj;
		return new EqualsBuilder()
			.append(this.slug, other.slug)
			.append(this.rsSlug, other.rsSlug)
			.append(this.itemId, other.itemId)
			.isEquals();
	}

	public Money previousPrice() {
		// TODO Auto-generated method stub
		return null;
	}
}
