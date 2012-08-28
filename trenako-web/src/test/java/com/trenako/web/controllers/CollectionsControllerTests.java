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
package com.trenako.web.controllers;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static com.trenako.test.TestDataBuilder.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Account;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.security.AccountDetails;
import com.trenako.services.CollectionsService;
import com.trenako.services.RollingStocksService;
import com.trenako.values.Condition;
import com.trenako.values.LocalizedEnum;
import com.trenako.web.controllers.form.CollectionItemForm;
import com.trenako.web.security.UserContext;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CollectionsControllerTests {

	@Mock UserContext mockUserContext;
	@Mock RedirectAttributes mockRedirectAtts;
	@Mock BindingResult mockResults;
	@Mock CollectionsService service;
	@Mock RollingStocksService rsService;
	private CollectionsController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new CollectionsController(service, rsService, mockUserContext);
	}
	
	@Test
	public void shouldRenderTheFormToAddCollectionItems() {
		String slug = "acme-123456";
		ModelMap model = new ModelMap();
		when(rsService.findBySlug(eq(slug))).thenReturn(rollingStock());
		
		String viewName = controller.addItemForm(slug, model);
		
		assertEquals("collection/add", viewName);
		assertTrue("Item form not found in the model", model.containsAttribute("itemForm"));
	}
	
	@Test
	public void shouldAddNewItemsToCollections() {
		CollectionItem newItem = newForm().collectionItem(owner());
		ModelMap model = new ModelMap();
		
		when(mockResults.hasErrors()).thenReturn(false);
		when(rsService.findBySlug(eq("acme-123456"))).thenReturn(rollingStock());
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		
		String redirect = controller.addItem(newForm(), mockResults, model, mockRedirectAtts);

		assertEquals("redirect:/rollingstocks/{slug}", redirect);
		verify(service, times(1)).addRollingStock(eq(owner()), eq(newItem));
		verify(mockRedirectAtts, times(1)).addAttribute(eq("slug"), eq("acme-123456"));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringNewItemsAreAdded() {
		CollectionItem newItem = newItem();
		ModelMap model = new ModelMap();
		
		when(mockResults.hasErrors()).thenReturn(true);
		
		String viewName = controller.addItem(newForm(), mockResults, model, mockRedirectAtts);
		
		assertEquals("collection/add", viewName);
		assertTrue("Item form not found in the model", model.containsAttribute("itemForm"));
		assertEquals(newForm(), (CollectionItemForm) model.get("itemForm"));
		
		verify(service, times(0)).addRollingStock(eq(owner()), eq(newItem));
	}
 	
	CollectionItemForm newForm() {
		return new CollectionItemForm(newItem(), BigDecimal.valueOf(0), BigDecimal.valueOf(0), null);
	}
	
	CollectionItem newItem() {
		CollectionItem item = new CollectionItem(rollingStock(), date("2012/07/16"));
		return item;
	}
	
	AccountDetails ownerDetails() {
		return new AccountDetails(owner());
	}

	Account owner() {
		return new Account.Builder("mail@mail.com").displayName("Bob").build();
	}

	Iterable<LocalizedEnum<Condition>> conditionsList() {
		return LocalizedEnum.list(Condition.class);
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
}
