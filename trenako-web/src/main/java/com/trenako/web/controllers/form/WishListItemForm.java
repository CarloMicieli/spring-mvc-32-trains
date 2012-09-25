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

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.validator.constraints.Range;
import org.springframework.context.MessageSource;

import com.trenako.entities.Account;
import com.trenako.entities.Money;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;
import com.trenako.mapping.WeakDbRef;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.Priority;

/**
 * It represents a web form for {@code WishList} items.
 * @author Carlo Micieli
 *
 */
public class WishListItemForm {
	@NotNull
	private String slug;
	
	@NotNull
	private String rsSlug;
	private String rsLabel;
	
	@Range(min = 0, max = 9999, message = "wishListItem.price.range.notmet")
	private BigDecimal price;
	
	private BigDecimal previousPrice;
	private WishListItem item;
	private Iterable<LocalizedEnum<Priority>> priorities;
	
	private static final WishListItem DEFAULT_ITEM = new WishListItem();
		
	/**
	 * Creates a new empty {@code WishListItemForm}.
	 */
	public WishListItemForm() {
		this.item = new WishListItem();
	}
	
	private WishListItemForm(String slug, 
			WishListItem item,
			Iterable<LocalizedEnum<Priority>> priorities) {
		this.slug = slug;
		this.priorities = priorities;
		this.item = item;
	}

	private WishListItemForm(String slug, 
			String rsSlug, 
			String rsLabel, 
			WishListItem item, 
			BigDecimal price, 
			BigDecimal previousPrice, 
			Iterable<LocalizedEnum<Priority>> priorities) {
		
		this.slug = slug;
		this.rsLabel = rsLabel;
		this.rsSlug = rsSlug;
		this.item = item;
		this.price = price;
		this.previousPrice = previousPrice;
		this.priorities = priorities;		
	}

	/**
	 * Returns the rejected {@code WishListItemForm}.
	 * @param form the original form
	 * @param messageSource the message source
	 * @return the rejected form
	 */	
	public static WishListItemForm rejectForm(WishListItemForm form, 
			MessageSource messageSource) {

		form.priorities = 
			LocalizedEnum.list(Priority.class, messageSource, null);

		return form;
	}

	public static WishListItemForm newForm(WishList wishList, 
			RollingStock rs, 
			WishListItem item, 
			MessageSource messageSource) {
		
		return new WishListItemForm(
				wishList.getSlug(),
				rs.getSlug(),
				rs.getLabel(),
				item,
				Money.moneyValue(item.getPrice()),
				Money.moneyValue(item.getPrice()),
				LocalizedEnum.list(Priority.class, messageSource, null));
	}
	
	public static WishListItemForm jsForm(WishList wishList, MessageSource messageSource) {
		return new WishListItemForm(
				wishList.getSlug(),
				DEFAULT_ITEM,
				LocalizedEnum.list(Priority.class, messageSource, null));
	}
	
	/**
	 * Builds a new {@code WishListItem} using the form values.
	 * @param owner the {@code WishList} owner
	 * @return the new {@code WishList} item
	 */
	public WishListItem buildItem(Account owner) {
		return new WishListItem(
				getItem().getItemId(),
				rollingStock(getRsSlug(), getRsLabel()), 
				getItem().getNotes(), 
				priority(getItem().getPriority()), 
				addedAt(getItem().getAddedAt()),
				Money.newMoney(getPrice(), owner));
	}
	
	public WishListItem deletedItem(Account owner) {
		return new WishListItem(item.getItemId(), Money.newMoney(getPrice(), owner));
	}

	public Money previousPrice(Account owner) {
		return Money.newMoney(getPreviousPrice(), owner);
	}
	
	/**
	 * Returns the {@code WishListItem} slug.
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}

	/**
	 * Sets the {@code WishListItem} slug.
	 * @param slug the slug
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}

	/**
	 * Returns the {@code RollingStock} slug for the {@code WishListItem}.
	 * @return the {@code RollingStock} slug
	 */
	public String getRsSlug() {
		return rsSlug;
	}

	/**
	 * Sets the {@code RollingStock} slug for the {@code WishListItem}.
	 * @param rsSlug the {@code RollingStock} slug
	 */
	public void setRsSlug(String rsSlug) {
		this.rsSlug = rsSlug;
	}
	
	/**
	 * Returns the {@code RollingStock} label for the {@code WishListItem}.
	 * @return the {@code RollingStock} label
	 */	
	public String getRsLabel() {
		return rsLabel;
	}

	/**
	 * Sets the {@code RollingStock} label for the {@code WishListItem}.
	 * @param rsLabel the {@code RollingStock} label
	 */
	public void setRsLabel(String rsLabel) {
		this.rsLabel = rsLabel;
	}
	
	/**
	 * Returns the {@code WishListItem} price.
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Sets the {@code WishListItem} price.
	 * @param price the price
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getPreviousPrice() {
		return previousPrice;
	}

	public void setPreviousPrice(BigDecimal previousPrice) {
		this.previousPrice = previousPrice;
	}

	public WishListItem getItem() {
		if (item == null) {
			return DEFAULT_ITEM;
		}
		return item;
	}

	public void setItem(WishListItem item) {
		this.item = item;
	}

	public Iterable<LocalizedEnum<Priority>> getPriorities() {
		return priorities;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof WishListItemForm)) return false;
		
		WishListItemForm other = (WishListItemForm) obj;
		return new EqualsBuilder()
			.append(this.slug, other.slug)
			.append(this.rsSlug, other.rsSlug)
			.append(this.item, other.item)
			.isEquals();
	}
	
	private static Date addedAt(Date date) {
		if (date == null) {
			return new Date();
		}
		return date;
	}
	
	private static Priority priority(String prio) {
		return Priority.parse(prio, WishListItem.defaultPriority());
	}
	
	private static WeakDbRef<RollingStock> rollingStock(String rsSlug, String rsLabel) {
		return new WeakDbRef<RollingStock>(rsSlug, rsLabel); 
	}

}