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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.services.ReviewsService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/reviews")
public class ReviewsController {

	private final ReviewsService service;
	
	final static ControllerMessage REVIEW_POSTED_MSG = ControllerMessage.success("review.posted.message");
	
	@Autowired
	public ReviewsController(ReviewsService service) {
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postReview(@ModelAttribute RollingStock rollingStock, 
			@Valid @ModelAttribute Review review, 
			BindingResult bindingResult,
			RedirectAttributes redirectAtts) {

		if (bindingResult.hasErrors()) {
			redirectAtts.addAttribute("review", review);
			redirectAtts.addAttribute("slug", rollingStock.getSlug());
			return "redirect:/rollingstock/{slug}/reviews";
		}
		
		service.postReview(rollingStock, review);
		REVIEW_POSTED_MSG.appendToRedirect(redirectAtts);
		redirectAtts.addAttribute("slug", rollingStock.getSlug());
		return "redirect:/rollingstock/{slug}/reviews";
	}

}
