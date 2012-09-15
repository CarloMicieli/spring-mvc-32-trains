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
import org.springframework.context.MessageSource;

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

	private MessageSource messageSource;
	
	@Test
	public void shouldCreateFormsForCollectionItemsCreations() {
		CollectionItemForm form = CollectionItemForm.newForm(rollingStock(), messageSource);
		
		assertEquals("[(new), (pre-owned)]", form.getConditionsList().toString());
		assertEquals("acme-123456", form.getRsSlug());
		assertEquals("{slug: acme-123456, label: ACME 123456}", form.getItem().getRollingStock().toCompleteString());
		assertEquals(rollingStock().getCategory(), form.getItem().getCategory());
		assertEquals(BigDecimal.valueOf(0), form.getPrice());
		assertNull(form.getItem().getNotes());
		assertNull(form.getItem().getCondition());
		assertNull(form.getItem().getAddedAt());
		assertNull(form.getItem().getItemId());
	}
	
	@Test
	public void shouldReturnCollectionItemsToBeAdded() {
		CollectionItemForm form = CollectionItemForm.newForm(rollingStock(), messageSource);
		
		form.setPrice(BigDecimal.valueOf(100));
		CollectionItem item = form.getItem();
		item.setNotes("My notes");
		item.setCondition("new");
		item.setAddedAt(date("2012/09/01"));
		
		CollectionItem newItem = form.newItem(rollingStock(), georgeStephenson());
		
		assertEquals("My notes", newItem.getNotes());
		assertEquals("new", newItem.getCondition());
		assertEquals(date("2012/09/01"), newItem.getAddedAt());
		assertEquals("2012-09-01_acme-123456", newItem.getItemId());
		assertEquals("$100.00", newItem.getPrice().toString());
	}
	
	@Test
	public void shouldReturnCollectionItemsToBeUpdated() {
		
		CollectionItem item = postedForm().editItem(rollingStock(), georgeStephenson());
		
		assertEquals("2012-09-01_acme-123456", item.getItemId());
		assertEquals("My notes", item.getNotes());
	}
	
	@Test
	public void shouldReturnCollectionItemsToBeDeleted() {
		CollectionItemForm form = CollectionItemForm.newForm(rollingStock(), messageSource);
		CollectionItem item = new CollectionItem();
		item.setItemId("2012-09-01_acme-123456");
		form.setItem(item);
		
		CollectionItem deletedItem = form.deletedItem(rollingStock(), georgeStephenson());
		
		assertEquals("2012-09-01_acme-123456", deletedItem.getItemId());
		assertEquals(rollingStock().getCategory(), deletedItem.getCategory());
	}
	
	@Test
	public void shuoldCreateFormsForJavascriptCalls() {
		CollectionItemForm form = CollectionItemForm.jsForm(messageSource);
		
		assertEquals("[(new), (pre-owned)]", form.getConditionsList().toString());
		assertNull("Rolling stock slug is not null", form.getRsSlug());
		assertNull("Collection item is not null", form.getItem());
	}
	
	CollectionItemForm postedForm() {
		CollectionItem item = new CollectionItem();
		item.setItemId("2012-09-01_acme-123456");
		item.setAddedAt(date("2012-09-09"));
		item.setNotes("My notes");
		
		CollectionItemForm form = new CollectionItemForm();
		form.setPrice(BigDecimal.valueOf(100));
		form.setItem(item);
		form.setRsSlug("acme-123456");
		
		return form;
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
