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

import static com.trenako.web.controllers.ControllerMessage.error;
import static com.trenako.web.controllers.ControllerMessage.success;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trenako.AppGlobals;
import com.trenako.entities.Railway;
import com.trenako.services.RailwaysService;
import com.trenako.web.controllers.ControllerMessage;
import com.trenako.web.images.ImageRequest;
import com.trenako.web.images.MultipartFileValidator;
import com.trenako.web.images.UploadRequest;
import com.trenako.web.images.WebImageService;

/**
 * 
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/admin/railways")
public class AdminRailwaysController {
	private final RailwaysService service;
	private final WebImageService imgService;
	
	private MultipartFileValidator fileValidator;
	
	final static ControllerMessage RAILWAY_CREATED_MSG = success("railway.created.message");
	final static ControllerMessage RAILWAY_SAVED_MSG = success("railway.saved.message");
	final static ControllerMessage RAILWAY_DELETED_MSG = success("railway.deleted.message");
	final static ControllerMessage RAILWAY_LOGO_UPLOADED_MSG = success("railway.logo.uploaded.message");
	final static ControllerMessage RAILWAY_INVALID_UPLOAD_MSG = error("railway.invalid.file.message");
	final static ControllerMessage RAILWAY_LOGO_DELETED_MSG = success("railway.logo.deleted.message");
	
	@Autowired
	public AdminRailwaysController(RailwaysService service, WebImageService imgService) {
		this.service = service;
		this.imgService = imgService;
	}
	
	@Autowired(required = false) 
	public void setMultipartFileValidator(MultipartFileValidator validator) {
		fileValidator = validator;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);

	    // convert empty String to null
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@ModelAttribute("countries")
	public Map<String, String> countries() {
		return AppGlobals.countries();
	}

	/**
	 * This actions shows all the {@code Railway}s.
	 * <p>
	 * Maps the request to {@code GET /admin/railways}.
	 * </p>
	 * @param paging 
	 *
	 * @param pageable the paging information
	 * @return the model and view for the {@code Railway}s list
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(Pageable paging) {
		return new ModelAndView("railway/list", "railways", service.findAll(paging));
	}

	/**
	 * This actions shows all the {@code Railway}s.
	 * <p>
	 * Maps the request to {@code GET /admin/railways/:slug}.
	 * </p>
	 *
	 * @param slug the {@code Railway} slug
	 * @return the model and view for the {@code Railway}
	 */
	@RequestMapping(value = "/{slug}", method = RequestMethod.GET)
	public ModelAndView show(@PathVariable("slug") String slug) {
		return new ModelAndView("railway/show", 
				"railway",
				service.findBySlug(slug));
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
	 * @param result the validation results
	 * @param file the logo image
	 * @param redirectAtts the redirect attributes
	 * @throws IOException 
	 *
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute Railway railway, 
			BindingResult result,
			@RequestParam("file") MultipartFile file, 
			RedirectAttributes redirectAtts) throws IOException {
		
		// validate the uploaded file
		if (fileValidator != null) {
			fileValidator.validate(file, result);
		}

		if (result.hasErrors()) {
			redirectAtts.addAttribute(railway);		
			return "railway/new";	
		}

		// save brand
		service.save(railway);
		if (!file.isEmpty()) {
			imgService.saveImageWithThumb(UploadRequest.create(railway, file), 50);
		}

		redirectAtts.addFlashAttribute("message", RAILWAY_CREATED_MSG);
		return "redirect:/admin/railways";
	}
	
	/**
	 * This action renders the form to edit a {@code Railway}.
	 *
	 * <p>
	 * Maps the request to {@code GET /admin/railways/:slug/edit}.
	 * </p>
	 *
	 * @param slug the {@code Railway} slug
	 *
	 */
	@RequestMapping(value = "/{slug}/edit", method = RequestMethod.GET)
	public ModelAndView editForm(@PathVariable("slug") String slug) {
		return new ModelAndView("railway/edit", "railway", service.findBySlug(slug));
	}

	/**
	 * This action saves a new {@code Railway}.
	 *
	 * <p>
	 * Maps the request to {@code POST /admin/railways}.
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
		RedirectAttributes redirectAtts) {
	
		if (result.hasErrors()) {
			redirectAtts.addAttribute("railway", railway);
			return "railway/edit";
		}
	
		try {
			service.save(railway);
			redirectAtts.addFlashAttribute("message", RAILWAY_SAVED_MSG);
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
	 * @param railway the {@code Railway}
	 * @param redirectAtts the redirect attributes
	 * @return the view name
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) 
	public String delete(@ModelAttribute() Railway railway, RedirectAttributes redirectAtts) {
		service.remove(railway);
		
		redirectAtts.addFlashAttribute("message", RAILWAY_DELETED_MSG);
		return "redirect:/admin/railways";
	}
	
	@RequestMapping(value = "/{slug}/upload", method = RequestMethod.POST)
	public String uploadImage(@ModelAttribute Railway railway, 
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
			redirectAtts.addAttribute("slug", railway.getSlug());
			redirectAtts.addFlashAttribute("message", RAILWAY_INVALID_UPLOAD_MSG);
			return "redirect:/admin/railways/{slug}";			
		}
		
		imgService.saveImageWithThumb(UploadRequest.create(railway, file), 50);
		
		redirectAtts.addFlashAttribute("message", RAILWAY_LOGO_UPLOADED_MSG);
		redirectAtts.addAttribute("slug", railway.getSlug());
		return "redirect:/admin/railways/{slug}";
	}
	
	@RequestMapping(value = "/{slug}/upload", method = RequestMethod.DELETE)
	public String deleteImage(@ModelAttribute Railway railway, RedirectAttributes redirectAtts) {
		imgService.deleteImage(new ImageRequest("railway", railway.getSlug()));
		
		redirectAtts.addAttribute("slug", railway.getSlug());
		redirectAtts.addFlashAttribute("message", RAILWAY_LOGO_DELETED_MSG);
		return "redirect:/admin/railways/{slug}";
	}
}
