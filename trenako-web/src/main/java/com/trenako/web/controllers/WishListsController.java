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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Account;
import com.trenako.entities.WishList;
import com.trenako.services.WishListsService;
import com.trenako.values.Visibility;
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
	final static ControllerMessage WISH_LIST_CREATED_MSG = ControllerMessage.success("wish.list.created.message");
	final static ControllerMessage WISH_LIST_REMOVED_MSG = ControllerMessage.success("wish.list.removed.message");
	final static ControllerMessage WISH_LIST_ITEM_UPDATED_MSG = ControllerMessage.success("wish.list.item.updated.message");
	final static ControllerMessage WISH_LIST_ITEM_DELETED_MSG = ControllerMessage.success("wish.list.item.deleted.message");
	final static ControllerMessage WISH_LIST_ITEM_ADDED_MSG = ControllerMessage.success("wish.list.item.added.message");
	final static ControllerMessage WISH_LIST_VISIBILITY_CHANGED_MSG = ControllerMessage.success("wish.list.visibility.changed.message");
	final static ControllerMessage WISH_LIST_NAME_CHANGED_MSG = ControllerMessage.success("wish.list.name.changed.message");
	final static ControllerMessage WISH_LIST_DUPLICATED_KEY_MSG = ControllerMessage.error("wish.list.duplicated.key.message");
	
	private final UserContext userContext;
	private final WishListsService service;

	/**
	 * Creates a new {@code WishListsController}.
	 * @param service the {@code WishList} service
	 * @param userContext the security context
	 */
	@Autowired
	public WishListsController(WishListsService service, UserContext userContext) {
		this.service = service;
		this.userContext = userContext;
	}
	
	@RequestMapping(value = "/owner/{slug}", method = RequestMethod.GET)
	public String showOwnerWishLists(@ModelAttribute Account owner, ModelMap model) {
		model.addAttribute("results", service.findByOwner(owner));
		return "wishlist/list";
	}
	
	@RequestMapping(value = "/{slug}", method = RequestMethod.GET)
	public String showWishList(@ModelAttribute WishList wishList, ModelMap model) {
		model.addAttribute(service.findBySlug(wishList.getSlug()));
		return "wishlist/show";
	}


	@RequestMapping(value = "/owner/{owner}/new", method = RequestMethod.GET)
	public String newWishList(ModelMap model) {
		Account owner = userContext.getCurrentUser().getAccount();
		//model.addAttribute(new WishList(owner));
		return "wishlist/new";
	}

	@RequestMapping(value = "/owner/{owner}", method = RequestMethod.POST)
	public String createWishList(@ModelAttribute @Valid WishList wishList,
			BindingResult bindingResults, 
			ModelMap model, 
			RedirectAttributes redirectAtts) {

		if (bindingResults.hasErrors()) {
			model.addAttribute(wishList);
			return "wishlist/new";
		}

		try {
			//wishList.setOwner(userContext.getCurrentUser().getAccount());
			service.createNew(wishList);
	
			WISH_LIST_CREATED_MSG.appendToRedirect(redirectAtts);
			return "redirect:/wishlists/{slug}";
		}
		catch (DuplicateKeyException ex) {
			model.addAttribute("message", WISH_LIST_DUPLICATED_KEY_MSG);
			model.addAttribute(wishList);
			return "wishlist/new";
		}
	}

	@RequestMapping(value = "/{slug}", method = RequestMethod.DELETE)
	public String removeWishList(@ModelAttribute WishList wishList, RedirectAttributes redirectAtts) {
		service.remove(wishList);

		WISH_LIST_REMOVED_MSG.appendToRedirect(redirectAtts);
		return "redirect:/wishlists/owner/{owner}";
	}

	@RequestMapping(value = "/{slug}/items", method = RequestMethod.POST)
	public String addItem(@ModelAttribute @Valid WishListItemForm form, 
		BindingResult bindingResult, 
		ModelMap model, 
		RedirectAttributes redirectAtts) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("wishListForm", form);
			return "wishlist/addItem";
		}

		WishList wishList = service.findBySlug(form.getSlug());
		service.addItem(wishList, form.wishListItem());

		WISH_LIST_ITEM_ADDED_MSG.appendToRedirect(redirectAtts);
		return "redirect:/wishlists/{slug}";
	}

	@RequestMapping(value = "/{slug}/items", method = RequestMethod.PUT)
	public String updateItem(@ModelAttribute @Valid WishListItemForm form, 
		BindingResult bindingResult, 
		ModelMap model, 
		RedirectAttributes redirectAtts) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("wishListForm", form);
			return "wishlist/editItem";
		}

		WishList wishList = service.findBySlug(form.getSlug());
//		service.updateItem(wishList, form.wishListItem());

		WISH_LIST_ITEM_UPDATED_MSG.appendToRedirect(redirectAtts);
		return "redirect:/wishlists/{slug}";
	}

	@RequestMapping(value = "/{slug}/items", method = RequestMethod.DELETE)
	public String removeItem(@ModelAttribute @Valid WishListItemForm form, 
		RedirectAttributes redirectAtts) {

		WishList wishList = service.findBySlug(form.getSlug());
		service.removeItem(wishList, form.wishListItem());

		WISH_LIST_ITEM_DELETED_MSG.appendToRedirect(redirectAtts);
		return "redirect:/wishlists/{slug}";
	}

	@RequestMapping(value = "/{slug}/visibility", method = RequestMethod.PUT)
	public String updateVisibility(@ModelAttribute WishList wishList, 
		RedirectAttributes redirectAtts) {

		Visibility visibility = Visibility.parse(wishList.getVisibility());

		service.changeVisibility(wishList, visibility);
		
		WISH_LIST_VISIBILITY_CHANGED_MSG.appendToRedirect(redirectAtts);
		return "redirect:/wishlists/{slug}"; 
	}

	@RequestMapping(value = "/{slug}/name", method = RequestMethod.PUT)
	public String updateName(@ModelAttribute WishList wishList, 
		RedirectAttributes redirectAtts) {

		service.changeName(wishList, wishList.getName());
		
		WISH_LIST_NAME_CHANGED_MSG.appendToRedirect(redirectAtts);
		return "redirect:/wishlists/{slug}"; 
	}
}
