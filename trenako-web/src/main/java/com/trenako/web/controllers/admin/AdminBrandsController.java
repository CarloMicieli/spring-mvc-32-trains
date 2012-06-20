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

import java.io.IOException;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Brand;
import com.trenako.services.BrandsService;

/**
 * It represents the {@code controller} to manage the {@code Brand} elements.
 *
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/admin/brands")
public class AdminBrandsController {

	private final BrandsService service;
	
	/**
	 * Creates a new {@code AdminBrandsController} controller.
	 */
	@Autowired
	public AdminBrandsController(BrandsService service) {
		this.service = service;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		return new ModelAndView("brand/list", "brands", service.findAll());
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable("id") ObjectId id) {
		return new ModelAndView("brand/show", "brand", service.findById(id));
	}
	
	/**
	 * This action renders the form to create new {@code Brand}.
	 * <p>
	 * Maps the request to {@code GET /brands/new}.
	 * </p>
	 *
	 * @return the view and model
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newForm() {
		return new ModelAndView("brand/edit", "brand", new Brand());
	}

	/**
	 * This action creates a new {@code Brand}.
	 *
	 * <p>
	 * Maps the request to {@code POST /brands}.
	 * </p>
	 *
	 * @param brand the {@code Brand} to be added
	 * @param result the validation results
	 * @param redirectAtts the redirect attributes
	 *
	 */
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
		return "redirect:/admin/brands";		
	}

	/**
	 * This action renders the form to edit a {@code Brand}.
	 *
	 * <p>
	 * Maps the request to {@code GET /brands/:id/edit}.
	 * </p>
	 *
	 * @param brandId the {@code Brand} id
	 *
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView editForm(@PathVariable("id") ObjectId brandId) {
		final Brand brand = service.findById(brandId);
		return new ModelAndView("brand/edit", "brand", brand);
	}
	
	/**
	 * This action saves a new {@code Brand}.
	 *
	 * <p>
	 * Maps the request to {@code PUT /brands}.
	 * </p>
	 *
	 * @param brand the {@code Brand} to be saved
	 * @param result the validation results
	 * @param redirectAtts the redirect attributes
	 *
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public String save(@Valid @ModelAttribute Brand brand,
		BindingResult result, 
		RedirectAttributes redirectAtts) throws IOException {
	
		if (result.hasErrors()) {
			redirectAtts.addAttribute(brand);
			return "brand/edit";
		}
	
		try {
			service.save(brand);
			return "redirect:/admin/brands";
		}
		catch (DataIntegrityViolationException dae) {
			result.reject("database.error");
			redirectAtts.addAttribute(brand);
			return "brand/edit";
		}
	}
	
	/**
	 * This action deletes a {@code Brand}.
	 *
	 * <p>
	 * Maps the request to {@code DELETE /brands/:id}.
	 * </p>
	 *
	 * @param brandId the {@code Brand} id
	 * @param redirectAtts the redirect attributes
	 *
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) 
	public String delete(@PathVariable("id") ObjectId brandId, RedirectAttributes redirectAtts) {
		final Brand brand = service.findById(brandId);
		service.remove(brand);
		
		redirectAtts.addFlashAttribute("message", "Brand deleted");
		return "redirect:/admin/brands";
	}
}