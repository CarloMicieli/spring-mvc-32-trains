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

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.trenako.entities.Account;
import com.trenako.entities.Profile;
import com.trenako.entities.WishList;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class WishListFormTests {

	@Test
	public void shouldCreateNewWishListForms() {
		WishListForm form = WishListForm.newForm(wishList(), null);
		
		assertNotNull(form);
		assertEquals(wishList(), form.getWishList());
		assertEquals("{(public)=checked, (private)=}", form.getVisibilities().toString());
		assertEquals(BigDecimal.valueOf(0), form.getBudget());
	}
	
	
	@Test
	public void shouldCreateWishListWithFormValues() {
		WishListForm form = WishListForm.newForm(wishList(), BigDecimal.valueOf(150.50), null);
		
		WishList wishList = form.wishListFor(owner());
		
		assertEquals("bob-my-list", wishList.getSlug());
		assertEquals("bob", wishList.getOwner());
		assertEquals("EUR150.50", wishList.getBudget().toString());
		assertEquals("public", wishList.getVisibility());
	}
	
	@Test
	public void shouldUseTheCurrencyCodeFromUserProfile() {
		WishListForm form = WishListForm.newForm(wishList(), BigDecimal.valueOf(150.50), null);
		
		WishList wishList = form.wishListFor(ownerWithUSD());
		
		assertEquals("$150.50", wishList.getBudget().toString());
	}
	
	@Test
	public void shouldSetWishListVisibility() {
		WishListForm form = WishListForm.newForm(privateWishList(), BigDecimal.valueOf(150.50), null);
		
		WishList wishList = form.wishListFor(owner());
		
		assertEquals("private", wishList.getVisibility());
	}
	
	@Test
	public void shouldSetPrivateVisibilitSelected() {
		WishListForm form = WishListForm.newForm(privateWishList(), BigDecimal.valueOf(150.50), null);
		assertEquals("{(public)=, (private)=checked}", form.getVisibilities().toString());
	}
	
	WishList wishList() {
		WishList wl = new WishList();
		wl.setName("My list");
		return wl;
	}

	WishList privateWishList() {
		WishList wl = new WishList();
		wl.setName("My list");
		wl.setVisibility("private");
		return wl;
	}
	
	Account ownerWithUSD() {
		return new Account.Builder("mail@mail.com")
			.id(new ObjectId())
			.displayName("Bob")
			.profile(new Profile("USD"))
			.build();
	}
	
	Account owner() {
		return new Account.Builder("mail@mail.com")
			.id(new ObjectId())
			.displayName("Bob")
			.build();
	}
}
