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
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.activities.Activity;
import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.WishList;
import com.trenako.security.AccountDetails;
import com.trenako.services.ProfilesService;
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
	}
	
	@After
	public void cleanup() {
		reset(service);
		reset(secContext);
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
	
	Collection collection(Account account) {
		return new Collection(account);
	}
	
	Iterable<WishList> wishLists() {
		return Collections.emptyList();
	}
	
	Iterable<Activity> userActivity() {
		return Collections.emptyList();
	}
}
