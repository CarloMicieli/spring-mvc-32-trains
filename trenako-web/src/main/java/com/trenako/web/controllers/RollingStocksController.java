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

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trenako.entities.RollingStock;
import com.trenako.services.RollingStocksService;
import com.trenako.services.SelectOptionsService;

/**
 * It represents the rolling stocks management controller.
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/rollingstocks")
public class RollingStocksController {

	private final RollingStocksService service;
	private final SelectOptionsService soService;
	
	/**
	 * Creates a new {@code RollingStocksController}.
	 * @param service 
	 * @param soService the service used to fill the drop down lists
	 */
	public RollingStocksController(RollingStocksService service, SelectOptionsService soService) {
		this.service = service;
		this.soService = soService;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView createNew() {
		ModelAndView mav = new ModelAndView("rollingstock/new");
		mav.addObject(new RollingStock());
		
		// fills the data for the drop down lists.
		mav.addObject("brands", soService.brands());
		mav.addObject("scales", soService.scales());
		mav.addObject("railways", soService.railways());
		mav.addObject("categories", soService.categories());
		mav.addObject("eras", soService.eras());
		mav.addObject("powerMethods", soService.powerMethods());
		
		return mav;
	}

	@RequestMapping(value = "/{slug}", method = RequestMethod.GET)
	public String show(@PathVariable("slug") String slug, ModelMap model) {

		model.addAttribute("rollingStock", service.findBySlug(slug));
		return "rollingstock/show";
	}
}
