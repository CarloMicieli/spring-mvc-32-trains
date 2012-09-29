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

import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.entities.Account;
import com.trenako.entities.Profile;
import com.trenako.entities.WishList;
import com.trenako.security.AccountDetails;
import com.trenako.services.AccountsService;
import com.trenako.services.WishListsService;
import com.trenako.web.security.UserContext;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class WishListsControllerMappingTests extends AbstractSpringControllerTests {
	
	private @Autowired WishListsService service;
	private @Autowired UserContext userContext;
	private @Autowired AccountsService accountsService;
	
	private final Account owner = new Account.Builder("mail@mail.com")
		.displayName("Bob")
		.profile(new Profile("EUR"))
		.build(); 
	
	@Override
	protected void init() {
		super.init();
		
		AccountDetails ownerDetails = new AccountDetails(owner);
		when(userContext.getCurrentUser()).thenReturn(ownerDetails);
	}
	
	@After
	public void cleanup() {
		reset(userContext);
		reset(service);
		reset(accountsService);
	}
	
	@Test
	public void shouldShowWishLists() throws Exception {
		when(service.findBySlug(eq("bob-my-list"))).thenReturn(new WishList());
		
		mockMvc().perform(get("/wishlists/{slug}", "bob-my-list"))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("wishList"))
			.andExpect(model().attributeExists("editForm"))
			.andExpect(forwardedUrl(view("wishlist", "show")));		
	}
	
	@Test
	public void shouldRenderWishListCreationForm() throws Exception {
		mockMvc().perform(get("/wishlists/new"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("newForm"))
			.andExpect(forwardedUrl(view("wishlist", "new")));
	}
	
	@Test
	public void shouldCreateNewWishLists() throws Exception {
		mockMvc().perform(post("/wishlists")
				.param("wishList.name", "My list")
				.param("wishList.owner", "bob")
				.param("wishList.notes", "My list notes")
				.param("wishList.visibility", "private")
				.param("budget", "100.0"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("slug"))
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(WishListsController.WISH_LIST_CREATED_MSG)))
			.andExpect(redirectedUrl("/you/wishlists/bob-my-list"));
	}
		
	@Test
	public void shouldRenderishListEditingForm() throws Exception {
		when(service.findBySlug(eq("bob-my-list"))).thenReturn(new WishList());
		
		mockMvc().perform(get("/wishlists/{slug}/edit", "bob-my-list"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("editForm"))
			.andExpect(forwardedUrl(view("wishlist", "edit")));
	}
	
	@Test
	public void shouldSaveWishListsChanges() throws Exception {
		mockMvc().perform(put("/wishlists")
				.param("wishList.name", "My list")
				.param("wishList.owner", "bob")
				.param("wishList.notes", "My list notes")
				.param("wishList.visibility", "private")
				.param("budget", "100.0"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("slug"))
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(WishListsController.WISH_LIST_SAVED_MSG)))
			.andExpect(redirectedUrl("/you/wishlists/bob-my-list"));
	}
	
	@Test
	public void shouldDeleteWishLists() throws Exception {
		mockMvc().perform(delete("/wishlists"))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(WishListsController.WISH_LIST_REMOVED_MSG)))
			.andExpect(redirectedUrl("/you/wishlists"));
	}
	
	@Test
	public void shouldAddItemsToWishLists() throws Exception {
		when(service.findBySlugOrDefault(eq(owner), eq("bob-my-list"))).thenReturn(new WishList("bob-my-list"));
		
		mockMvc().perform(post("/wishlists/items")
				.param("slug", "bob-my-list")
				.param("rsSlug", "acme-123456")
				.param("rsLabel", "ACME 123456")
				.param("item.priority", "normal")
				.param("item.notes", "My list notes")
				.param("wishList.visibility", "private")
				.param("price", "100.0"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("slug"))
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(WishListsController.WISH_LIST_ITEM_ADDED_MSG)))
			.andExpect(redirectedUrl("/you/wishlists/bob-my-list"));
	}
	
	@Test
	public void shouldEditItemsInWishLists() throws Exception {
		when(service.findBySlug(eq("bob-my-list"))).thenReturn(new WishList("bob-my-list"));
		
		mockMvc().perform(put("/wishlists/items")
				.param("slug", "bob-my-list")
				.param("rsSlug", "acme-123456")
				.param("rsLabel", "ACME 123456")
				.param("item.itemId", "acme-123456")
				.param("item.priority", "normal")
				.param("item.notes", "My list notes")
				.param("wishList.visibility", "private")
				.param("previousPrice", "100.0")
				.param("price", "100.0"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("slug"))
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(WishListsController.WISH_LIST_ITEM_UPDATED_MSG)))
			.andExpect(redirectedUrl("/you/wishlists/bob-my-list"));
	}
	
	@Test
	public void shouldDeleteItemsFromWishLists() throws Exception {
		when(service.findBySlug(eq("bob-my-list"))).thenReturn(new WishList("bob-my-list"));
		
		mockMvc().perform(delete("/wishlists/items")
				.param("slug", "bob-my-list")
				.param("rsSlug", "acme-123456")
				.param("item.itemId", "acme-123456")
				.param("price", "100.0"))
			.andExpect(status().isOk())
			.andExpect(model().size(1))
			.andExpect(model().attributeExists("slug"))
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(WishListsController.WISH_LIST_ITEM_DELETED_MSG)))
			.andExpect(redirectedUrl("/you/wishlists/bob-my-list"));
	}
}
