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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.activities.Activity;
import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.WishList;
import com.trenako.security.AccountDetails;
import com.trenako.services.CollectionsService;
import com.trenako.services.ProfilesService;
import com.trenako.services.WishListsService;
import com.trenako.services.view.ProfileOptions;
import com.trenako.services.view.ProfileView;
import com.trenako.web.security.UserContext;
import com.trenako.web.test.AbstractSpringControllerTests;

/**
 * 
 * @author Carlo Micieli
 *
 */
public class YouControllerMappingTests extends AbstractSpringControllerTests {

	private @Autowired CollectionsService collectionsService;
	private @Autowired WishListsService wishListsService;
	private @Autowired ProfilesService service;
	private @Autowired UserContext secContext;
	
	@Override
	protected void init() {
		super.init();
		
		Account account = new Account.Builder("mail@mail.com").displayName("Bob").build();
		AccountDetails ownerDetails = new AccountDetails(account);
		when(secContext.getCurrentUser()).thenReturn(ownerDetails);
		
		ProfileView value = new ProfileView(
				userActivity(),
				collection(account), 
				wishLists(), 
				ProfileOptions.DEFAULT);
		when(service.findProfileView(eq(account))).thenReturn(value);
		when(collectionsService.findByOwner(eq(account))).thenReturn(collection(account));
		when(wishListsService.findByOwner(eq(account))).thenReturn(wishLists());
		when(wishListsService.findBySlugOrDefault(eq(account), eq("bob-new-list"))).thenReturn(wishList(account));
	}
	
	@After
	public void cleanup() {
		reset(service);
		reset(secContext);
		reset(collectionsService);
		reset(wishListsService);
	}
	
	@Test
	public void shouldRenderTheUserPersonalPage() throws Exception {
		mockMvc().perform(get("/you"))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("user"))
			.andExpect(model().attributeExists("info"))
			.andExpect(forwardedUrl(view("you", "index")));
	}
	
	@Test
	public void shouldRenderTheCollectionManagementPage() throws Exception {
		mockMvc().perform(get("/you/collection"))
			.andExpect(status().isOk())
			.andExpect(model().size(3))
			.andExpect(model().attributeExists("collection"))
			.andExpect(model().attributeExists("owner"))
			.andExpect(model().attributeExists("editForm"))
			.andExpect(forwardedUrl(view("collection", "manage")));
	}
	
	@Test
	public void shouldRenderTheWishListsPage() throws Exception {
		mockMvc().perform(get("/you/wishlists"))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("results"))
			.andExpect(model().attributeExists("owner"))
			.andExpect(forwardedUrl(view("wishlist", "list")));
	}
	
	@Test
	public void shouldRenderTheWishListManagementPage() throws Exception {
		mockMvc().perform(get("/you/wishlists/{slug}", "bob-new-list"))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("wishList"))
			.andExpect(model().attributeExists("editForm"))
			.andExpect(forwardedUrl(view("wishlist", "manage")));
	}
	
	Collection collection(Account account) {
		return new Collection(account);
	}
	
	WishList wishList(Account account) {
		return  new WishList(account, "New list");
	}
	
	Iterable<WishList> wishLists() {
		return Collections.emptyList();
	}
	
	Iterable<Activity> userActivity() {
		return Collections.emptyList();
	}
}
