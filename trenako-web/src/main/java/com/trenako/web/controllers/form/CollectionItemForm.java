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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.context.MessageSource;

import com.trenako.entities.Account;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.Money;
import com.trenako.entities.RollingStock;
import com.trenako.values.Condition;
import com.trenako.values.LocalizedEnum;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionItemForm {

	private CollectionItem item;
	
	@Range(min = 0, max = 9999, message = "collectionItem.price.range.notmet")
	private BigDecimal price;
	
	private BigDecimal previousPrice;
	
	private boolean alreadyInCollection;
	
	private Iterable<LocalizedEnum<Condition>> conditionsList;
	
	public CollectionItemForm() {
	}
	
	public CollectionItemForm(
			CollectionItem item, 
			BigDecimal price,
			BigDecimal previousPrice,
			Iterable<LocalizedEnum<Condition>> conditionsList) {
		this.item = item;
		this.conditionsList = conditionsList;
		this.price = price;
		this.previousPrice = previousPrice;
	}

	public static CollectionItemForm newForm(
			RollingStock rs, 
			MessageSource messageSource) {
		return newForm(rs, new Date(), messageSource);
	}
	
	public static CollectionItemForm newForm(
			RollingStock rs, 
			Date addedAt,
			MessageSource messageSource) {

		CollectionItem newItem = new CollectionItem(rs, addedAt);	
		BigDecimal price = Money.moneyValue(newItem.getPrice());
		return new CollectionItemForm(newItem,
				price,
				price,
				LocalizedEnum.list(Condition.class, messageSource, null));
	}

	public CollectionItem collectionItem(Account owner) {
		return new CollectionItem(
				getItem().getRollingStock(),
				getItem().getCategory(),
				getItem().getAddedAt(),
				getItem().getNotes(),
				Money.newMoney(getPrice(), owner),
				condition(getItem()));
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

	public BigDecimal getPreviousPrice() {
		return previousPrice;
	}

	public void setPreviousPrice(BigDecimal previousPrice) {
		this.previousPrice = previousPrice;
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
