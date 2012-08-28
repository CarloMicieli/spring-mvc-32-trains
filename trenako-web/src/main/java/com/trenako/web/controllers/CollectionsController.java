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

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Account;
import com.trenako.entities.CollectionItem;
import com.trenako.entities.RollingStock;
import com.trenako.services.CollectionsService;
import com.trenako.services.RollingStocksService;
import com.trenako.web.controllers.form.CollectionItemForm;
import com.trenako.web.security.UserContext;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/collections")
public class CollectionsController {

	private @Autowired(required = false) MessageSource messageSource;
	
	private final UserContext userContext;
	private final CollectionsService service;
	private final RollingStocksService rsService;
	
	@Autowired
	public CollectionsController(CollectionsService service,
			RollingStocksService rsService,
			UserContext userContext) {
		this.service = service;
		this.rsService = rsService;
		this.userContext = userContext;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

	    binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
	}
	
	@RequestMapping(value = "/add/{slug}", method = RequestMethod.GET)
	public String addItemForm(@PathVariable("slug") String slug, ModelMap model) {
		
		RollingStock rs = rsService.findBySlug(slug);
		
		CollectionItemForm newForm = CollectionItemForm.newForm(rs, messageSource);
		model.addAttribute("itemForm", newForm);
		return "collection/add";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addItem(@Valid @ModelAttribute CollectionItemForm form, 
			BindingResult bindingResult,
			ModelMap model, 
			RedirectAttributes redirectAtts) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("itemForm", form);
			return "collection/add";
		}
		
		Account owner = userContext.getCurrentUser().getAccount();
		CollectionItem newItem = form.collectionItem(owner);
		service.addRollingStock(owner, newItem);
		
		redirectAtts.addAttribute("slug", newItem.getRollingStock().getSlug());
		return "redirect:/rollingstocks/{slug}";
	}
}
