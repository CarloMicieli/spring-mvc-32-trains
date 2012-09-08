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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import com.trenako.entities.Account;
import com.trenako.entities.Money;
import com.trenako.entities.Profile;
import com.trenako.entities.WishList;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class WishListFormTests {

	@Mock MessageSource messageSource;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldCreateNewWishListForms() {
		WishListForm form = WishListForm.newForm(messageSource);
		
		assertNotNull(form);
		assertEquals(new WishList(), form.getWishList());
		assertEquals("{(public)=checked, (private)=}", form.getVisibilities().toString());
		assertEquals(BigDecimal.valueOf(0), form.getBudget());
	}
		
	@Test
	public void shouldCreateWishListWithFormValues() {
		WishListForm form = WishListForm.newForm(messageSource);
		form.setBudget(BigDecimal.valueOf(150.50));
		form.setWishList(wishList());

		WishList wishList = form.wishListFor(owner());
		
		assertEquals("bob-my-list", wishList.getSlug());
		assertEquals("bob", wishList.getOwner());
		assertEquals("EUR150.50", wishList.getBudget().toString());
		assertEquals("public", wishList.getVisibility());
	}
	
	@Test
	public void shouldUseTheCurrencyCodeFromUserProfileCreatingWishLists() {
		WishListForm form = WishListForm.newForm(messageSource);
		form.setBudget(BigDecimal.valueOf(150.50));
		
		WishList wlUSD = form.wishListFor(owner("USD"));
		assertEquals("$150.50", wlUSD.getBudget().toString());
		
		WishList wlGBP = form.wishListFor(owner("GBP"));
		assertEquals("GBP150.50", wlGBP.getBudget().toString());		
	}
	
	@Test
	public void shouldSelectCorrectVisibilityValueForWishListForms() {
		WishListForm newForm = WishListForm.newForm(messageSource);
		assertEquals("{(public)=checked, (private)=}", newForm.getVisibilities().toString());
		
		WishListForm editFormPublic = WishListForm.editForm(wishList(), messageSource);
		assertEquals("{(public)=checked, (private)=}", editFormPublic.getVisibilities().toString());
		
		WishListForm editFormPrivate = WishListForm.editForm(privateWishList(), messageSource);
		assertEquals("{(public)=, (private)=checked}", editFormPrivate.getVisibilities().toString());
	}
	
	@Test
	public void shouldCreateEditingWishListForms() {
		WishListForm form = WishListForm.editForm(privateWishList(), messageSource);
		
		assertNotNull("Editing form is null", form);
		
		assertEquals(privateWishList(), form.getWishList());
		assertEquals(BigDecimal.valueOf(100), form.getBudget());
		assertEquals("{(public)=, (private)=checked}", form.getVisibilities().toString());
	}
	
	@Test
	public void shouldEditWishListUsingTheFormValues() {
		WishListForm form = WishListForm.editForm(privateWishList(), messageSource);
		form.setBudget(BigDecimal.valueOf(250.50));
		form.setWishList(wishList());
		
		WishList wishList = form.wishListFor(owner());
		
		assertEquals("bob-my-list", wishList.getSlug());
		assertEquals("bob", wishList.getOwner());
		assertEquals("EUR250.50", wishList.getBudget().toString());
		assertEquals("public", wishList.getVisibility());
	}
	
	WishList wishList() {
		WishList wl = new WishList();
		wl.setName("My list");
		wl.setNotes("My notes");
		wl.setVisibility("public");
		return wl;
	}

	WishList privateWishList() {
		WishList wl = new WishList();
		wl.setName("My list");
		wl.setNotes("My notes");
		wl.setVisibility("private");
		wl.setBudget(new Money(10000, "USD"));
		return wl;
	}
	
	Account owner(String currency) {
		return new Account.Builder("mail@mail.com")
			.id(new ObjectId())
			.displayName("Bob")
			.profile(new Profile(currency))
			.build();
	}
		
	Account owner() {
		return new Account.Builder("mail@mail.com")
			.id(new ObjectId())
			.displayName("Bob")
			.build();
	}
}
