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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

import com.trenako.entities.Comment;
import com.trenako.entities.RollingStock;
import com.trenako.mapping.WeakDbRef;
import com.trenako.services.RollingStocksService;
import com.trenako.values.DeliveryDate;
import com.trenako.web.editors.WeakDbRefPropertyEditor;
import com.trenako.web.editors.DeliveryDatePropertyEditor;
import com.trenako.web.errors.NotFoundException;
import com.trenako.web.images.MultipartFileValidator;
import com.trenako.web.images.UploadRequest;
import com.trenako.web.images.WebImageService;
import com.trenako.web.security.UserContext;

import static com.trenako.web.controllers.ControllerMessage.*;

/**
 * It represents the rolling stocks management controller.
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/rollingstocks")
public class RollingStocksController {

	private UserContext secContext;
	
	private MultipartFileValidator fileValidator;
	
	private final RollingStocksService service;
	private final WebImageService imgService;
	
	final static ControllerMessage ROLLING_STOCK_CREATED_MSG = success("rollingStock.created.message");
	final static ControllerMessage ROLLING_STOCK_SAVED_MSG = success("rollingStock.saved.message");
	final static ControllerMessage ROLLING_STOCK_DELETED_MSG = success("rollingStock.deleted.message");
	
	/**
	 * Creates a new {@code RollingStocksController}.
	 * @param service 
	 * @param imgService the upload files service
	 */
	@Autowired
	public RollingStocksController(RollingStocksService service, 
			WebImageService imgService) {
		this.service = service;
		this.imgService = imgService;
	}
	
	@Autowired(required = false) 
	public void setMultipartFileValidator(MultipartFileValidator validator) {
		fileValidator = validator;
	}
	
	@Autowired(required = false) 
	public void setUserContext(UserContext secContext) {
		this.secContext = secContext;
	}
	
	// registers the custom property editors
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(WeakDbRef.class, new WeakDbRefPropertyEditor(true));
		binder.registerCustomEditor(DeliveryDate.class, new DeliveryDatePropertyEditor(true));
	}
	
	@RequestMapping(value = "/{slug}", method = RequestMethod.GET)
	public String show(@PathVariable("slug") String slug, ModelMap model) {
		RollingStock rs = service.findBySlug(slug);
		if (rs == null) {
			throw new NotFoundException();
		}
		
		// init comments form only for authenticated users
		if (secContext != null && secContext.getCurrentUser() != null) {
			Comment newComment = new Comment(secContext.getCurrentUser().getAccount(), 
					rs, 
					""); 
			model.addAttribute("newComment", newComment);		
		}
		
		model.addAttribute("rollingStock", rs);
		return "rollingstock/show";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView createNew() {
		ModelAndView mav = new ModelAndView("rollingstock/new");
		mav.addObject(new RollingStock());
		
		// fills the data for the drop down lists.
		fillDropdownLists(mav);
		
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)	
	public String create(@ModelAttribute @Valid RollingStock rs, 
			BindingResult bindingResult,
			@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAtts) {
		
		// validate the uploaded file
		if (!file.isEmpty() && fileValidator != null) {
			fileValidator.validate(file, bindingResult);
		}
		
		if (bindingResult.hasErrors()) {
			redirectAtts.addAttribute(rs);
			appendListValues(redirectAtts);
			return "rollingstock/new";
		}
		
		service.save(rs);
		if (!file.isEmpty()) {
			imgService.saveImageWithThumb(UploadRequest.create(rs, file), 100);
		}

		redirectAtts.addFlashAttribute("message", ROLLING_STOCK_CREATED_MSG);
		redirectAtts.addAttribute("slug", rs.getSlug());
		return "redirect:/rollingstocks/{slug}";
	}

	@RequestMapping(value = "/{slug}/edit", method = RequestMethod.GET)
	public ModelAndView editForm(@PathVariable("slug") String slug) {
		RollingStock rs = service.findBySlug(slug);
		if (rs == null) {
			throw new NotFoundException();
		}
		
		ModelAndView mav = new ModelAndView("rollingstock/edit");
		mav.addObject("rollingStock", rs);
		
		// fills the data for the drop down lists.
		fillDropdownLists(mav);
		
		return mav;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String save(@ModelAttribute @Valid RollingStock rollingStock, 
			BindingResult bindingResult,
			RedirectAttributes redirectAtts) {
		
		if (bindingResult.hasErrors()) {
			redirectAtts.addAttribute(rollingStock);
			appendListValues(redirectAtts);
			return "rollingstock/edit";
		}
		
		service.save(rollingStock);		
		redirectAtts.addFlashAttribute("message", ROLLING_STOCK_SAVED_MSG);
		redirectAtts.addAttribute("slug", rollingStock.getSlug());
		return "redirect:/rollingstocks/{slug}";
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public String delete(@ModelAttribute RollingStock rs, 
			RedirectAttributes redirectAtts) {
		
		service.remove(rs);
		redirectAtts.addFlashAttribute("message", ROLLING_STOCK_DELETED_MSG);
		return "redirect:/rs";
	}
	
	private void fillDropdownLists(ModelAndView mav) {
		mav.addObject("brands", service.brands());
		mav.addObject("scales", service.scales());
		mav.addObject("railways", service.railways());
		mav.addObject("categories", service.categories());
		mav.addObject("eras", service.eras());
		mav.addObject("powerMethods", service.powerMethods());
	}
	
	private void appendListValues(Model model) {
		model.addAttribute("brands", service.brands());
		model.addAttribute("scales", service.scales());
		model.addAttribute("railways", service.railways());
		model.addAttribute("categories", service.categories());
		model.addAttribute("eras", service.eras());
		model.addAttribute("powerMethods", service.powerMethods());
	}
}