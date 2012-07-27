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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.trenako.services.BrowseService;

/**
 * It represents a controller to browse rolling stocks.
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/browse")
public class BrowseController {

	private final BrowseService service;
	
	/**
	 * Creates a new {@code BrowseController}.
	 * @param service the service
	 */
	@Autowired
	public BrowseController(BrowseService service) {
		this.service = service;
	}

	/**
	 * Renders the browsing index page.
	 * @return the model and view
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("browse/index");

		mav.addObject("brands", service.brands());
		mav.addObject("scales", service.scales());
		mav.addObject("railways", service.railways());
		mav.addObject("eras", service.eras());
		mav.addObject("categories", service.categories());
		
		return mav;
	}

	/**
	 * Renders the {@code Brand}s index page.
	 * @return the model and view
	 */
	@RequestMapping(value = "/brands", method = RequestMethod.GET)
	public ModelAndView brands() {
		ModelAndView mav = new ModelAndView("browse/brands");
		mav.addObject("brands", service.brands());
		return mav;
	}

	/**
	 * Renders the {@code era}s index page.
	 * @return the model and view
	 */
	@RequestMapping(value = "/eras", method = RequestMethod.GET)
	public ModelAndView eras() {
		ModelAndView mav = new ModelAndView("browse/eras");
		mav.addObject("eras", service.eras());
		return mav;
	}

	/**
	 * Renders the {@code Railway}s index page.
	 * @return the model and view
	 */
	@RequestMapping(value = "/railways", method = RequestMethod.GET)
	public ModelAndView railways() {
		ModelAndView mav = new ModelAndView("browse/railways");
		mav.addObject("railways", service.railways());
		return mav;
	}

	/**
	 * Renders the {@code Scale}s index page.
	 * @return the model and view
	 */
	@RequestMapping(value = "/scales", method = RequestMethod.GET)
	public ModelAndView scales() {
		ModelAndView mav = new ModelAndView("browse/scales");
		mav.addObject("scales", service.scales());
		return mav;
	}

	/**
	 * Renders the {@code categories} index page.
	 * @return the model and view
	 */
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ModelAndView categories() {
		ModelAndView mav = new ModelAndView("browse/categories");
		mav.addObject("categories", service.categories());
		return mav;
	}

	@RequestMapping(value = "/brands/{slug}", method = RequestMethod.GET)
	public ModelAndView brand(@PathVariable("slug") String slug) {
		return new ModelAndView("browse/brand", "brand", service.findBrand(slug));
	}

	@RequestMapping(value = "/railways/{slug}", method = RequestMethod.GET)
	public ModelAndView railway(@PathVariable("slug") String slug) {
		ModelAndView mav = new ModelAndView("browse/railway");
		mav.addObject("railway", service.findRailway(slug));
		mav.addObject("categories", service.categories());
		mav.addObject("eras", service.eras());
		return mav;
	}

	@RequestMapping(value = "/scales/{slug}", method = RequestMethod.GET)
	public ModelAndView scale(@PathVariable("slug") String slug) {
		ModelAndView mav = new ModelAndView("browse/scale");
		mav.addObject("scale", service.findScale(slug));
		mav.addObject("categories", service.categories());
		mav.addObject("eras", service.eras());
		return mav;
	}

	@RequestMapping(value = "/categories/{slug}", method = RequestMethod.GET)
	public ModelAndView category(@PathVariable("slug") String slug) {
		ModelAndView mav = new ModelAndView("browse/category");
		mav.addObject("category", service.findCategory(slug));
		mav.addObject("brands", service.brands());
		mav.addObject("railways", service.railways());
		return mav;
	}
	
	@RequestMapping(value = "/eras/{slug}", method = RequestMethod.GET)
	public ModelAndView era(@PathVariable("slug") String slug) {
		ModelAndView mav = new ModelAndView("browse/era");
		mav.addObject("era", service.findEra(slug));
		mav.addObject("brands", service.brands());
		mav.addObject("railways", service.railways());
		return mav;
	}
}
