/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trenako.entities.Account;
import com.trenako.web.security.SignupService;

/**
 * It represents the authentication controller.
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
	
	private final SignupService signupService;
	
	@Autowired
	public AuthController(SignupService signupService) {
		this.signupService = signupService;
	}
	
	// for testing 
	// void setSecurityService(SecurityService 
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "auth/login";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signUp() {
		return new ModelAndView("auth/signup", "account", new Account());
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String createUser(@Valid Account account, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			model.addAttribute(account);
			return "auth/signup";
		}
				
		signupService.createAccount(account);
		
		// automatically sign in the new user
		signupService.authenticate(account);
		
		return "redirect:/default";
	}
}


