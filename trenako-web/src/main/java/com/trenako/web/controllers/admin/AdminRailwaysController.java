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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Railway;
import com.trenako.services.RailwaysService;
import com.trenako.web.errors.NotFoundException;
import com.trenako.web.images.ImageProcessingAdapter;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/admin/railways")
public class AdminRailwaysController {
	private final RailwaysService service;
	private final ImageProcessingAdapter imgUtils;
	
	@Autowired
	public AdminRailwaysController(RailwaysService service, ImageProcessingAdapter imgUtils) {
		this.service = service;
		this.imgUtils = imgUtils;
	}

	/**
	 * This actions shows all the {@code Railway}s.
	 * <p>
	 * Maps the request to {@code GET /admin/railways}.
	 * </p>
	 *
	 * @param pageable the paging information
	 * @return the model and view for the {@code Railway}s list
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list() {
		return new ModelAndView("railway/list", "railways", service.findAll());
	}

	/**
	 * This actions shows all the {@code Railway}s.
	 * <p>
	 * Maps the request to {@code GET /admin/railways/:id}.
	 * </p>
	 *
	 * @param id the {@code Railway} id
	 * @return the model and view for the {@code Railway}
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable("id") ObjectId id) {
		Railway railway = service.findById(id);
		if (railway==null)
			throw new NotFoundException();
		
		return new ModelAndView("railway/show", "railway", railway);
	}

	/**
	 * This action renders the form to create new {@code Railway}.
	 * <p>
	 * Maps the request to {@code GET /admin/railways/new}.
	 * </p>
	 *
	 * @return the model and view for the new {@code Railway} form
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView newForm() {
		return new ModelAndView("railway/new", "railway", new Railway());
	}
	

	/**
	 * This action creates a new {@code Railway}.
	 *
	 * <p>
	 * Maps the request to {@code POST /admin/railways}.
	 * </p>
	 *
	 * @param railway the {@code Railway} to be added
	 * @param file the logo image
	 * @param result the validation results
	 * @param redirectAtts the redirect attributes
	 * @throws IOException 
	 *
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute Railway railway, 
			@RequestParam("file") MultipartFile file,
			BindingResult result, 
			RedirectAttributes redirectAtts) throws IOException {
		
		if (result.hasErrors()) {
			redirectAtts.addAttribute("railway", railway);
			return "railway/new";
		}
		
		if (!file.isEmpty()) {
			railway.setImage(imgUtils.createImage(file));
		}
		
		service.save(railway);
		redirectAtts.addFlashAttribute("message", "Railway created");
		return "redirect:/admin/railways";
	}
	
	/**
	 * This action renders the form to edit a {@code Railway}.
	 *
	 * <p>
	 * Maps the request to {@code GET /admin/railways/:id/edit}.
	 * </p>
	 *
	 * @param railwayId the {@code Railway} id
	 *
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView editForm(@PathVariable("id") ObjectId railwayId) {
		Railway railway = service.findById(railwayId);
		if (railway==null)
			throw new NotFoundException();
		
		return new ModelAndView("railway/edit", "railway", railway);
	}

	/**
	 * This action saves a new {@code Railway}.
	 *
	 * <p>
	 * Maps the request to {@code POST /admin/railways:id}.
	 * </p>
	 *
	 * @param railway the {@code Railway} to be updated
	 * @param result the validation results
	 * @param redirectAtts the redirect attributes
	 * @return the view name
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public String save(@Valid @ModelAttribute Railway railway,
		BindingResult result,
		RedirectAttributes redirectAtts) throws IOException {
	
		if (result.hasErrors()) {
			redirectAtts.addAttribute("railway", railway);
			return "railway/edit";
		}
	
		try {
			service.save(railway);
			
			redirectAtts.addFlashAttribute("message", "Railway saved");
			return "redirect:/admin/railways";
		}
		catch (DataIntegrityViolationException dae) {
			result.reject("database.error");
			redirectAtts.addAttribute("railway", railway);
			return "railway/edit";
		}
	}

	/**
	 * This action deletes a {@code Railway}.
	 *
	 * <p>
	 * Maps the request to {@code DELETE /admin/railways/:id}.
	 * </p>
	 *
	 * @param railwayId the {@code Railway} id
	 * @param redirectAtts the redirect attributes
	 * @return the view name
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) 
	public String delete(@PathVariable("id") ObjectId railwayId, RedirectAttributes redirectAtts) {
		Railway railway = service.findById(railwayId);
		if (railway==null)
			throw new NotFoundException();
		
		service.remove(railway);
		
		redirectAtts.addFlashAttribute("message", "Railway deleted");
		return "redirect:/admin/railways";
	}
	
}
