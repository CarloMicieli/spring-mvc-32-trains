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

import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
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

import com.trenako.entities.Account;
import com.trenako.entities.RollingStock;
import com.trenako.mapping.WeakDbRef;
import com.trenako.services.FormValuesService;
import com.trenako.services.RollingStocksService;
import com.trenako.services.view.RollingStockView;
import com.trenako.values.DeliveryDate;
import com.trenako.web.controllers.form.CommentForm;
import com.trenako.web.controllers.form.RollingStockForm;
import com.trenako.web.editors.WeakDbRefPropertyEditor;
import com.trenako.web.editors.DeliveryDatePropertyEditor;
import com.trenako.web.errors.NotFoundException;
import com.trenako.web.images.UploadRequest;
import com.trenako.web.images.WebImageService;
import com.trenako.web.security.UserContext;

import static com.trenako.web.controllers.form.RollingStockForm.*;
import static com.trenako.web.controllers.ControllerMessage.*;

/**
 * It represents the rolling stocks management controller.
 * @author Carlo Micieli
 *
 */
@Controller
@RequestMapping("/rollingstocks")
public class RollingStocksController {

	private final static Logger log = LoggerFactory.getLogger("com.trenako.web");
	
	private UserContext secContext;
	
	private final RollingStocksService service;
	private final FormValuesService valuesService;
	private final WebImageService imgService;
	
	final static ControllerMessage ROLLING_STOCK_CREATED_MSG = success("rollingStock.created.message");
	final static ControllerMessage ROLLING_STOCK_SAVED_MSG = success("rollingStock.saved.message");
	final static ControllerMessage ROLLING_STOCK_DELETED_MSG = success("rollingStock.deleted.message");
	final static ControllerMessage ROLLING_STOCK_DUPLICATED_VALUE_MSG = error("rollingStock.duplicated.value.message");
	final static ControllerMessage ROLLING_STOCK_DATABASE_ERROR_MSG = error("rollingStock.database.error.message");
	
	/**
	 * Creates a new {@code RollingStocksController}.
	 * @param service the service to manage rolling stocks
	 * @param valuesService the service to fill the form drop down lists
	 * @param imgService the service for the upload files
	 */
	@Autowired
	public RollingStocksController(RollingStocksService service, 
			FormValuesService valuesService,
			WebImageService imgService) {
		this.service = service;
		this.valuesService = valuesService;
		this.imgService = imgService;
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
		
		Account loggedUser = UserContext.authenticatedUser(secContext);
		RollingStockView rsView = service.findRollingStockView(slug, loggedUser);
		if (rsView == null) {
			throw new NotFoundException();
		}
		
		CommentForm form = CommentForm.newForm(rsView.getRs(), secContext);
		if (form != null) {
			model.addAttribute("commentForm", form);
		}
		
		model.addAttribute("result", rsView);
		return "rollingstock/show";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createNew(ModelMap model) {
		model.addAttribute(newForm(new RollingStock(), valuesService));
		return "rollingstock/new";
	}

	@RequestMapping(method = RequestMethod.POST)	
	public String create(@ModelAttribute @Valid RollingStockForm form, 
			BindingResult bindingResult,
			ModelMap model,
			RedirectAttributes redirectAtts) {
		
		MultipartFile file = form.getFile();
		RollingStock rs = form.buildRollingStock(valuesService, secContext, new Date());
		
		if (bindingResult.hasErrors()) {
			model.addAttribute(rejectForm(form, valuesService));
			return "rollingstock/new";
		}
		
		try {
			service.createNew(rs);
			if (!file.isEmpty()) {
				imgService.saveImageWithThumb(UploadRequest.create(rs, file), 100);
			}
	
			redirectAtts.addFlashAttribute("message", ROLLING_STOCK_CREATED_MSG);
			redirectAtts.addAttribute("slug", rs.getSlug());
			return "redirect:/rollingstocks/{slug}";
		}
		catch (DuplicateKeyException duplEx) {
			log.error(duplEx.toString());
			model.addAttribute(newForm(rs, valuesService));
			model.addAttribute("message", ROLLING_STOCK_DUPLICATED_VALUE_MSG);
			return "rollingstock/new";
		}
		catch (DataAccessException dataEx) {
			log.error(dataEx.toString());
			model.addAttribute(newForm(rs, valuesService));
			model.addAttribute("message", ROLLING_STOCK_DATABASE_ERROR_MSG);
			return "rollingstock/new";
		}
	}

	@RequestMapping(value = "/{slug}/edit", method = RequestMethod.GET)
	public String editForm(@PathVariable("slug") String slug, ModelMap model) {
		RollingStock rs = service.findBySlug(slug);
		if (rs == null) {
			throw new NotFoundException();
		}
		
		model.addAttribute(newForm(rs, valuesService));
		return "rollingstock/edit";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String save(@ModelAttribute @Valid RollingStockForm form, 
			BindingResult bindingResult,
			ModelMap model,
			RedirectAttributes redirectAtts) {
		
		RollingStock rs = form.buildRollingStock(valuesService, secContext, new Date());
		
		if (bindingResult.hasErrors()) {
			model.addAttribute(newForm(rs, valuesService));
			return "rollingstock/edit";
		}
			
		try {
			service.save(rs);
	
			redirectAtts.addFlashAttribute("message", ROLLING_STOCK_SAVED_MSG);
			redirectAtts.addAttribute("slug", rs.getSlug());
			return "redirect:/rollingstocks/{slug}";
		}
		catch (DataAccessException dataEx) {
			log.error(dataEx.toString());
			model.addAttribute(newForm(rs, valuesService));
			model.addAttribute("message", ROLLING_STOCK_DATABASE_ERROR_MSG);
			return "rollingstock/edit";
		}
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public String delete(@ModelAttribute RollingStock rs, 
			RedirectAttributes redirectAtts) {
		
		service.remove(rs);
		redirectAtts.addFlashAttribute("message", ROLLING_STOCK_DELETED_MSG);
		return "redirect:/rs";
	}
}