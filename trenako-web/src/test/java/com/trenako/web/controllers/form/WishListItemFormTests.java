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
import static com.trenako.test.TestDataBuilder.*;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.trenako.entities.Account;
import com.trenako.entities.Money;
import com.trenako.entities.Profile;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class WishListItemFormTests {
	@Test
	public void shouldCreateNewWishListItemForms() {
		WishListItemForm form = WishListItemForm.newForm(wishList(), rollingStock(), item(), null);
		
		assertNotNull(form);
		assertEquals(wishList().getSlug(), form.getSlug());
		assertEquals(rollingStock().getSlug(), form.getRsSlug());
		assertEquals(rollingStock().getLabel(), form.getRsLabel());
		assertEquals(BigDecimal.valueOf(0), form.getPrice());
		assertEquals(BigDecimal.valueOf(0), form.getPreviousPrice());
		assertEquals("[(high), (normal), (low)]", form.getPriorities().toString());
	}
	
	@Test
	public void shouldSetThePreviousPriceForWishListItems() {
		WishListItemForm form = WishListItemForm.newForm(wishList(), rollingStock(), itemWithPrice(BigDecimal.valueOf(100)), null);
	
		assertEquals(BigDecimal.valueOf(100), form.getPrice());
		assertEquals(BigDecimal.valueOf(100), form.getPreviousPrice());
		
		assertEquals("EUR100.00", form.previousPrice(owner()).toString());
	}
	
	@Test
	public void shouldReturnTheWishListItemToBeSavedWithFormValues() {
		WishListItemForm form = WishListItemForm.newForm(wishList(), rollingStock(), item(), null);
		
		form.getItem().setNotes("My notes");
		form.setPrice(BigDecimal.valueOf(100));
		
		WishListItem item = form.buildItem(owner());
		
		assertEquals("acme-123456_2012-09-09_10-00-00", item.getItemId());
		assertEquals("normal", item.getPriority());
		assertEquals("EUR100.00", item.getPrice().toString());
		assertEquals("My notes", item.getNotes());
		assertNotNull("Item date is null", item.getAddedAt());
	}
	
	@Test
	public void shouldUseProfileCurrencyForWishListItems() {
		WishListItemForm form = WishListItemForm.newForm(wishList(), rollingStock(), itemWithPrice(BigDecimal.valueOf(100)), null);
		
		WishListItem item = form.buildItem(ownerWithUSD());
		
		assertEquals("$100.00", item.getPrice().toString());
	}
	
	@Test
	public void shouldSetPriorityValueForWishListItems() {
		WishListItemForm form = WishListItemForm.newForm(wishList(), rollingStock(), itemWithPrice(BigDecimal.valueOf(100)), null);
		form.getItem().setPriority("low");
		
		WishListItem item = form.buildItem(ownerWithUSD());
		
		assertEquals("low", item.getPriority());
	}
	
	@Test
	public void shouldReturnTheWishListToRemoved() {
		WishListItemForm form = WishListItemForm.newForm(wishList(), rollingStock(), item("acme-123456", 100), null);
		
		WishListItem item = form.deletedItem(owner());
		
		assertEquals("acme-123456", item.getItemId());
		assertEquals("EUR100.00", item.getPrice().toString());
	}
	
	RollingStock rollingStock() {
		return new RollingStock.Builder(acme(), "123456")
			.scale(scaleH0())
			.railway(fs())
			.description("desc")
			.build();
	}
	
	WishListItem item() {
		WishListItem item = new WishListItem();
		item.setAddedAt(fulldate("2012/09/09 10:00:00.000"));
		return item;
	}
	
	WishListItem item(String itemId, double d) {
		WishListItem item = new WishListItem();
		item.setItemId(itemId);
		item.setPrice(new Money(BigDecimal.valueOf(d), "EUR"));
		item.setAddedAt(fulldate("2012/09/09 10:00:00.000"));
		return item;
	}
	
	WishListItem itemWithPrice(BigDecimal d) {
		WishListItem item = new WishListItem();
		item.setPrice(new Money(d, "EUR"));
		item.setAddedAt(fulldate("2012/09/09 10:00:00.000"));
		return item;
	}
	
	WishList wishList() {
		WishList wl = new WishList();
		wl.setName("My list");
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
