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

import static com.trenako.test.TestDataBuilder.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import com.trenako.entities.Account;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.Profile;
import com.trenako.entities.RollingStock;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class CollectionItemFormTests {

	@Test
	public void shouldInitializeFormForCollectionItemsCreation() {
		CollectionItemForm form = CollectionItemForm.newForm(rollingStock(), fulldate("2012/07/12 21:12:44.002"), null);
		
		assertEquals("[(new), (pre-owned)]", form.getConditionsList().toString());
		assertEquals("{slug: acme-123456, label: ACME 123456}", form.getItem().getRollingStock().toCompleteString());
		assertEquals("electric-locomotives", form.getItem().getCategory());
		assertNull(form.getItem().getNotes());
		assertNull(form.getItem().getCondition());
		assertEquals(fulldate("2012/07/12 21:12:44.002"), form.getItem().getAddedAt());
		assertEquals("2012-07-12_acme-123456", form.getItem().getItemId());
	}
	
	@Test
	public void shouldReturnCollectionItemsToBeAdded() {
		CollectionItemForm form = CollectionItemForm.newForm(rollingStock(), fulldate("2012/07/12 21:12:44.002"), null);
		
		form.setPrice(BigDecimal.valueOf(100));
		CollectionItem item = form.getItem();
		item.setNotes("My notes");
		item.setCondition("new");
		item.setAddedAt(date("2012/09/01"));
		
		CollectionItem newItem = form.collectionItem(rollingStock(), georgeStephenson());
		
		assertEquals("My notes", newItem.getNotes());
		assertEquals("new", newItem.getCondition());
		assertEquals(date("2012/09/01"), newItem.getAddedAt());
		assertEquals("2012-09-01_acme-123456", newItem.getItemId());
		assertEquals("$100.00", newItem.getPrice().toString());
	}
	
	
	RollingStock rollingStock() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.railway(fs())
			.scale(scaleH0())
			.category("electric-locomotives")
			.description("desc")
			.build();
		return rs;		
	}
	
	Account georgeStephenson() {
		return new Account.Builder("mail@mail.com")
			.profile(new Profile("USD"))
			.displayName("George Stephenson")
			.build();
	}
}
