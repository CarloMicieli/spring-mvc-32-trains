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

import javax.validation.Valid;

import org.hibernate.validator.constraints.Range;

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
	//@Valid
	private CollectionItem item;
	private RollingStock rs;
	private boolean alreadyInCollection;
	private Iterable<LocalizedEnum<Condition>> conditionsList;
	
	@Range(min = 0)
	private int price;
	
	public CollectionItemForm() {
	}
	
	public CollectionItemForm(CollectionItem item, 
			RollingStock rs, 
			Iterable<LocalizedEnum<Condition>> conditionsList,
			boolean included) {
		this.item = item;
		this.rs = rs;
		this.alreadyInCollection = included;
		this.conditionsList = conditionsList;
	}

	public RollingStock getRs() {
		return rs;
	}

	public void setRs(RollingStock rs) {
		this.rs = rs;
	}

	public CollectionItem getItem() {	
		item.setPrice(new Money(getPrice(), "USD"));
		item.setItemId(item.getItemId());
		return item;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Iterable<LocalizedEnum<Condition>> getConditionsList() {
		return conditionsList;
	}

	public void setConditionsList(Iterable<LocalizedEnum<Condition>> conditionsList) {
		this.conditionsList = conditionsList;
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
}
