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
import org.springframework.dao.DataAccessException;
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
import com.trenako.services.WishListsService;
import com.trenako.web.controllers.form.WishListForm;
import com.trenako.web.controllers.form.WishListItemForm;
import com.trenako.web.errors.NotFoundException;
import com.trenako.web.infrastructure.LogUtils;
import com.trenako.web.security.UserContext;

/**
 * It represents the controller for the {@code WishList} management.
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
	final static ControllerMessage WISH_LIST_DB_ERROR_MSG = ControllerMessage.error("wish.list.db.error.message");
	final static ControllerMessage WISH_LIST_NOT_FOUND_MSG = ControllerMessage.error("wish.list.not.found.message");
	
	private final UserContext userContext;
	private final WishListsService service;
	
	private @Autowired(required = false) MessageSource messageSource;

	/**
	 * Creates a new {@code WishListsController}.
	 * @param service the {@code WishList} service
	 * @param userContext the security context
	 */
	@Autowired
	public WishListsController(WishListsService service, 
			UserContext userContext) {
		this.service = service;
		this.userContext = userContext;
	}
	
	/**
	 * It shows the web form to create new {@code WishList}s.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code GET /wishlists/new}
	 * </blockquote></pre>
	 * </p>
	 *
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newWishList(ModelMap model) {
		model.addAttribute("newForm", WishListForm.newForm(messageSource));
		return "wishlist/new";
	}
	
	/**
	 * Creates a new {@code WishList}.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code POST /wishlists}
	 * </blockquote></pre>
	 * </p>
	 *
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String createWishList(@ModelAttribute @Valid WishListForm form,
			BindingResult bindingResult, 
			ModelMap model, 
			RedirectAttributes redirectAtts) {

		if (bindingResult.hasErrors()) {
			LogUtils.logValidationErrors(log, bindingResult);
			model.addAttribute("newForm", WishListForm.rejectForm(form, messageSource));
			return "wishlist/new";
		}

		try {
			Account owner = UserContext.authenticatedUser(userContext);
			WishList wishList = form.buildWishList(owner);
			service.createNew(wishList);
	
			WISH_LIST_CREATED_MSG.appendToRedirect(redirectAtts);
			
			redirectAtts.addAttribute("slug", wishList.getSlug());
			return "redirect:/you/wishlists/{slug}";
		}
		catch (DuplicateKeyException dke) {
			LogUtils.logException(log, dke);
			WISH_LIST_DUPLICATED_KEY_MSG.appendToModel(model);
		}
		catch (DataAccessException dae) {
			LogUtils.logException(log, dae);
			WISH_LIST_DB_ERROR_MSG.appendToModel(model);
		}
		
		model.addAttribute("newForm", WishListForm.rejectForm(form, messageSource));
		return "wishlist/new";
	}
	
	@RequestMapping(value = "/{slug}", method = RequestMethod.GET)
	public String showWishList(@PathVariable("slug") String slug, ModelMap model) {
		WishList wishList = service.findBySlug(slug);
		if (wishList == null) {
			throw new NotFoundException();
		}
		
		model.addAttribute("wishList", wishList);
		model.addAttribute("editForm", WishListItemForm.jsForm(wishList, messageSource));
		return "wishlist/show";
	}

	/**
	 * 
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code GET /wishlists/:slug/edit}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param slug the {@code WishList} slug
	 * @param model the model
	 * @param redirectAtts the redirect information
	 * @return the view name
	 */
	@RequestMapping(value = "/{slug}/edit", method = RequestMethod.GET)
	public String editWishList(@PathVariable("slug") String slug, 
		ModelMap model, 
		RedirectAttributes redirectAtts) {

		WishList list = service.findBySlug(slug);
		if (list == null) {
			WISH_LIST_NOT_FOUND_MSG.appendToRedirect(redirectAtts);
			return "redirect:/you/wishlists";
		}

		model.addAttribute("editForm", WishListForm.newForm(list, messageSource));
		return "wishlist/edit";
	}
	
	/**
	 * 
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code PUT /wishlists}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param form the {@code WishListForm} form
	 * @param bindingResult the validation results
	 * @param model the model
	 * @param redirectAtts the redirect information
	 * @return the view name
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public String saveWishList(@ModelAttribute @Valid WishListForm form,
			BindingResult bindingResult, 
			ModelMap model, 
			RedirectAttributes redirectAtts) {

		if (bindingResult.hasErrors()) {
			LogUtils.logValidationErrors(log, bindingResult);
			model.addAttribute("editForm", WishListForm.rejectForm(form, messageSource));
			return "wishlist/edit";
		}

		try {
			Account owner = UserContext.authenticatedUser(userContext);
			WishList wishList = form.buildWishList(owner);
			service.saveChanges(wishList);
	
			WISH_LIST_SAVED_MSG.appendToRedirect(redirectAtts);
			redirectAtts.addAttribute("slug", wishList.getSlug());
			return "redirect:/you/wishlists/{slug}";
		}
		catch (DuplicateKeyException dke) {
			LogUtils.logException(log, dke);
			WISH_LIST_DUPLICATED_KEY_MSG.appendToModel(model);
		}
		catch (DataAccessException dae) {
			LogUtils.logException(log, dae);
			WISH_LIST_DB_ERROR_MSG.appendToModel(model);
		}

		model.addAttribute("editForm", WishListForm.rejectForm(form, messageSource));
		return "wishlist/edit";
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public String removeWishList(@ModelAttribute WishList wishList, RedirectAttributes redirectAtts) {
		service.remove(wishList);

		WISH_LIST_REMOVED_MSG.appendToRedirect(redirectAtts);
		return "redirect:/you/wishlists";
	}
	
	/**
	 * 
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code POST /wishlists/items}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param form the {@code WishListItemForm} form
	 * @param bindingResult the validation results
	 * @param model the model
	 * @param redirectAtts the redirect information
	 * @return the view name
	 */
	@RequestMapping(value = "/items", method = RequestMethod.POST)
	public String addItem(@ModelAttribute @Valid WishListItemForm form, 
		BindingResult bindingResult, 
		ModelMap model, 
		RedirectAttributes redirectAtts) {

		if (bindingResult.hasErrors()) {
			LogUtils.logValidationErrors(log, bindingResult);
			model.addAttribute("wishListForm", WishListItemForm.rejectForm(form, messageSource));
			return "wishlist/addItem";
		}

		Account owner = UserContext.authenticatedUser(userContext);
		WishList wishList = service.findBySlugOrDefault(owner, form.getSlug());

		try {
			service.addItem(wishList, form.buildItem(owner));

			WISH_LIST_ITEM_ADDED_MSG.appendToRedirect(redirectAtts);
			redirectAtts.addAttribute("slug", wishList.getSlug());
			return "redirect:/you/wishlists/{slug}";
		}
		catch (DataAccessException dae) {
			LogUtils.logException(log, dae);
			WISH_LIST_DB_ERROR_MSG.appendToModel(model);
		}

		model.addAttribute("wishListForm", WishListItemForm.rejectForm(form, messageSource));
		return "wishlist/addItem";
	}
		
	/**
	 * 
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code PUT /wishlists/items}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param form the {@code WishListItemForm} form
	 * @param bindingResult the validation results
	 * @param model the model
	 * @param redirectAtts the redirect information
	 * @return the view name
	 */	
	@RequestMapping(value = "/items", method = RequestMethod.PUT)
	public String updateItem(@ModelAttribute @Valid WishListItemForm form, 
		BindingResult bindingResult, 
		ModelMap model, 
		RedirectAttributes redirectAtts) {

		if (bindingResult.hasErrors()) {
			LogUtils.logValidationErrors(log, bindingResult);
			model.addAttribute("wishListForm", WishListItemForm.rejectForm(form, messageSource));
			return "wishlist/editItem";
		}

		Account owner = UserContext.authenticatedUser(userContext);
		WishList wishList = service.findBySlug(form.getSlug());

		try {
			service.updateItem(wishList, 
				form.buildItem(owner), 
				form.previousPrice(owner));

			WISH_LIST_ITEM_UPDATED_MSG.appendToRedirect(redirectAtts);
			redirectAtts.addAttribute("slug", wishList.getSlug());
			return "redirect:/you/wishlists/{slug}";
		}
		catch (DataAccessException dae) {
			LogUtils.logException(log, dae);
			WISH_LIST_DB_ERROR_MSG.appendToModel(model);
		}

		model.addAttribute("wishListForm", WishListItemForm.rejectForm(form, messageSource));
		return "wishlist/editItem";
	}
	
	/**
	 * 
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code DELETE /wishlists/items}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param form the {@code WishListItemForm} form
	 * @param bindingResult the validation results
	 * @param redirectAtts the redirect information
	 * @return the view name
	 */
	@RequestMapping(value = "/items", method = RequestMethod.DELETE)
	public String removeItem(@ModelAttribute @Valid WishListItemForm form, 
		BindingResult bindingResult, 
		RedirectAttributes redirectAtts) {

		if (bindingResult.hasErrors()) {
			LogUtils.logValidationErrors(log, bindingResult);
			redirectAtts.addAttribute("slug", form.getSlug());
			return "redirect:/you/wishlists/{slug}";
		}
		
		WishList wishList = service.findBySlug(form.getSlug());
		
		Account owner = UserContext.authenticatedUser(userContext);
		service.removeItem(wishList, form.deletedItem(owner));

		WISH_LIST_ITEM_DELETED_MSG.appendToRedirect(redirectAtts);
		redirectAtts.addAttribute("slug", wishList.getSlug());
		return "redirect:/you/wishlists/{slug}";
	}
}
