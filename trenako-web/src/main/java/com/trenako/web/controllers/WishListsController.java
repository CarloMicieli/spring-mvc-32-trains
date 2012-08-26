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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Account;
import com.trenako.entities.WishList;
import com.trenako.services.AccountsService;
import com.trenako.services.WishListsService;
import com.trenako.web.controllers.form.WishListForm;
import com.trenako.web.controllers.form.WishListItemForm;
import com.trenako.web.security.UserContext;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/wishlists")
public class WishListsController {
	
	private final static Logger log = LoggerFactory.getLogger("com.trenako.web");
	
	final static ControllerMessage WISH_LIST_CREATED_MSG = ControllerMessage.success("wish.list.created.message");
	final static ControllerMessage WISH_LIST_SAVED_MSG = ControllerMessage.success("wish.list.saved.message");
	final static ControllerMessage WISH_LIST_REMOVED_MSG = ControllerMessage.success("wish.list.removed.message");
	final static ControllerMessage WISH_LIST_ITEM_UPDATED_MSG = ControllerMessage.success("wish.list.item.updated.message");
	final static ControllerMessage WISH_LIST_ITEM_DELETED_MSG = ControllerMessage.success("wish.list.item.deleted.message");
	final static ControllerMessage WISH_LIST_ITEM_ADDED_MSG = ControllerMessage.success("wish.list.item.added.message");
	final static ControllerMessage WISH_LIST_VISIBILITY_CHANGED_MSG = ControllerMessage.success("wish.list.visibility.changed.message");
	final static ControllerMessage WISH_LIST_NAME_CHANGED_MSG = ControllerMessage.success("wish.list.name.changed.message");
	final static ControllerMessage WISH_LIST_DUPLICATED_KEY_MSG = ControllerMessage.error("wish.list.duplicated.key.message");
	
	private final UserContext userContext;
	private final WishListsService service;
	private final AccountsService accountsService;
	
	private @Autowired(required = false) MessageSource messageSource;

