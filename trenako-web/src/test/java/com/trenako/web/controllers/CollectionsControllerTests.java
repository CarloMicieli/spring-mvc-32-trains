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
import com.trenako.entities.Collection;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.security.AccountDetails;
import com.trenako.services.AccountsService;
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

	@Mock RedirectAttributes mockRedirectAtts;
	@Mock BindingResult mockResults;

	@Mock UserContext mockUserContext;
	@Mock AccountsService usersService;
	@Mock CollectionsService service;
	@Mock RollingStocksService rsService;
	private CollectionsController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new CollectionsController(service, rsService, usersService, mockUserContext);
	}
	
	@Test
	public void shouldShowCollections() {
		String slug = "bob";
		ModelMap model = new ModelMap();
		when(service.findBySlug(eq(slug))).thenReturn(new Collection(owner()));
		when(usersService.findBySlug(eq(slug))).thenReturn(new Account());
		
		String viewName = controller.show(slug, model);
		
		assertEquals("collection/show", viewName);
		assertTrue("Collection not found", model.containsAttribute("collection"));
		assertTrue("Owner not found", model.containsAttribute("owner"));
	}
	
	@Test
	public void shouldShowDefaultCollectionWhenOneWasNotCreatedYet() {
		String slug = "not-found";
		ModelMap model = new ModelMap();
		when(service.findBySlug(eq(slug))).thenReturn(Collection.defaultCollection());
		when(usersService.findBySlug(eq(slug))).thenReturn(new Account());
		
		String viewName = controller.show(slug, model);
		
		assertEquals("collection/show", viewName);
		assertTrue("Collection not found", model.containsAttribute("collection"));
		assertTrue("Owner not found", model.containsAttribute("owner"));
	}
	
	@Test
	public void shouldRenderCollectionEditingForms() {
		String slug = "bob";
		Collection coll = new Collection(owner());
		ModelMap model = new ModelMap();
		when(service.findBySlug(eq(slug))).thenReturn(coll);
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		
		String viewName = controller.editForm(slug, model);
		
		assertEquals("collection/edit", viewName);
		assertEquals(coll, (Collection) model.get("collection"));
		assertEquals(owner(), (Account) model.get("owner"));
		assertTrue(model.containsAttribute("visibilities"));
	}
	
	@Test
	public void shouldRenderTheFormToAddCollectionItems() {
		String slug = "acme-123456";
		ModelMap model = new ModelMap();
		when(rsService.findBySlug(eq(slug))).thenReturn(rollingStock());
		
		String viewName = controller.addItemForm(slug, model);
		
		assertEquals("collection/add", viewName);
		assertTrue("Item form not found in the model", model.containsAttribute("itemForm"));
		assertTrue("Rolling stock not found in the model", model.containsAttribute("rs"));
	}
	
	@Test
	public void shouldAddNewItemsToCollections() {
		ModelMap model = new ModelMap();
		CollectionItem newItem = postedForm().newItem(rollingStock(), owner());
		
		when(mockResults.hasErrors()).thenReturn(false);
		when(rsService.findBySlug(eq(postedForm().getRsSlug()))).thenReturn(rollingStock());
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		
		String redirect = controller.addItem(postedForm(), mockResults, model, mockRedirectAtts);

		assertEquals("redirect:/collections/{slug}", redirect);
		verify(service, times(1)).addRollingStock(eq(owner()), eq(newItem));
		verify(mockRedirectAtts, times(1)).addAttribute(eq("slug"), eq("bob"));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringNewItemsAreAdded() {
		ModelMap model = new ModelMap();
		CollectionItemForm newForm = CollectionItemForm.newForm(rollingStock(), null);
		
		when(rsService.findBySlug(eq(postedForm().getRsSlug()))).thenReturn(rollingStock());
		when(mockResults.hasErrors()).thenReturn(true);
		
		String viewName = controller.addItem(postedForm(), mockResults, model, mockRedirectAtts);
		
		assertEquals("collection/add", viewName);
		assertTrue("Item form not found in the model", model.containsAttribute("itemForm"));
		assertEquals(newForm, (CollectionItemForm) model.get("itemForm"));
		assertTrue("Rolling stock not found in the model", model.containsAttribute("rs"));
		assertEquals(rollingStock(), (RollingStock) model.get("rs"));
		
		verify(service, times(0)).addRollingStock(eq(owner()), isA(CollectionItem.class));
	}
 	
	@Test
	public void shouldDeleteItemsFromCollections() {
		ModelMap model = new ModelMap();
		CollectionItem item = postedForm().deletedItem(rollingStock(), owner());
		
		when(mockResults.hasErrors()).thenReturn(false);
		when(rsService.findBySlug(eq(postedForm().getRsSlug()))).thenReturn(rollingStock());
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		
		String redirect = controller.removeItem(postedForm(), mockResults, model, mockRedirectAtts);

		assertEquals("redirect:/collections/{slug}", redirect);
		verify(service, times(1)).removeRollingStock(eq(owner()), eq(item));
		verify(mockRedirectAtts, times(1)).addAttribute(eq("slug"), eq("bob"));
	}
	
	CollectionItemForm newForm() {
		return CollectionItemForm.newForm(rollingStock(), null);
	}
	
	CollectionItemForm postedForm() {
		CollectionItemForm form = CollectionItemForm.newForm(rollingStock(), null);
		CollectionItem item = new CollectionItem();
		item.setItemId("item-id");
		item.setAddedAt(date("2012/09/01"));
		item.setCondition("new");
		item.setNotes("My notes");
		
		form.setItem(item);
		return form;
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
