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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trenako.entities.Account;
import com.trenako.services.HomeService;
import com.trenako.web.security.UserContext;

/**
 * It represents the controller for homepage and its children pages (mostly static). 
 * @author Carlo Micieli
 *
 */
@Controller
public class HomeController {
	
	private final HomeService service;
	private final UserContext userContext;
	
	/**
	 * Creates a new {@code HomeController}.
	 * @param service the homepage service
	 * @param userContext the user context
	 */
	@Autowired
	public HomeController(HomeService service, UserContext userContext) {
		this.service = service;
		this.userContext = userContext;
	}
		
	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public String home(ModelMap model) {
		model.addAttribute("content", service.getHomeContent(loggedUser()));		
		return "home/index";
	}
	
	@RequestMapping(value = "/home/explore", method = RequestMethod.GET)
	public String explore() {
		return "home/explore";
	}
	
	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public String defaultAction() {
		return "redirect:/home";
	}

	@RequestMapping(value = "/home/developers", method = RequestMethod.GET)
	public String developers() {
		return "home/developers";
	}
	
	@RequestMapping(value = "/home/privacy", method = RequestMethod.GET)
	public String privacy() {
		return "home/privacy";
	}
	
	@RequestMapping(value = "/home/terms", method = RequestMethod.GET)
	public String termsOfUse() {
		return "home/terms";
	}
	
	private Account loggedUser() {
		if (userContext != null && userContext.getCurrentUser() != null) {
			return userContext.getCurrentUser().getAccount();
		}
		
		return null;
	}
}
