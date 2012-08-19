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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trenako.security.AccountDetails;
import com.trenako.services.ProfilesService;
import com.trenako.web.security.UserContext;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/you")
public class YouController {

	private final ProfilesService service;
	private final UserContext secContext;
	
	@Autowired
	public YouController(ProfilesService service, UserContext secContext) {
		this.service = service;
		this.secContext = secContext;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		AccountDetails user = secContext.getCurrentUser();
		
		ModelAndView mav = new ModelAndView("you/index");
		mav.addObject("user", user);
		mav.addObject("info", service.findProfileView(user.getAccount()));
		return mav;
	}
	
}
