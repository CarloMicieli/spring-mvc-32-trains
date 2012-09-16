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
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.AppGlobals;
import com.trenako.entities.Brand;
import com.trenako.services.BrandsService;
import com.trenako.web.controllers.ControllerMessage;
import com.trenako.web.images.ImageRequest;
import com.trenako.web.images.MultipartFileValidator;
import com.trenako.web.images.UploadRequest;
import com.trenako.web.images.WebImageService;

import static com.trenako.web.controllers.ControllerMessage.*;

/**
 * It represents the {@code controller} to manage the {@code Brand} elements.
 *
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/admin/brands")
public class AdminBrandsController {

	private static final Logger log = LoggerFactory.getLogger("trenako.web");
	private MultipartFileValidator fileValidator;
	
	private final BrandsService service;
	private final WebImageService imgService;
	
	final static ControllerMessage BRAND_CREATED_MSG = success("brand.created.message");
	final static ControllerMessage BRAND_SAVED_MSG = success("brand.saved.message");
	final static ControllerMessage BRAND_DELETED_MSG = success("brand.deleted.message");
	final static ControllerMessage BRAND_LOGO_UPLOADED_MSG = success("brand.logo.uploaded.message");
	final static ControllerMessage BRAND_INVALID_UPLOAD_MSG = error("brand.invalid.file.message");
	final static ControllerMessage BRAND_LOGO_DELETED_MSG = success("brand.logo.deleted.message");
	
	/**
	 * Creates a new {@code AdminBrandsController} controller.
	 */
	@Autowired
	public AdminBrandsController(BrandsService service, WebImageService imgService) {
		this.service = service;
		this.imgService = imgService;
	}
	
	@Autowired(required = false) 
	public void setMultipartFileValidator(MultipartFileValidator validator) {
		fileValidator = validator;
	}
	
	@ModelAttribute("countries")
	public Map<String, String> countries() {
		return AppGlobals.countries();
	}
	
	/**
	 * This actions shows all the {@code Brand}s.
	 * <p>
	 * Maps the request to {@code GET /admin/brands}.
	 * </p>
	 *
	 * @param pageable the paging information
	 * @return the model and view for the {@code Brand}s list
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(Pageable pageable) {
		return new ModelAndView("brand/list", "brands", service.findAll(pageable));
	}
	
	/**
	 * This actions shows a {@code Brand}.
	 * <p>
	 * Maps the request to {@code GET /admin/brands/:slug}.
	 * </p>
	 *
	 * @param slug the {@code Brand} slug
	 * @return the model and view for the {@code Brand}
	 */
	@RequestMapping(value = "/{slug}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable("slug") String slug) {
		return new ModelAndView("brand/show", 
				"brand", 
				service.findBySlug(slug));
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
		return new ModelAndView("brand/new", "brand", new Brand());
	}

	/**
	 * This action creates a new {@code Brand}.
	 *
	 * <p>
	 * Maps the request to {@code POST /brands}.
	 * </p>
	 *
	 * @param brand the {@code Brand} to be added
	 * @param file the logo image
	 * @param result the validation results
	 * @param redirectAtts the redirect attributes
	 * @throws IOException 
	 *
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute Brand brand, 
			BindingResult result, 
			@RequestParam("file") MultipartFile file,
			ModelMap model,
			RedirectAttributes redirectAtts) throws IOException {

		// validate the uploaded file
		if (fileValidator != null) {
			fileValidator.validate(file, result);
		}
		
		if (result.hasErrors()) {
			model.addAttribute(brand);
			return "brand/new";	
		}
		
		try {
			service.save(brand);
			if (!file.isEmpty()) {
				imgService.saveImageWithThumb(UploadRequest.create(brand, file), 50);
			}
			
			redirectAtts.addFlashAttribute("message", BRAND_CREATED_MSG);
			return "redirect:/admin/brands";
		}
		catch (DataAccessException dae) {
			log.error(dae.toString());
			result.reject("database.error");
			model.addAttribute(brand);
			return "brand/new";
		}
	}

	/**
	 * This action renders the form to edit a {@code Brand}.
	 *
	 * <p>
	 * Maps the request to {@code GET /brands/:slug/edit}.
	 * </p>
	 *
	 * @param slug the {@code Brand} slug
	 *
	 */
	@RequestMapping(value = "/{slug}/edit", method = RequestMethod.GET)
	public ModelAndView editForm(@PathVariable("slug") String slug) {
		return new ModelAndView("brand/edit", 
				"brand", 
				service.findBySlug(slug));
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
		ModelMap model,
		RedirectAttributes redirectAtts) {
		
		if (result.hasErrors()) {
			model.addAttribute(brand);
			return "brand/edit";
		}
	
		try {
			service.save(brand);
			redirectAtts.addFlashAttribute("message", BRAND_SAVED_MSG);
			return "redirect:/admin/brands";
		}
		catch (DataAccessException dae) {
			log.error(dae.toString());
			result.reject("database.error");
			model.addAttribute(brand);
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
	 * @param brand the {@code Brand}
	 * @param redirectAtts the redirect attributes
	 *
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) 
	public String delete(@ModelAttribute() Brand brand, RedirectAttributes redirectAtts) {
		service.remove(brand);
		
		redirectAtts.addFlashAttribute("message", BRAND_DELETED_MSG);
		return "redirect:/admin/brands";
	}
	
	@RequestMapping(value = "/{slug}/upload", method = RequestMethod.POST)
	public String uploadImage(@ModelAttribute("brand") Brand brand, 
			BindingResult result, 
			@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAtts) throws IOException {

		boolean hasErrors = (file == null || file.isEmpty());
		
		// validate the uploaded file
		if (!hasErrors && fileValidator != null) {
			fileValidator.validate(file, result);
			hasErrors = result.hasErrors();
		}
		
		if (hasErrors) {
			redirectAtts.addAttribute("slug", brand.getSlug());
			redirectAtts.addFlashAttribute("message", BRAND_INVALID_UPLOAD_MSG);
			return "redirect:/admin/brands/{slug}";			
		}
		
		imgService.saveImageWithThumb(UploadRequest.create(brand, file), 50);
		
		redirectAtts.addFlashAttribute("message", BRAND_LOGO_UPLOADED_MSG);
		redirectAtts.addAttribute("slug", brand.getSlug());
		return "redirect:/admin/brands/{slug}";
	}
	
	@RequestMapping(value = "/{slug}/upload", method = RequestMethod.DELETE)
	public String deleteImage(@ModelAttribute("brand") Brand brand, RedirectAttributes redirectAtts) {
		imgService.deleteImage(new ImageRequest("brand", brand.getSlug()));
		
		redirectAtts.addAttribute("slug", brand.getSlug());
		redirectAtts.addFlashAttribute("message", BRAND_LOGO_DELETED_MSG);
		return "redirect:/admin/brands/{slug}";
	}
}