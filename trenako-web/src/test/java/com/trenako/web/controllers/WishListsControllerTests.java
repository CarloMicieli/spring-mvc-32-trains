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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Account;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;
import com.trenako.security.AccountDetails;
import com.trenako.services.WishListsService;
import com.trenako.values.Visibility;
import com.trenako.web.controllers.form.WishListForm;
import com.trenako.web.controllers.form.WishListItemForm;
import com.trenako.web.security.UserContext;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class WishListsControllerTests {
	@Mock UserContext mockUserContext;
	@Mock RedirectAttributes mockRedirectAtts;
	@Mock WishListsService mockService;
	@Mock BindingResult mockResults;
	
	private WishListsController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new WishListsController(mockService, mockUserContext);
	}
	
	@Test
	public void shouldRenderCreationFormForWishLists() {
		ModelMap model = new ModelMap();
		
		String viewName = controller.newWishList(model);

		assertEquals("wishlist/new", viewName);

		WishListForm form = (WishListForm) model.get("newForm");
		assertNotNull(form);
		assertNotNull("Visibility list is empty", form.getVisibilities());
		assertEquals(new WishList(), form.getWishList());
		assertEquals(BigDecimal.valueOf(0), form.getBudget());
	}
	
	@Test
	public void shouldCreateNewWishLists() {
		ModelMap model = new ModelMap();
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(mockResults.hasErrors()).thenReturn(false);

		String viewName = controller.createWishList(form(), mockResults, model, mockRedirectAtts);

		assertEquals("redirect:/wishlists/{slug}", viewName);
		verify(mockService, times(1)).createNew(eq(wishList()));
		verify(mockRedirectAtts, times(1)).addAttribute(eq("slug"), eq("bob-my-list"));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(WishListsController.WISH_LIST_CREATED_MSG));
	}
	
	@Test
	public void shouldRedirectAfterFormValidationErrorsCreatingWishLists() {
		ModelMap model = new ModelMap();
		when(mockResults.hasErrors()).thenReturn(true);

		String viewName = controller.createWishList(form(), mockResults, model, mockRedirectAtts);

		assertEquals("wishlist/new", viewName);
		verify(mockService, times(0)).createNew(isA(WishList.class));

		WishListForm form = (WishListForm) model.get("newForm");
		assertNotNull("Wish list form is null", form);
	}

	@Test
	public void shouldShowAWishList() {
		when(mockService.findBySlug(eq(wishList().getSlug()))).thenReturn(wishList());

		ModelMap model = new ModelMap();

		String viewName = controller.showWishList(wishList(), model);

		assertEquals("wishlist/show", viewName);

		WishList result = (WishList) model.get("wishList");
		assertNotNull("Wish list is null", result);
		assertEquals(wishList(), result);
	}

	@Test
	public void shouldShowWishListsForTheProvidedOwner() {
		when(mockService.findByOwner(eq(owner()))).thenReturn(Arrays.asList(new WishList(), new WishList()));
		ModelMap model = new ModelMap();

		String viewName = controller.showOwnerWishLists(owner(), model);

		assertEquals("wishlist/list", viewName);

//		Account owner = (Account) model.get("owner");
//		assertNotNull("Owner is null", owner);
		
		@SuppressWarnings("unchecked")
		Iterable<WishList> results = (Iterable<WishList>) model.get("results");
		assertNotNull("Wish lists result is null", results);
		assertEquals(2, ((List<WishList>) results).size());
	}
	
	
	/*

	

	
	@Test 
	public void shouldAddNewItemsToWishLists() {
		WishListItem newItem = wishListForm().wishListItem();
		ModelMap model = new ModelMap();

		when(mockResults.hasErrors()).thenReturn(false);
		when(mockService.findBySlug(eq(wishListForm().getSlug()))).thenReturn(wishList());

		String viewName = controller.addItem(wishListForm(), mockResults, model, mockRedirectAtts);

		assertEquals("redirect:/wishlists/{slug}", viewName);
		verify(mockService, times(1)).findBySlug(eq(wishListForm().getSlug()));
		verify(mockService, times(1)).addItem(eq(wishList()), eq(newItem));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(WishListsController.WISH_LIST_ITEM_ADDED_MSG));
	}

	@Test
	public void shouldRedirectAfterFormValidationErrorsAddingItemsToWishLists() {
		WishListItem newItem = wishListForm().wishListItem();
		ModelMap model = new ModelMap();

		when(mockResults.hasErrors()).thenReturn(true);

		String viewName = controller.addItem(wishListForm(), mockResults, model, mockRedirectAtts);

		assertEquals("wishlist/addItem", viewName);
		verify(mockService, times(0)).findBySlug(eq(wishListForm().getSlug()));
		verify(mockService, times(0)).addItem(eq(wishList()), eq(newItem));

		WishListItemForm form = (WishListItemForm) model.get("wishListForm");
		assertNotNull("Wish list form is null", form);
		assertEquals(wishListForm(), form);
	}

	@Test 
	public void shouldUpdateItemsInTheWishLists() {
		WishListItem newItem = wishListForm().wishListItem();
		ModelMap model = new ModelMap();

		when(mockResults.hasErrors()).thenReturn(false);
		when(mockService.findBySlug(eq(wishListForm().getSlug()))).thenReturn(wishList());

		String viewName = controller.updateItem(wishListForm(), mockResults, model, mockRedirectAtts);

		assertEquals("redirect:/wishlists/{slug}", viewName);
		verify(mockService, times(1)).findBySlug(eq(wishListForm().getSlug()));
//		verify(mockService, times(1)).updateItem(eq(wishList()), eq(newItem));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(WishListsController.WISH_LIST_ITEM_UPDATED_MSG));
	}

	@Test
	public void shouldRedirectAfterFormValidationErrorsUpdatingItemsToWishLists() {
		WishListItem newItem = wishListForm().wishListItem();
		ModelMap model = new ModelMap();

		when(mockResults.hasErrors()).thenReturn(true);

		String viewName = controller.updateItem(wishListForm(), mockResults, model, mockRedirectAtts);

		assertEquals("wishlist/editItem", viewName);
		verify(mockService, times(0)).findBySlug(eq(wishListForm().getSlug()));
//		verify(mockService, times(0)).updateItem(eq(wishList()), eq(newItem));

		WishListItemForm form = (WishListItemForm) model.get("wishListForm");
		assertNotNull("Wish list form is null", form);
		assertEquals(wishListForm(), form);
	}

	@Test 
	public void shouldRemoveItemsFromTheWishLists() {
		WishListItem newItem = wishListForm().wishListItem();

		when(mockResults.hasErrors()).thenReturn(false);
		when(mockService.findBySlug(eq(wishListForm().getSlug()))).thenReturn(wishList());

		String viewName = controller.removeItem(wishListForm(), mockRedirectAtts);

		assertEquals("redirect:/wishlists/{slug}", viewName);
		verify(mockService, times(1)).findBySlug(eq(wishListForm().getSlug()));
		verify(mockService, times(1)).removeItem(eq(wishList()), eq(newItem));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(WishListsController.WISH_LIST_ITEM_DELETED_MSG));
	}

	@Test
	public void shouldUpdateWishListsVisibility() {

		String viewName = controller.updateVisibility(wishList(), mockRedirectAtts);

		assertEquals("redirect:/wishlists/{slug}", viewName);
		verify(mockService, times(1)).changeVisibility(eq(wishList()), eq(Visibility.PUBLIC));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(WishListsController.WISH_LIST_VISIBILITY_CHANGED_MSG));
	}

	@Test
	public void shouldUpdateWishListsName() {

		String viewName = controller.updateName(wishList(), mockRedirectAtts);

		assertEquals("redirect:/wishlists/{slug}", viewName);
		verify(mockService, times(1)).changeName(eq(wishList()), eq("My list"));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(WishListsController.WISH_LIST_NAME_CHANGED_MSG));
	}






	@Test
	public void shouldRedirectAfterDuplicatedKeyErrorsDuringWishListsCreation() {
		ModelMap model = new ModelMap();

		doThrow(new DuplicateKeyException("Duplicate key error"))
			.when(mockService).createNew(eq(wishList()));

		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(mockResults.hasErrors()).thenReturn(false);

		String viewName = controller.createWishList(wishList(), mockResults, model, mockRedirectAtts);

		assertEquals("wishlist/new", viewName);
		assertEquals(WishListsController.WISH_LIST_DUPLICATED_KEY_MSG, (ControllerMessage) model.get("message"));
		verify(mockService, times(0)).createNew(eq(wishList()));

		WishList wl = (WishList) model.get("wishList");
		assertNotNull("Wish list is null", wl);
		assertEquals(wishList(), wl);
	}

	@Test
	public void shouldDeleteWishLists() {
		String viewName = controller.removeWishList(wishList(), mockRedirectAtts);

		assertEquals("redirect:/wishlists/owner/{owner}", viewName);
		verify(mockService, times(1)).remove(eq(wishList()));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(WishListsController.WISH_LIST_REMOVED_MSG));
	}
	*/
	
	WishListForm form() {
		return new WishListForm(wishList(), BigDecimal.valueOf(100), null);
	}
	
	WishListItemForm wishListitemForm() {
		return new WishListItemForm("my-list", "acme-123456", "ACME 123456");
	}

	WishList wishList() {
		return new WishList(owner(), "My List", Visibility.PUBLIC);
	}

	Account owner() {
		return new Account.Builder("mail@mail.com").displayName("Bob").build();
	}

	AccountDetails ownerDetails() {
		return new AccountDetails(owner());
	}
}
