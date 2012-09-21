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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.entities.Brand;
import com.trenako.services.BrandsService;
import com.trenako.services.FormValuesService;
import com.trenako.web.controllers.ControllerMessage;
import com.trenako.web.controllers.form.BrandForm;
import com.trenako.web.controllers.form.UploadForm;
import com.trenako.web.images.UploadRequest;
import com.trenako.web.images.WebImageService;
import com.trenako.web.infrastructure.LogUtils;

import static com.trenako.web.controllers.ControllerMessage.*;

/**
 * It represents the administration controller for {@code Brand}s.
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/admin/brands")
public class AdminBrandsController {

	private static final Logger log = LoggerFactory.getLogger("com.trenako.web");
	
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
	
	/**
	 * It shows the {@code Brand}s list.
	 *
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
	 * It shows a {@code Brand}. If the {@code Brand} with the provided slug
	 * is not found, this method will redirect to the brands list.
	 * <p>
	 * <pre><blockquote>
	 * {@code GET /admin/brands/:slug}
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
	 * It shows the web form for new {@code Brand} creation.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code GET /brands/new}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param model the model
	 * @return the view name
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newForm(ModelMap model) {
		model.addAttribute(BrandForm.newForm(new Brand(), formService));
		return "brand/new";
	}

	/**
	 * It creates a new {@code Brand} using the posted form values.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code POST /brands}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param form the form for {@code Brand} to be created
	 * @param bindingResult the validation results
	 * @param model the model
	 * @param redirectAtts the redirect attributes
	 * @return the view name
	 * @throws IOException
	 *
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute BrandForm form, 
			BindingResult bindingResult, 
			ModelMap model,
			RedirectAttributes redirectAtts) throws IOException {

		if (bindingResult.hasErrors()) {
			LogUtils.logValidationErrors(log, bindingResult);
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
			bindingResult.rejectValue("brand.name", "brand.name.already.used");
		}
		catch (DataAccessException dae) {
			LogUtils.logException(log, dae);
			BRAND_DB_ERROR_MSG.appendToModel(model);
		}
		
		model.addAttribute(BrandForm.rejectedForm(form, formService));
		return "brand/new";
	}

	/**
	 * It shows the web form for {@code Brand} editing.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code GET /brands/:slug/edit}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param slug the {@code Brand} slug
	 * @param model the model
	 * @param redirectAtts the redirect attributes 
	 * @return the view name
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
	 * It saves the {@code Brand} using the posted form values.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code PUT /brands}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param form the {@code Brand} form to be saved
	 * @param bindingResult the validation results
	 * @param model the model
	 * @param redirectAtts the redirect attributes
	 * @return the view name
	 *
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public String save(@Valid @ModelAttribute BrandForm form,
		BindingResult bindingResult, 
		ModelMap model,
		RedirectAttributes redirectAtts) {
		
		if (bindingResult.hasErrors()) {
			LogUtils.logValidationErrors(log, bindingResult);
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
	 * It deletes a {@code Brand}.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code DELETE /brands/:id}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param brand the {@code Brand}
	 * @param redirectAtts the redirect attributes
	 *
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) 
	public String delete(@ModelAttribute Brand brand, RedirectAttributes redirectAtts) {
		service.remove(brand);
		redirectAtts.addFlashAttribute("message", BRAND_DELETED_MSG);
		return "redirect:/admin/brands";
	}
	
	/**
	 * This action is uploading a new logo picture for a {@code Brand}.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code POST /admin/brands/upload}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param uploadForm the form for the file upload
	 * @param bindingResult the validation results
	 * @param redirectAtts the redirect attributes
	 * @return the view name
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadImage(@Valid @ModelAttribute UploadForm uploadForm,
		 BindingResult bindingResult, 
		 RedirectAttributes redirectAtts) throws IOException {
	
		if (bindingResult.hasErrors()) {
			LogUtils.logValidationErrors(log, bindingResult);
			
			BRAND_INVALID_UPLOAD_MSG.appendToRedirect(redirectAtts);
			redirectAtts.addAttribute("slug", uploadForm.getSlug());
			return "redirect:/admin/brands/{slug}";
		}
		
		if (!uploadForm.isEmpty()) {
			UploadRequest uploadRequest = uploadForm.buildUploadRequest();
			imgService.saveImageWithThumb(uploadRequest, 50);
			BRAND_LOGO_UPLOADED_MSG.appendToRedirect(redirectAtts);
		}
			
		redirectAtts.addAttribute("slug", uploadForm.getSlug());
		return "redirect:/admin/brands/{slug}";
	}
	
	/**
	 * This action is deleting the logo picture from the {@code Brand}.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code DELETE /admin/brands/upload}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param uploadForm the form for the file upload
	 * @param redirectAtts the redirect attributes
	 * @return the view name
	 */	
	@RequestMapping(value = "/upload", method = RequestMethod.DELETE)
	public String deleteImage(@ModelAttribute UploadForm uploadForm, RedirectAttributes redirectAtts) {
		imgService.deleteImage(uploadForm.buildImageRequest());
	
		BRAND_LOGO_DELETED_MSG.appendToRedirect(redirectAtts);
		redirectAtts.addAttribute("slug", uploadForm.getSlug());
		return "redirect:/admin/brands/{slug}";
	}
}