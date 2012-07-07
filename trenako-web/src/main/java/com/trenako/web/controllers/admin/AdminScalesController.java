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
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.Standard;
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

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// allow empty string
	    binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
	}
	
	@ModelAttribute("standards")
	public Iterable<String> standards() {
		return Standard.labels();
	}
	
	/**
	 * This actions shows all the {@code Scale}s.
	 * <p>
	 * Maps the request to {@code GET /admin/scales}.
	 * </p>
	 * @param paging 
	 *
	 * @return the model and view for the {@code Scale}s list
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(Pageable paging) {
		return new ModelAndView("scale/list", "scales", service.findAll(paging));
	}
		
	/**
	 * This actions shows all the {@code Scale}s.
	 * <p>
	 * Maps the request to {@code gitGET /admin/scales/:id}.
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
	public String create(@Valid @ModelAttribute Scale scale, 
			BindingResult result, 
			RedirectAttributes redirectAtts) {
		
		if (result.hasErrors()) {
			redirectAtts.addAttribute("scale", scale);		
			return "scale/new";
		}
		
		service.save(scale);
		redirectAtts.addFlashAttribute("message", "Scale created");
		return "redirect:/admin/scales";
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
	public String save(@Valid @ModelAttribute Scale scale,
		BindingResult result,
		RedirectAttributes redirectAtts) {
	
		if (result.hasErrors()) {
			redirectAtts.addAttribute("scale", scale);
			return "scale/edit";
		}
	
		try {
			service.save(scale);
			
			redirectAtts.addFlashAttribute("message", "Scale saved");
			return "redirect:/admin/scales";
		}
		catch (DataIntegrityViolationException dae) {
			result.reject("database.error");
			redirectAtts.addAttribute("scale", scale);
			return "scale/edit";
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
	public String delete(@PathVariable("id") ObjectId scaleId, RedirectAttributes redirectAtts) {
		Scale scale = service.findById(scaleId);
		if (scale==null)
			throw new NotFoundException();
		
		service.remove(scale);
		
		redirectAtts.addFlashAttribute("message", "Scale deleted");
		return "redirect:/admin/scales";
	}
}
