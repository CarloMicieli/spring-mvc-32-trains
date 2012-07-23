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

import com.trenako.criteria.SearchRequest;
import com.trenako.results.RangeRequest;
import com.trenako.services.BrowseService;

/**
 * It represents the controller to manage the requests to search
 * rolling stocks by any of the available criteria (ie by brand, by railway
 * and so on).
 * 
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/rs")
public class RollingStocksSearchController {
	
	private final BrowseService service;
	
	/**
	 * Creates a new {@code RollingStockSearchController}.
	 * @param rsService
	 */
	@Autowired
	public RollingStocksSearchController(BrowseService service) {
		this.service = service;
	}

	@RequestMapping(value = "/**", method = RequestMethod.GET)
	public ModelAndView search(SearchRequest search, RangeRequest range) {
		ModelAndView mav = new ModelAndView("browse/results");
		
		mav.addObject("results", service.findByCriteria(search, range));
		return mav;
	}
	
}
