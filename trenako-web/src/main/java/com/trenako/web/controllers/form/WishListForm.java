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
import java.util.HashMap;
import java.util.LinkedHashMap;

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
	
	@Range(min = 0, max = 9999, message = "wishlist.budget.range.notmet")
	private BigDecimal budget;
	
	private Iterable<LocalizedEnum<Visibility>> visibilities;
	
	/**
	 * Creates an empty {@code WishListForm}.
	 */
	public WishListForm() {
	}

	private WishListForm(WishList wishList, BigDecimal budget, Iterable<LocalizedEnum<Visibility>> visibilities) {
		this.wishList = wishList;
		this.budget = budget;
		this.visibilities = visibilities;
	}
	
	/**
	 * Creates a new {@code WishList} form the provided user.
	 * @param wishList the {@code WishList}
	 * @param messageSource the message source for localized labels
	 * @return the form
	 */
	public static WishListForm newForm(WishList wishList, MessageSource messageSource) {
		return WishListForm.newForm(wishList, budget(wishList.getBudget()), messageSource);
	}
	
	/**
	 * Creates a new {@code WishList} form the provided user.
	 * @param wishList the {@code WishList}
	 * @param budget the budget
	 * @param messageSource the message source for localized labels
	 * @return the form
	 */
	public static WishListForm newForm(WishList wishList, BigDecimal budget, MessageSource messageSource) {
		return new WishListForm(wishList,
				budget,
				LocalizedEnum.list(Visibility.class, messageSource, null));
	}
	
	public WishList getWishList() {
		return wishList;
	}

	public void setWishList(WishList wishList) {
		this.wishList = wishList;
	}

	public WishList wishListFor(Account owner) {
		WishList wishList = new WishList(owner, 
				getWishList().getName(), 
				visibility(getWishList().getVisibility()),
				budget(owner, getBudget()));
		return wishList;
	}
	
	public BigDecimal getBudget() {
		return budget;
	}
	
	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	/**
	 * Returns the {@code Visibility} list.
	 * @return
	 */
	public HashMap<LocalizedEnum<Visibility>, String> getVisibilities() {
		
		HashMap<LocalizedEnum<Visibility>, String> map = 
				new LinkedHashMap<LocalizedEnum<Visibility>, String>(Visibility.values().length);
		
		for (LocalizedEnum<Visibility> val : visibilities) {
			String checked = val.getValue().equals(getWishList().getVisibilityValue()) ? "checked" : "";
			map.put(val, checked);
		}
		
		return map;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (!(obj instanceof WishListForm)) return false;
		
		WishListForm other = (WishListForm) obj;
		return this.wishList.equals(other.wishList) &&
				this.budget.equals(other.budget);	
	}
	
	private static BigDecimal budget(Money money) {
		int budget = (money != null) ? money.getValue() : 0;
		return 	BigDecimal.valueOf(budget).divide(Money.MONEY_VALUE_FACTOR);
	}
	
	private static Visibility visibility(String vis) {
		return Visibility.parse(vis, WishList.defaultVisibility());
	}
	
	private static Money budget(Account owner, BigDecimal val) {
		return new Money(val, owner.getProfile().getCurrency());
	}
}
