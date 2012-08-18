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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Account;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockReviews;
import com.trenako.security.AccountDetails;
import com.trenako.services.ReviewsService;
import com.trenako.services.RollingStocksService;
import com.trenako.web.controllers.form.ReviewForm;
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
		
		when(mockUserContext.getCurrentUser()).thenReturn(new AccountDetails(author()));
		
		controller = new ReviewsController(mockService, mockRsService);
		controller.setUserContext(mockUserContext);
	}
	
	@Test
	public void shouldShowRollingStockReviews() {
		String slug = "acme-123456";
		ModelMap model = new ModelMap();
		RollingStockReviews value = new RollingStockReviews();
		
		when(mockRsService.findBySlug(eq(slug))).thenReturn(rollingStock());
		when(mockService.findByRollingStock(rollingStock())).thenReturn(value);
		
		String viewName = controller.reviews(slug, model);
		
		assertEquals("review/list", viewName);
		
		RollingStock rs = (RollingStock) model.get("rollingStock");
		assertNotNull("Rolling stock not found", rs);
		RollingStockReviews reviews = (RollingStockReviews) model.get("reviews");
		assertNotNull("Review not found", reviews);
	}
	
	@Test
	public void shouldRenderReviewsCreationView() {
		String slug = "acme-123456";
		ModelMap model = new ModelMap();

		when(mockRsService.findBySlug(eq(slug))).thenReturn(rollingStock());
		when(mockUserContext.getCurrentUser()).thenReturn(new AccountDetails(author()));
		
		String viewName = controller.newReview(slug, model);
		
		assertEquals("review/new", viewName);
		assertTrue("Review not found", model.containsAttribute("reviewForm"));
		
		ReviewForm form = (ReviewForm) model.get("reviewForm");
		assertEquals(rollingStock(), form.getRs());
		assertEquals(author(), form.getAuthor());
	}
	
	@Test
	public void shouldPostNewReviews() {
		String slug = "acme-123456";
		ModelMap model = new ModelMap();

		when(mockResult.hasErrors()).thenReturn(false);
		when(mockRsService.findBySlug(eq(slug))).thenReturn(rollingStock());
		when(mockUserContext.getCurrentUser()).thenReturn(new AccountDetails(author()));
		
		String redirect = controller.postReview(slug, newForm(), mockResult, model, mockRedirectAtts);
		
		assertEquals("redirect:/rollingstocks/{slug}/reviews", redirect);
		
		ArgumentCaptor<Review> arg = ArgumentCaptor.forClass(Review.class);
		verify(mockService, times(1)).postReview(eq(rollingStock()), arg.capture());
		Review review = (Review) arg.getValue();
		assertEquals(author().getSlug(), review.getAuthor());
		
		verify(mockRsService, times(1)).findBySlug(eq(slug));
		verify(mockUserContext, times(1)).getCurrentUser();
		//verify(mockRedirectAtts, times(1)).addAttribute(eq("slug"), eq(rollingStock().getSlug()));
		verify(mockRedirectAtts, times(1)).addFlashAttribute(eq("message"), eq(ReviewsController.REVIEW_POSTED_MSG));
	}
	
	@Test
	public void shouldRedirectAfterValidationErrorsDuringReviewsPosting() {
		String slug = "acme-123456";
		ModelMap model = new ModelMap();

		when(mockResult.hasErrors()).thenReturn(true);
		when(mockRsService.findBySlug(eq(slug))).thenReturn(rollingStock());
		when(mockUserContext.getCurrentUser()).thenReturn(new AccountDetails(author()));
		
		String viewName = controller.postReview(slug, newForm(), mockResult, model, mockRedirectAtts);
		
		assertEquals("review/new", viewName);
		
		assertTrue("Review not found", model.containsAttribute("reviewForm"));
		ReviewForm form = (ReviewForm) model.get("reviewForm");
		assertEquals(rollingStock(), form.getRs());
		assertEquals(author(), form.getAuthor());
		
		verify(mockService, times(0)).postReview(isA(RollingStock.class), isA(Review.class));
		//verify(mockRedirectAtts, times(1)).addAttribute(eq("slug"), eq(rollingStock().getSlug()));
	}
	
	ReviewForm newForm() {
		Account author = new Account.Builder("mail@mail.com").displayName("bob").build();
		ReviewForm form = new ReviewForm();
		form.setAuthor(author);
		form.setReview(review());
		form.setRs(rollingStock());
		return form;
	}
	
	Account author() {
		return new Account.Builder("mail@mail.com").displayName("Bob").build();
	}
	
	RollingStock rollingStock() {
		RollingStock rs = new RollingStock.Builder(acme(), "123456")
			.railway(fs())
			.scale(scaleH0())
			.description("desc")
			.build();
		return rs;		
	}
	
	Review review() {
		Review r = new Review(author(), "title", "content", 5);
		return r;
	}
}
