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

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.context.MessageSource;

import com.trenako.entities.Account;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.Money;
import com.trenako.entities.RollingStock;
import com.trenako.mapping.WeakDbRef;
import com.trenako.values.Condition;
import com.trenako.values.LocalizedEnum;

/**
 * It represents a web form for {@code CollectionItem}s.
 * @author Carlo Micieli
 *
 */
public class CollectionItemForm {

	private CollectionItem item;
	
	@NotNull
	private String rsSlug; 
	
	@Range(min = 0, max = 9999, message = "collectionItem.price.range.notmet")
	private BigDecimal price;

	private boolean alreadyInCollection;
	
	private Iterable<LocalizedEnum<Condition>> conditionsList;
	
	/**
	 * Creates a new empty {@code CollectionItemForm}.
	 */
	public CollectionItemForm() {
		item = new CollectionItem();
	}
	
	private CollectionItemForm(Iterable<LocalizedEnum<Condition>> conditionsList) {
		this.conditionsList = conditionsList;
	}
	
	private CollectionItemForm(
			String rsSlug,
			CollectionItem item, 
			BigDecimal price,
			Iterable<LocalizedEnum<Condition>> conditionsList) {
		this.rsSlug = rsSlug;
		this.item = item;
		this.conditionsList = conditionsList;
		this.price = price;
	}

	/**
	 * Creates a new form for Javascript calls.
	 * @param messageSource
	 * @return
	 */
	public static CollectionItemForm jsForm(MessageSource messageSource) {
		return new CollectionItemForm(
				LocalizedEnum.list(Condition.class, messageSource, null));
	}
	
	/**
	 * Creates a new form for {@code CollectionItemForm} insertion.
	 * @param rs the rolling stock
	 * @param messageSource the message source
	 * @return the new form
	 */
	public static CollectionItemForm newForm(
			RollingStock rs, 
			MessageSource messageSource) {
		
		CollectionItem newItem = new CollectionItem(rs);
		BigDecimal price = Money.moneyValue(newItem.getPrice());
		return new CollectionItemForm(
				rs.getSlug(),
				newItem,
				price,
				LocalizedEnum.list(Condition.class, messageSource, null));
	}

	public CollectionItem deletedItem(RollingStock rs, Account owner) {
		CollectionItem ci = new CollectionItem();
		ci.setItemId(getItem().getItemId());
		ci.setCategory(rs.getCategory());
		return ci;
	}
	
	/**
	 * Returns the {@code CollectionItem} object for insertion filled with form values.
	 * @param rs the rolling stock
	 * @param owner the owner
	 * @return the {@code CollectionItem}
	 */
	public CollectionItem newItem(RollingStock rs, Account owner) {
		return new CollectionItem(
				WeakDbRef.buildRef(rs),
				rs.getCategory(),
				getItem().getAddedAt(),
				getItem().getNotes(),
				Money.newMoney(getPrice(), owner),
				condition(getItem()));
	}
	
	/**
	 * Returns the {@code CollectionItem} object for editing filled with form values.
	 * @param rs the rolling stock
	 * @param owner the owner
	 * @return the {@code CollectionItem}
	 */
	public CollectionItem editItem(RollingStock rs, Account owner) {
		return new CollectionItem(
				getItem().getItemId(),
				WeakDbRef.buildRef(rs),
				rs.getCategory(),
				getItem().getAddedAt(),
				getItem().getNotes(),
				Money.newMoney(getPrice(), owner),
				condition(getItem()));
	}
	
	public String getRsSlug() {
		return rsSlug;
	}

	public void setRsSlug(String rsSlug) {
		this.rsSlug = rsSlug;
	}

	public void setItem(CollectionItem item) {	
		this.item = item;
	}
	
	public CollectionItem getItem() {	
		return item;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Iterable<LocalizedEnum<Condition>> getConditionsList() {
		return conditionsList;
	}
	
	public boolean isAlreadyInCollection() {
		return alreadyInCollection;
	}

	public void setAlreadyInCollection(boolean alreadyInCollection) {
		this.alreadyInCollection = alreadyInCollection;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof CollectionItemForm)) return false;
		
		CollectionItemForm other = (CollectionItemForm) obj;
		return this.item.equals(other.item);
	}
	
	private static Condition condition(CollectionItem item) {
		if (StringUtils.isBlank(item.getCondition())) {
			return null;
		}
		
		return Condition.parse(item.getCondition());
	}
}
