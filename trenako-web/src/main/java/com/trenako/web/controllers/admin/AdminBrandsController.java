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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Brand;
import com.trenako.services.BrandsService;
import com.trenako.services.FormValuesService;
import com.trenako.web.controllers.ControllerMessage;
import com.trenako.web.controllers.form.BrandForm;
import com.trenako.web.images.ImageRequest;
import com.trenako.web.images.MultipartFileValidator;
import com.trenako.web.images.UploadRequest;
import com.trenako.web.images.WebImageService;
import com.trenako.web.infrastructure.LogUtils;

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

	private static final Logger log = LoggerFactory.getLogger("com.trenako.web");
	private MultipartFileValidator fileValidator;
	
	private final BrandsService service;
	private final WebImageService imgService;
	private final FormValuesService formService;
	
	final static ControllerMessage BRAND_CREATED_MSG = success("brand.created.message");
	final static ControllerMessage BRAND_SAVED_MSG = success("brand.saved.message");
	final static ControllerMessage BRAND_DELETED_MSG = success("brand.deleted.message");
	final static ControllerMessage BRAND_LOGO_UPLOADED_MSG = success("brand.logo.uploaded.message");
	final static ControllerMessage BRAND_INVALID_UPLOAD_MSG = error("brand.invalid.file.message");
	final static ControllerMessage BRAND_LOGO_DELETED_MSG = success("brand.logo.deleted.message");
	final static ControllerMessage BRAND_DB_ERROR_MSG = error("brand.database.error.message");
	final static ControllerMessage BRAND_NOT_FOUND_MSG = error("brand.not.found.message");
	
	/**
	 * Creates a new {@code AdminBrandsController} controller.
	 * @param formService 
	 */
	@Autowired
	public AdminBrandsController(BrandsService service, FormValuesService formService, WebImageService imgService) {
		this.service = service;
		this.imgService = imgService;
		this.formService = formService;
	}
	
	@Autowired(required = false) 
	public void setMultipartFileValidator(MultipartFileValidator validator) {
		fileValidator = validator;
	}

	/**
	 * This actions shows the {@code Brand} list.
	 * <p>
	 * <pre><blockquote>
	 * {@code GET /admin/brands}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param pageable the paging information
	 * @param model the model
	 * @return the view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("brands", service.findAll(pageable));
		return "brand/list";
	}
	
	/**
	 * This actions shows a {@code Brand}. If the {@code Brand} with the provided slug
	 * is not found, this method will redirect to the brands list.
	 * <p>
	 * <pre><blockquote>
	 * {@code GET /admin/brands/:slug}.
	 * </blockquote></pre>	 
	 * </p>
	 *
	 * @param slug the {@code Brand} slug
	 * @param model the model
	 * @param redirectAtts the redirect attributes	 
	 * @return the view name
	 */
	@RequestMapping(value = "/{slug}", method = RequestMethod.GET)
	public String show(@PathVariable("slug") String slug, ModelMap model, RedirectAttributes redirectAtts) {
		Brand brand = service.findBySlug(slug);
		if (brand == null) {
			BRAND_NOT_FOUND_MSG.appendToRedirect(redirectAtts);
			return "redirect:/admin/brands";
		}

		model.addAttribute(brand);
		return "brand/show";
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
	public String newForm(ModelMap model) {
		model.addAttribute(BrandForm.newForm(new Brand(), formService));
		return "brand/new";
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
	public String create(@Valid @ModelAttribute BrandForm form, 
			BindingResult result, 
			ModelMap model,
			RedirectAttributes redirectAtts) throws IOException {

		if (result.hasErrors()) {
			LogUtils.logValidationErrors(log, result);
			model.addAttribute(BrandForm.rejectedForm(form, formService));
			return "brand/new";	
		}

		Brand brand = form.getBrand();
		MultipartFile file = form.getFile();
		try {
			service.save(brand);
			if (!file.isEmpty()) {
				imgService.saveImageWithThumb(UploadRequest.create(brand, file), 50);
			}
			
			BRAND_CREATED_MSG.appendToRedirect(redirectAtts);
			return "redirect:/admin/brands";
		}
		catch (DuplicateKeyException dke) {
			LogUtils.logException(log, dke);
			result.rejectValue("brand.name", "brand.name.already.used");
			model.addAttribute(BrandForm.rejectedForm(form, formService));
			return "brand/new";
		}
		catch (DataAccessException dae) {
			LogUtils.logException(log, dae);
			BRAND_DB_ERROR_MSG.appendToModel(model);
			model.addAttribute(BrandForm.rejectedForm(form, formService));
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
	public String editForm(@PathVariable("slug") String slug, ModelMap model, RedirectAttributes redirectAtts) {
		Brand brand = service.findBySlug(slug);
		if (brand == null) {
			BRAND_NOT_FOUND_MSG.appendToRedirect(redirectAtts);
			return "redirect:/admin/brands";
		}

		model.addAttribute(BrandForm.newForm(brand, formService));
		return "brand/edit";
	}
	
	/**
	 * This action saves a new {@code Brand}.
	 *
	 * <p>
	 * Maps the request to {@code PUT /brands}.
	 * </p>
	 *
	 * @param form the {@code Brand} form to be saved
	 * @param result the validation results
	 * @param redirectAtts the redirect attributes
	 *
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public String save(@Valid @ModelAttribute BrandForm form,
		BindingResult result, 
		ModelMap model,
		RedirectAttributes redirectAtts) {
		
		if (result.hasErrors()) {
			log.info(result.toString());
			LogUtils.logValidationErrors(log, result);
			model.addAttribute(BrandForm.rejectedForm(form, formService));
			return "brand/edit";
		}
		
		Brand brand = form.getBrand();
		try {
			service.save(brand);
			BRAND_SAVED_MSG.appendToRedirect(redirectAtts);
			return "redirect:/admin/brands";
		}
		catch (DataAccessException dae) {
			LogUtils.logException(log, dae);
			BRAND_DB_ERROR_MSG.appendToModel(model);
			model.addAttribute(BrandForm.rejectedForm(form, formService));
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