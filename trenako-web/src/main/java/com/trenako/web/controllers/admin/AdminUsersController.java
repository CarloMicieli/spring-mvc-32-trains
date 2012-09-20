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
package com.trenako.web.controllers.admin;

import static com.trenako.web.controllers.ControllerMessage.*;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Account;
import com.trenako.services.AccountsService;
import com.trenako.web.controllers.ControllerMessage;

import com.trenako.web.infrastructure.LogUtils;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/admin/users")
public class AdminUsersController {

	private static final Logger log = LoggerFactory.getLogger("com.trenako.web");

	final static ControllerMessage USER_SAVED_MSG = success("user.saved.message");
	final static ControllerMessage USER_BLOCKED_MSG = success("user.blocked.message");
	final static ControllerMessage USER_DB_ERROR_MSG = error("user.db.error.message");
	final static ControllerMessage USER_NOT_FOUND_MSG = error("user.not.found.message");
	
	private final AccountsService userService;

	@Autowired
	public AdminUsersController(AccountsService userService) {
		this.userService = userService;
	}
	
	@ModelAttribute("rolesList")
	public Iterable<String> roles() {
		return Account.accountRoles();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String usersList(Pageable pageable, ModelMap model) {
		model.addAttribute("users", userService.findAll(pageable));
		return "user/list";
	}

	@RequestMapping(value = "/{slug}/edit", method = RequestMethod.GET)
	public String editForm(@PathVariable("slug") String slug, 
		ModelMap model, 
		RedirectAttributes redirectAtts) {

		Account user = userService.findBySlug(slug);
		if (user == null) {
			USER_NOT_FOUND_MSG.appendToRedirect(redirectAtts);
			return "redirect:/admin/users";
		}

		model.addAttribute(user);
		return "user/edit";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String saveUser(@Valid @ModelAttribute Account user, 
		BindingResult bindingResult, 
		ModelMap model, 
		RedirectAttributes redirectAtts) {

		if (bindingResult.hasErrors()) {
			LogUtils.logValidationErrors(log, bindingResult);
			model.addAttribute(user);
			return "user/edit";
		}

		try {
			userService.updateChanges(user);
			USER_SAVED_MSG.appendToRedirect(redirectAtts);
			return "redirect:/admin/users";
		}
		catch (DataAccessException dae) {
			LogUtils.logException(log, dae);
			USER_DB_ERROR_MSG.appendToModel(model);
			model.addAttribute(user);
			return "user/edit";
		}
	}

}
