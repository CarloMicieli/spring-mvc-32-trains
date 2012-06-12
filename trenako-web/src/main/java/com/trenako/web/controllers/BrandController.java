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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Brand;
import com.trenako.services.BrandsService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/brands")
public class BrandController {

	Logger log = Logger.getLogger(BrandController.class);
	
	private final BrandsService service;
	
	@Autowired
	public BrandController(BrandsService service) {
		this.service = service;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		Iterable<Brand> brands = service.findAll();
		model.addAttribute("brands", brands);
		return "brand/list";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newBrand(Model model) {
		model.addAttribute("brand", new Brand());
		return "brand/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute Brand brand, 
			BindingResult bindingResult, 
			RedirectAttributes redirectAtt) {
		
		try {
			service.save(brand);	
		}
		catch(DataAccessException dae) {
			log.error(dae.getMessage());
			
			redirectAtt.addFlashAttribute("message", "Database error");
			redirectAtt.addAttribute("brand", brand);
			return "brand/new";
		}
		
		return "redirect:brands";
	}
}
