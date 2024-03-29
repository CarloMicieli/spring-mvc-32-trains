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

import java.util.Date;

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

import com.trenako.entities.Review;
import com.trenako.entities.RollingStock;
import com.trenako.entities.RollingStockReviews;
import com.trenako.services.ReviewsService;
import com.trenako.services.RollingStocksService;
import com.trenako.web.controllers.form.ReviewForm;
import com.trenako.web.security.UserContext;

/**
 * @author Carlo Micieli
 */
@Controller
@RequestMapping("/rollingstocks/{slug}/reviews")
public class ReviewsController {

    @Autowired
    private UserContext userContext;
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

    @RequestMapping(method = RequestMethod.GET)
    public String reviews(@PathVariable("slug") String slug, ModelMap model) {

        RollingStock rs = rsService.findBySlug(slug);
        RollingStockReviews reviews = service.findByRollingStock(rs);

        model.addAttribute("reviews", reviews);
        model.addAttribute("rollingStock", rs);
        return "review/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newReview(@PathVariable("slug") String slug, ModelMap model) {
        RollingStock rs = rsService.findBySlug(slug);
        ReviewForm newForm = ReviewForm.newForm(rs, userContext);
        model.addAttribute(newForm);
        model.addAttribute("rs", rs);
        return "review/new";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String postReview(@Valid @ModelAttribute ReviewForm reviewForm,
                             BindingResult bindingResult,
                             ModelMap model,
                             RedirectAttributes redirectAtts) {

        RollingStock rollingStock = rsService.findBySlug(reviewForm.getRsSlug());
        Review review = reviewForm.buildReview(new Date(), userContext);

        if (bindingResult.hasErrors()) {
            model.addAttribute(reviewForm);
            return "review/new";
        }

        service.postReview(rollingStock, review);
        REVIEW_POSTED_MSG.appendToRedirect(redirectAtts);
        redirectAtts.addAttribute("slug", rollingStock.getSlug());
        return "redirect:/rollingstocks/{slug}/reviews";
    }
}
