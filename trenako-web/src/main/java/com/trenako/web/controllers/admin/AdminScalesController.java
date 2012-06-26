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
import org.springframework.web.servlet.view.RedirectView;

import com.trenako.entities.Scale;
import com.trenako.services.ScalesService;
import com.trenako.web.errors.NotFoundException;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/admin/scales")
public class AdminScalesController {

	private final ScalesService service;
	
	/**
	 * Creates a new {@code AdminScalesController} controller.
	 * @param service the scales service
	 */
	@Autowired
	public AdminScalesController(ScalesService scaleService) {
		this.service = scaleService;
	}

	/**
	 * This actions shows all the {@code Scale}s.
	 * <p>
	 * Maps the request to {@code GET /admin/scales}.
	 * </p>
	 *
	 * @return the model and view for the {@code Scale}s list
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		return new ModelAndView("scale/list", "scales", service.findAll());
	}
		
	/**
	 * This actions shows all the {@code Scale}s.
	 * <p>
	 * Maps the request to {@code GET /admin/scales/:id}.
	 * </p>
	 *
	 * @param id the {@code Scale} id
	 * @return the model and view for the {@code Scale}
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable("id") ObjectId id) {
		Scale scale = service.findById(id);
		if (scale==null)
			throw new NotFoundException();
		return new ModelAndView("scale/show", "scale", scale);
	}
	
	/**
	 * This action renders the form to create new {@code Scale}.
	 * <p>
	 * Maps the request to {@code GET /admin/scales/new}.
	 * </p>
	 *
	 * @return the model and view for the new {@code Scale} form
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newForm() {
		return new ModelAndView("scale/new", "scale", new Scale());
	}

	/**
	 * This action creates a new {@code Scale}.
	 *
	 * <p>
	 * Maps the request to {@code POST /admin/scales}.
	 * </p>
	 *
	 * @param scale the {@code Scale} to be added
	 * @param file the logo image
	 * @param result the validation results
	 * @param redirectAtts the redirect attributes
	 *
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView create(@Valid @ModelAttribute Scale scale, 
			BindingResult result, 
			RedirectAttributes redirectAtts) {
		
		if (result.hasErrors()) {
			return new ModelAndView("scale/new", "scale", scale);
		}
		
		service.save(scale);
		redirectAtts.addFlashAttribute("message", "Scale created");
		return new ModelAndView(new RedirectView("/admin/scales"));
	}
	
	/**
	 * This action renders the form to edit a {@code Scale}.
	 *
	 * <p>
	 * Maps the request to {@code GET /admin/scales/:id/edit}.
	 * </p>
	 *
	 * @param scaleId the {@code Scale} id
	 *
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView editForm(@PathVariable("id") ObjectId scaleId) {
		Scale scale = service.findById(scaleId);
		if (scale==null)
			throw new NotFoundException();
		
		return new ModelAndView("scale/edit", "scale", scale);
	}

	/**
	 * This action saves a new {@code Scale}.
	 *
	 * <p>
	 * Maps the request to {@code POST /admin/scales:id}.
	 * </p>
	 *
	 * @param scale the {@code Scale} to be updated
	 * @param result the validation results
	 * @param redirectAtts the redirect attributes
	 * @return the view name
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ModelAndView save(@Valid @ModelAttribute Scale scale,
		BindingResult result,
		RedirectAttributes redirectAtts) {
	
		if (result.hasErrors()) {
			return new ModelAndView("scale/edit", "scale", scale);
		}
	
		try {
			service.save(scale);
			
			redirectAtts.addFlashAttribute("message", "Scale saved");
			return new ModelAndView(new RedirectView("/admin/scales"));
		}
		catch (DataIntegrityViolationException dae) {
			result.reject("database.error");
			return new ModelAndView("scale/edit", "scale", scale);
		}
	}

	/**
	 * This action deletes a {@code Scale}.
	 *
	 * <p>
	 * Maps the request to {@code DELETE /admin/scales/:id}.
	 * </p>
	 *
	 * @param scaleId the {@code Scale} id
	 * @param redirectAtts the redirect attributes
	 * @return the view name
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) 
	public ModelAndView delete(@PathVariable("id") ObjectId scaleId, RedirectAttributes redirectAtts) {
		Scale scale = service.findById(scaleId);
		if (scale==null)
			throw new NotFoundException();
		
		service.remove(scale);
		
		redirectAtts.addFlashAttribute("message", "Scale deleted");
		return new ModelAndView(new RedirectView("/admin/scales"));
	}
}
