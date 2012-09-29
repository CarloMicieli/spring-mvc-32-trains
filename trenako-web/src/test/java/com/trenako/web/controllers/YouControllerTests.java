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

import static org.springframework.test.web.ModelAndViewAssert.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

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
import com.trenako.values.Visibility;
import com.trenako.web.security.UserContext;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class YouControllerTests {

	private @Mock ProfilesService service;
	private @Mock UserContext secContext;
	private @Mock WishListsService wishListsService;
	private @Mock CollectionsService collectionService;
	private ModelMap model = new ModelMap();

	private YouController controller;
	
	private final static Account ACCOUNT = new Account.Builder("mail@mail.com").displayName("bob").build();
	private final static AccountDetails USER = new AccountDetails(ACCOUNT);
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new YouController(service, collectionService, wishListsService, secContext);
		
		when(secContext.getCurrentUser()).thenReturn(USER);
	}
	
	@Test
	public void shouldRenderUserPage() {
		ProfileView value = new ProfileView(
				userActivity(),
				collection(), 
				wishLists(), 
				ProfileOptions.DEFAULT);
		when(service.findProfileView(eq(ACCOUNT))).thenReturn(value);
		
		ModelAndView mav = controller.index();
		assertViewName(mav, "you/index");
		assertModelAttributeValue(mav, "user", ACCOUNT);
		assertModelAttributeValue(mav, "info", value);
	}
	
	@Test
	public void shouldRenderTheCollectionManagementView() {
		Collection coll = new Collection(ACCOUNT);
		
		when(collectionService.findByOwner(eq(ACCOUNT))).thenReturn(coll);
				
		String viewName = controller.collection(model);
		
		verify(collectionService, times(1)).findByOwner(eq(ACCOUNT));
		assertEquals("collection/manage", viewName);
		assertEquals(coll, model.get("collection"));
		assertEquals(ACCOUNT, model.get("owner"));
		assertTrue(model.containsAttribute("editForm"));
	}
	
	@Test
	public void shouldRenderTheListOfWishLists() {
		when(wishListsService.findByOwner(eq(ACCOUNT))).thenReturn(Arrays.asList(new WishList(), new WishList()));

		String viewName = controller.wishlists(model);

		assertEquals("wishlist/list", viewName);

		assertEquals(ACCOUNT, model.get("owner"));
		
		@SuppressWarnings("unchecked")
		Iterable<WishList> results = (Iterable<WishList>) model.get("results");
		assertNotNull("Wish lists result is null", results);
		assertEquals(2, ((List<WishList>) results).size());
	}
	
	@Test
	public void shouldShowWishLists() {
		when(wishListsService.findBySlugOrDefault(eq(ACCOUNT), eq("bob-my-list"))).thenReturn(wishList());

		String viewName = controller.wishlist("bob-my-list", model);

		assertEquals("wishlist/manage", viewName);
		assertEquals(wishList(), model.get("wishList"));
		assertTrue("Wish list item form not found", model.containsAttribute("editForm"));
	}
	
	WishList wishList() {
		return new WishList(ACCOUNT, "My List", "My notes", Visibility.PUBLIC, null);
	}

	private Collection collection() {
		return new Collection(ACCOUNT);
	}
	
	Iterable<Activity> userActivity() {
		return Collections.emptyList();
	}
	
	Iterable<WishList> wishLists() {
		return Collections.emptyList();
	}
}
