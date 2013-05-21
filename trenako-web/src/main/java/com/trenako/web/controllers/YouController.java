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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trenako.entities.Account;
import com.trenako.entities.Collection;
import com.trenako.entities.WishList;
import com.trenako.services.CollectionsService;
import com.trenako.services.ProfilesService;
import com.trenako.services.WishListsService;
import com.trenako.web.controllers.form.CollectionItemForm;
import com.trenako.web.controllers.form.WishListItemForm;
import com.trenako.web.security.UserContext;

/**
 * @author Carlo Micieli
 */
@Controller
@RequestMapping("/you")
public class YouController {

    @Autowired(required = false)
    private MessageSource messageSource;
    private final CollectionsService collections;
    private final WishListsService wishListsService;
    private final ProfilesService service;
    private final UserContext secContext;

    @Autowired
    public YouController(ProfilesService service,
                         CollectionsService collections,
                         WishListsService wishListsService,
                         UserContext secContext) {
        this.service = service;
        this.secContext = secContext;
        this.collections = collections;
        this.wishListsService = wishListsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        Account user = UserContext.authenticatedUser(secContext);

        ModelAndView mav = new ModelAndView("you/index");
        mav.addObject("user", user);
        mav.addObject("info", service.findProfileView(user));
        return mav;
    }

    @RequestMapping(value = "/collection", method = RequestMethod.GET)
    public String collection(ModelMap model) {
        Account user = UserContext.authenticatedUser(secContext);
        Collection collection = collections.findByOwner(user);

        model.addAttribute("collection", collection);
        model.addAttribute("owner", user);
        model.addAttribute("editForm", CollectionItemForm.jsForm(messageSource));

        return "collection/manage";
    }

    @RequestMapping(value = "/wishlists", method = RequestMethod.GET)
    public String wishlists(ModelMap model) {
        Account user = UserContext.authenticatedUser(secContext);

        model.addAttribute("owner", user);
        model.addAttribute("results", wishListsService.findByOwner(user));
        return "wishlist/list";
    }

    @RequestMapping(value = "/wishlists/{slug}", method = RequestMethod.GET)
    public String wishlist(@PathVariable("slug") String slug, ModelMap model) {
        Account user = UserContext.authenticatedUser(secContext);

        WishList wishList = wishListsService.findBySlugOrDefault(user, slug);

        model.addAttribute("wishList", wishList);
        model.addAttribute("editForm", WishListItemForm.jsForm(wishList, messageSource));
        return "wishlist/manage";
    }
}
