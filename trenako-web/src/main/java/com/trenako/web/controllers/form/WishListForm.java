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

import javax.validation.Valid;

import org.hibernate.validator.constraints.Range;
import org.springframework.context.MessageSource;

import com.trenako.entities.Account;
import com.trenako.entities.Money;
import com.trenako.entities.WishList;
import com.trenako.values.LocalizedEnum;
import com.trenako.values.Visibility;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class WishListForm {

	@Valid
	private WishList wishList;
	
	@Range(min = 0, message = "wishlist.budget.range.notmet")
	private BigDecimal budget;
	
	private Iterable<LocalizedEnum<Visibility>> visibilities;
	
	public WishListForm() {
	}

	public WishListForm(WishList wishList, BigDecimal budget, Iterable<LocalizedEnum<Visibility>> visibilities) {
		this.wishList = wishList;
		this.budget = budget;
		this.visibilities = visibilities;
	}
	
	public static WishListForm newForm(WishList wishList, MessageSource ms) {
		return new WishListForm(wishList,
				BigDecimal.valueOf(0),
				LocalizedEnum.list(Visibility.class, ms, null));
	}
	
	public WishList getWishList() {
		return wishList;
	}

	public void setWishList(WishList wishList) {
		this.wishList = wishList;
	}

	public WishList build(Account owner) {
		WishList wishList = getWishList();
		wishList.setBudget(new Money(getBudget(), "EUR"));
		wishList.setOwner(owner.getSlug());
		return wishList;
	}
	
	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public Iterable<LocalizedEnum<Visibility>> getVisibilities() {
		return visibilities;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof WishListForm)) return false;
		
		WishListForm other = (WishListForm) obj;
		return this.wishList.equals(other.wishList) &&
				this.budget.equals(other.budget);	
	}
}
