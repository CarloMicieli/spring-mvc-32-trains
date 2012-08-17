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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Account;
import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.services.ReviewsService;
import com.trenako.services.RollingStocksService;
import com.trenako.web.controllers.form.ReviewForm;
import com.trenako.web.security.UserContext;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/rollingstocks/{slug}/reviews")
public class ReviewsController {

	private @Autowired UserContext userContext;
	private final ReviewsService service;
	private final RollingStocksService rsService;
	
	final static ControllerMessage REVIEW_POSTED_MSG = ControllerMessage.success("review.posted.message");
	
	@Autowired
	public ReviewsController(ReviewsService service, RollingStocksService rsService) {
		this.service = service;
		this.rsService = rsService;
	}
	
	void setUserContext(UserContext userContext) {
		this.userContext = userContext;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newReview(@PathVariable("slug") String slug, ModelMap model) {
		
		ReviewForm newForm = new ReviewForm();
		newForm.setAuthor(userContext.getCurrentUser().getAccount());
		newForm.setRs(rsService.findBySlug(slug));
		newForm.setReview(new Review());
		
		model.addAttribute(newForm);
		return "review/new";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String postReview(@PathVariable("slug") String slug, 
			@Valid @ModelAttribute ReviewForm reviewForm, 
			BindingResult bindingResult,
			ModelMap model,
			RedirectAttributes redirectAtts) {

		RollingStock rollingStock = rsService.findBySlug(slug);
		Review review = reviewForm.getReview();
		Account author = userContext.getCurrentUser().getAccount();
		review.setAuthor(author);
		
		if (bindingResult.hasErrors()) {
			reviewForm.setAuthor(author);
			reviewForm.setRs(rollingStock);
			
			model.addAttribute(reviewForm);
			model.addAttribute("slug", rollingStock.getSlug());
			return "review/new";
		}
		
		service.postReview(rollingStock, review);
		REVIEW_POSTED_MSG.appendToRedirect(redirectAtts);
		redirectAtts.addAttribute("slug", rollingStock.getSlug());
		return "redirect:/rollingstock/{slug}/reviews";
	}
}