	/**
	 * Creates a new {@code WishListsController}.
	 * @param service the {@code WishList} service
	 * @param userContext the security context
	 */
	@Autowired
	public WishListsController(WishListsService service, 
			AccountsService accountsService,
			UserContext userContext) {
		this.service = service;
		this.accountsService = accountsService;
		this.userContext = userContext;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newWishList(ModelMap model) {
		model.addAttribute("newForm", WishListForm.newForm(new WishList(), messageSource));
		return "wishlist/new";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String createWishList(@ModelAttribute @Valid WishListForm form,
			BindingResult bindingResults, 
			ModelMap model, 
			RedirectAttributes redirectAtts) {

		if (bindingResults.hasErrors()) {
			model.addAttribute("newForm", form);
			return "wishlist/new";
		}

		try {
			WishList wishList = form.wishListFor(userContext.getCurrentUser().getAccount());
			service.createNew(wishList);
	
			WISH_LIST_CREATED_MSG.appendToRedirect(redirectAtts);
			
			redirectAtts.addAttribute("slug", wishList.getSlug());
			return "redirect:/wishlists/{slug}";
		}
		catch (DuplicateKeyException ex) {
			log.error(ex.toString());
			model.addAttribute("message", WISH_LIST_DUPLICATED_KEY_MSG);
			model.addAttribute("newForm", form);
			return "wishlist/new";
		}
	}
	
	@RequestMapping(value = "/{slug}", method = RequestMethod.GET)
	public String showWishList(@ModelAttribute WishList wishList, ModelMap model) {
		model.addAttribute("wishList", service.findBySlug(wishList.getSlug()));
		return "wishlist/show";
	}

	@RequestMapping(value = "/owner/{owner}", method = RequestMethod.GET)
	public String showOwnerWishLists(@PathVariable("owner") String owner, ModelMap model) {
		
		Account user = accountsService.findBySlug(owner);
		
		model.addAttribute("owner", user);
		model.addAttribute("results", service.findByOwner(user));
		return "wishlist/list";
	}
	
	@RequestMapping(value = "/{slug}/edit", method = RequestMethod.GET)
	public String editWishList(@PathVariable("slug") String slug, ModelMap model) {
		WishList list = service.findBySlug(slug);
		model.addAttribute("editForm", WishListForm.newForm(list, messageSource));
		return "wishlist/edit";
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public String saveWishList(@ModelAttribute @Valid WishListForm form,
			BindingResult bindingResults, 
			ModelMap model, 
			RedirectAttributes redirectAtts) {

		if (bindingResults.hasErrors()) {
			model.addAttribute("editForm", form);
			return "wishlist/edit";
		}

		try {
			WishList wishList = form.wishListFor(userContext.getCurrentUser().getAccount());
			service.saveChanges(wishList);
	
			WISH_LIST_SAVED_MSG.appendToRedirect(redirectAtts);
			
			redirectAtts.addAttribute("slug", wishList.getSlug());
			return "redirect:/wishlists/{slug}";
		}
		catch (DuplicateKeyException ex) {
			log.error(ex.toString());
			model.addAttribute("message", WISH_LIST_DUPLICATED_KEY_MSG);
			model.addAttribute("editForm", form);
			return "wishlist/edit";
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public String removeWishList(@ModelAttribute WishList wishList, RedirectAttributes redirectAtts) {
		service.remove(wishList);

		WISH_LIST_REMOVED_MSG.appendToRedirect(redirectAtts);
		
		redirectAtts.addAttribute("owner", userContext.getCurrentUser().getAccount().getSlug());
		return "redirect:/wishlists/owner/{owner}";
	}
	
	@RequestMapping(value = "/items", method = RequestMethod.POST)
	public String addItem(@ModelAttribute @Valid WishListItemForm form, 
		BindingResult bindingResult, 
		ModelMap model, 
		RedirectAttributes redirectAtts) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("wishListForm", form);
			return "wishlist/addItem";
		}

		Account owner = userContext.getCurrentUser().getAccount();
		WishList wishList = service.findBySlug(form.getSlug());
		service.addItem(wishList, form.newItem(owner));

		WISH_LIST_ITEM_ADDED_MSG.appendToRedirect(redirectAtts);
		redirectAtts.addAttribute("slug", wishList.getSlug());
		return "redirect:/wishlists/{slug}";
	}
	
	@RequestMapping(value = "/items", method = RequestMethod.DELETE)
	public String removeItem(@ModelAttribute @Valid WishListItemForm form, 
		BindingResult bindingResult, 
		RedirectAttributes redirectAtts) {

		if (bindingResult.hasErrors()) {
			redirectAtts.addAttribute("slug", form.getSlug());
			return "redirect:/wishlists/{slug}";
		}
		
		WishList wishList = service.findBySlug(form.getSlug());
		
		Account owner = userContext.getCurrentUser().getAccount();
		service.removeItem(wishList, form.deletedItem(owner));

		WISH_LIST_ITEM_DELETED_MSG.appendToRedirect(redirectAtts);
		redirectAtts.addAttribute("slug", wishList.getSlug());
		return "redirect:/wishlists/{slug}";
	}
	
	@RequestMapping(value = "/{slug}/items", method = RequestMethod.PUT)
	public String updateItem(@ModelAttribute @Valid WishListItemForm form, 
		BindingResult bindingResult, 
		ModelMap model, 
		RedirectAttributes redirectAtts) {

		if (bindingResult.hasErrors()) {
			redirectAtts.addAttribute("slug", form.getSlug());
			return "redirect:/wishlists/{slug}";
		}

		Account owner = userContext.getCurrentUser().getAccount();
		WishList wishList = service.findBySlug(form.getSlug());
		service.updateItem(wishList, form.newItem(owner), form.previousPrice(owner));

		WISH_LIST_ITEM_UPDATED_MSG.appendToRedirect(redirectAtts);
		redirectAtts.addAttribute("slug", wishList.getSlug());
		return "redirect:/wishlists/{slug}";
	}
}
