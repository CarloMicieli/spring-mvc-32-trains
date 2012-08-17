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
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.security.AccountDetails;
import com.trenako.services.ReviewsService;
import com.trenako.services.RollingStocksService;
import com.trenako.web.security.UserContext;

/**
 * 
 * @author Carlo Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ReviewsControllerTests {

	@Mock UserContext mockUserContext;
	@Mock BindingResult mockResult;
	@Mock RedirectAttributes mockRedirectAtts;
	@Mock ReviewsService mockService;
	@Mock RollingStocksService mockRsService;
	
	private ReviewsController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		Account account = new Account.Builder("mail@mail.com").displayName("Bob").build();
		when(mockUserContext.getCurrentUser()).thenReturn(new AccountDetails(account));
		
		controller = new ReviewsController(mockService, mockRsService);
		controller.setUserContext(mockUserContext);
	}
	
	@Test
	public void shouldRenderReviewsCreationView() {
		String slug = "rs-slug";
		ModelMap model = new ModelMap();
		
		String viewName = controller.newReview(slug, model);
		
		assertEquals("review/new", viewName);
		assertTrue("Review not found", model.containsAttribute("reviewForm"));
	}
	
//	@Test
//	public void shouldPostNewReviews() {
//		when(mockResult.hasErrors()).thenReturn(false);
//		
//		String redirect = controller.postReview(rollingStock(), review(), mockResult, mockRedirectAtts);
//		
//		assertEquals("redirect:/rollingstock/{slug}/reviews", redirect);
//		verify(mockService, times(1)).postReview(eq(rollingStock()), eq(review()));
//		verify(mockRedirectAtts, times(1)).addAttribute(eq("slug"), eq(rollingStock().getSlug()));
//		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(ReviewsController.REVIEW_POSTED_MSG));
//	}
//	
//	@Test
//	public void shouldRedirectAfterValidationErrorsDuringReviewsPosting() {
//		when(mockResult.hasErrors()).thenReturn(true);
//		
//		String redirect = controller.postReview(rollingStock(), review(), mockResult, mockRedirectAtts);
//		
//		assertEquals("redirect:/rollingstock/{slug}/reviews", redirect);
//		verify(mockService, times(0)).postReview(eq(rollingStock()), eq(review()));
//		verify(mockRedirectAtts, times(1)).addAttribute(eq("slug"), eq(rollingStock().getSlug()));
//		verify(mockRedirectAtts, times(1)).addAttribute(eq("review"), eq(review()));
//	}
	
	RollingStock rollingStock() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.railway(fs())
			.scale(scaleH0())
			.description("desc")
			.build();
		return rs;		
	}
	
	Review review() {
		Account author = new Account.Builder("mail@mail.com").displayName("bob").build();
		Review r = new Review(author, "title", "content", 5);
		return r;
	}
}
