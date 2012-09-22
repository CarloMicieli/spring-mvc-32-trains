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

import static com.trenako.web.controllers.ControllerMessage.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.AppGlobals;
import com.trenako.entities.Railway;
import com.trenako.services.RailwaysService;
import com.trenako.web.controllers.ControllerMessage;
import com.trenako.web.controllers.form.RailwayForm;
import com.trenako.web.controllers.form.UploadForm;
import com.trenako.web.images.UploadRequest;
import com.trenako.web.images.WebImageService;
import com.trenako.web.infrastructure.LogUtils;

/**
 * The administration controller for {@code Railway}s.
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/admin/railways")
public class AdminRailwaysController {

	private static final Logger log = LoggerFactory.getLogger("com.trenako.web");

	private final RailwaysService service;
	private final WebImageService imgService;
	
	final static ControllerMessage RAILWAY_CREATED_MSG = success("railway.created.message");
	final static ControllerMessage RAILWAY_SAVED_MSG = success("railway.saved.message");
	final static ControllerMessage RAILWAY_DELETED_MSG = success("railway.deleted.message");
	final static ControllerMessage RAILWAY_NOT_FOUND_MSG = error("railway.not.found.message");
	final static ControllerMessage RAILWAY_DB_ERROR_MSG = error("railway.db.error.message");
	final static ControllerMessage RAILWAY_LOGO_UPLOADED_MSG = success("railway.logo.uploaded.message");
	final static ControllerMessage RAILWAY_INVALID_UPLOAD_MSG = error("railway.invalid.file.message");
	final static ControllerMessage RAILWAY_LOGO_DELETED_MSG = success("railway.logo.deleted.message");
	
	/**
	 * Creates a new {@code AdminRailwaysController}.
	 * @param service the railways service
	 * @param imgService the images service
	 */
	@Autowired
	public AdminRailwaysController(RailwaysService service, WebImageService imgService) {
		this.service = service;
		this.imgService = imgService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);

	    // convert empty String to null
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	/**
	 * Returns the countries list.
	 * @return the countries list
	 */
	@ModelAttribute("countries")
	public Map<String, String> countries() {
		return AppGlobals.countries();
	}

	/**
	 * It shows the {@code Railway}s list.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code GET /admin/railways}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param pageable the paging information
	 * @param model the model 
	 * @return the view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("railways", service.findAll(pageable));
		return "railway/list";
	}

	/**
	 * It shows a {@code Railway}.
	 *
	 * <p>
	 * </blockquote></pre>
	 * {@code GET /admin/railways/:slug}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param slug the {@code Railway} slug
	 * @param model the model
	 * @param redirectAtts the redirect attributes
	 * @return the view name
	 */
	@RequestMapping(value = "/{slug}", method = RequestMethod.GET)
	public String show(@PathVariable("slug") String slug, ModelMap model, RedirectAttributes redirectAtts) {
		Railway railway = service.findBySlug(slug);
		if (railway == null) {
			RAILWAY_NOT_FOUND_MSG.appendToRedirect(redirectAtts);
			return "redirect:/admin/railways";
		}

		model.addAttribute("railway", railway);
		model.addAttribute(new UploadForm("railway", slug, null));
		return "railway/show";
	}

	/**
	 * It shows the web form for new {@code Railway} creation.
	 *
	 * <p>
	 * </blockquote></pre>
	 * {@code GET /admin/railways/new}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param model the model
	 * @return the view name
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newForm(ModelMap model) {
		RailwayForm form = RailwayForm.newForm(new Railway());
		model.addAttribute(form);
		return "railway/new";
	}
	
	/**
	 * It creates a new {@code Railway} using the posted form values.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code POST /admin/railways}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param railwayForm the form for the {@code Railway} to be added
	 * @param bindingResult the validation results
	 * @param model the model
	 * @param redirectAtts the redirect attributes
	 * @return the view name
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute RailwayForm railwayForm, 
			BindingResult bindingResult,
			ModelMap model,
			RedirectAttributes redirectAtts) throws IOException {
		
		if (bindingResult.hasErrors()) {
			LogUtils.logValidationErrors(log, bindingResult);
			model.addAttribute(railwayForm);
			return "railway/new";
		}

		Railway railway = railwayForm.getRailway();
		MultipartFile file = railwayForm.getFile();
		try {
			service.save(railway);
			if (file != null && !file.isEmpty()) {
				imgService.saveImageWithThumb(UploadRequest.create(railway, file), 50);
			}

			RAILWAY_CREATED_MSG.appendToRedirect(redirectAtts);
			return "redirect:/admin/railways";
		}
		catch (DuplicateKeyException dke) {
			LogUtils.logException(log, dke);
			bindingResult.rejectValue("railway.name", "railway.name.already.used");
		}		
		catch (DataAccessException dae) {
			LogUtils.logException(log, dae);
			RAILWAY_DB_ERROR_MSG.appendToModel(model);
		}
		
		model.addAttribute(railwayForm);
		return "railway/new";
	}
	
	/**
	 * It shows the form for {@code Railway} editing.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code GET /admin/railways/:slug/edit}
	 * </blockquote></pre>
	 * </p>	 
	 *
	 * @param slug the {@code Railway} slug
	 * @param model the model
	 * @param redirectAtts the redirect attributes
	 * @return the view name
	 *
	 */
	@RequestMapping(value = "/{slug}/edit", method = RequestMethod.GET)
	public String editForm(@PathVariable("slug") String slug, ModelMap model, RedirectAttributes redirectAtts) {
		Railway railway = service.findBySlug(slug);
		if (railway == null) {
			RAILWAY_NOT_FOUND_MSG.appendToRedirect(redirectAtts);
			return "redirect:/admin/railways";
		}
		model.addAttribute(RailwayForm.newForm(railway));
		return "railway/edit"; 
	}

	/**
	 * It saves the {@code Railway} changes using the posted form values.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code PUT /admin/railways}
	 * </blockquote></pre>
	 * </p>
	 *
	 * @param railwayForm the form for the {@code Railway} to be updated
	 * @param bindingResult the validation results
	 * @param model the model
	 * @param redirectAtts the redirect attributes
	 * @return the view name
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public String save(@Valid @ModelAttribute RailwayForm railwayForm,
		BindingResult bindingResult,
		ModelMap model,
		RedirectAttributes redirectAtts) {
	
		if (bindingResult.hasErrors()) {
			LogUtils.logValidationErrors(log, bindingResult);
			model.addAttribute(railwayForm);
			return "railway/edit";
		}
	
		Railway railway = railwayForm.getRailway();
		try {
			service.save(railway);
			RAILWAY_SAVED_MSG.appendToRedirect(redirectAtts);
			return "redirect:/admin/railways";
		}
		catch (DataAccessException dae) {
			LogUtils.logException(log, dae);
			model.addAttribute(railwayForm);
			RAILWAY_DB_ERROR_MSG.appendToModel(model);
			return "railway/edit";
		}
	}

	/**
	 * It deletes a {@code Railway}.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code DELETE /admin/railways/:id}
	 * </blockquote></pre>	 
	 * </p>
	 *
	 * @param railway the {@code Railway}
	 * @param redirectAtts the redirect attributes
	 * @return the view name
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) 
	public String delete(@ModelAttribute() Railway railway, RedirectAttributes redirectAtts) {
		service.remove(railway);

		RAILWAY_DELETED_MSG.appendToRedirect(redirectAtts);
		return "redirect:/admin/railways";
	}
	
	/**
	 * This action is uploading a new picture for a {@code Railway}.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code POST /admin/railways/upload}
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
			
			RAILWAY_INVALID_UPLOAD_MSG.appendToRedirect(redirectAtts);
			redirectAtts.addAttribute("slug", uploadForm.getSlug());
			return "redirect:/admin/railways/{slug}";
		}
		
		if (!uploadForm.isEmpty()) {
			UploadRequest uploadRequest = uploadForm.buildUploadRequest();
			imgService.saveImageWithThumb(uploadRequest, 50);
			RAILWAY_LOGO_UPLOADED_MSG.appendToRedirect(redirectAtts);
		}
			
		redirectAtts.addAttribute("slug", uploadForm.getSlug());
		return "redirect:/admin/railways/{slug}";
	}
	
	/**
	 * This action is deleting the picture from the {@code Railway}.
	 *
	 * <p>
	 * <pre><blockquote>
	 * {@code DELETE /admin/railways/upload}
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
	
		RAILWAY_LOGO_DELETED_MSG.appendToRedirect(redirectAtts);
		redirectAtts.addAttribute("slug", uploadForm.getSlug());
		return "redirect:/admin/railways/{slug}";
	}
}