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

import static com.trenako.test.TestDataBuilder.acme;
import static com.trenako.test.TestDataBuilder.fs;
import static com.trenako.test.TestDataBuilder.scaleH0;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

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
import com.trenako.entities.Money;
import com.trenako.entities.Profile;
import com.trenako.entities.RollingStock;
import com.trenako.entities.WishList;
import com.trenako.entities.WishListItem;
import com.trenako.security.AccountDetails;
import com.trenako.services.WishListsService;
import com.trenako.values.Visibility;
import com.trenako.web.controllers.form.WishListForm;
import com.trenako.web.controllers.form.WishListItemForm;
import com.trenako.web.errors.NotFoundException;
import com.trenako.web.security.UserContext;
import com.trenako.web.test.DatabaseError;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class WishListsControllerTests {
	@Mock UserContext mockUserContext;
	@Mock RedirectAttributes redirectAtts;
	@Mock WishListsService wishListsService;
	@Mock BindingResult bindingResults;
	
	private ModelMap model = new ModelMap();
	private WishListsController controller;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new WishListsController(wishListsService, mockUserContext);
	}
	
	@Test
	public void shouldRenderCreationFormForWishLists() {
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
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(bindingResults.hasErrors()).thenReturn(false);

		String viewName = controller.createWishList(postedForm(), bindingResults, model, redirectAtts);

		assertEquals("redirect:/you/wishlists/{slug}", viewName);
		verify(wishListsService, times(1)).createNew(eq(wishList()));
		verify(redirectAtts, times(1)).addAttribute(eq("slug"), eq("bob-my-list"));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(WishListsController.WISH_LIST_CREATED_MSG));
	}
	
	@Test
	public void shouldRedirectAfterFormValidationErrorsCreatingWishLists() {
		when(bindingResults.hasErrors()).thenReturn(true);

		String viewName = controller.createWishList(form(), bindingResults, model, redirectAtts);

		assertEquals("wishlist/new", viewName);
		verify(wishListsService, times(0)).createNew(isA(WishList.class));
		assertTrue("Wish list form is null", model.containsAttribute("newForm"));
	}

	@Test
	public void shouldRedirectAfterDuplicatedKeyErrorsDuringWishListsCreation() {
		doThrow(new DuplicateKeyException("Duplicate key error"))
			.when(wishListsService).createNew(eq(form().buildWishList(owner())));

		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(bindingResults.hasErrors()).thenReturn(false);

		String viewName = controller.createWishList(form(), bindingResults, model, redirectAtts);

		assertEquals("wishlist/new", viewName);
		assertEquals(WishListsController.WISH_LIST_DUPLICATED_KEY_MSG, model.get("message"));
		assertTrue("Wish list form is null", model.containsAttribute("newForm"));
	}

	@Test
	public void shouldRedirectAfterDatabaseErrorsDuringWishListsCreation() {
		doThrow(new DatabaseError())
			.when(wishListsService).createNew(eq(form().buildWishList(owner())));

		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(bindingResults.hasErrors()).thenReturn(false);

		String viewName = controller.createWishList(form(), bindingResults, model, redirectAtts);

		assertEquals("wishlist/new", viewName);
		assertEquals(WishListsController.WISH_LIST_DB_ERROR_MSG, model.get("message"));
		assertTrue("Wish list form is null", model.containsAttribute("newForm"));
	}
	
	@Test
	public void shouldShowWishLists() {
		when(wishListsService.findBySlug(eq("bob-my-list"))).thenReturn(wishList());

		String viewName = controller.showWishList("bob-my-list", model);

		assertEquals("wishlist/show", viewName);
		assertEquals(wishList(), model.get("wishList"));
		assertTrue("Wish list item form not found", model.containsAttribute("editForm"));
	}
	
	@Test(expected = NotFoundException.class)
	public void shouldReturnNotFoundErrorWhenWishListWasNotFound() {
		when(wishListsService.findBySlug(eq("bob-my-list"))).thenReturn(null);
		
		@SuppressWarnings("unused")
		String viewName = controller.showWishList("bob-my-list", model);
	}

	@Test
	public void shouldRenderTheEditingFormForWishLists() {
		when(wishListsService.findBySlug(eq("bob-my-list"))).thenReturn(wishList());
		
		String viewName = controller.editWishList("bob-my-list", model, redirectAtts);
		
		assertEquals("wishlist/edit", viewName);
		assertTrue("Wish list form is null", model.containsAttribute("editForm"));
		verify(wishListsService, times(1)).findBySlug(eq("bob-my-list"));
	}
	
	@Test
	public void shouldRedirectToWishListsPageWhenTheListToBeEditedWasNotFound() {
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(wishListsService.findBySlug(eq("bob-my-list"))).thenReturn(null);
		
		String viewName = controller.editWishList("bob-my-list", model, redirectAtts);
		
		assertEquals("redirect:/you/wishlists", viewName);
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(WishListsController.WISH_LIST_NOT_FOUND_MSG));
		verify(wishListsService, times(1)).findBySlug(eq("bob-my-list"));
	}
	
	@Test
	public void shouldSaveWishListChanges() {
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(bindingResults.hasErrors()).thenReturn(false);
		
		String viewName = controller.saveWishList(postedForm(), bindingResults, model, redirectAtts);
		
		assertEquals("redirect:/you/wishlists/{slug}", viewName);
		verify(redirectAtts, times(1)).addAttribute(eq("slug"), eq("bob-my-list"));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(WishListsController.WISH_LIST_SAVED_MSG));
		verify(wishListsService, times(1)).saveChanges(eq(wishList()));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsSavingWishLists() {
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(bindingResults.hasErrors()).thenReturn(true);
		
		String viewName = controller.saveWishList(postedForm(), bindingResults, model, redirectAtts);
		
		assertEquals("wishlist/edit", viewName);
		assertTrue("Wish list form is null", model.containsAttribute("editForm"));
		verify(wishListsService, times(0)).saveChanges(eq(wishList()));
	}
	
	@Test
	public void shouldRedirectAfterDatabaseErrorSavingWishLists() {
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(bindingResults.hasErrors()).thenReturn(false);
		doThrow(new DatabaseError())
			.when(wishListsService)
			.saveChanges(eq(wishList()));
		
		String viewName = controller.saveWishList(postedForm(), bindingResults, model, redirectAtts);
		
		assertEquals("wishlist/edit", viewName);
		assertTrue("Wish list form is null", model.containsAttribute("editForm"));
		assertEquals(WishListsController.WISH_LIST_DB_ERROR_MSG, model.get("message"));
	}
	
	@Test
	public void shouldRedirectAfterDuplicatedKeyErrorSavingWishLists() {
		when(bindingResults.hasErrors()).thenReturn(false);
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		doThrow(new DuplicateKeyException("duplicated key"))
			.when(wishListsService)
			.saveChanges(eq(wishList()));
		
		String viewName = controller.saveWishList(postedForm(), bindingResults, model, redirectAtts);
		
		assertEquals("wishlist/edit", viewName);
		assertTrue("Wish list form is null", model.containsAttribute("editForm"));
		assertEquals(WishListsController.WISH_LIST_DUPLICATED_KEY_MSG, model.get("message"));
	}
			
	@Test
	public void shouldDeleteWishLists() {
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		
		String viewName = controller.removeWishList(wishList(), redirectAtts);

		assertEquals("redirect:/you/wishlists", viewName);
		verify(wishListsService, times(1)).remove(eq(wishList()));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(WishListsController.WISH_LIST_REMOVED_MSG));
	}
	
	@Test 
	public void shouldAddNewItemsToWishLists() {
		WishListItem item = itemForm().buildItem(owner());
		
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(bindingResults.hasErrors()).thenReturn(false);
		when(wishListsService.findBySlugOrDefault(eq(owner()), eq(itemForm().getSlug()))).thenReturn(wishList());
		
		String viewName = controller.addItem(itemForm(), bindingResults, model, redirectAtts);

		assertEquals("redirect:/you/wishlists/{slug}", viewName);
		verify(wishListsService, times(1)).findBySlugOrDefault(eq(owner()), eq(itemForm().getSlug()));
		verify(wishListsService, times(1)).addItem(eq(wishList()), eq(item));
		verify(redirectAtts, times(1)).addAttribute(eq("slug"), eq("bob-my-list"));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(WishListsController.WISH_LIST_ITEM_ADDED_MSG));
	}
	
	@Test
	public void shouldAddNewItemToDefaultWishListEvenIfItDoesntExist() {
		WishListItem item = defaultItemForm().buildItem(owner());
		
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(bindingResults.hasErrors()).thenReturn(false);
		when(wishListsService.findBySlugOrDefault(eq(owner()), eq(defaultItemForm().getSlug()))).thenReturn(wishList());
		
		String viewName = controller.addItem(defaultItemForm(), bindingResults, model, redirectAtts);

		assertEquals("redirect:/you/wishlists/{slug}", viewName);
		verify(wishListsService, times(1)).findBySlugOrDefault(eq(owner()), eq(defaultItemForm().getSlug()));
		verify(wishListsService, times(1)).addItem(eq(wishList()), eq(item));
	}
	
	@Test
	public void shouldRedirectAfterDatabaseErrorsAddingItemToWishLists() {
		WishListItem item = itemForm().buildItem(owner());
		
		doThrow(new DatabaseError())
			.when(wishListsService)
			.addItem(eq(wishList()), eq(item));
		
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(bindingResults.hasErrors()).thenReturn(false);
		when(wishListsService.findBySlugOrDefault(eq(owner()), eq(itemForm().getSlug()))).thenReturn(wishList());
		
		String viewName = controller.addItem(itemForm(), bindingResults, model, redirectAtts);

		assertEquals("wishlist/addItem", viewName);
		assertEquals(WishListsController.WISH_LIST_DB_ERROR_MSG, model.get("message"));
		assertTrue("Wish list item form is null", model.containsAttribute("wishListForm"));
		verify(wishListsService, times(1)).findBySlugOrDefault(eq(owner()), eq(itemForm().getSlug()));
		verify(wishListsService, times(1)).addItem(eq(wishList()), eq(item));
	}
	
	@Test 
	public void shouldUpdateItemsInTheWishLists() {
		WishListItem item = itemForm().buildItem(owner());
		
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(bindingResults.hasErrors()).thenReturn(false);
		when(wishListsService.findBySlug(eq(itemForm().getSlug()))).thenReturn(wishList());

		String viewName = controller.updateItem(itemForm(), bindingResults, model, redirectAtts);

		assertEquals("redirect:/you/wishlists/{slug}", viewName);
		verify(wishListsService, times(1)).findBySlug(eq(itemForm().getSlug()));
		verify(wishListsService, times(1)).updateItem(eq(wishList()), eq(item), eq(new Money(0, "EUR")));
		verify(redirectAtts, times(1)).addAttribute(eq("slug"), eq("bob-my-list"));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(WishListsController.WISH_LIST_ITEM_UPDATED_MSG));
	}
	
	@Test 
	public void shouldRedirectAfterDatabaseErrorsEditingItemForWishLists() {
		WishListItem item = itemForm().buildItem(owner());

		doThrow(new DatabaseError())
			.when(wishListsService)
			.updateItem(eq(wishList()), eq(item), eq(new Money(0, "EUR")));
		
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(bindingResults.hasErrors()).thenReturn(false);
		when(wishListsService.findBySlug(eq(itemForm().getSlug()))).thenReturn(wishList());

		String viewName = controller.updateItem(itemForm(), bindingResults, model, redirectAtts);

		assertEquals("wishlist/editItem", viewName);
		assertEquals(WishListsController.WISH_LIST_DB_ERROR_MSG, model.get("message"));
		assertTrue("Wish list item form is null", model.containsAttribute("wishListForm"));
		verify(wishListsService, times(1)).findBySlug(eq(itemForm().getSlug()));
		verify(wishListsService, times(1)).updateItem(eq(wishList()), eq(item), eq(new Money(0, "EUR")));
	}
	
	@Test 
	public void shouldRemoveItemsFromTheWishLists() {
		WishListItem deletedItem = deleteItemForm().deletedItem(owner());
		
		when(mockUserContext.getCurrentUser()).thenReturn(ownerDetails());
		when(bindingResults.hasErrors()).thenReturn(false);
		when(wishListsService.findBySlug(eq(itemForm().getSlug()))).thenReturn(wishList());

		String viewName = controller.removeItem(deleteItemForm(), bindingResults, redirectAtts);

		assertEquals("redirect:/you/wishlists/{slug}", viewName);
		verify(wishListsService, times(1)).findBySlug(eq(deleteItemForm().getSlug()));
		verify(wishListsService, times(1)).removeItem(eq(wishList()), eq(deletedItem));
		verify(redirectAtts, times(1)).addAttribute(eq("slug"), eq("bob-my-list"));
		verify(redirectAtts, times(1)).addFlashAttribute(eq("message"), eq(WishListsController.WISH_LIST_ITEM_DELETED_MSG));
	}

	RollingStock rollingStock() {
		return new RollingStock.Builder(acme(), "123456")
			.scale(scaleH0())
			.railway(fs())
			.description("desc")
			.build();
	}
	
	WishListForm form() {
		return WishListForm.newForm(null);
	}
	
	WishListForm postedForm() {
		WishListForm form = WishListForm.newForm(null);
		
		form.setBudget(BigDecimal.valueOf(100));
		form.setWishList(wishList());
		
		return form;
	}
	
	WishListItemForm itemForm() {
		return WishListItemForm.newForm(wishList(), rollingStock(), new WishListItem(), null);
	}
	
	WishListItemForm defaultItemForm() {
		return WishListItemForm.newForm(WishList.defaultList(owner()), rollingStock(), new WishListItem(), null);
	}
	
	WishListItemForm deleteItemForm() {
		WishListItem item = new WishListItem("item-id", new Money(0, "EUR"));
		return WishListItemForm.newForm(wishList(), rollingStock(), item, null);
	}

	WishList wishList() {
		return new WishList(owner(), "My List", "My notes", Visibility.PUBLIC, null);
	}

	Account owner() {
		return new Account.Builder("mail@mail.com")
			.displayName("Bob")
			.profile(new Profile("EUR"))
			.build();
	}

	AccountDetails ownerDetails() {
		return new AccountDetails(owner());
	}
}
