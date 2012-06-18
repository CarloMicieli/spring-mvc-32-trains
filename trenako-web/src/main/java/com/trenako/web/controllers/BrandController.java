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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Brand;
import com.trenako.services.BrandsService;
import com.trenako.web.errors.NotFoundException;

/**
 * It represents the brands controller.
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/brands")
public class BrandController {

	Logger log = Logger.getLogger(BrandController.class);
	
	private final BrandsService service;
	
	/**
	 * Creates a new {@code BrandController}.
	 * @param service
	 */
	@Autowired
	public BrandController(BrandsService service) {
		this.service = service;
	}

	/**
	 * Maps the requests to {@code GET /brands}.
	 * @return a {@code Brand} list
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		Iterable<Brand> brands = service.findAll();
		return new ModelAndView("brand/list", "brands", brands);
	}
	
	/**
	 * Maps the requests to {@code GET /brands/:slug}.
	 * @param slug the {@code Brand} slug
	 * @return a {@code Brand}
	 */
	@RequestMapping(value = "/{slug}", method = RequestMethod.GET) 
	public ModelAndView show(@PathVariable("slug") String slug) {
		Brand brand = service.findBySlug(slug);
		if( brand==null )
			throw new NotFoundException();
		
		return new ModelAndView("brand/show", "brand", brand);
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newBrand(Model model) {
		model.addAttribute("brand", new Brand());
		return "brand/edit";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute Brand brand, 
			BindingResult result, 
			RedirectAttributes redirectAtts) {
		
		if( result.hasErrors() ) {
			redirectAtts.addAttribute("brand", brand);		
			return "brand/edit";		
		}
		
		// save brand
		service.save(brand);
		redirectAtts.addFlashAttribute("message", "Brand created");
		return "redirect:/brands";		
	}
}
