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

import static com.trenako.test.TestDataBuilder.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.server.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.trenako.entities.Account;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockReviews;
import com.trenako.security.AccountDetails;
import com.trenako.services.ReviewsService;
import com.trenako.services.RollingStocksService;
import com.trenako.web.security.UserContext;
import com.trenako.web.test.AbstractSpringControllerTests;

public class ReviewsControllerMappingTests extends AbstractSpringControllerTests {
	
	private final static String SLUG = "acme-123456";
	private @Autowired ReviewsService service;
	private @Autowired RollingStocksService rsService;
	private @Autowired UserContext secContext;
	
	@After
	public void cleanup() {
		reset(service);
		reset(rsService);
		reset(secContext);
	}
	
	@Test
	public void shouldShowTheReviewsList() throws Exception {
		RollingStock value = new RollingStock();
		when(rsService.findBySlug(eq(SLUG))).thenReturn(value);
		when(service.findByRollingStock(eq(value))).thenReturn(new RollingStockReviews());
		
		mockMvc().perform(get("/rollingstocks/{slug}/reviews", SLUG))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("reviews"))
			.andExpect(model().attributeExists("rollingStock"))
			.andExpect(forwardedUrl(view("review", "list")));
	}
	
	@Test
	public void shouldRenderTheNewReviewCreationForms() throws Exception {
		Account account = new Account.Builder("mail@mail.com").displayName("Bob").build();
		AccountDetails ownerDetails = new AccountDetails(account);
		when(secContext.getCurrentUser()).thenReturn(ownerDetails);
		when(rsService.findBySlug(eq(SLUG))).thenReturn(rollingStock());
		
		mockMvc().perform(get("/rollingstocks/{slug}/reviews/new", SLUG))
			.andExpect(status().isOk())
			.andExpect(model().size(2))
			.andExpect(model().attributeExists("reviewForm"))
			.andExpect(model().attributeExists("rs"))
			.andExpect(forwardedUrl(view("review", "new")));
	}
	
	@Test
	public void shouldPostNewRollingStockReviews() throws Exception {
		Account account = new Account.Builder("mail@mail.com").displayName("Bob").build();
		AccountDetails ownerDetails = new AccountDetails(account);
		when(secContext.getCurrentUser()).thenReturn(ownerDetails);
		when(rsService.findBySlug(eq(SLUG))).thenReturn(rollingStock());
		
		mockMvc().perform(post("/rollingstocks/{slug}/reviews", SLUG)
				.param("rsSlug", "acme-123456")
				.param("rsLabel", "ACME 123456")
				.param("review.author", "bob")
				.param("review.title", "title")
				.param("review.rating", "1")
				.param("review.content", "Review content"))
			.andExpect(status().isOk())
			.andExpect(flash().attributeCount(1))
			.andExpect(flash().attribute("message", equalTo(ReviewsController.REVIEW_POSTED_MSG)))
			.andExpect(redirectedUrl("/rollingstocks/acme-123456/reviews"));
	}

	RollingStock rollingStock() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.railway(fs())
			.scale(scaleH0())
			.description("desc")
			.build();
		return rs;		
	}
}
